import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcus Trujillo
 * @version 5/2/20
 */

public class MapDisplay {
    private int X_FRONTIER = 1000;
    private int Y_FRONTIER = 1000;
    private int NODE_RADIUS = 10;
    private double SCALE_FACTOR = NODE_RADIUS *3;
    private HBox root;
    private Pane modelDisplay = new Pane();
    private ScrollPane modelScroll = new ScrollPane(modelDisplay);
    private MapPopulation population;
    private List<Circle> nodeViews = new ArrayList<>();
    private ListProperty<String> logEntries = new SimpleListProperty<String>();
    private SimpleStringProperty lastEntry = new SimpleStringProperty();
    private Group networkShapes = new Group();
    /**
     * Constructor
     * @param primaryStage
     */
    public MapDisplay(Stage primaryStage, MapPopulation population){
        this.population = population;
        Scene scene = new Scene(makeRoot(),primaryStage.getMaxWidth(), primaryStage.getMaxWidth());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        makeNodes();
    }

    private BorderPane makeRoot(){
        BorderPane root = new BorderPane();
        root.setCenter(modelScroll);
        return root;
    }

    private void makeNodes(){
        //Map<Coordinate,Node> coordinateNodeMap = network.getCoordinateNodeMap();
        //Set<Coordinate> coordinates = coordinateNodeMap.keySet();
        int i = 0; int j = 0;
        //for(Coordinate coordinate : coordinates){
        for(Agent agent : population.getPopulation() ){
            //Node ithNode = coordinateNodeMap.get(coordinate);
            Circle newCircle = new Circle(agent.getxPosition()*SCALE_FACTOR,agent.getyPosition()*SCALE_FACTOR, NODE_RADIUS, Paint.valueOf("blue"));
            newCircle.setStrokeWidth(NODE_RADIUS/2);
            newCircle.fillProperty().bind(agent.getColor());
            //newCircle.strokeProperty().bind(ithNode.getStrokeColor()); I think strokes should always be blk
            nodeViews.add(newCircle);
            networkShapes.getChildren().add(newCircle);
            addOutgoingLines(agent);
        }

        Translate translate = new Translate();
        translate.setY(NODE_RADIUS*4+ networkShapes.getTranslateY());
        translate.setX(NODE_RADIUS*4+ networkShapes.getTranslateX());
        networkShapes.getTransforms().add(translate);
        modelDisplay.getChildren().add(networkShapes);

    }

    private double scaleLineCoordinate(double coordinate){
        return (coordinate * SCALE_FACTOR);
    }

    private void addOutgoingLines(Agent node) {
        for(Agent neighbor : node.getNeighbors()){
            //Coordinate neighborCoordinate = neighbor.getCoordinate();
            int neighborX = neighbor.getxPosition(); int neighborY = neighbor.getyPosition();
            networkShapes.getChildren().add(new Line(
                    scaleLineCoordinate(node.getxPosition()),
                    scaleLineCoordinate(node.getyPosition()),
                    scaleLineCoordinate(neighborX),
                    scaleLineCoordinate(neighborY))
            );
        }
    }

}
