package fu.de200045.data.service;

import fu.de200045.data.exception.StudentNotFoundException;
import fu.de200045.data.exception.ValidationException;
import fu.de200045.data.pojo.Student;
import fu.de200045.data.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

// TODO 3.1: @Service + @Transactional(readOnly = true) mặc định cho toàn class
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");

    // TODO 3.2: Constructor injection (không dùng @Autowired trên field)
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // TODO 3.3: Thêm mới sinh viên
    @Override
    @Transactional
    public Student create(Student s) {
        if (s == null) {
            throw new ValidationException("Student is required");
        }
        normalize(s);
        validate(s);

        if (studentRepository.existsByEmailIgnoreCase(s.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        s.setId(null); // Đảm bảo ID null để Hibernate thực hiện INSERT
        return studentRepository.save(s);
    }

    // TODO 3.4: Tìm theo ID
    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    // TODO 3.5: Lấy danh sách tất cả sinh viên (sắp xếp id tăng dần)
    @Override
    public List<Student> findAll() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    // TODO 3.6: Cập nhật thông tin sinh viên
    @Override
    @Transactional
    public Student update(Student s) {
        if (s == null || s.getId() == null) {
            throw new ValidationException("Student ID is required for update");
        }
        // Kiểm tra tồn tại trong DB, nếu không có sẽ ném StudentNotFoundException
        Student existing = findById(s.getId());

        normalize(s);
        validate(s);

        // Kiểm tra trùng email với sinh viên khác
        if (studentRepository.existsByEmailIgnoreCaseAndIdNot(s.getEmail(), s.getId())) {
            throw new ValidationException("Email already exists");
        }

        // Cập nhật các trường vào entity đang được quản lý (managed entity)
        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
        existing.setAge(s.getAge());

        return studentRepository.save(existing);
    }

    // TODO 3.7: Xoá sinh viên theo ID
    @Override
    @Transactional
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    // TODO 3.8: Tìm kiếm
    @Override
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return studentRepository.search(keyword.trim());
    }

    // TODO 3.9: Chuẩn hóa dữ liệu (trim & lowerCase email)
    private void normalize(Student s) {
        if (s.getName() != null) {
            s.setName(s.getName().trim());
        }
        if (s.getEmail() != null) {
            s.setEmail(s.getEmail().trim().toLowerCase());
        }
    }

    // TODO 3.10: Kiểm tra nghiệp vụ theo đúng thứ tự: Name -> Email -> Age
    private void validate(Student s) {
        // 1. Kiểm tra Name
        if (s.getName() == null || s.getName().isBlank()) {
            throw new ValidationException("Name is required");
        }
        if (s.getName().length() > 100) {
            throw new ValidationException("Name must be at most 100 characters");
        }

        // 2. Kiểm tra Email
        if (s.getEmail() == null || !EMAIL.matcher(s.getEmail()).matches()) {
            throw new ValidationException("Invalid email");
        }

        // 3. Kiểm tra Age (từ 18 đến 60)
        if (s.getAge() == null || s.getAge() < 18 || s.getAge() > 60) {
            throw new ValidationException("Age must be between 18 and 60");
        }
    }
}