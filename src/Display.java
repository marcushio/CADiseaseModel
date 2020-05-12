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

public class Display {
    private int X_FRONTIER = 1000;
    private int Y_FRONTIER = 1000;
    private int NODE_RADIUS = 6;
    private double SCALE_FACTOR = NODE_RADIUS *2;
    private Pane modelDisplay = new Pane();
    private ScrollPane modelScroll = new ScrollPane(modelDisplay);
    private Population population;
    private List<Circle> nodeViews = new ArrayList<>();
    private Group networkShapes = new Group();
    /**
     * Constructor
     * @param primaryStage
     */
    public Display(Stage primaryStage, Population population){
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
        Map<Coordinate, Agent> coordinateAgentMap = population.getCoordinateAgentMap();
        Set<Coordinate> coordinates = coordinateAgentMap.keySet();
        int i = 0; int j = 0;
        for(Coordinate coordinate : coordinates){
            Agent agent = coordinateAgentMap.get(coordinate);
            Circle newCircle = new Circle(agent.getX()*SCALE_FACTOR,agent.getY()*SCALE_FACTOR, NODE_RADIUS, Paint.valueOf("blue"));
            newCircle.setStrokeWidth(NODE_RADIUS/2);
            newCircle.fillProperty().bind(agent.getColor());
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
        for(Agent neighbor : node.getNeighborhood()){
            //Coordinate neighborCoordinate = neighbor.getCoordinate();
            int neighborX = neighbor.getX(); int neighborY = neighbor.getY();
            networkShapes.getChildren().add(new Line(
                    scaleLineCoordinate(node.getX()),
                    scaleLineCoordinate(node.getY()),
                    scaleLineCoordinate(neighborX),
                    scaleLineCoordinate(neighborY))
            );
        }
    }

}