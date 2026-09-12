package fe.de200045;

import fe.de200045.dao.EmployeeDAO;
import fe.de200045.entity.Employee;
import fe.de200045.entity.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        Employee emp = new Employee("Nguyen Van A", "a@example.com",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2026, 1, 15));
        dao.save(emp);
        System.out.println("Da tao: " + emp);
    }
}