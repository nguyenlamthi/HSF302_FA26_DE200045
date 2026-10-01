package com.hsf302.ch4.service;

import com.hsf302.ch4.repository.CourseRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    // Nghiệp vụ đăng ký cần tương tác cả Sinh viên lẫn Khóa học
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Sẽ cài đặt các method từ TODO 7
}