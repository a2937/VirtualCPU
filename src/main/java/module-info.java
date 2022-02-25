module edu.wvup.acottri9.virtualcpu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens edu.wvup.acottri9.virtualcpu to javafx.fxml;
    exports edu.wvup.acottri9.virtualcpu;
}
