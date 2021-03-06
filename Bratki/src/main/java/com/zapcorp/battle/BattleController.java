package com.zapcorp.battle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.zapcorp.missions.MissionBattleConnector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static javax.swing.JOptionPane.showMessageDialog;

public class BattleController {

    @FXML
    private Label agilCounterLabel;

    @FXML
    private Button startButton;

    @FXML
    private Label topLabel;

    @FXML
    private VBox battleFunc;

    private char difficulty;
    private int missionNum;
    private boolean boss;

    MissionBattleConnector connector;

    @FXML
    Stage stage;

    @FXML
    HBox statsBox;

    @FXML
    BorderPane mainPane;

    private Battle battle;

    @FXML
    private Label enemyStats;

    @FXML
    private Label enemyHead;

    @FXML
    private Label yourStats;

    @FXML
    public void initialize() {}

    @FXML
    public void startBattle() {

        startButton.setVisible(false);
        topLabel.setText("Battle in progress..");
        battleFunc.setVisible(true);
        this.difficulty = connector.getDifficulty();
        this.missionNum = connector.getMission();
        this.boss = connector.isBoss();
        if(missionNum!=0) {
            battle = new GeneralBattle();
            battle.setBoss(boss);
            battle.setDifficulty(difficulty);
            battle.setEnemyCount(missionNum);
            battle.init();
        }
        else{
            battle = new SpecialBattle(new GeneralBattle());
            battle.init();
        }
        updateLabelStats();

    }

    @FXML
    public void performAttack() throws IOException {

        battle.performAttack();
        if(!battle.isHeroDead() && !battle.isEnemyDead()) {
            updateLabelStats();
        }
        else {
            if (battle.isHeroDead()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mission failed!");
                alert.setTitle("Coolest game ever");
                alert.setHeaderText(null);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Mission completed successfully!");
                alert.setTitle("Coolest game ever");
                alert.setHeaderText(null);
                alert.show();
            }

            stage = (Stage) mainPane.getScene().getWindow();
            BorderPane root;
            root = FXMLLoader.load(
                    Paths.get("src/main/resources/com/zapcorp/menu/MainMenu.fxml").toUri().toURL());
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }

    }

    private void updateLabelStats() {

        yourStats.setText(
                "HP: " + battle.getHeroHp()+
                "\nAttack: " + battle.getHeroAttack()
                );
        if(battle.getAgilCounter()==0) {
            agilCounterLabel.setText(
                "\nCare! "+
                battle.getName()
                +" attacks you next turn"
                );
            agilCounterLabel.setTextFill(Color.web("#F44336"));
        }
        else {
            agilCounterLabel.setText(
                    "\nHits left till "+
                    battle.getName()
                    +" attacks you: "+
                    battle.getAgilCounter()
            );
            agilCounterLabel.setTextFill(Color.web("#d8d8d8"));
        }
        enemyStats.setText(
                "\nHP: " + battle.getEnemyHp()+
                "\nAttack: " + battle.getEnemyAttack()+
                "\nAgility: " + battle.getEnemyAgility()+
                "\n\nEnemies left: "+ (battle.getEnemyCount()+1)
                );
        try {
            enemyHead.setGraphic(new ImageView(new Image(
                    Files.newInputStream(Paths.get("src/main/resources/" + battle.getIcon())))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        enemyHead.setText(
                "Current enemy:\n" +
                battle.getName());

    }

    public void init(MissionBattleConnector connector) {
        this.connector = connector;
    }

    public void startSpecialMission() {

    }

}
