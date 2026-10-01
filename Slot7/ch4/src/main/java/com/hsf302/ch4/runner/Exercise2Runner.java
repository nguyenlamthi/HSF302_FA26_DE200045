package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.service.CourseService;
import com.hsf302.ch4.service.EnrollmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
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
        partB();
        partC();
        partD();
        bonus();
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
//        todo10();
//        todo11();
    }

    private void partD() {
        // Sẽ gọi todo12() -> todo19();
    }

    private void bonus() {
        // Sẽ gọi todo25();
    }

    private void partE() {
        // Sẽ gọi todo20() -> todo24();
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
}