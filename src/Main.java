import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;

public class Main extends Application  {
    private boolean simpleRules = true;
    private Stage primaryStage;
    private Controller controller;
    private GeneticAlgorithm ga;
    //I'll have to make vars to keep track of the metrics

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        controller = new Controller(primaryStage);

        //this'll be wrapped up after I add a start button
        Timer timer = new Timer();
        timer.schedule(controller, 1000, 1500 );
    }

}
