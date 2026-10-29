package fu.de200045.ch08javafx.b3;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RegisterApp extends Application {

    // Regex mật khẩu mạnh: ít nhất 8 ký tự, có chữ thường, chữ hoa, số và ký tự đặc biệt
    private static final String STRONG_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
    private static final String[] LEVEL_TEXT  = {"Rất yếu", "Yếu", "Trung bình", "Khá", "Mạnh"};
    private static final String[] LEVEL_COLOR = {"#d93025", "#d93025", "#f29900", "#1a73e8", "#188038"};

    private Scene scene;
    private Parent registerView;

    // ===== Khai báo Controls cho Form Đăng Ký =====
    private final TextField tfFullName = new TextField();
    private final TextField tfEmail = new TextField();
    private final TextField tfUsername = new TextField();
    private final PasswordField pfPassword = new PasswordField();
    private final PasswordField pfConfirm = new PasswordField();
    private final ToggleGroup tgGender = new ToggleGroup();
    private final RadioButton rbMale = new RadioButton("Nam");
    private final RadioButton rbFemale = new RadioButton("Nữ");
    private final RadioButton rbOther = new RadioButton("Khác");
    private final DatePicker dpDob = new DatePicker();
    private final ComboBox<String> cbMajor = new ComboBox<>();
    private final CheckBox chkSport = new CheckBox("Thể thao");
    private final CheckBox chkMusic = new CheckBox("Âm nhạc");
    private final CheckBox chkGame = new CheckBox("Game");
    private final CheckBox chkReading = new CheckBox("Đọc sách");
    private final CheckBox chkAgree = new CheckBox("Tôi đồng ý với điều khoản sử dụng");
    private final ProgressBar pbStrength = new ProgressBar(0);
    private final Label lblStrength = new Label();
    private final Button btnRegister = new Button("Đăng ký");
    private final Button btnReset = new Button("Làm lại");

    // ===== Label thông báo lỗi riêng cho từng field =====
    private final Label errFullName = err(), errEmail = err(), errUsername = err(), errPassword = err(),
            errConfirm = err(), errGender = err(), errDob = err(), errMajor = err();

    @Override
    public void start(Stage stage) {
        // Tạm thời hiển thị để kiểm tra khởi tạo Bước 1
        scene = new Scene(new StackPane(new Label("B3 - Khởi tạo RegisterApp")), 580, 660);
        stage.setScene(scene);
        stage.setTitle("B3 - Đăng ký & Đăng nhập");
        stage.show();
    }

    // Helper tạo Label lỗi màu đỏ chữ nhỏ 11px
    private static Label err() {
        Label l = new Label();
        l.setStyle("-fx-text-fill: #d93025; -fx-font-size: 11;");
        l.setWrapText(true);
        return l;
    }

    public static void main(String[] args) {
        launch(args);
    }
}