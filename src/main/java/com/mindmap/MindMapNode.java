package com.mindmap;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MindMapNode extends StackPane {

    private String id;
    private Rectangle background;
    private TextField textField;
    private double dragStartX, dragStartY;
    private boolean selected;
    private List<MindMapNode> connectedNodes;

    private static final double NODE_WIDTH = 150;
    private static final double NODE_HEIGHT = 60;

    public MindMapNode(double x, double y, String text) {
        this.id = UUID.randomUUID().toString();
        this.connectedNodes = new ArrayList<>();

        // Create background rectangle
        background = new Rectangle(NODE_WIDTH, NODE_HEIGHT);
        background.setFill(Color.LIGHTBLUE);
        background.setStroke(Color.DARKBLUE);
        background.setStrokeWidth(2);
        background.setArcWidth(10);
        background.setArcHeight(10);

        // Create text field
        textField = new TextField(text);
        textField.setStyle("-fx-background-color: transparent; -fx-text-fill: black; " +
                          "-fx-font-size: 14px; -fx-font-weight: bold;");
        textField.setMaxWidth(NODE_WIDTH - 10);
        textField.setAlignment(javafx.geometry.Pos.CENTER);

        getChildren().addAll(background, textField);

        setLayoutX(x);
        setLayoutY(y);

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Drag functionality
        setOnMousePressed(e -> {
            dragStartX = e.getSceneX() - getLayoutX();
            dragStartY = e.getSceneY() - getLayoutY();
            e.consume();
        });

        setOnMouseDragged(e -> {
            setLayoutX(e.getSceneX() - dragStartX);
            setLayoutY(e.getSceneY() - dragStartY);

            // Request parent to redraw connections
            if (getParent() instanceof MindMapCanvas) {
                ((MindMapCanvas) getParent()).redrawConnections();
            }
            e.consume();
        });

        // Double-click to edit text
        textField.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                textField.setEditable(true);
                textField.requestFocus();
                textField.selectAll();
            }
        });

        // Finish editing on Enter
        textField.setOnAction(e -> {
            textField.setEditable(false);
        });

        // Finish editing on focus lost
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                textField.setEditable(false);
            }
        });
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            background.setStroke(Color.RED);
            background.setStrokeWidth(3);
        } else {
            background.setStroke(Color.DARKBLUE);
            background.setStrokeWidth(2);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void addConnection(MindMapNode node) {
        if (!connectedNodes.contains(node)) {
            connectedNodes.add(node);
        }
    }

    public void removeConnection(MindMapNode node) {
        connectedNodes.remove(node);
    }

    public List<MindMapNode> getConnectedNodes() {
        return connectedNodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public double getCenterX() {
        return getLayoutX() + NODE_WIDTH / 2;
    }

    public double getCenterY() {
        return getLayoutY() + NODE_HEIGHT / 2;
    }

    public void setColor(String color) {
        background.setFill(Color.web(color));
    }

    public String getColor() {
        Color color = (Color) background.getFill();
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
}
