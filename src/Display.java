import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

/**
 *  @author Marcus Trujillo
 *  @version 4/2/2020
 *
 * This class is responsible for displaying everything
 */

public class Display {
    private Stage primaryStage;
    private VBox root;
    private HBox metrics;
    private GridPane populationGraphic;
    private Population population;
    //settings used for 20x20
    // private int cellHeight = 40, cellWidth = 40;
    // private int popHeight = 20, popWidth = 20;
    private int cellHeight = 20, cellWidth = 20;
    private int popHeight = 40, popWidth = 40;
    private int windowHeight = 830, windowWidth = 810;

    public Display(Stage primaryStage, Population population){
        this.primaryStage = primaryStage;
        this.population = population;
        this.popHeight = population.getHeight();
        this.popWidth = population.getWidth();
        cellHeight = windowHeight / popHeight > 2 ? windowHeight / popHeight : 2 ;
        cellWidth = windowWidth / popWidth > 2 ? windowWidth / popWidth : 2 ;
        root = new VBox();
        metrics = new HBox();
        //populationGraphic = new Canvas(800, 800);
        makeGrid();
        //root.getChildren().add(metrics);
        root.getChildren().add(populationGraphic);
        primaryStage.setTitle("Covid-19 modeling");
        primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
        primaryStage.show();
    }

    private void makeGrid(){
        this.populationGraphic = new GridPane();
        for(int x = 0; x < popWidth; x++){
            for(int y = 0; y < popHeight; y++ ){
                Agent thisAgent = population.getAgent(x,y);
                Rectangle newRectangle = new Rectangle(0, 0, cellWidth, cellHeight) ;
                newRectangle.fillProperty().bind(thisAgent.getColor());
                newRectangle.setStroke(Color.BLACK);
                this.populationGraphic.add(newRectangle, y, x ); //oddly gridpanes add params are (object, colIndex, rowIndex)
            }
        }
    }



    /**
     * Update the display showing the new states of cells and new metrics numbers
     * @param population represented by 2d array of Agent

    //Will this be obsolete with the use of bindings?? Pretty surrrre it will be
    public void update(Population population){
        Agent[][] population = population.getPopulation();
        //first update the board
        for(int x = 0; x < popWidth; x++){
            for(int y = 0; y < popHeight; y++){
                State virus1State = population[x][y].getState();
                //State virus2State = population[x][y].getNovelState();

                if (virus1State == State.INFECTED && virus2State == State.NOVEL_I){//check for dual states before single states
                    gc.setFill(Color.ORANGE);
                    gc.fillRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                    gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                    gc.setFill(Color.RED);
                    gc.fillRect(x * cellWidth + cellWidth/2, y* cellHeight, cellWidth/2, cellHeight );
                    gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth, cellHeight );
                } else if (virus2State == State.NOVEL_I ){
                    gc.setFill(Color.ORANGE);
                    gc.fillRect(x * cellWidth, y* cellHeight, cellWidth, cellHeight );
                    gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth, cellHeight );
                } else if (population[x][y].getState() == State.SUSCEPTIBLE && virus2State == State.NOVEL_S){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (population[x][y].getState() == State.INFECTED){
                    gc.setFill(Color.RED);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (virus1State == State.RECOVERED && virus2State == State.NOVEL_R){ //check for dual states before single states
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                    //gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth/2, cellHeight );
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x * cellWidth + cellWidth/2, y* cellHeight, cellWidth/2, cellHeight );
                    gc.strokeRect(x * cellWidth, y* cellHeight, cellWidth, cellHeight );
                } else if (population[x][y].getState() == State.RECOVERED){
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                } else if (virus2State == State.NOVEL_R){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth,y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
        //then update the metrics display
    }
     */
}