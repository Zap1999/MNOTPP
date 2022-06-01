module com.zapcorp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.swing;
    requires java.sql;
    requires java.desktop;

    opens com.zapcorp.battle to javafx.fxml;
    opens com.zapcorp.chat to javafx.fxml;
    opens com.zapcorp.currency to javafx.fxml;
    opens com.zapcorp.db to javafx.fxml;
    opens com.zapcorp.enemy to javafx.fxml;
    opens com.zapcorp.game to javafx.fxml;
    opens com.zapcorp.items to javafx.fxml;
    opens com.zapcorp.menu to javafx.fxml;
    opens com.zapcorp.missions to javafx.fxml;
    opens com.zapcorp.myhero to javafx.fxml;
    opens com.zapcorp.start to javafx.fxml;
    exports com.zapcorp.start;
}
