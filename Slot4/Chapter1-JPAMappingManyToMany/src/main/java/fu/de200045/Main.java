package fu.de200045;

import fu.de200045.dao.EmployeeDAO;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;
import fu.de200045.pojo.Project;
import fu.de200045.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = JPAUtil.getEMF();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        Employee nv1 = new Employee("Nguyen Van A", new BigDecimal("15000000"),
                LocalDate.of(2022, 3, 1), "nv1@company.com", Gender.MALE);
        Employee nv2 = new Employee("Tran Thi B", new BigDecimal("18000000"),
                LocalDate.of(2021, 6, 15), "nv2@company.com", Gender.FEMALE);
        Employee nv3 = new Employee("Le Van C", new BigDecimal("12000000"),
                LocalDate.of(2023, 1, 10), "nv3@company.com", Gender.MALE);

        Project projectA = new Project("PRJ-A", "Website Redesign",
                new BigDecimal("500000000"), LocalDate.of(2024, 1, 1), null);
        Project projectB = new Project("PRJ-B", "Mobile App",
                new BigDecimal("800000000"), LocalDate.of(2024, 2, 1), null);

        // ===== 2. Lưu Employee/Project xuống DB để có id =====
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(projectA);
            em.persist(projectB);
            em.persist(nv1);
            em.persist(nv2);
            em.persist(nv3);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        employeeDAO.assignEmployeeToProject(nv1.getId(), projectA.getId());
        employeeDAO.assignEmployeeToProject(nv1.getId(), projectB.getId());
        employeeDAO.assignEmployeeToProject(nv2.getId(), projectB.getId());
        employeeDAO.assignEmployeeToProject(nv3.getId(), projectA.getId());

        EntityManager em2 = emf.createEntityManager();
        try {
            for (Employee e : em2.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList()) {
                System.out.println(e.getFullName() + " (" + e.getEmail() + ") tham gia project:");
                for (Project p : e.getProjects()) {
                    System.out.println("   - " + p.getProjectCode() + ": " + p.getProjectName());
                }
            }
        } finally {
            em2.close();
        }

        JPAUtil.close();
    }
}