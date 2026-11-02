# Student Management System
> JavaFX + Spring Data JPA (Repository · Service · ServiceImpl) — Multi-module Maven

Ứng dụng quản lý sinh viên CRUD xây dựng theo kiến trúc N-Layer chuẩn mực, tích hợp Spring Boot trong vòng đời JavaFX, lưu trữ dữ liệu trên SQL Server (chạy app) và H2 In-Memory (chạy unit test).

---

## 1. Môi trường phát triển & Công nghệ

- **JDK:** 21 (Oracle OpenJDK / Eclipse Temurin)
- **Build tool:** Apache Maven 3.9+
- **Spring Boot:** 3.5.16
- **Spring Data JPA & Hibernate:** 6.6.x
- **JavaFX:** 21.0.5 (javafx-controls, javafx-fxml)
- **Database:** Microsoft SQL Server (port 1433)
- **Test Database:** H2 in-memory (tự động cấu hình với `@DataJpaTest`)
- **IDE:** IntelliJ IDEA 2024 / 2025, Gluon Scene Builder

---

## 2. Kiến trúc N-Layer (Multi-Module)

```
┌────────────────────────── ui-module ──────────────────────────┐
│  student-view.fxml + style.css            (View)              │
│  StudentController  @Component            (Controller)        │
│  JavaFxApplication / Launcher / StudentManagementApplication  │
│  application.properties                   (cấu hình DB)       │
└───────────────┬───────────────────────────────────────────────┘
                │ chỉ phụ thuộc INTERFACE StudentService
┌───────────────▼─────────── data-module ───────────────────────┐
│  StudentService            (interface)                        │
│  StudentServiceImpl        @Service @Transactional            │
│        │ constructor injection                                │
│  StudentRepository extends JpaRepository<Student, Long>       │
│        │ Spring Data JPA tự sinh implementation               │
│  Student  @Entity                                             │
└───────────────┬───────────────────────────────────────────────┘
                ▼
        SQL Server (StudentDB)   |   H2 in-memory (test)
```

### Cấu trúc thư mục

```
student-management/
├── pom.xml                                   (parent = spring-boot-starter-parent)
├── README.md
├── docs/
│   └── screenshots/                          (ảnh chụp manual test)
│       ├── 01-list.png
│       ├── 02-add.png
│       ├── 03-validation.png
│       └── 04-delete-confirm.png
├── data-module/
│   ├── pom.xml
│   └── src/
│       ├── main/java/fu/de200045/data/
│       │   ├── pojo/Student.java                         (Entity)
│       │   ├── repository/StudentRepository.java         (Repository)
│       │   ├── exception/ValidationException.java        (Exception)
│       │   ├── exception/StudentNotFoundException.java   (Exception)
│       │   ├── service/StudentService.java               (Service Interface)
│       │   └── service/StudentServiceImpl.java           (Service Implementation)
│       └── test/java/fu/de200045/data/
│           ├── DataTestApplication.java                  (Test config)
│           ├── repository/StudentRepositoryTest.java     (10/10 test pass)
│           └── service/StudentServiceImplTest.java       (16/16 test pass)
└── ui-module/
    ├── pom.xml
    └── src/main/
        ├── java/fu/de200045/
        │   ├── StudentManagementApplication.java         (@SpringBootApplication)
        │   └── ui/
        │       ├── JavaFxApplication.java                (JavaFX Application lifecycle)
        │       ├── Launcher.java                         (Main Entry Point)
        │       └── controller/StudentController.java     (UI Controller)
        └── resources/
            ├── application.properties                    (Datasource configuration)
            ├── student-view.fxml                         (JavaFX Layout FXML)
            └── style.css                                 (JavaFX Stylesheet)
```

---

## 3. Cài đặt & Cấu hình Database

### 3.1. Tạo Database trên SQL Server

Mở SQL Server Management Studio (SSMS) và thực thi script:

```sql
IF DB_ID('StudentDB') IS NULL 
    CREATE DATABASE StudentDB;
GO

USE StudentDB;
GO

-- (Tuỳ chọn) Tạo sẵn dữ liệu mẫu ban đầu:
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'students')
BEGIN
    CREATE TABLE students (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(100) NOT NULL,
        email VARCHAR(150) NOT NULL UNIQUE,
        age INT NOT NULL
    );
END

INSERT INTO students(name, email, age) VALUES
(N'Nguyễn Văn An', 'an.nv@fpt.edu.vn',   20),
(N'Trần Thị Bình', 'binh.tt@fpt.edu.vn', 21),
(N'Lê Văn Cường',  'cuong.lv@gmail.com', 22),
(N'Phạm Thị Dung', 'dung.pt@fpt.edu.vn', 19),
(N'Hoàng Văn Em',  'em.hv@gmail.com',    23);
GO
```

### 3.2. Cấu hình Datasource

Mở file `ui-module/src/main/resources/application.properties` và điều chỉnh tài khoản/mật khẩu nếu cần:

```properties
spring.application.name=student-management
spring.main.web-application-type=none

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=StudentDB;encrypt=false;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=12345
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 4. Biên dịch, Kiểm thử & Chạy ứng dụng

### 4.1. Chạy Unit Test (Data Layer)
Chạy kiểm thử 26 bài test tự động trên cơ sở dữ liệu ảo H2:

```bash
mvn test -pl data-module
```

### 4.2. Build toàn bộ dự án
```bash
mvn clean install
```

### 4.3. Khởi chạy ứng dụng JavaFX

**Cách 1 (Từ IntelliJ IDEA - Khuyến nghị):**
- Tìm đến class `fu.de200045.ui.Launcher` trong `ui-module`.
- Nhấp chuột phải chọn **Run 'Launcher.main()'**.

**Cách 2 (Từ dòng lệnh Maven):**
```bash
mvn -f ui-module/pom.xml spring-boot:run
```

---

## 5. Kịch bản kiểm thử thủ công (Manual Test Cases)

| TC | Thao tác kiểm thử | Kết quả mong đợi |
|---|---|---|
| **TC01** 📸 | Khởi động app | Hiển thị danh sách sinh viên, tên tiếng Việt có dấu đúng chuẩn, status `Loaded N students`, nút Update/Delete bị mờ |
| **TC02** | Nhập `bình` vào ô search → bấm Enter | Lọc đúng sinh viên Trần Thị Bình, status `Found 1 students` |
| **TC03** | Nhập `GMAIL` → bấm Search | Lọc ra các sinh viên có email gmail (không phân biệt chữ hoa/thường) |
| **TC04** | Bấm Refresh, chọn 1 sinh viên trên bảng | Form bên phải tự động điền thông tin, nút Update và Delete sáng lên |
| **TC05** 📸 | Nhập Form: Tên `Võ Thị Giang`, Email `giang@fpt.edu.vn`, Tuổi `20` → bấm Add | Thông báo màu xanh `Added: Võ Thị Giang`, sinh viên mới hiển thị trên bảng |
| **TC06** 📸 | Bấm Add khi để trống ô Tên | Thông báo đỏ `Name is required`, dữ liệu form được giữ nguyên |
| **TC07** | Nhập Tuổi chữ cái (vd `abc`) → bấm Add | Thông báo đỏ `Age must be a number` |
| **TC08** | Nhập email đã tồn tại trong DB → bấm Add | Thông báo đỏ `Email already exists` |
| **TC09** | Chọn sinh viên → đổi Tuổi → bấm Update | Thông báo xanh `Updated: <tên>`, bảng cập nhật số tuổi mới |
| **TC10** | Chọn sinh viên → nhập email sai định dạng → bấm Update | Thông báo đỏ `Invalid email`, trên bảng vẫn giữ email cũ không bị thay đổi |
| **TC11** 📸 | Chọn sinh viên → bấm Delete → bấm Cancel ở Popup | Không có sinh viên nào bị xoá |
| **TC12** | Chọn sinh viên → bấm Delete → bấm OK ở Popup | Thông báo xanh `Deleted: <tên>`, sinh viên biến mất khỏi bảng |
| **TC13** | Tắt dịch vụ SQL Server → bấm Refresh | Thông báo đỏ `Cannot load data: ...`, ứng dụng không bị crash |
| **TC14** | Đóng cửa sổ ứng dụng | Ứng dụng thoát sạch sẽ, process Java kết thúc |

*(Các ảnh chụp màn hình minh hoạ cho các test case có ký hiệu 📸 được lưu trữ tại thư mục `docs/screenshots/`)*.
