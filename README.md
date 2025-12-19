# JavaFX Mind Map Application

A feature-rich mind mapping application built with JavaFX that allows you to create, edit, and visualize hierarchical information structures.

## 🚀 Getting Started

**Seeing "JavaFX runtime components are missing" error?**
→ **[Read QUICKSTART.md](QUICKSTART.md)** for quick solutions
→ **[Read SETUP.md](SETUP.md)** for detailed setup instructions

**Quick fix (with internet):**
```bash
mvn clean install
mvn javafx:run
```

## Features

- **Create Nodes**: Double-click anywhere on the canvas or use the "New Node" button
- **Edit Text**: Double-click on any node's text to edit it
- **Drag & Drop**: Click and drag nodes to reposition them
- **Connect Nodes**: Use "Connect Mode" to create relationships between nodes
- **Delete Nodes**: Select a node and click "Delete Selected"
- **Save/Load**: Save your mind maps to JSON files and load them later
- **Clear All**: Remove all nodes and start fresh
- **Visual Feedback**: Selected nodes are highlighted with a red border

## Requirements

- Java 21 (recommended) or Java 11+
- Maven 3.6 or higher
- JavaFX 21 (automatically downloaded by Maven, or install manually - see SETUP.md)

## Building the Project

```bash
mvn clean compile
```

## Running the Application

```bash
mvn javafx:run
```

## Usage Guide

### Creating Nodes
- **Method 1**: Double-click anywhere on the white canvas
- **Method 2**: Click the "New Node" button in the toolbar

### Editing Node Text
1. Double-click on the text inside a node
2. Type your new text
3. Press Enter or click outside to finish editing

### Moving Nodes
- Click and drag any node to move it around the canvas
- Connections will automatically update as you move nodes

### Connecting Nodes
1. Click the "Connect Mode" button (it will turn green)
2. Click on the first node you want to connect
3. Click on the second node to create the connection
4. Click "Connect Mode" again to exit connection mode

### Deleting Nodes
1. Click on a node to select it (it will have a red border)
2. Click the "Delete Selected" button

### Saving Your Work
1. Click the "Save" button
2. Choose a location and filename
3. Your mind map will be saved as a JSON file

### Loading a Mind Map
1. Click the "Load" button
2. Select a previously saved JSON file
3. Your mind map will be restored

## Project Structure

```
mindmap/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── mindmap/
│       │           ├── MindMapApp.java      # Main application class
│       │           ├── MindMapCanvas.java   # Canvas for drawing
│       │           └── MindMapNode.java     # Node representation
│       └── resources/
│           └── styles.css                    # Application styling
└── README.md
```

## Technical Details

- **Framework**: JavaFX 17
- **Build Tool**: Maven
- **Data Format**: JSON (using Gson library)
- **Architecture**: MVC-inspired design with separate concerns for UI and data

## Features in Detail

### Node Features
- Rounded rectangle design with gradient background
- Editable text fields
- Draggable with visual feedback
- Selection highlighting
- Unique ID for persistence

### Connection Features
- Directional arrows between nodes
- Automatic line redrawing when nodes move
- Bidirectional connection support
- Connection persistence in save files

### Canvas Features
- Infinite drawing space
- Visual connection layer
- Click-to-deselect functionality
- Double-click to create nodes

## License

This project is open source and available for educational purposes.
