package com.zapcorp.myhero;

import com.zapcorp.game.ContextOriginator;
import com.zapcorp.game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;

public class MyHeroController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView heroView;

    @FXML
    private Label attack;
    @FXML
    private Label hp;
    @FXML
    private Label money;

    private Stage stage;

    @FXML
    public void initialize() {
        try {
            Game game = ContextOriginator.getCurrentState();
            attack.setText(String.valueOf(game.getHeroAttck()));
            hp.setText(String.valueOf(game.getHeroHp()));
            money.setText("10000$");
            heroView.setImage(new Image(Files.newInputStream(Paths.get("src/main/resources/" + game.getViewPath())),
                    300, 400, false, true));
        }
        catch (Exception e) {
            System.err.println("Loading hero view error.");
            e.printStackTrace();
        }
    }

    @FXML
    private void back() throws IOException {
        stage = (Stage) mainPane.getScene().getWindow();
        BorderPane root;
        root = (BorderPane) FXMLLoader.load(
                Paths.get("src/main/resources/com/zapcorp/menu/MainMenu.fxml").toUri().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
