import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 * @version 4/28/20
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class StochasticPopulation extends Population {
//    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
//            probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75; these are just different settings to play with
    private double probStoI_1 = .5 , probStoI_2 = .36, probStoI_3 = .7, probStoI_4 = .8, probStoI_5 = .55,
            probStoI_6 = .59, probStoI_7 = .7, probStoI_8 = .60;

    int time = 0;
    int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0; //here for debugs
    private int width = 40, height = 40;
    private int startX = 10, startY = 10;
    private int startX2 = 20, startY2 = 20;
    private Agent[][] population;
    private int totalCases = 0; //, currInfected, recovered, virus2Cases;

    public StochasticPopulation(){
        super(40,40);
        population = new Agent[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                boolean isCorner = false;
                boolean isEdge = false;
                if ( (x == 0 ) || (x == width - 1 ) || ( y == height -1)|| ( y == 0) ){isEdge = true;}
                if ( (x == 0 && y == 0) || (x == 0 && y == height-1) || (x == width-1  && y == height-1)|| (x == width-1  && y == 0) ){ isCorner = true; isEdge = false; }
                //population[x][y] = new Agent(State.SUSCEPTIBLE][ State.SUSCEPTIBLE][ isEdge][ isCorner][ x][ y); this was before 2 virus
                population[x][y] = new Agent(State.SUSCEPTIBLE, State.SUSCEPTIBLE, State.NOVEL_S, isEdge, isCorner, x, y);
            }
        }
        //our case0
        population[startX][startY].setState(State.INFECTED);
        population[startX2][startY2].setState(State.NOVEL_I);
    }

    @Override
    public void update(){
        //this is the main update loop
    }

    @Override
        /**
     * @param neighborhood the states of the neighbors of a cell
     * @return the number of infected in a neighborhood
     */
    public int countSickNeighbors(int x, int y){
        int sickNeighbors = 0;

        return sickNeighbors;
    }

    /**
     * for getting the members of a neighborhood that are sick with the SECONDVIRUS
     * @param neighborhood the states of the neighbors of a cell
     * @return the number of infected in a neighborhood
     */
    public int countSickNeighbors2(ArrayList<Agent> neighborhood){
        int sickNeighbors = 0;
        for(Agent neighbor: neighborhood){
            if(neighbor.getState() == State.NOVEL_I || neighbor.getState() == State.DUAL_I){ sickNeighbors++; }
        }
        return sickNeighbors;
    }



    public State applyRule(ArrayList<Agent> neighborhood, State thisAgentState){
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


    public State applyRuleRandom(ArrayList<Agent> neighborhood, State thisAgentState){
        //Math.random() produces a double 0<1
        double transition = Math.random();
        int sickNeighbors = 0; //countSickNeighbors(neighborhood);

        if(thisAgentState == State.RECOVERED){ return State.RECOVERED; }

        if(thisAgentState == State.SUSCEPTIBLE) { //cover susceptible cases first
            //lol why didn't I just use a switch?
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

    public State applyRuleVirus2(ArrayList<Agent> neighborhood, State thisAgentState){
        //Math.random() produces a double 0<1
        double transition = Math.random();
        int sickNeighbors = countSickNeighbors2(neighborhood);
        if (sickNeighbors == 0 && thisAgentState == State.NOVEL_S) {  return State.NOVEL_S; }
        if(thisAgentState == State.NOVEL_R){ return  State.NOVEL_R; }

        if(thisAgentState == State.NOVEL_S) { //cover susceptible cases first
            //lol why didn't I just use a switch? Actually I really don't like switches that much
            if (sickNeighbors == 0) {
                return State.NOVEL_S;
            } else if (sickNeighbors == 1) {
                if(transition < probNovelStoI_1){ return State.NOVEL_I; }
            } else if (sickNeighbors == 2 ) {
                if (transition < probNovelStoI_2) { return  State.NOVEL_I; } //adjust for my odds
            } else if (sickNeighbors == 3) {
                if (transition < probNovelStoI_3) { return State.NOVEL_I; }
            } else if (sickNeighbors == 4) {
                if (transition < probNovelStoI_4) { return State.NOVEL_I; }
            } else if (sickNeighbors == 5) {
                if (transition < probNovelStoI_5) { return State.NOVEL_I; }
            } else if (sickNeighbors == 6) {
                if (transition < probNovelStoI_6) { return State.NOVEL_I; }
            } else if (sickNeighbors == 7) {
                if (transition < probNovelStoI_7) { return State.NOVEL_I; }
            } else if (sickNeighbors == 8) {
                if (transition < probNovelStoI_8) { return State.NOVEL_I; }
            }
        } else if (thisAgentState == State.NOVEL_I){ //then cover infected cases
            //in future keep agent histories and make this a function of time for now we'll use the # of sick neighbors as a proxy for time
            if(sickNeighbors >= 0 && sickNeighbors <= 4 ){
                if(transition > .9) {return State.NOVEL_R;}
                else {return State.NOVEL_I; }
            }  else if(sickNeighbors >=5  ){ //if there are 5 peeps around this sick person they've probably had it long enough to recover
                return State.NOVEL_R;
            }
        }
        return State.NOVEL_S; //default return all logic above applies to non sus returns
    }

    private State getNextState(){
        //
        return State.SUSCEPTIBLE;
    }

    //debug stuffs
    public void printStatus(){
        time++;
        int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Agent thisAgent = population[x][y];
                if(thisAgent.getState() == State.INFECTED){ currInfected++; this.currInfected++; }
                if(thisAgent.getState() == State.SUSCEPTIBLE){ susceptible++; this.susceptible++; }
                if(thisAgent.getState() == State.RECOVERED){ recovered ++; this.recovered++;}
                if(thisAgent.getState() == State.NOVEL_I){  novelInfectd++; this.novelInfectd++; }
                if(thisAgent.getState() == State.NOVEL_R){ novelRecovered++; this.novelRecovered++; }
                if(thisAgent.getState() == State.NOVEL_S){ novelSusceptible++; this.novelSusceptible++;}
            }
        }
        System.out.println("______Population printed from within population STATS at Time t = " + time);
        System.out.println("Current I: " + currInfected);
        System.out.println("Current I': " + novelInfectd);
        System.out.println("Current S: " + susceptible);
        System.out.println("Current S': " + novelSusceptible);
        System.out.println("Current R: " + recovered);
        System.out.println("Current R': " + novelRecovered);
    }

    //debug stuffs
    public boolean sickies(){
        boolean truth = false;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if( (x != 10 && y != 10) && (x != 20 && y!= 20) ) {
                    if (population[x][y].getState() == State.NOVEL_I) {
                        truth = true;
                        System.out.println("found sicky at " + x + " " + y);
                    }
                }
            }
        }
        return truth;
    }

    public void setPatientZero(){population[20][20].setState(State.INFECTED);}
    public Agent[][] getPopulation(){
        return this.population;
    }
    public int getTotalCases(){
        return totalCases;
    }
    public int getCurrInfected(){ return currInfected; }
    public int getRecovered(){
        return recovered;
    }
    public int getVirus2Cases(){
        return novelInfectd ;
    }
    public Agent getAgent(int x, int y){
        return population[x][y];
    }
    public void setAgentNovelState(State novelState, int x, int y){
        population[x][y].setState(novelState);
    }
    public void setAgentState(State state, int x, int y){
        population[x][y].setState(state);
    }
    public void setNovelInfected(int virus2Cases){
        this.novelInfectd = virus2Cases;
    }
    public void setInfected(int infected) { this.currInfected = infected; }
    public void setRecovered(int recovered){
        this.recovered = recovered;
    }
    public void incrementTotalCases(int cases){
        this.totalCases += cases;
    }
}
