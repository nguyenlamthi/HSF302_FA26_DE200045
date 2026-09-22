package fu.de200045.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TODO 5.1 - Entity Employee
 * Tái sử dụng cấu trúc từ bài OneToMany, bổ sung field cho bài ManyToMany.
 *
 * Quan hệ N-N với Project sẽ được thêm ở TODO 5.2 (owning side, dùng @JoinTable).
 * Dùng Set<> (không dùng List<>) để tránh trùng lặp phần tử trong quan hệ N-N.
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "salary", nullable = false, precision = 15, scale = 2)
    private BigDecimal salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    // ===== Constructors =====
    public Employee() {
    }

    public Employee(String fullName, BigDecimal salary, LocalDate hireDate,
                    String email, Gender gender) {
        this.fullName = fullName;
        this.salary = salary;
        this.hireDate = hireDate;
        this.email = email;
        this.gender = gender;
        this.active = true;
    }

    // ===== Getters / Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    /**
     * TODO 5.5 - Helper method để đồng bộ cả 2 chiều của quan hệ N-N.
     *
     * Vì Employee là owning side, chỉ add vào this.projects thì Hibernate
     * sẽ lưu đúng xuống bảng employee_project. Tuy nhiên nếu không add
     * ngược lại vào project.getEmployees(), thì object Project trong bộ nhớ
     * (chưa refresh từ DB) sẽ "không biết" là đã có Employee này tham gia
     * => project.getEmployees() sẽ thiếu dữ liệu nếu dùng ngay trong cùng
     * transaction/session, dễ gây bug khó phát hiện.
     */
    public void assignToProject(Project p) {
        this.projects.add(p);
        p.getEmployees().add(this);
    }

    public void unassignFromProject(Project p) {
        this.projects.remove(p);
        p.getEmployees().remove(this);
    }

    /*
     * Không dùng id để so sánh vì id chỉ được sinh ra SAU KHI entity đã được
     * persist (GenerationType.IDENTITY). Trước khi lưu, id luôn là null, nên
     * nếu dựa vào id thì mọi Employee mới tạo (chưa persist) sẽ có cùng
     * hashCode/equals => Set<Employee> sẽ coi chúng là "trùng nhau" một cách
     * sai lệch, hoặc ngược lại không phát hiện được trùng lặp thật sự.
     * Email là business key: duy nhất, ổn định, có ý nghĩa nghiệp vụ ngay cả
     * khi entity chưa được lưu vào DB, nên dùng email để equals/hashCode.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", active=" + active +
                '}';
    }
}