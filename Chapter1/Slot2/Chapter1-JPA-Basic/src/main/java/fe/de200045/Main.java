package fe.de200045;

import fe.de200045.dao.EmployeeDAO;
import fe.de200045.entity.Employee;
import fe.de200045.entity.Gender;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("===== CREATE =====");
        Employee emp = new Employee("Nguyen Van B", "b@example.com",
                new BigDecimal("20000000"), Gender.FEMALE, LocalDate.of(2026, 2, 28));
        dao.save(emp);
        System.out.println("Da tao: " + emp);

        System.out.println("\n===== READ =====");
        Employee found = dao.findById(emp.getId());
        System.out.println("Tim thay: " + found);

        System.out.println("\n===== UPDATE =====");
        found.setSalary(new BigDecimal("25000000"));
        found = dao.update(found);
        Employee afterUpdate = dao.findById(emp.getId());
        System.out.println("Doc lai tu DB: " + afterUpdate);

        System.out.println("\n===== DELETE =====");
        dao.delete(emp.getId());
        Employee check = dao.findById(emp.getId());
        System.out.println("Doc lai sau khi xoa: " + check);
    }
}