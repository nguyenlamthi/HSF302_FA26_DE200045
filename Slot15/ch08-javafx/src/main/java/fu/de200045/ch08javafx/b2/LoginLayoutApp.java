package fu.de200045.ch08javafx.b2;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginLayoutApp extends Application {

    // Danh sách tài khoản hợp lệ cố định (username -> password)
    private static final Map<String, String> ACCOUNTS = Map.of(
            "admin", "Admin@123",
            "student", "Student@123");

    // Viền đỏ nét đứt dùng để hiển thị ranh giới layout khi bật debug
    private static final Border DEBUG_BORDER = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1)));

    // Giữ Scene làm field để có thể đổi giao diện bằng scene.setRoot(...)
    private Scene scene;
    private String rememberedUser;                              // Lưu tên tài khoản nếu chọn "Ghi nhớ"
    private final List<Region> containers = new ArrayList<>();  // Quản lý các layout container để vẽ viền debug

    @Override
    public void start(Stage stage) {
        // Tạm thời hiển thị placeholder trước khi ghép các vùng Header/Center/Footer
        scene = new Scene(new StackPane(new Label("B2 - Khởi tạo LoginLayoutApp")), 680, 480);
        stage.setScene(scene);
        stage.setTitle("B2 - Layout & Form đăng nhập");
        stage.setMinWidth(480);
        stage.setMinHeight(400);
        stage.show();
    }

    // Helper: đưa container vào danh sách theo dõi debug viền
    private <T extends Region> T track(T region) {
        containers.add(region);
        return region;
    }

    public static void main(String[] args) {
        launch(args);
    }
}