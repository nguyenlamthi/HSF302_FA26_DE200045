package fu.de200045.ch08javafx.b2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginLayoutApp extends Application {

    // Danh sách tài khoản hợp lệ cố định
    private static final Map<String, String> ACCOUNTS = Map.of(
            "admin", "Admin@123",
            "student", "Student@123");

    // Viền đỏ nét đứt dùng để hiển thị ranh giới layout khi bật debug
    private static final Border DEBUG_BORDER = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1)));

    private Scene scene;
    private String rememberedUser;
    private final List<Region> containers = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        // Tạm ghép Header và Footer vào BorderPane để kiểm tra Bước 2
        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(new StackPane(new Label("Khu vực Center (sẽ hoàn thiện ở Bước 3 & 4)")));
        root.setBottom(buildFooter());

        scene = new Scene(root, 680, 480);
        stage.setScene(scene);
        stage.setTitle("B2 - Layout & Form đăng nhập");
        stage.setMinWidth(480);
        stage.setMinHeight(400);
        stage.show();
    }

    // =========================================================================
    // BƯỚC 2: Header — HBox: logo, tiêu đề, SPACER giãn, version bên phải
    // =========================================================================
    private Node buildHeader() {
        Label logo = new Label("HSF");
        logo.setStyle("-fx-background-color: #f27024; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 4 8;");
        Label title = new Label("Hệ thống quản lý sinh viên");
        title.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        Label version = new Label("v1.0");
        version.setStyle("-fx-text-fill: #cfd8dc;");

        // Spacer chiếm hết chỗ trống dư thừa -> "đẩy" version dạt sang sát phải
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, logo, title, spacer, version);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 16, 10, 16));
        header.setStyle("-fx-background-color: #263238;");
        return track(header);
    }

    // =========================================================================
    // BƯỚC 2: Footer — HBox: CheckBox debug | spacer | bản quyền
    // =========================================================================
    private Node buildFooter() {
        CheckBox chkDebug = new CheckBox("Hiện viền layout (debug)");
        chkDebug.setOnAction(e -> containers.forEach(
                r -> r.setBorder(chkDebug.isSelected() ? DEBUG_BORDER : null)));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox footer = new HBox(10, chkDebug, spacer, new Label("© 2026 HSF302 — Chapter 08"));
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(6, 16, 6, 16));
        footer.setStyle("-fx-background-color: #cfd8dc;");
        return track(footer);
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