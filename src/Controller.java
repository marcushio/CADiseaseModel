import javafx.stage.Stage;

import java.util.TimerTask;

public class Controller extends TimerTask {
    //private StochasticPopulation stochasticPopulation;
//    private Population population;
    private Population population;
    private Display display;
    private int time = 1;
    //width and height of population
    private int width = 400, height = 400;
    private boolean running = true;
    private boolean header = true;

    public Controller(Stage primaryStage){
        this.population = new StochasticPopulation(width, height);
        //this.population = new DeterministicPopulation();
        this.display = new Display(primaryStage, population);
    }

    public Controller(){//this one is for when we don't need a display
        this.population = new StochasticPopulation();
//        this.population = new DeterministicPopulation();
    }

    public void run(){
        if(header) {
            System.out.println("Susceptible,Recovered,Carier,Infected,Hospitalized,Dead");
            header = false;
        }
        if(running) {
            running = population.update();
            if(!running){
                System.out.println("Total Infected: " + population.getTotalInfected());
                System.out.println("Total Hospitalized: " + population.getTotalHospitalized());
                System.out.println("Total Deaths: " + population.getTotalDeaths());
                System.out.println("Percent Infected: " + ((double) population.getTotalInfected())/((double) population.getTotalAgents()));
                System.out.println("Percent Hospitalized: " + ((double) population.getTotalHospitalized())/((double) population.getTotalAgents()));
                System.out.println("Percent Deaths: " + ((double) population.getTotalDeaths())/((double) population.getTotalAgents()));
                System.out.println("Perceived Hospitalization Rate: " + ((double) population.getTotalHospitalized())/((double) population.getTotalInfected()));
                System.out.println("Perceived Mortality Rate: " + ((double) population.getTotalDeaths())/((double) population.getTotalInfected()));
            }
            time++;
        }
    }

    public void step(){

    }

}
