package com.hsf302.ch4.runner;

import com.hsf302.ch4.dto.CourseEnrollmentCount;
import com.hsf302.ch4.dto.EnrollmentView;
import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.CourseService;
import com.hsf302.ch4.service.EnrollmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Order(3)                                // Chạy sau khi toàn bộ dữ liệu đã được seed xong
@Profile("ex2")                          // Chỉ chạy khi profile ex2 đang kích hoạt
@RequiredArgsConstructor
public class Exercise2Runner implements CommandLineRunner {

    // N-layer: CHỈ inject Service interface, KHÔNG inject Repository
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;          // của Exercise 1 (dùng cho TODO 16a, 21)

    @Override
    public void run(String... args) {
        System.out.println("\n========== START EXERCISE 2 ==========");
//        partB();
//        partC();
//        partD();
//        bonus();
        partE();
        System.out.println("\n========== END EXERCISE 2 ==========");
    }

    private void partB() {
        todo6();
        todo7();
    }

    private void partC() {
        todo8();
        todo9();
        todo10();
        todo11();
    }

    private void partD() {
        todo12();
        todo13();
        todo14();
        todo15();
        todo16();
        todo17();
        todo18();
        todo19();
    }

    private void bonus() {
        // Sẽ gọi todo25();
    }

    private void partE() {
        todo20();
        todo21();
    }

    // ===== Helpers in ấn dùng chung =====
    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    /** Helper chạy thao tác ghi dữ liệu có bắt exception (dùng cho Part E) */
    private void attempt(String label, Runnable action) {
        try {
            action.run();
            System.out.println("   [OK]   " + label);
        } catch (RuntimeException e) {
            System.out.println("   [FAIL] " + label + " -> " + e.getMessage());
        }
    }

    // ===== TODO 6 =====
    private void todo6() {
        title("TODO 6: count, findAll(Sort), findById");
        System.out.println("Total courses: " + courseService.count());
        printList("All courses order by code", courseService.findAllOrderByCode());

        for (long id : new long[]{2L, 99L}) {
            System.out.println("findById(" + id + "): "
                    + courseService.findById(id).map(Course::toString).orElse("Not found"));
        }
    }

    // ===== TODO 7 =====
    private void todo7() {
        title("TODO 7: navigate student.getCourses() / course.getStudents()");
        printList("(a) Courses of SE001", enrollmentService.getCoursesOfStudent("SE001"));
        printList("(b) Students of AIL303", enrollmentService.getStudentsOfCourse("AIL303"));
    }

    // ===== TODO 8 =====
    private void todo8() {
        title("TODO 8: findByCode, findBySemester, countBySemester");
        for (String code : List.of("HSF302", "XXX000")) {
            System.out.println("(a) " + code + ": "
                    + courseService.findByCode(code).map(Course::getName).orElse("Not found"));
        }
        printList("(b) Semester SU26", courseService.findBySemester("SU26"));
        System.out.println("(c) Courses in FA26: " + courseService.countBySemester("FA26"));
    }

    // ===== TODO 9 =====
    private void todo9() {
        title("TODO 9: derived query through collection courses");
        printList("(a) Students of PRJ301", enrollmentService.findStudentsInCourse("PRJ301"));
        System.out.println("(b) Enrolled in HSF302: " + enrollmentService.countStudentsInCourse("HSF302"));
        printList("(c) Active students of PRJ301", enrollmentService.findActiveStudentsInCourse("PRJ301"));
    }

    // ===== TODO 10 =====
    private void todo10() {
        title("TODO 10: from inverse side & Distinct keyword");
        printList("(a) Courses of SE002", courseService.findCoursesOfStudent("SE002"));
        List<Course> nonDistinct = courseService.findCoursesOfDepartment("AI", false);
        List<Course> distinct = courseService.findCoursesOfDepartment("AI", true);
        printList("(b1) Courses of AI dept (NO distinct)", nonDistinct);
        printList("(b2) Courses of AI dept (DISTINCT)", distinct);
        System.out.println("   Comparison: non-distinct = " + nonDistinct.size()
                + " rows, distinct = " + distinct.size() + " rows");
    }

    // ===== TODO 11 =====
    private void todo11() {
        title("TODO 11: IsEmpty & existsBy");
        printList("(a) Students without courses", enrollmentService.findStudentsWithoutCourses());
        printList("(b) Courses without students", courseService.findCoursesWithoutStudents());
        System.out.println("(c) SE001 enrolled in AIL303? "
                + enrollmentService.isEnrolled("SE001", "AIL303"));
        System.out.println("    SE002 enrolled in AIL303? "
                + enrollmentService.isEnrolled("SE002", "AIL303"));
    }

    // ===== TODO 12 =====
    private void todo12() {
        title("TODO 12: JPQL JOIN collection + named parameter");
        printList("HSF302, GPA >= 3.5",
                enrollmentService.findGoodStudentsInCourse("HSF302", 3.5));
    }

    // ===== TODO 13 =====
    private void todo13() {
        title("TODO 13: Course statistics (LEFT JOIN + GROUP BY + DTO)");
        printList("Course statistics", courseService.getStatistics());
    }

    // ===== TODO 14 =====
    private void todo14() {
        title("TODO 14: Student credit summary (GROUP BY + HAVING)");
        printList("Students with >= 7 credits", enrollmentService.getCreditSummary(7));
    }

    // ===== TODO 15 =====
    private void todo15() {
        title("TODO 15: SIZE() function");
        printList("(a) Full courses", courseService.findFullCourses());
        printList("(b) Students with > 2 courses", enrollmentService.findStudentsWithMoreThan(2));
    }

    // ===== TODO 16 =====
    private void todo16() {
        title("TODO 16: LazyInitializationException, JOIN FETCH & @EntityGraph");
        // (a) Tái hiện LazyInitializationException: nạp student bằng service thường rồi truy cập courses ngoài transaction
        Student s = studentService.findByStudentCode("SE001").orElseThrow();
        try {
            System.out.println("Courses size: " + s.getCourses().size());
        } catch (org.hibernate.LazyInitializationException e) {
            System.out.println("(a) Caught: " + e.getClass().getSimpleName());
            System.out.println("    " + e.getMessage());
        }
        // (b) JOIN FETCH: Nạp sẵn courses vào bộ nhớ ngay trong 1 câu SQL
        Student sFull = enrollmentService.getStudentWithCourses("SE001");
        System.out.println("(b) SE001 courses (JOIN FETCH):");
        sFull.getCourses().stream()
                .sorted(java.util.Comparator.comparing(Course::getCode))
                .forEach(c -> System.out.println("     " + c));
        // (c) @EntityGraph: Nạp sẵn students của Course một cách linh hoạt
        Course cFull = courseService.getWithStudents("SWP391");
        System.out.println("(c) SWP391 students (@EntityGraph):");
        cFull.getStudents().stream()
                .sorted(java.util.Comparator.comparing(Student::getFullName))
                .forEach(st -> System.out.println("     " + st));
    }

    // ===== TODO 17 =====
    private void todo17() {
        title("TODO 17: Native query - TOP N enrolled courses");
        List<CourseEnrollmentCount> list = courseService.findTopEnrolled(3);
        list.forEach(c -> System.out.printf("   %s | %-40s | %d students%n",
                c.getCode(), c.getName(), c.getEnrolled()));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    // ===== TODO 18 =====
    private void todo18() {
        title("TODO 18: Enrollment view interface projection (JOIN 3 entities)");
        List<EnrollmentView> list = enrollmentService.getEnrollmentsOfDepartment("AI");
        list.forEach(e -> System.out.printf("   %s | %-15s | %s - %-40s | %d credits%n",
                e.getStudentCode(), e.getFullName(), e.getCourseCode(), e.getCourseName(), e.getCredits()));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    private void todoNhap() {
        title("TODO Nhap: Find course by keyword");
        printList("Courses", courseService.findByNameContainKeyWord("ment"));
    }

    // ===== TODO 19 =====
    private void todo19() {
        title("TODO 19: Paginate students of course (@Query + countQuery)");
        int pageIndex = 0;
        int size = 2;
        Page<Student> page;
        do {
            page = enrollmentService.findStudentsInCoursePage("HSF302", pageIndex, size);
            printList("HSF302 - page " + page.getNumber(), page.getContent());
            System.out.println("   totalElements=" + page.getTotalElements()
                    + ", totalPages=" + page.getTotalPages()
                    + ", isLast=" + page.isLast());
            pageIndex++;
        } while (page.hasNext());
    }

    // ===== TODO 20 =====
    private void todo20() {
        title("TODO 20: enroll student with business rules");

        // Thử nghiệm 5 kịch bản thông qua helper attempt()
        attempt("Enroll IA003 -> MKT101 (OK)",
                () -> enrollmentService.enroll("IA003", "MKT101"));
        attempt("Enroll SE001 -> PRJ301 (already enrolled)",
                () -> enrollmentService.enroll("SE001", "PRJ301"));
        attempt("Enroll SE004 -> AIL303 (full: 4/4)",
                () -> enrollmentService.enroll("SE004", "AIL303"));
        attempt("Enroll SE003 -> HSF302 (inactive)",
                () -> enrollmentService.enroll("SE003", "HSF302"));
        attempt("Enroll XX999 -> HSF302 (not found)",
                () -> enrollmentService.enroll("XX999", "HSF302"));
        // Kiểm tra lại dữ liệu sau khi đăng ký thành công
        printList("Courses of IA003 after enroll",
                enrollmentService.getCoursesOfStudent("IA003"));
        System.out.println("Enrolled in MKT101: "
                + enrollmentService.getStudentsOfCourse("MKT101").size());
    }

    // ===== TODO 21 =====
    private void todo21() {
        title("TODO 21: unenroll student from course");
        // 1. Huỷ môn AIL303 của AI002 (hợp lệ)
        attempt("Unenroll AI002 from AIL303 (OK)",
                () -> enrollmentService.unenroll("AI002", "AIL303"));
        // 2. Huỷ môn PRJ301 của IA003 (thất bại: chưa từng đăng ký)
        attempt("Unenroll IA003 from PRJ301 (not enrolled)",
                () -> enrollmentService.unenroll("IA003", "PRJ301"));
        // 3. Đăng ký SE004 vào AIL303 (lúc trước ở TODO 20 bị đầy 4/4, giờ AI002 rút môn nên trống 1 chỗ -> thành công!)
        attempt("Enroll SE004 -> AIL303 now (was full, now has 1 slot)",
                () -> enrollmentService.enroll("SE004", "AIL303"));
        // In kiểm tra danh sách sinh viên của AIL303 và môn học của AI002
        printList("Students of AIL303 after changes",
                enrollmentService.getStudentsOfCourse("AIL303"));
        printList("Courses of AI002 after unenroll",
                enrollmentService.getCoursesOfStudent("AI002"));
        // Chứng minh: unenroll CHỈ xoá liên kết trong student_courses, KHÔNG làm mất Entity
        System.out.println("Student AI002 exists? "
                + studentService.findByStudentCode("AI002").isPresent());
        System.out.println("Total courses still = " + courseService.count());
    }
}