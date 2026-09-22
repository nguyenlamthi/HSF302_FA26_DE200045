package fu.de200045;

import fu.de200045.dao.EmployeeDAO;
import fu.de200045.dto.ProjectStat;
import fu.de200045.pojo.Employee;
import fu.de200045.pojo.Gender;
import fu.de200045.pojo.Project;
import fu.de200045.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<ProjectStat> stats = employeeDAO.countActiveEmployeesAndTotalSalaryByProject();
        System.out.println("\n=== Thống kê theo project ===");
        for (ProjectStat stat : stats) {
            System.out.printf("Project %s: %d nhân viên active, tổng salary = %s%n",
                    stat.projectName(), stat.activeCount(), stat.totalSalary());
        }

        JPAUtil.close();
    }
}