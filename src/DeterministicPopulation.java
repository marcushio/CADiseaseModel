import java.util.ArrayList;

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
    private Agent[][] population;
    private int totalCases = 0; //, currInfected, recovered, virus2Cases;

    public DeterministicPopulation(){
        super(40,40);
        setPatientZero();
    }
    public DeterministicPopulation(int height, int width){
        super(height, width);
        setPatientZero();
    }

    public void update(){
        Agent[][] nextPopulation = population;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                State nextState = applyRule(x,y);
                nextPopulation[x][y].setState( nextState );
            }
        }
        this.population = nextPopulation;
    }

    public int getSickNeighbors(int x, int y){
        int sickNeighbors = 0;
        ArrayList<Agent> neighborhood = super.getNeighborhood(x,y);
        for(Agent neighbor: neighborhood){
            if(neighbor.getState() == State.INFECTED){ sickNeighbors++; }
        }
        return sickNeighbors;
    }

    private State applyRule(int x, int y){
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
