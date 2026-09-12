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

        Employee emp = new Employee("Nguyen Van B", "b@example.com",
                new BigDecimal("20000000"), Gender.FEMALE, LocalDate.of(2026, 2, 28));
//        dao.save(emp);
//        System.out.println("Da tao: " + emp);

//        Employee found = dao.findById(1L);
//        System.out.println(found);

//        List<Employee> result = dao.findAll();
//        for (Employee e : result) {
//            System.out.println(e);
//        }

//        Employee found = dao.findByEmail("b@example.com");
//        System.out.println("Tim email da ton tai: " + found);

//        Employee notFound = dao.findByEmail("khongtontai@example.com");
//        System.out.println("Tim email khong ton tai: " + notFound);

//        List<Employee> res = dao.findActiveAndSalaryGreaterThan(new BigDecimal("50000000"));
//        System.out.println("So nhan vien active, luong > 10tr: " + res.size());

//        Employee e = dao.findById(1L);
//        e.setGender(Gender.OTHER);
//        e = dao.update(e);
//        System.out.println(e);

        dao.delete(5L);
        Employee check = dao.findById(5L);
        System.out.println(check);
    }
}