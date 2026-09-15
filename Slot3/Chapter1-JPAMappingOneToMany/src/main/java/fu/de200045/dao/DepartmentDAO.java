package fu.de200045.dao;

import fu.de200045.pojo.Department;
import fu.de200045.pojo.Employee;
import fu.de200045.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DepartmentDAO {
    private final EntityManagerFactory emf = JPAUtil.getEMF();

    public void saveDepartment(Department dept){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(dept); // cascade=ALL sẽ tự persist luôn các Employee đã add
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Department dept = em.find(Department.class, id);
            return dept;
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d ORDER BY d.id", Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Department update(Department dept) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Department merged = em.merge(dept);
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
            Department dept = em.find(Department.class, id);
            if (dept == null) {
                throw new IllegalArgumentException(
                        "Không tìm thấy Department với id = " + id);
            }
            em.remove(dept);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Department findByIdWithEmployees(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.id = :id", Department.class)
                    .setParameter("id", id).getResultList().getFirst();

        } finally {
            em.close();
        }
    }
}
