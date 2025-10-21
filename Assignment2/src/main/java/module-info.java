module com.columbus {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.columbus to javafx.fxml;
    exports com.columbus;
}
