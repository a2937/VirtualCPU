package edu.wvup.acottri9.virtualcpu;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class CPUApp extends Application
{
    protected static final Logger logger = LogManager.getLogger();
    private static VirtualCPU cpu;

    public static void main(String[] args)
    {
        cpu = new VirtualCPU();
        //PropertiesConfigurator is used to configure logger from properties file
        launch();
    }

    @Override
    public void start(Stage stage)
    {

        Label aLabel = new Label("a: ");
        Label xLabel = new Label("x: ");
        Label yLabel = new Label("y: ");
        Label programCounter = new Label("Instruction Length: ");
        //Adding labels for nodes
        HBox box = new HBox(5);
        box.setPadding(new Insets(25, 5, 5, 50));
        box.getChildren().addAll(aLabel, xLabel, yLabel, programCounter);

        BorderPane pane = new BorderPane(box);

        //Creating a menu
        Menu fileMenu = new Menu("File");
        //Creating menu Items
        MenuItem openImageItem = new MenuItem("Open Text File");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openImageItem, exitItem);
        //Creating a File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Commands");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        //Adding action on the menu openImageItem
        openImageItem.setOnAction(event ->
        {
            //Opening a dialog box
            File file = fileChooser.showOpenDialog(stage);
            try
            {
                if (file != null)
                {
                    cpu.processFile(file);
                    aLabel.setText(String.format("a: %s", cpu.getValueInRegisterA()));
                    xLabel.setText(String.format("x: %s", cpu.getValueInRegisterX()));
                    yLabel.setText(String.format("y: %s", cpu.getValueInRegisterY()));
                    programCounter.setText("Instruction Length: " + cpu.getProgramCounter());

                }
            }
            catch (FileNotFoundException e)
            {
                logger.error(e.getMessage());
            }
        });
        exitItem.setOnAction(event ->
            System.exit(0));
        //Creating a menu bar and adding menu to it.
        MenuBar menuBar = new MenuBar(fileMenu);

        menuBar.setTranslateX(3);
        menuBar.setTranslateY(3);
        pane.setTop(menuBar);

        //Setting the stage
        Group root = new Group(pane);
        Scene scene = new Scene(root, 595, 355, Color.BEIGE);
        stage.setTitle("Virtual CPU Application");
        stage.setScene(scene);
        stage.show();
    }
}
