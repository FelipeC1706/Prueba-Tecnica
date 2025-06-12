package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView login = new LoginView();
        login.showLogin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
