import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *  @author Marcus Trujillo
 *  @version 4/2/2020
 *
 * This class is responsible for displaying everything
 */

public class Display {
    int t = 0;
    Stage primaryStage;
    private VBox root;
    private HBox metrics;
    private Canvas populationGraphic;
    private GraphicsContext gc;
    //settings used for 20x20
    // private int cellHeight = 40, cellWidth = 40;
    // private int popHeight = 20, popWidth = 20;
    private int cellHeight = 20, cellWidth = 20;
    private int popHeight = 40, popWidth = 40;


    public Display(Stage primaryStage, Population population){
        this.primaryStage = primaryStage;
        root = new VBox();
        metrics = new HBox();
        Text casesText = new Text("Cases so far:  1    " );
        Text infectedText = new Text("Current Infected: 1    " );
        Text recoveredText = new Text("Total recovered: 0    " );
        metrics.getChildren().addAll(casesText, infectedText, recoveredText);

        populationGraphic = new Canvas(800, 800);
        gc = populationGraphic.getGraphicsContext2D();

        //root.getChildren().add(metrics);
        root.getChildren().add(populationGraphic);
        primaryStage.setTitle("Covid-19 modeling");
        primaryStage.setScene(new Scene(root, 810, 830));
        primaryStage.show();
    }

    /**
     * Update the display showing the new states of cells and new metrics numbers
     * @param populationData represented by 2d array of Agent
     */
    public void update(Population populationData){
        Agent[][] population = populationData.getPopulation();
        int totalCases = populationData.getTotalCases() ;
        int currInfected = populationData.getCurrInfected();
        int currRecovered = populationData.getRecovered();
        System.out.println("Time Step: " + ++t);
        System.out.println("Total Cases so far: " + totalCases + "\n" + "Currently Infected: " + currInfected + "\n" + "Recovered: " + currRecovered);
        //first update the board I think since states flow in one direction we're fine just drawing over them
        for(int x = 0; x < popWidth; x++){
            for(int y = 0; y < popHeight; y++){
                if (population[x][y].getState() == State.SUSCEPTIBLE){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (population[x][y].getState() == State.INFECTED){
                    gc.setFill(Color.RED);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (population[x][y].getState() == State.RECOVERED){
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
        //then update the metrics display
        metrics = new HBox();
        Text casesText = new Text("Cases so far:  " + populationData.getTotalCases());
        Text infectedText = new Text("Current Infected: " + populationData.getCurrInfected());
        Text recoveredText = new Text("Total recovered: " + populationData.getRecovered());
        metrics.getChildren().addAll(casesText, infectedText, recoveredText);
        //primaryStage.show();
    }




}
