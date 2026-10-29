package fu.de200045.ch08javafx.b1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;

public class DrinkOrderApp extends Application {

    // Bảng giá menu — LinkedHashMap giữ đúng thứ tự chèn khi đổ vào ComboBox
    private static final Map<String, Integer> MENU = new LinkedHashMap<>();
    static {
        MENU.put("Cà phê sữa", 25_000);
        MENU.put("Trà sữa", 30_000);
        MENU.put("Nước cam", 28_000);
        MENU.put("Sinh tố bơ", 35_000);
    }
    private static final int TOPPING_PRICE = 5_000;

    // TODO 1: Khai báo control là field của class
    private final TextField tfName = new TextField();
    private final ComboBox<String> cbDrink = new ComboBox<>();
    private final ToggleGroup tgSize = new ToggleGroup();
    private final RadioButton rbS = new RadioButton("S");
    private final RadioButton rbM = new RadioButton("M (+5.000)");
    private final RadioButton rbL = new RadioButton("L (+10.000)");
    private final CheckBox chkPearl = new CheckBox("Trân châu (+5.000)");
    private final CheckBox chkJelly = new CheckBox("Thạch (+5.000)");
    private final CheckBox chkLessSugar = new CheckBox("Ít đường");
    private final TextField tfQty = new TextField("1");
    private final Button btnCalc = new Button("Tính tiền");
    private final Button btnClear = new Button("Làm lại");
    private final Label lblResult = new Label("Chưa có đơn hàng");
    private final TextArea taLog = new TextArea();

    @Override
    public void start(Stage stage) {
        // ==========================================
        // TODO 2: Cấu hình control
        // ==========================================
        tfName.setPromptText("Nhập tên khách hàng");

        cbDrink.getItems().addAll(MENU.keySet());
        cbDrink.setPromptText("-- Chọn đồ uống --");
        cbDrink.setMaxWidth(Double.MAX_VALUE);

        // Gom nhóm RadioButton size
        rbS.setToggleGroup(tgSize);
        rbM.setToggleGroup(tgSize);
        rbL.setToggleGroup(tgSize);
        // Gắn phụ thu tương ứng vào userData
        rbS.setUserData(0);
        rbM.setUserData(5_000);
        rbL.setUserData(10_000);
        tgSize.selectToggle(rbM);            // Mặc định chọn size M

        tfQty.setPrefColumnCount(4);
        taLog.setEditable(false);           // Nhật ký chỉ đọc
        taLog.setPrefRowCount(8);
        taLog.setTooltip(new Tooltip("Double-click để xóa nhật ký"));
        btnCalc.setDefaultButton(true);     // Phím Enter = bấm "Tính tiền"

        // Tạm giữ root đơn giản để kiểm tra compile TODO 2
        VBox root = new VBox(new Label("TODO 2: Đã cấu hình xong các controls"));
        stage.setScene(new Scene(root, 400, 200));
        stage.setTitle("B1 - DrinkOrderApp");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}