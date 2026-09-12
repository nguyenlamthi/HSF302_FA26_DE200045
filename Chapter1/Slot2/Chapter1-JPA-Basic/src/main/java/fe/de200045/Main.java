package fe.de200045;

import fe.de200045.dao.EmployeeDAO;
import fe.de200045.entity.Employee;
import fe.de200045.entity.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // ===== CREATE =====
        Employee emp = new Employee("Nguyen Van B", "b@example.com",
                new BigDecimal("20000000"), Gender.FEMALE, LocalDate.of(2026, 2, 28));
//        dao.save(emp);
//        System.out.println("Da tao: " + emp);

        // ===== READ =====
        Employee found = dao.findById(1L);
        System.out.println(found);
//        List<Employee> result = dao.findAll();
//        for (Employee e : result) {
//            System.out.println(e);
//        }
    }
}