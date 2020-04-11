import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;

public class Main extends Application  {
    private boolean simpleRules = true;
    private Stage primaryStage;
    private Display display;

    private Population population;
    private Controller controller;
    private GeneticAlgorithm ga;
    //I'll have to make vars to keep track of the metrics

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        ga = new GeneticAlgorithm();
        population = new Population();
        display = new Display(primaryStage, population);
        display.update(population);
        Timer timer = new Timer();
//        Controller controller = new Controller( population, display);
//        for(int i = 0; i < 500; i++) {
//            controller.stepStochastic();
//        }
        //controller.step();
        //controller.step();
        //controller.step();
        timer.schedule(controller = new Controller(population, display), 1000, 1500 );
    }





}
