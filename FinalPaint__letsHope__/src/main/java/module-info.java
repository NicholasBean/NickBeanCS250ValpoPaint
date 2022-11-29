module finalpaint.finalpaint__letshope__ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens finalpaint.finalpaint__letshope__ to javafx.fxml;
    exports finalpaint.finalpaint__letshope__;
}