package fu.de200045.ui;

import fu.de200045.StudentManagementApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // TODO 4.3a: Khởi động Spring Boot trong môi trường JavaFX
        context = new SpringApplicationBuilder(StudentManagementApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO 4.3b: FXMLLoader "/student-view.fxml" + loader.setControllerFactory(context::getBean)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student-view.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();

        // TODO 4.3c: Scene 1000x600 + "/style.css"; title "Student Management System"; min 900x500; show
        Scene scene = new Scene(root, 1000, 600);
        var cssResource = getClass().getResource("/style.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }

        stage.setTitle("Student Management System");
        stage.setMinWidth(900);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // TODO 4.3d: context.close(); Platform.exit();
        if (context != null) {
            context.close();
        }
        Platform.exit();
    }
}
