package com.mindmap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MindMapApp extends Application {

    private MindMapCanvas canvas;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Mind Map - JavaFX");

        BorderPane root = new BorderPane();

        // Create the mind map canvas
        canvas = new MindMapCanvas();
        root.setCenter(canvas);

        // Create toolbar
        HBox toolbar = createToolbar();
        root.setTop(toolbar);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        Button addNodeBtn = new Button("New Node");
        addNodeBtn.setOnAction(e -> canvas.addNewNode());

        Button deleteNodeBtn = new Button("Delete Selected");
        deleteNodeBtn.setOnAction(e -> canvas.deleteSelectedNode());

        Button connectBtn = new Button("Connect Mode");
        connectBtn.setOnAction(e -> {
            canvas.toggleConnectionMode();
            if (canvas.isConnectionMode()) {
                connectBtn.setText("Connection Mode: ON");
                connectBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            } else {
                connectBtn.setText("Connect Mode");
                connectBtn.setStyle("");
            }
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> saveMindMap());

        Button loadBtn = new Button("Load");
        loadBtn.setOnAction(e -> loadMindMap());

        Button clearBtn = new Button("Clear All");
        clearBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Clear Mind Map");
            alert.setHeaderText("Are you sure you want to clear the entire mind map?");
            alert.setContentText("This action cannot be undone.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                canvas.clear();
            }
        });

        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        Label instructions = new Label("Double-click: Add Node | Drag: Move Node | Click: Select");
        instructions.setStyle("-fx-font-style: italic; -fx-text-fill: #666;");

        toolbar.getChildren().addAll(
            addNodeBtn, deleteNodeBtn, connectBtn,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            saveBtn, loadBtn, clearBtn,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            instructions
        );

        return toolbar;
    }

    private void saveMindMap() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Mind Map");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Mind Map Files", "*.json")
        );
        fileChooser.setInitialFileName("mindmap.json");

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            if (canvas.saveToFile(file)) {
                showAlert("Success", "Mind map saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save mind map.", Alert.AlertType.ERROR);
            }
        }
    }

    private void loadMindMap() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Mind Map");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Mind Map Files", "*.json")
        );

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            if (canvas.loadFromFile(file)) {
                showAlert("Success", "Mind map loaded successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to load mind map.", Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
