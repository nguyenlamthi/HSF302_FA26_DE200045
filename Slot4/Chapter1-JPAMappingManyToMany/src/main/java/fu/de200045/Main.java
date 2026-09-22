package fu.de200045;

import fu.de200045.dao.EmployeeDAO;
import fu.de200045.dto.ProjectStat;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;
import fu.de200045.pojo.Project;
import fu.de200045.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = JPAUtil.getEMF();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        Employee nv1 = new Employee("Nguyen Van A", new BigDecimal("15000000"),
                LocalDate.of(2022, 3, 1), "nv1@company.com", Gender.MALE);
        Project projectA = new Project("PRJ-A", "Website Redesign",
                new BigDecimal("500000000"), LocalDate.of(2024, 1, 1), null);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(projectA);
            em.persist(nv1);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        employeeDAO.assignEmployeeToProject(nv1.getId(), projectA.getId());

        // ===== 2. Kiểm tra TRƯỚC khi gỡ =====
        printState(emf, nv1.getId(), projectA.getId(), "TRƯỚC khi gỡ");

        // ===== 3. Gỡ NV1 khỏi Project A =====
        employeeDAO.unassignEmployeeFromProject(nv1.getId(), projectA.getId());

        // ===== 4. Kiểm tra SAU khi gỡ: entity gốc còn nguyên, chỉ mất dòng liên kết =====
        printState(emf, nv1.getId(), projectA.getId(), "SAU khi gỡ");

        JPAUtil.close();
    }

    private static void printState(EntityManagerFactory emf, Long employeeId, Long projectId, String label) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            System.out.println("\n=== " + label + " ===");
            System.out.println("Employee tồn tại: " + (employee != null));
            System.out.println("Project tồn tại: " + (project != null));
            System.out.println("Employee đang tham gia " + employee.getProjects().size()
                    + " project: " + employee.getProjects());
            System.out.println("Project có " + project.getEmployees().size()
                    + " employee: " + project.getEmployees());
        } finally {
            em.close();
        }
    }
}