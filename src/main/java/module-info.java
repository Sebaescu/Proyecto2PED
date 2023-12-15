module com.sebaescu.proyecto2ped {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sebaescu.proyecto2ped to javafx.fxml;
    exports com.sebaescu.proyecto2ped;
}
