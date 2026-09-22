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
}
