import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: Marcus Trujillo
 *
 * represents a class that is ruled by deterministic spread of a virus.
 */

public class DeterministicPopulation extends Population {
    int time = 0;
    int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfected = 0, novelRecovered = 0, novelSusceptible = 0; //here for debugs
    private int width = 40, height = 40;
    private int startX = 10, startY = 10;
    private int startX2 = 20, startY2 = 20;


    public DeterministicPopulation(){
        super(40,40);
        setPatientZero();
    }
    public DeterministicPopulation(int height, int width){
        super(height, width);
        setPatientZero();
    }

    @Override
    public boolean update(){
        Agent[][] nextPopulation = new Agent[width][height];
        //annoying I have to manually copy agents because I don't want to get a reference to the population
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                nextPopulation[i][j] = new Agent(population[i][j].getState(), population[i][j].getxPosition(), population[i][j].getyPosition());
            }
        }
        currInfected = 0; susceptible = 0; recovered = 0;
        int totalPop = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                State nextState = applyRule(x,y);
                if(nextState == State.INFECTED){ currInfected++; }
                if(nextState == State.SUSCEPTIBLE){ susceptible++; }
                if(nextState == State.RECOVERED){ recovered++; }
                nextPopulation[x][y].setState( nextState );
            }
        }
        totalPop = currInfected + susceptible + recovered;
        System.out.println("Infected: " + currInfected + "\n" + "Susceptible: " + susceptible + "\n" + "Recovered: " + recovered + "Total Peeps " + totalPop );
        //finally we actually change the state of our real population
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                population[x][y].setState(nextPopulation[x][y].getState());
            }
        }
        if (susceptible + currInfected > 0)
            return true;
        else
            return false;
    }

    @Override
    public int countSickNeighbors(int x, int y){
        int sickNeighbors = 0;
        ArrayList<Agent> neighborhood = super.getNeighborhood(x,y);
        for(Agent neighbor: neighborhood){
            if(neighbor.getState() == State.INFECTED){ sickNeighbors++; }
        }
        return sickNeighbors;
    }

    @Override
    public State applyRule(int x, int y){
        ArrayList<Agent> neighborhood = super.getNeighborhood(x,y);
        State thisAgentState = population[x][y].getState();
        boolean allNeighborsSick = true;
        boolean hasSickNeighbor = false;
        for(int i = 0; i < neighborhood.size(); i++){
            if(neighborhood.get(i).getState() == State.SUSCEPTIBLE){allNeighborsSick = false; }
            if(neighborhood.get(i).getState() == State.INFECTED){hasSickNeighbor = true; }
        }
        if (thisAgentState == State.INFECTED){
            if(allNeighborsSick){return State.RECOVERED; }
            else{return State.INFECTED;}
        } else if (thisAgentState == State.SUSCEPTIBLE){
            if(hasSickNeighbor){return State.INFECTED;}
            else{return State.SUSCEPTIBLE;}
        }
        return State.RECOVERED;
    }

    public void setPatientZero(){
        population[10][10].setState(State.INFECTED);
    }

}
