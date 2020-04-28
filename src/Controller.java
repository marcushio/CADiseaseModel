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
        //this.stochasticPopulation = new StochasticPopulation();
        this.population = new DeterministicPopulation();
        this.display = new Display(primaryStage, population);
        display.update(population);
    }

    public Controller(){//this one is for when we don't need a display
        this.stochasticPopulation = new StochasticPopulation();
    }

    public void run(){
        stochasticPopulation.update();
        display.update(stochasticPopulation);
        time++;
    }

    public void step(){
        stochasticPopulation.update();
        display.update(stochasticPopulation);
    }

}
