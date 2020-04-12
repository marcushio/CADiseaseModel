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
        int virus2Cases = populationData.getVirus2Cases();
        System.out.println("Time Step: " + ++t);
        System.out.println("Total Cases so far: " + totalCases + "\n" + "Currently Infected: " + currInfected + "\n" + "Recovered: " + currRecovered);
        System.out.println("Cases of Virus2: " + virus2Cases);
        //first update the board I think since states flow in one direction we're fine just drawing over them

        gc.setFill(Color.ORANGE);
        gc.fillRect(0 * cellWidth, 0* cellHeight, cellWidth/2, cellHeight );
        gc.strokeRect(0 * cellWidth,0 * cellHeight, cellWidth, cellHeight);
        gc.setFill(Color.RED);
        gc.fillRect(0 * cellWidth + cellWidth/2, 0* cellHeight, cellWidth/2, cellHeight );
        gc.strokeRect(0 * cellWidth,0 * cellHeight, cellWidth, cellHeight);
        for(int x = 0; x < popWidth; x++){
            for(int y = 0; y < popHeight; y++){
                State virus1State = population[x][y].getState();
                State virus2State = population[x][y].getNovelState();
                //make sure we covered all possibilities
                if (virus1State == State.INFECTED && virus2State == State.NOVEL_I){
                    gc.setFill(Color.ORANGE);
                    gc.fillRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                    gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                    gc.setFill(Color.RED);
                    gc.fillRect(x * cellWidth + cellWidth/2, y* cellHeight, cellWidth/2, cellHeight );
                    gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                } else if (virus2State == State.NOVEL_I && virus1State != State.INFECTED ){
                    gc.setFill(Color.ORANGE);
                    gc.fillRect(x * cellWidth, y* cellHeight, cellWidth, cellHeight );
                } else if (population[x][y].getState() == State.SUSCEPTIBLE){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (population[x][y].getState() == State.INFECTED){
                    gc.setFill(Color.RED);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (virus1State == State.RECOVERED && virus2State == State.NOVEL_R){ //check for dual states before single states
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth/2, cellHeight);
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (population[x][y].getState() == State.RECOVERED){
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
//        gc.setFill(Color.ORANGE); this works for the I'I case in cell 0,0
//        gc.fillRect(0 * cellWidth, 0* cellHeight, cellWidth/2, cellHeight );
//        gc.strokeRect(0 * cellWidth,0 * cellHeight, cellWidth, cellHeight);
//        gc.setFill(Color.RED);
//        gc.fillRect(0 * cellWidth + cellWidth/2, 0* cellHeight, cellWidth/2, cellHeight );
//        gc.strokeRect(0 * cellWidth,0 * cellHeight, cellWidth, cellHeight);
        //then update the metrics display
        metrics = new HBox();
        Text casesText = new Text("Cases so far:  " + populationData.getTotalCases());
        Text infectedText = new Text("Current Infected: " + populationData.getCurrInfected());
        Text recoveredText = new Text("Total recovered: " + populationData.getRecovered());
        metrics.getChildren().addAll(casesText, infectedText, recoveredText);
        //primaryStage.show();
    }




}
