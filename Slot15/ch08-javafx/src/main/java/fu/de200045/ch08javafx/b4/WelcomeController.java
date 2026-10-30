package fu.de200045.ch08javafx.b4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML private Label lblWelcome;

    // Phương thức công khai để LoginController truyền dữ liệu sang
    public void setUser(String user, String role) {
        lblWelcome.setText("Xin chào %s (%s)!".formatted(user, role));
    }

    // Đóng cửa sổ khi bấm Đăng xuất
    @FXML
    private void handleLogout(ActionEvent e) {
        ((Stage) lblWelcome.getScene().getWindow()).close();
    }
}