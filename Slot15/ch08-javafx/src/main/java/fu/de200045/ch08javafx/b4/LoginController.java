package fu.de200045.ch08javafx.b4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    // Các field có @FXML và tên TRÙNG KHỚP 100% với fx:id trong file login-view.fxml
    @FXML private TextField tfUsername;
    @FXML private TextField tfPasswordVisible;
    @FXML private PasswordField pfPassword;
    @FXML private CheckBox chkShow;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    // Các method có @FXML và tên TRÙNG KHỚP với onAction="#..." trong login-view.fxml
    @FXML
    private void handleLogin(ActionEvent e) {
        // Sẽ viết logic kiểm tra đăng nhập ở Bước 4
    }

    @FXML
    private void handleForgot() {
        // Sẽ viết logic quên mật khẩu ở Bước 4
    }
}