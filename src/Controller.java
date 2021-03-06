import javafx.stage.Stage;

import java.util.TimerTask;

public class Controller extends TimerTask {
    //private StochasticPopulation stochasticPopulation;
    private Population population;  
    private Display display;
    private int time = 1;
    //width and height of population
    private int width = 40, height = 40;


    public Controller(Stage primaryStage){
        this.population = new StochasticPopulation();
        //this.population = new DeterministicPopulation();
        this.display = new Display(primaryStage, population);
    }

    public Controller(){//this one is for when we don't need a display
        this.population = new DeterministicPopulation();
    }

    public void run(){
        population.update();
        time++;
    }

    public void step(){

    }

}
