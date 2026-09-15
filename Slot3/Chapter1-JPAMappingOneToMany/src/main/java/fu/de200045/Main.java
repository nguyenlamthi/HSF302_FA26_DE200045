package fu.de200045;

import fu.de200045.dao.DepartmentDAO;
import fu.de200045.pojo.Department;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Department dept = new Department("Marketing", "Ha Noi");
//        Employee e1 = new Employee("A", "a@company.com",
//                new BigDecimal("1000"), Gender.OTHER, LocalDate.now());
//        Employee e2 = new Employee("B", "b@company.com",
//                new BigDecimal("2000"), Gender.MALE, LocalDate.now());
//        Employee e3 = new Employee("C", "c@company.com",
//                new BigDecimal("3000"), Gender.FEMALE, LocalDate.now());
//        dept.addEmployee(e1);
//        dept.addEmployee(e2);
//        dept.addEmployee(e3);
//
//        DepartmentDAO deptDAO = new DepartmentDAO();
//        deptDAO.save(dept);
//        System.out.println("Đã thêm: "+ dept.getName());

        Employee e4 = new Employee("D", "d@company.com",
                new BigDecimal("4000"), Gender.FEMALE, LocalDate.now());
        dept.addEmployee(e4);
    }
}