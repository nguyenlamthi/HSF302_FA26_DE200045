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

    // ==========================================
    // TODO 1: Khai báo control là field của class
    // ==========================================
    // Lý do: Các method khác (tính tiền, reset, bắt sự kiện) đều cần đọc/ghi giá trị của controls
    private final TextField tfName = new TextField();
    private final ComboBox<String> cbDrink = new ComboBox<>();

    // ToggleGroup gom các RadioButton lại để tại 1 thời điểm chỉ chọn được 1 size
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
        // Tạm thời để Stage hiện cửa sổ trống để kiểm tra compile
        VBox root = new VBox(new Label("TODO 1: Đã khai báo xong controls"));
        stage.setScene(new Scene(root, 400, 200));
        stage.setTitle("B1 - DrinkOrderApp");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}