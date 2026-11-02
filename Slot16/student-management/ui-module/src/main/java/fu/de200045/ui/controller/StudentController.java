package fu.de200045.ui.controller;

import fu.de200045.data.pojo.Student;
import fu.de200045.data.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentController {

    @FXML private TableView<Student> tableView;
    @FXML private TextField searchField, idField, nameField, emailField, ageField;
    @FXML private Button btnUpdate, btnDelete;
    @FXML private Label messageLabel, statusLabel;

    // TODO 5.1: Danh sách quan sát duy nhất cho TableView
    private final ObservableList<Student> data = FXCollections.observableArrayList();

    private final StudentService studentService;   // INTERFACE, không phải StudentServiceImpl

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // TODO 5.2: Khởi tạo dữ liệu và binding trong initialize()
    @FXML
    public void initialize() {
        tableView.setItems(data);

        // Lắng nghe sự kiện chọn dòng trên bảng để điền vào form
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> fillForm(newVal));

        // Disable nút Update và Delete khi chưa chọn dòng nào trên bảng
        btnUpdate.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        btnDelete.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        loadData();
    }

    // TODO 5.3: Tải toàn bộ dữ liệu từ Service
    private void loadData() {
        try {
            List<Student> list = studentService.findAll();
            data.setAll(list);
            statusLabel.setText("Loaded " + list.size() + " students");
            messageLabel.setText("");
        } catch (Exception e) {
            showError("Cannot load data: " + e.getMessage());
            statusLabel.setText("Database error");
        }
    }

    // TODO 5.4: Tìm kiếm theo keyword (tên hoặc email)
    @FXML
    private void handleSearch() {
        try {
            String keyword = searchField.getText();
            List<Student> list = studentService.search(keyword);
            data.setAll(list);
            statusLabel.setText("Found " + list.size() + " students");
            messageLabel.setText("");
        } catch (Exception e) {
            showError("Search failed: " + e.getMessage());
        }
    }

    // TODO 5.4: Refresh ô tìm kiếm và tải lại toàn bộ bảng
    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadData();
    }

    // TODO 5.5: Điền thông tin sinh viên được chọn vào form
    private void fillForm(Student s) {
        if (s != null) {
            idField.setText(s.getId() != null ? String.valueOf(s.getId()) : "");
            nameField.setText(s.getName() != null ? s.getName() : "");
            emailField.setText(s.getEmail() != null ? s.getEmail() : "");
            ageField.setText(s.getAge() != null ? String.valueOf(s.getAge()) : "");
        } else {
            idField.clear();
            nameField.clear();
            emailField.clear();
            ageField.clear();
        }
    }

    // TODO 5.5: Hiển thị thông báo thành công (màu xanh)
    private void showInfo(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
    }

    // TODO 5.5: Hiển thị thông báo lỗi (màu đỏ)
    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setText(message);
    }

    @FXML private void handleAdd()     { }
    @FXML private void handleUpdate()  { }
    @FXML private void handleDelete()  { }
    @FXML private void handleClear()   { }
}
