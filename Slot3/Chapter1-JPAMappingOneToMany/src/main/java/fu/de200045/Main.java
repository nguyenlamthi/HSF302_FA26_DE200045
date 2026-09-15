package fu.de200045;

import fu.de200045.pojo.Department;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("Nguyen Van A", "a@gmail.com", new BigDecimal("10000000"), Gender.MALE, LocalDate.now());
        dept.addEmployee(emp);
        System.out.println(dept.getEmployees().contains(emp)); // phải true
        System.out.println(emp.getDepartment() == dept); // phải true
    }
}