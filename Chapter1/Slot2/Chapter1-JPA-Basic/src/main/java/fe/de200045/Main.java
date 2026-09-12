package fe.de200045;

import fe.de200045.dao.EmployeeDAO;
import fe.de200045.entity.Employee;
import fe.de200045.entity.Gender;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("===== CREATE =====");
        // Truoc dong nay: e dang o trang thai NEW/TRANSIENT
        // (chi la 1 object Java binh thuong, chua duoc EntityManager nao biet toi)
        Employee emp = new Employee("Nguyen Van B", "b@example.com",
                new BigDecimal("20000000"), Gender.FEMALE, LocalDate.of(2026, 2, 28));
        dao.save(emp);
        // Ben trong save(): ngay sau em.persist(e) (con trong transaction) -> e la MANAGED
        // Sau khi save() return (EntityManager da dong) -> e la DETACHED
        System.out.println("Da tao: " + emp);

        System.out.println("\n===== READ =====");
        Employee found = dao.findById(emp.getId());
        // found tra ve tu findById() -> EntityManager ben trong da dong -> found la DETACHED
        System.out.println("Tim thay: " + found);

        System.out.println("\n===== UPDATE =====");
        found.setSalary(new BigDecimal("25000000"));
        found = dao.update(found);
        // Ben trong update(): merge(found) tra ve 1 object khac (merged) - merged la MANAGED
        // trong luc con trong transaction. found (bien cu truyen vao) van la DETACHED, khong doi.
        // Sau khi update() return (EntityManager da dong) -> merged (gio la "found") cung thanh DETACHED
        Employee afterUpdate = dao.findById(emp.getId());
        // afterUpdate la DETACHED
        System.out.println("Doc lai tu DB: " + afterUpdate);

        System.out.println("\n===== DELETE =====");
        dao.delete(emp.getId());
        // Ben trong delete(): em.find() -> entity la MANAGED (cung EntityManager, chua dong)
        // em.remove(entity) -> entity chuyen sang REMOVED (van trong transaction)
        // Sau commit() -> dong DB bi xoa that su. Sau close() -> transaction/persistence context ket thuc
        Employee check = dao.findById(emp.getId());
        // Khong con dong nao trong DB co id nay -> findById tra ve null
        System.out.println("Doc lai sau khi xoa: " + check);
    }
}