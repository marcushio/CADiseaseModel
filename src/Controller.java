import javafx.stage.Stage;

import java.util.TimerTask;

public class Controller extends TimerTask {
    private MapPopulation population;
    //private Population population; argh I can't get map pops and pops to play along I could get it by just switching over to list storage of population for classic CA pops
    private int time = 1;
    private int width = 40, height = 40; //width and height of population
    private MapDisplay display;
    //use this whenever you're going for the classic CA
    //private Display display;


    public Controller(Stage primaryStage){
        this.population = new MapPopulation(height, width);
        this.display = new MapDisplay(primaryStage, population);
        //this.population = new StochasticPopulation();
        //this.display = new Display(primaryStage, population);
    }

    public Controller(){//this one is for when we don't need a display
        //this.population = new DeterministicPopulation();
    }

    public void run(){
        population.update();
        time++;
    }

}
