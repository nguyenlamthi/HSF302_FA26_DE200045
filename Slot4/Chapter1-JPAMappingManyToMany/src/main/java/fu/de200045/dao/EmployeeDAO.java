package fu.de200045.dao;

import fu.de200045.dto.ProjectStat;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Project;
import fu.de200045.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {
    private final EntityManagerFactory emf = JPAUtil.getEMF();

    /**
     * TODO 5.6 - Tìm cả 2 entity trong CÙNG 1 transaction rồi gọi assignToProject().
     *
     * Bắt buộc phải find cả 2 entity trong cùng 1 EntityManager/transaction vì:
     * - assignToProject() thao tác trên object đang được quản lý (managed)
     *   bởi persistence context, để Hibernate track thay đổi và tự flush
     *   xuống bảng employee_project khi commit.
     * - Nếu find Employee và Project ở 2 EntityManager khác nhau, chúng sẽ
     *   ở trạng thái detached đối với nhau, thay đổi trên collection có thể
     *   không được ghi nhận đúng khi merge lại.
     */
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee == null) {
                throw new IllegalArgumentException("Không tìm thấy Employee id = " + employeeId);
            }
            if (project == null) {
                throw new IllegalArgumentException("Không tìm thấy Project id = " + projectId);
            }

            employee.assignToProject(project);
            // Không cần gọi em.persist() hay em.merge() vì employee/project
            // đang là managed entity — Hibernate tự dirty-check và flush khi commit.

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<ProjectStat> countActiveEmployeesAndTotalSalaryByProject() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT new fu.de200045.dto.ProjectStat(p.projectName, COUNT(e), SUM(e.salary)) " +
                    "FROM Project p JOIN p.employees e " +
                    "WHERE e.active = true " +
                    "GROUP BY p.projectName";

            return em.createQuery(jpql, ProjectStat.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.9 - Gỡ employee khỏi project. Tìm cả 2 entity trong cùng
     * transaction (giống assignEmployeeToProject) để Hibernate track thay đổi
     * trên collection và tự flush DELETE xuống bảng employee_project khi commit.
     *
     * Không dùng cascade = ALL (hay CascadeType.REMOVE) trên quan hệ N-N này:
     * vì N-N nghĩa là 1 Project có thể có nhiều Employee và ngược lại, nếu
     * cascade REMOVE thì việc xóa/gỡ quan hệ ở 1 phía có thể vô tình xóa luôn
     * entity phía bên kia — ví dụ nếu lỡ cascade REMOVE trên Project.employees,
     * xóa 1 Project có thể kéo theo xóa cả Employee (dù Employee đó vẫn đang
     * tham gia project khác) — đây là hành vi sai nghiêm trọng, N-N chỉ nên
     * xóa DÒNG trong bảng trung gian, không được xóa entity gốc.
     */
    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee == null) {
                throw new IllegalArgumentException("Không tìm thấy Employee id = " + employeeId);
            }
            if (project == null) {
                throw new IllegalArgumentException("Không tìm thấy Project id = " + projectId);
            }

            employee.unassignFromProject(project);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.10 - Tìm các Employee active đang tham gia nhiều hơn 1 project.
     * SIZE(e.projects) đếm số phần tử trong collection projects của mỗi
     * employee ngay trong câu SQL (Hibernate dịch thành subquery COUNT),
     * không cần load hết projects ra rồi lọc bằng Java.
     */
    public List<Employee> findActiveEmployeesInMultipleProjects() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e " +
                    "WHERE e.active = true AND SIZE(e.projects) > 1";

            return em.createQuery(jpql, Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.11 - Deactivate employee (set active = false), KHÔNG xóa quan hệ
     * trong bảng employee_project.
     *
     * Câu hỏi: nhân viên nghỉ việc có nên tự động bị gỡ khỏi tất cả project không?
     * → KHÔNG NÊN gỡ tự động. Lý do:
     *   1. Dữ liệu tham gia project là LỊCH SỬ (nhân viên X đã từng làm project Y)
     *      — nghỉ việc không làm thay đổi sự thật lịch sử đó, chỉ thay đổi
     *      trạng thái hiện tại (còn làm hay không).
     *   2. Nếu xóa quan hệ trong employee_project khi deactivate, sẽ mất luôn
     *      dữ liệu để tra cứu sau này (ví dụ: báo cáo "ai đã từng làm project
     *      nào", tính công nợ/lương thưởng theo project cũ, audit trail...).
     *   3. Tách biệt 2 khái niệm: "active" (đang làm việc hay không) và
     *      "đang được PHÂN CÔNG vào project" là 2 chiều dữ liệu độc lập —
     *      gộp chung logic của chúng (tự động gỡ khi deactivate) làm code
     *      khó kiểm soát và sai lệch ý nghĩa nghiệp vụ.
     *   4. Nếu thực sự cần gỡ khỏi project đang active (ví dụ dự án cần thay
     *      người ngay), nên là một hành động TÁCH RIÊNG, tường minh — gọi
     *      unassignEmployeeFromProject() (TODO 5.9) — không phải side-effect
     *      ngầm của deactivateEmployee().
     *
     * => Đây cũng chính là lý do quan hệ N-N này KHÔNG dùng
     *    cascade = CascadeType.ALL/REMOVE: cascade REMOVE sẽ tự động xóa quan
     *    hệ (hoặc tệ hơn, xóa nhầm entity phía bên kia) mỗi khi 1 phía bị xóa/
     *    thay đổi, trong khi nghiệp vụ thực tế cần giữ nguyên lịch sử tham gia
     *    kể cả khi employee đã nghỉ việc.
     */
    public void deactivateEmployee(Long employeeId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            if (employee == null) {
                throw new IllegalArgumentException("Không tìm thấy Employee id = " + employeeId);
            }

            employee.setActive(false);
            // Không gọi unassignFromProject() ở đây — projects vẫn giữ nguyên.

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
