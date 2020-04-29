import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 * @version 4/28/20
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class StochasticPopulation extends Population {
//    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
//            probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75;
    private double probStoI_1 = .5 , probStoI_2 = .36, probStoI_3 = .7, probStoI_4 = .8, probStoI_5 = .55,
            probStoI_6 = .59, probStoI_7 = .7, probStoI_8 = .60;

    int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0; //here for debugs
    private int width = 40, height = 40;
    private int startX = 10, startY = 10;
    private int startX2 = 20, startY2 = 20;

    public StochasticPopulation() {
        super(40, 40);
        setPatientZero();
    }

    /**
     * updates the population to the next time step
     */
    @Override
    public void update(){
        Agent[][] nextPopulation = new Agent[width][height];
        //annoying I have to manually copy agents because I don't want to get a reference to the population
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                nextPopulation[i][j] = new Agent(population[i][j].getState(), population[i][j].isEdge, population[i][j].isCorner, population[i][j].getxPosition(), population[i][j].getyPosition());
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
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @return the state resulting from applying this populations rules
     */
    public State applyRule(int x, int y){
        //Math.random() produces a double 0<1
        double transition = Math.random();
        int sickNeighbors = countSickNeighbors(x,y);
        State thisAgentState = population[x][y].getState();
        if(thisAgentState == State.RECOVERED){ return State.RECOVERED; }

        if(thisAgentState == State.SUSCEPTIBLE) { //cover susceptible cases first
            if (sickNeighbors == 0) {
                return State.SUSCEPTIBLE;
            } else if (sickNeighbors == 1) {
                if(transition < probStoI_1){ return State.INFECTED; }
            } else if (sickNeighbors == 2 ) {
                if (transition < probStoI_2) { return  State.INFECTED; } //adjust for my odds
            } else if (sickNeighbors == 3) {
                if (transition < probStoI_3) { return State.INFECTED; }
            } else if (sickNeighbors == 4) {
                if (transition < probStoI_4) { return State.INFECTED; }
            } else if (sickNeighbors == 5) {
                if (transition < probStoI_5) { return State.INFECTED; }
            } else if (sickNeighbors == 6) {
                if (transition < probStoI_6) { return State.INFECTED; }
            } else if (sickNeighbors == 7) {
                if (transition < probStoI_7) { return State.INFECTED; }
            } else if (sickNeighbors == 8) {
                if (transition < probStoI_8) { return State.INFECTED; }
            }
        } else if (thisAgentState == State.INFECTED){ //then cover infected cases
            //in future keep agent histories and make this a function of time for now we'll use the # of sick neighbors as a proxy for time
            if(sickNeighbors >= 0 && sickNeighbors <= 4 ){
                if(transition > .5) {return State.RECOVERED;}
                return State.INFECTED;
            } else if(sickNeighbors >=5  ){ //if there are 5 peeps around this sick person they've probably had it long enough to recover
                return State.RECOVERED;
            }
        }
        return State.SUSCEPTIBLE; //default return all logic above applies to non sus returns
    }

    /**
     * our first case of the virus... dun dun dunnnn
     */
    @Override
    public void setPatientZero(){population[20][20].setState(State.INFECTED);}
    public Agent[][] getPopulation(){
        return this.population;
    }
}
