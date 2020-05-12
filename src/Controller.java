import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        PopulationFactory factory = new PopulationFactory();
        population = factory.build();
        population.setPatientZero();
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
                String outputFilename = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\outputRandomGraph.txt";
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), true))) {
                    String totalInfected = "Total Infected: " + population.getTotalInfected();
                    System.out.println(totalInfected);
                    String totalHospitalized = "Total Hospitalized: " + population.getTotalHospitalized();
                    System.out.println(totalHospitalized);
                    String totalDeaths = "Total Deaths: " + population.getTotalDeaths();
                    System.out.println(totalDeaths);
                    String percentInfected = "Percent Infected: " + ((double) population.getTotalInfected()) / ((double) population.getTotalAgents());
                    System.out.println(percentInfected);
                    String percentHospitalized = "Percent Hospitalized: " + ((double) population.getTotalHospitalized()) / ((double) population.getTotalAgents());
                    System.out.println(percentHospitalized);
                    String percentDeaths = "Percent Deaths: " + ((double) population.getTotalDeaths()) / ((double) population.getTotalAgents());
                    System.out.println(percentDeaths);
                    String perceivedHosp = "Perceived Hospitalization Rate: " + ((double) population.getTotalHospitalized()) / ((double) population.getTotalInfected());
                    System.out.println(perceivedHosp);
                    String perceivedMortality = "Perceived Mortality Rate: " + ((double) population.getTotalDeaths()) / ((double) population.getTotalInfected());
                    System.out.println(perceivedMortality);

                    writer.write(totalInfected);
                    writer.write(totalHospitalized);
                    writer.write(totalDeaths);
                    writer.write(percentInfected);
                    writer.write(percentHospitalized);
                    writer.write(percentDeaths);
                    writer.write(perceivedHosp);
                    writer.write(perceivedMortality);
                    
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            time++;
        }
    }

    public void step(){

    }

}
