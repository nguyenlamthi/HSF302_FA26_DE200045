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
        departmentDAO.findAllWithEmployees();

        JPAUtil.close();
    }
}