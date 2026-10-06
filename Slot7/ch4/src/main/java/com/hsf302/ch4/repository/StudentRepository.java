package com.hsf302.ch4.repository;

import com.hsf302.ch4.dto.EnrollmentView;
import com.hsf302.ch4.dto.StudentCreditDTO;
import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {
    Optional<Student> findByStudentCode(String studentCode);
    boolean existsByEmail(String email);
    long countByActiveTrue();
    List<Student> findByFullNameContainingIgnoreCase(String keyword);
    List<Student> findByEmailEndingWith(String suffix);
    List<Student> findByEmailIsNull();
    List<Student> findByGpaBetweenOrderByGpaDesc(double min, double max);
    List<Student> findByGenderAndActiveTrue(Gender gender);
    List<Student> findByDobAfter(LocalDate date);
    List<Student> findByDepartment_CodeOrderByFullNameAsc(String code);
    long countByDepartment_Code(String code);
    List<Student> findTop3ByOrderByGpaDesc();

    @Query("SELECT s FROM Student s " +
            "WHERE s.department.code = :code AND s.gpa >= :minGpa " +
            "ORDER BY s.gpa DESC")
    List<Student> findGoodStudentsInDepartment(@Param("code") String code,
                                               @Param("minGpa") double minGpa);

    @Query("SELECT s FROM Student s " +
            "WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "   OR LOWER(s.email)    LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "ORDER BY s.fullName")
    List<Student> searchByKeyword(@Param("kw") String keyword);

    @Query("SELECT s FROM Student s " +
            "WHERE s.gpa > (SELECT AVG(s2.gpa) FROM Student s2) " +
            "ORDER BY s.gpa DESC")
    List<Student> findAboveAverageGpa();

    @Query(value = "SELECT TOP (:n) s.* " +
            "FROM students s JOIN departments d ON s.department_id = d.id " +
            "WHERE d.code = :code " +
            "ORDER BY s.gpa DESC",
            nativeQuery = true)
    List<Student> findTopNByDepartmentNative(@Param("code") String code, @Param("n") int n);

    @Query("SELECT s.studentCode AS studentCode, s.fullName AS fullName, " +
            "       s.gpa AS gpa, d.name AS departmentName " +
            "FROM Student s JOIN s.department d " +
            "WHERE s.active = true " +
            "ORDER BY s.fullName")
    List<StudentSummary> findActiveSummaries();

    @Query("SELECT s FROM Student s WHERE s.department.code = :code AND s.active = true")
    Page<Student> findActiveByDepartment(@Param("code") String code, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Student s SET s.active = false WHERE s.gpa < :threshold AND s.active = true")
    int deactivateLowGpa(@Param("threshold") double threshold);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Student s SET s.department = :to WHERE s.department = :from")
    int transferStudents(@Param("from") Department from, @Param("to") Department to);

    long deleteByActiveFalse();
    List<Student> findByGender(Gender gender);

    @Query("SELECT s FROM Student s " +
            "WHERE s.gender = :gender")
    List<Student> findWithGender(@Param("gender") Gender gender);



    // ===== Exercise 2: TODO 9 =====
    List<Student> findByCourses_CodeOrderByFullNameAsc(String courseCode);
    long countByCourses_Code(String courseCode);
    List<Student> findByCourses_CodeAndActiveTrueOrderByFullNameAsc(String courseCode);

    // ===== Exercise 2: TODO 11 =====
    List<Student> findByCoursesIsEmpty();
    boolean existsByStudentCodeAndCourses_Code(String studentCode, String courseCode);

    // ===== Exercise 2: TODO 12 =====
    @Query("""
        SELECT s FROM Student s
        JOIN s.courses c
        WHERE c.code = :courseCode AND s.gpa >= :minGpa
        ORDER BY s.gpa DESC
    """)
    List<Student> findGoodStudentsInCourse(@Param("courseCode") String courseCode,
                                           @Param("minGpa") double minGpa);

    // ===== Exercise 2: TODO 14 =====
    @Query("""
        SELECT new com.hsf302.ch4.dto.StudentCreditDTO(
            s.studentCode,
            s.fullName,
            COUNT(c.id),
            COALESCE(SUM(c.credits), 0L)
        )
        FROM Student s
        JOIN s.courses c
        GROUP BY s.studentCode, s.fullName
        HAVING SUM(c.credits) >= :minCredits
        ORDER BY SUM(c.credits) DESC, s.fullName ASC
    """)
    List<StudentCreditDTO> getStudentCreditSummary(@Param("minCredits") int minCredits);

    // ===== Exercise 2: TODO 15 =====
    @Query("SELECT s FROM Student s WHERE SIZE(s.courses) > :n ORDER BY s.fullName ASC")
    List<Student> findStudentsWithMoreThan(@Param("n") int n);

    // ===== Exercise 2: TODO 16 =====
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.studentCode = :code")
    Optional<Student> findByStudentCodeWithCourses(@Param("code") String code);

    // ===== Exercise 2: TODO 18 =====
    @Query("""
        SELECT
            s.studentCode AS studentCode,
            s.fullName    AS fullName,
            c.code        AS courseCode,
            c.name        AS courseName,
            c.credits     AS credits
        FROM Student s
        JOIN s.courses c
        JOIN s.department d
        WHERE d.code = :deptCode
        ORDER BY s.studentCode ASC, c.code ASC
    """)
    List<EnrollmentView> getEnrollmentsByDepartment(@Param("deptCode") String deptCode);

    // ===== Exercise 2: TODO 19 =====
    @Query(
            value = "SELECT s FROM Student s JOIN s.courses c WHERE c.code = :courseCode ORDER BY s.fullName ASC",
            countQuery = "SELECT COUNT(s) FROM Student s JOIN s.courses c WHERE c.code = :courseCode"
    )
    Page<Student> findStudentsInCoursePage(
            @Param("courseCode") String courseCode,
            org.springframework.data.domain.Pageable pageable);
}