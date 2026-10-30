package fu.de200045.data.repository;

import fu.de200045.data.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// TODO 2.1: @Repository, extends JpaRepository<Student, Long>
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO 2.2: Lấy tất cả sinh viên sắp xếp theo id tăng dần
    List<Student> findAllByOrderByIdAsc();

    // TODO 2.3: Tìm theo email (không phân biệt hoa thường)
    Optional<Student> findByEmailIgnoreCase(String email);

    // TODO 2.4: Kiểm tra tồn tại email (không phân biệt hoa thường) - dùng khi Create
    boolean existsByEmailIgnoreCase(String email);

    // TODO 2.5: Kiểm tra tồn tại email của sinh viên khác (loại trừ id hiện tại) - dùng khi Update
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    // TODO 2.6: Tìm kiếm theo tên hoặc email chứa keyword, sắp xếp theo id tăng dần
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "ORDER BY s.id ASC")
    List<Student> search(@Param("kw") String keyword);
}