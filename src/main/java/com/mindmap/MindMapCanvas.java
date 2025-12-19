package com.mindmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MindMapCanvas extends Pane {

    private Canvas connectionCanvas;
    private List<MindMapNode> nodes;
    private MindMapNode selectedNode;
    private boolean connectionMode;
    private MindMapNode connectionStartNode;

    public MindMapCanvas() {
        nodes = new ArrayList<>();

        // Create canvas for drawing connections
        connectionCanvas = new Canvas(2000, 2000);
        getChildren().add(connectionCanvas);

        setStyle("-fx-background-color: white;");

        // Handle double-click to add node
        setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && e.getTarget() == this) {
                addNewNode(e.getX(), e.getY());
            }
        });

        // Handle click on canvas to deselect
        connectionCanvas.setOnMouseClicked(e -> {
            if (selectedNode != null) {
                selectedNode.setSelected(false);
                selectedNode = null;
            }
        });
    }

    public void addNewNode() {
        addNewNode(400 + Math.random() * 200, 300 + Math.random() * 200);
    }

    public void addNewNode(double x, double y) {
        MindMapNode node = new MindMapNode(x - 75, y - 30, "New Node");

        node.setOnMouseClicked(e -> {
            if (connectionMode) {
                handleConnectionModeClick(node);
            } else {
                handleNodeSelection(node);
            }
            e.consume();
        });

        nodes.add(node);
        getChildren().add(node);
        redrawConnections();
    }

    private void handleNodeSelection(MindMapNode node) {
        if (selectedNode != null) {
            selectedNode.setSelected(false);
        }
        selectedNode = node;
        node.setSelected(true);
    }

    private void handleConnectionModeClick(MindMapNode node) {
        if (connectionStartNode == null) {
            connectionStartNode = node;
            node.setSelected(true);
        } else {
            if (connectionStartNode != node) {
                connectionStartNode.addConnection(node);
                node.addConnection(connectionStartNode);
                redrawConnections();
            }
            connectionStartNode.setSelected(false);
            connectionStartNode = null;
        }
    }

    public void deleteSelectedNode() {
        if (selectedNode != null) {
            // Remove connections
            for (MindMapNode node : nodes) {
                node.removeConnection(selectedNode);
            }

            nodes.remove(selectedNode);
            getChildren().remove(selectedNode);
            selectedNode = null;
            redrawConnections();
        }
    }

    public void toggleConnectionMode() {
        connectionMode = !connectionMode;
        if (!connectionMode && connectionStartNode != null) {
            connectionStartNode.setSelected(false);
            connectionStartNode = null;
        }
    }

    public boolean isConnectionMode() {
        return connectionMode;
    }

    public void redrawConnections() {
        GraphicsContext gc = connectionCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, connectionCanvas.getWidth(), connectionCanvas.getHeight());

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);

        for (MindMapNode node : nodes) {
            for (MindMapNode connectedNode : node.getConnectedNodes()) {
                double startX = node.getCenterX();
                double startY = node.getCenterY();
                double endX = connectedNode.getCenterX();
                double endY = connectedNode.getCenterY();

                gc.strokeLine(startX, startY, endX, endY);

                // Draw arrow head
                drawArrowHead(gc, startX, startY, endX, endY);
            }
        }
    }

    private void drawArrowHead(GraphicsContext gc, double startX, double startY, double endX, double endY) {
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowLength = 10;
        double arrowAngle = Math.PI / 6;

        double x1 = endX - arrowLength * Math.cos(angle - arrowAngle);
        double y1 = endY - arrowLength * Math.sin(angle - arrowAngle);
        double x2 = endX - arrowLength * Math.cos(angle + arrowAngle);
        double y2 = endY - arrowLength * Math.sin(angle + arrowAngle);

        gc.strokeLine(endX, endY, x1, y1);
        gc.strokeLine(endX, endY, x2, y2);
    }

    public void clear() {
        nodes.clear();
        getChildren().clear();
        getChildren().add(connectionCanvas);
        selectedNode = null;
        connectionStartNode = null;
        connectionMode = false;
        redrawConnections();
    }

    public boolean saveToFile(File file) {
        try {
            List<NodeData> nodeDataList = new ArrayList<>();
            Map<String, Integer> nodeIndexMap = new HashMap<>();

            // First pass: create node data and index map
            for (int i = 0; i < nodes.size(); i++) {
                MindMapNode node = nodes.get(i);
                nodeIndexMap.put(node.getId(), i);

                NodeData data = new NodeData();
                data.id = node.getId();
                data.text = node.getText();
                data.x = node.getLayoutX();
                data.y = node.getLayoutY();
                data.color = node.getColor();
                data.collapsed = node.isCollapsed();
                data.connections = new ArrayList<>();

                nodeDataList.add(data);
            }

            // Second pass: add connections using indices
            for (int i = 0; i < nodes.size(); i++) {
                MindMapNode node = nodes.get(i);
                NodeData data = nodeDataList.get(i);

                for (MindMapNode connected : node.getConnectedNodes()) {
                    Integer connectedIndex = nodeIndexMap.get(connected.getId());
                    if (connectedIndex != null && !data.connections.contains(connectedIndex)) {
                        data.connections.add(connectedIndex);
                    }
                }
            }

            MindMapData mindMapData = new MindMapData();
            mindMapData.nodes = nodeDataList;

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(mindMapData, writer);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadFromFile(File file) {
        try {
            Gson gson = new Gson();
            MindMapData mindMapData;

            try (FileReader reader = new FileReader(file)) {
                mindMapData = gson.fromJson(reader, MindMapData.class);
            }

            clear();

            List<MindMapNode> loadedNodes = new ArrayList<>();

            // First pass: create all nodes
            for (NodeData data : mindMapData.nodes) {
                MindMapNode node = new MindMapNode(data.x, data.y, data.text);
                node.setId(data.id);
                node.setColor(data.color);

                // Restore collapsed state
                if (data.collapsed != null && data.collapsed) {
                    node.setCollapsed(true);
                }

                node.setOnMouseClicked(e -> {
                    if (connectionMode) {
                        handleConnectionModeClick(node);
                    } else {
                        handleNodeSelection(node);
                    }
                    e.consume();
                });

                loadedNodes.add(node);
                nodes.add(node);
                getChildren().add(node);
            }

            // Second pass: restore connections
            for (int i = 0; i < mindMapData.nodes.size(); i++) {
                NodeData data = mindMapData.nodes.get(i);
                MindMapNode node = loadedNodes.get(i);

                for (Integer connectedIndex : data.connections) {
                    if (connectedIndex < loadedNodes.size()) {
                        MindMapNode connectedNode = loadedNodes.get(connectedIndex);
                        node.addConnection(connectedNode);
                    }
                }
            }

            redrawConnections();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class MindMapData {
        List<NodeData> nodes;
    }

    private static class NodeData {
        String id;
        String text;
        double x;
        double y;
        String color;
        Boolean collapsed;
        List<Integer> connections;
    }
}
