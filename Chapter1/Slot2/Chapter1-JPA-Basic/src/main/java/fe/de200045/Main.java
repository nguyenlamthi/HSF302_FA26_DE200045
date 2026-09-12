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

        System.out.println("===== EMPLOYEE THU 1 =====");
        Employee e1 = new Employee("Nguyen Van A", "trung@example.com",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2023, 1, 15));
        dao.save(e1);
        System.out.println("Tao thanh cong: " + e1);

        System.out.println("\n===== EMPLOYEE THU 2 - TRUNG EMAIL =====");
        Employee e2 = new Employee("Tran Thi B", "trung@example.com",
                new BigDecimal("18000000"), Gender.FEMALE, LocalDate.of(2024, 3, 1));
        try {
            dao.save(e2);
            System.out.println("Tao thanh cong: " + e2);
        } catch (RuntimeException ex) {
            System.out.println("Loi: Email da ton tai trong he thong, khong the tao nhan vien moi!");
        }
    }
}