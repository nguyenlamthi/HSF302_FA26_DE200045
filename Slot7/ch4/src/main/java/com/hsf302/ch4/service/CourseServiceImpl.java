package com.hsf302.ch4.service;

import com.hsf302.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)          // Mặc định mọi thao tác là READ-ONLY (tối ưu hiệu năng)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    // Sẽ cài đặt các method từ TODO 6
}