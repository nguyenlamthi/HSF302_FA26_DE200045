package com.hsf302.ch4.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, unique = true, length = 10)
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 100, columnDefinition = "nvarchar(100)")
    private String fullName;

    @Column(unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private LocalDate dob;

    private Double gpa;

    private boolean active;

    // Owning side: bảng students có cột department_id (FK → departments.id)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // ===== PHẦN MỚI EXERCISE 2: Many-To-Many (Owning side) =====
    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),          // FK trỏ về Student (bảng hiện tại)
            inverseJoinColumns = @JoinColumn(name = "course_id")     // FK trỏ về Course (bảng bên kia)
    )
    private Set<Course> courses = new HashSet<>();

    // ===== Helper đồng bộ 2 chiều (BẮT BUỘC CỦA HSF302) =====
    public void enroll(Course c) {
        courses.add(c);                 // Owning side -> Hibernate INSERT vào student_courses
        c.getStudents().add(this);      // Inverse side -> giữ object Java nhất quán trong bộ nhớ
    }

    public void unenroll(Course c) {
        courses.remove(c);              // Owning side -> Hibernate DELETE khỏi student_courses
        c.getStudents().remove(this);
    }

    // equals / hashCode theo Business Key (studentCode)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student other)) return false;
        return studentCode != null && studentCode.equals(other.getStudentCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentCode);
    }

    @Override
    public String toString() {
        return String.format("%s | %-15s | %-20s | %.1f | %s",
                studentCode, fullName, email, gpa, active ? "active" : "inactive");
        // Tuyệt đối KHÔNG in department hay courses để tránh LazyInitializationException
    }
}