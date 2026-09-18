package fu.de200045.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TODO 5.1 - Entity Project
 *
 * Quan hệ N-N với Employee sẽ được thêm ở TODO 5.3 (inverse side, mappedBy = "projects").
 * Dùng Set<> (không dùng List<>) để tránh trùng lặp phần tử trong quan hệ N-N.
 */
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_code", unique = true, nullable = false)
    private String projectCode;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "budget", nullable = false, precision = 15, scale = 2)
    private BigDecimal budget;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // Có thể null nếu dự án chưa kết thúc
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();

    // ===== Constructors =====
    public Project() {
    }

    public Project(String projectCode, String projectName, BigDecimal budget,
                   LocalDate startDate, LocalDate endDate) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // ===== Getters / Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    /*
     * Tương tự Employee: không dùng id vì id chỉ có giá trị sau khi persist.
     * projectCode là business key duy nhất và ổn định, phù hợp để equals/hashCode,
     * đặc biệt quan trọng khi Project được đưa vào Set<Project> (như trong
     * Employee.projects) trước khi được lưu vào DB.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(projectCode, project.projectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectCode='" + projectCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}