package fu.de200045;

import fu.de200045.dao.DepartmentDAO;
import fu.de200045.pojo.Department;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO deptDAO = new DepartmentDAO();
        Department dept = deptDAO.findByIdWithEmployees(2L);
        List<Employee> employees = dept.getEmployees();
        employees.forEach(System.out::println);
    }
}