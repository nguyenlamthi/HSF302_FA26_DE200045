package fu.de200045.dao;

import fu.de200045.pojo.Department;
import fu.de200045.pojo.Employee;
import fu.de200045.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class EmployeeDAO {
    private final EntityManagerFactory emf = JPAUtil.getEMF();

    public void saveEmployee(Employee emp, Long departmentId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Department dept = em.find(Department.class, departmentId);
            if (dept == null) {
                throw new IllegalArgumentException(
                        "Không tìm thấy Department với id = " + departmentId);
            }
            dept.addEmployee(emp);
            em.persist(emp);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee emp = em.find(Employee.class, id);
            return emp;
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee merged = em.merge(e);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee emp = em.find(Employee.class, id);
            if (emp == null) {
                throw new IllegalArgumentException(
                        "Không tìm thấy Employee với id = " + id);
            }
            Department dept = emp.getDepartment();
            if (dept != null) {
                dept.removeEmployee(emp);
            }
            em.remove(emp);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
