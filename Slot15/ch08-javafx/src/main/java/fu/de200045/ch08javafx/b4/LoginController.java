package fu.de200045.ch08javafx.b4;

import fu.de200045.ch08javafx.common.AlertUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private TextField tfPasswordVisible;
    @FXML private PasswordField pfPassword;
    @FXML private CheckBox chkShow;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    // Danh sách tài khoản hợp lệ (username -> [password, vai trò])
    private static final Map<String, String[]> ACCOUNTS = Map.of(
            "student", new String[]{"Student@123", "Sinh viên"},
            "teacher", new String[]{"Teacher@123", "Giảng viên"});

    private int failedCount = 0; // Đếm số lần đăng nhập sai

    @FXML
    public void initialize() {
        // 1. Đồng bộ 2 chiều nội dung giữa ô password ẩn và ô password hiện
        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());

        // 2. Hiện/Ẩn: liên kết cả visible và managed (managed=false để không chiếm diện tích)
        tfPasswordVisible.visibleProperty().bind(chkShow.selectedProperty());
        tfPasswordVisible.managedProperty().bind(chkShow.selectedProperty());
        pfPassword.visibleProperty().bind(chkShow.selectedProperty().not());
        pfPassword.managedProperty().bind(chkShow.selectedProperty().not());

        // 3. Tự động Disable nút Đăng nhập nếu chưa điền đủ 3 trường
        btnLogin.disableProperty().bind(tfUsername.textProperty().isEmpty()
                .or(pfPassword.textProperty().isEmpty())
                .or(cbRole.valueProperty().isNull()));

        Platform.runLater(tfUsername::requestFocus);
    }

    @FXML
    private void handleLogin(ActionEvent e) {
        String user = tfUsername.getText().trim().toLowerCase();
        String pass = pfPassword.getText();
        String role = cbRole.getValue();

        // Kiểm tra số lần sai
        if (failedCount >= 3) {
            lblError.setText("Sai quá 3 lần. Vui lòng khởi động lại ứng dụng.");
            return;
        }

        // Validate username
        if (!user.matches("[a-z0-9_]{3,20}")) {
            lblError.setText("Username 3–20 ký tự, chỉ chữ thường/số/_");
            return;
        }

        // So khớp tài khoản và mật khẩu
        String[] acc = ACCOUNTS.get(user);
        if (acc == null || !acc[0].equals(pass)) {
            failedCount++;
            lblError.setText("Sai username hoặc mật khẩu (còn %d lần)".formatted(3 - failedCount));
            pfPassword.clear();
            return;
        }

        // So khớp vai trò
        if (!acc[1].equals(role)) {
            lblError.setText("Vai trò không đúng với tài khoản");
            return;
        }

        lblError.setText("");
        // Tạm thời hiển thị Alert đăng nhập thành công (Bước 5 sẽ mở cửa sổ Welcome FXML)
        AlertUtil.info("Đăng nhập thành công!\nXin chào %s (%s)".formatted(user, role));
    }

    @FXML
    private void handleForgot() {
        TextInputDialog d = new TextInputDialog();
        d.setHeaderText("Nhập email để lấy lại mật khẩu");
        d.showAndWait().ifPresent(email -> {
            if (email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
                AlertUtil.info("Đã gửi hướng dẫn tới " + email);
            } else {
                AlertUtil.warn("Email không hợp lệ");
            }
        });
    }
}