package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.CourseStatDTO;
import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    // ===== TODO 6 =====
    @Override
    public long count() {
        return courseRepository.count();
    }

    @Override
    public List<Course> findAllOrderByCode() {
        return courseRepository.findAll(Sort.by("code"));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    // ===== TODO 8 =====
    @Override
    public Optional<Course> findByCode(String code) {
        return courseRepository.findByCode(code);
    }
    @Override
    public List<Course> findBySemester(String semester) {
        return courseRepository.findBySemesterOrderByCodeAsc(semester);
    }
    @Override
    public long countBySemester(String semester) {
        return courseRepository.countBySemester(semester);
    }

    // ===== TODO 10 =====
    @Override
    public List<Course> findCoursesOfStudent(String studentCode) {
        if (studentCode == null || studentCode.isBlank()) {
            throw new IllegalArgumentException("Student code must not be blank");
        }
        return courseRepository.findByStudents_StudentCodeOrderByCodeAsc(studentCode);
    }

    @Override
    public List<Course> findCoursesOfDepartment(String deptCode, boolean distinct) {
        if (deptCode == null || deptCode.isBlank()) {
            throw new IllegalArgumentException("Department code must not be blank");
        }
        return distinct
                ? courseRepository.findDistinctByStudents_Department_CodeOrderByCodeAsc(deptCode)
                : courseRepository.findByStudents_Department_CodeOrderByCodeAsc(deptCode);
    }

    // ===== TODO 11 =====
    @Override
    public List<Course> findCoursesWithoutStudents() {
        return courseRepository.findByStudentsIsEmpty();
    }

    // ===== TODO 13 =====
    @Override
    public List<CourseStatDTO> getStatistics() {
        return courseRepository.getCourseStatistics();
    }

    // ===== TODO 15 =====
    @Override
    public List<Course> findFullCourses() {
        return courseRepository.findFullCourses();
    }

    // ===== TODO 16 =====
    @Override
    public Course getWithStudents(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code must not be blank");
        }
        return courseRepository.findWithStudentsByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + code));
    }

    // ===== TODO 17 =====
    @Override
    public List<com.hsf302.ch4.dto.CourseEnrollmentCount> findTopEnrolled(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        return courseRepository.findTopEnrolledCourses(n);
    }

    @Override
    public List<Course> findByNameContainKeyWord(String keyword) {
        if(keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be blank");
        }
        return courseRepository.findByNameContainKeyWord(keyword);
    }

    // ===== TODO 23 =====
    @Override
    @Transactional
    public void deleteCourseDirectly(String code) {
        Course c = courseRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + code));
        courseRepository.delete(c);
        courseRepository.flush(); // Bắt buộc flush() để ép Hibernate bắn câu DELETE ngay lập tức
    }

    @Override
    @Transactional
    public int deleteCourse(String code) {
        Course c = courseRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + code));

        // Tạo bản sao danh sách sinh viên để tránh ConcurrentModificationException khi vừa duyệt vừa gỡ
        java.util.List<com.hsf302.ch4.pojo.Student> students = new java.util.ArrayList<>(c.getStudents());
        for (com.hsf302.ch4.pojo.Student s : students) {
            s.unenroll(c); // Owning side tự động DELETE các dòng trong student_courses
        }

        courseRepository.delete(c);
        return students.size();
    }
}