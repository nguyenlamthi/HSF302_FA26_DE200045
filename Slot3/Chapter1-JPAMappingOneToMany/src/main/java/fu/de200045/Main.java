package fu.de200045;

import fu.de200045.dao.DepartmentDAO;
import fu.de200045.pojo.Department;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;
import fu.de200045.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        Department it = new Department("Marketing", "Ha Noi");

        Employee e1 = new Employee("A", "a@company.com",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("B", "b@company.com",
                new BigDecimal("20000000"), Gender.FEMALE, LocalDate.of(2022, 1, 15));
        Employee e3 = new Employee("C", "c@company.com",
                new BigDecimal("25000000"), Gender.OTHER, LocalDate.of(2022, 1, 20));

        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

        departmentDAO.saveDepartment(it);

        System.out.println("Da luu Department, id = " + it.getId());

        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        System.out.println("Phong ban: " + found.getName());
        for (Employee e : found.getEmployees()) {
            System.out.println("  - " + e);
        }

        JPAUtil.close();
    }
}