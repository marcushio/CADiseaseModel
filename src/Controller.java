import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.TimerTask;

public class Controller extends TimerTask {
    int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0; //here for debug
    private Population population;
    private Display display;
    private int time = 1;
    //width and height of population
    private int width = 40, height = 40;

    //private double probStoI_1 = .25, probStoI_2to3 = .33, probStoI_4to6 = .5, probStoI_7up = .75 ; //jeeeez these var names are baad
//    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
//            probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75;
    private double probStoI_1 = .87, probStoI_2 = .36, probStoI_3 = .7, probStoI_4 = .8, probStoI_5 = .55,
            probStoI_6 = .5, probStoI_7 = .20, probStoI_8 = .20;
    private double probNovelStoI_1 = Math.random(), probNovelStoI_2 = Math.random(), probNovelStoI_3 = Math.random(),
                   probNovelStoI_4 = Math.random(), probNovelStoI_5 = Math.random(), probNovelStoI_6 = Math.random(),
                   probNovelStoI_7 = Math.random(), probNovelStoI_8 = Math.random();   //probStoI meaning probability of S->I and prob Novel like probability for the novel virus

    public Controller(Stage primaryStage){
        this.population = new Population();
        this.display = new Display(primaryStage, population);
        display.update(population);
        System.out.println("New Pop Made here are stats");
        printStatus();
    }

    public Controller(){//this one is for when we don't need a display
        this.population = new Population();
    }

    public void run(){
        population = getNextTwoVirusPopulation(population);
        //setMetrics();
        printStatus();
        display.update(population);
        time++;
    }

    public void step(){
        population = getNextTwoVirusPopulation(population);
        setMetrics();
        display.update(population);
    }

    public State applyRuleRandom(ArrayList<Agent> neighborhood, State thisAgentState){
        //Math.random() produces a double 0<1
        double transition = Math.random();
        int sickNeighbors = population.getSickNeighbors(neighborhood);

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
        int sickNeighbors = population.getSickNeighbors2(neighborhood);
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

    private Population getNextTwoVirusPopulation(Population pop){
            Population nextPopulation = new Population();
            boolean hasSick = nextPopulation.sickies();
            nextPopulation = pop;
            hasSick = nextPopulation.sickies();
            pop.printStatus();

            for(int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if(x == 19 && y == 19){
                        System.out.println("debug rule appl");
                    }
                    ArrayList<Agent> hood = pop.getNeighborhood(x,y);
                    int sickies = pop.getSickNeighbors2(hood);
                    if(sickies > 1){
                        System.out.println("why the hell are there sick neighbors?"); //debug because I was getting neighborhoods with sick people in new populations.
                        pop.getNeighborhood(x,y);
                    }
                    Agent thisAgent = pop.getAgent(x,y);

                    State nextState = applyRuleRandom(hood, thisAgent.getState() );
                    State nextNovelState = applyRuleVirus2(hood, thisAgent.getNovelState() );

                    nextPopulation.setAgentNovelState(nextNovelState, x, y);
                    nextPopulation.setAgentState(nextState, x, y);
                }
            }
            return  nextPopulation;
    }

    private Population getNextStochasticPopulation(){
        Population nextPopulation = population;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                State nextState = applyRuleRandom(population.getNeighborhood(x,y), population.getPopulation()[x][y].getState() );
                nextPopulation.getPopulation()[x][y].setState( nextState );
            }
        }
        return  nextPopulation;
    }

    private Population getNextPopulation(){
        Population nextPopulation = population;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                State nextState = applyRule(population.getNeighborhood(x,y), population.getPopulation()[x][y].getState() );
                nextPopulation.getPopulation()[x][y].setState( nextState );
            }
        }
        return nextPopulation;
    }

    /**
     * Apply a rule to update a cell's State at a given position
     * @param x the x position of the cell
     * @param y the y position of the cell
     * @return the new State the cell should be in given it's position (by analyzing neighbors)
     */
    private ArrayList<State> getNeighborhood(int x, int y){
        //not sure if it's actually easier but I'm hoping using logic to decide next states rather than writing all rules is shorter
        Agent agent = population.getAgent(x, y);
        ArrayList<State> neighborhoodStates = new ArrayList<State>();
        //go through and find the status of this agent's neighborhoods
        if(agent.isCorner){
            if(x == 0){
                if(y == 0){ //upper left corner
                    neighborhoodStates.add(population.getAgent(x+1 ,y).getState());
                    neighborhoodStates.add(population.getAgent(x+1 ,y+1).getState());
                    neighborhoodStates.add(population.getAgent(x ,y+1).getState());
                } else { //lower left corner
                    neighborhoodStates.add(population.getAgent(x+1 ,y).getState());
                    neighborhoodStates.add(population.getAgent(x+1 ,y-1).getState());
                    neighborhoodStates.add(population.getAgent(x ,y-1).getState());
                }
            } else if (x == 20){
                if(y == 0){//upper right corner
                    neighborhoodStates.add(population.getAgent(x-1 ,y).getState());
                    neighborhoodStates.add(population.getAgent(x-1 ,y+1).getState());
                    neighborhoodStates.add(population.getAgent(x ,y+1).getState());
                } else { //lower right corner
                    neighborhoodStates.add(population.getAgent(x-1 ,y-1).getState());
                    neighborhoodStates.add(population.getAgent(x-1 ,y).getState());
                    neighborhoodStates.add(population.getAgent(x ,y-1).getState());
                }
            }

        } else if (agent.isEdge){
            if(x == 0){//left edges
                neighborhoodStates.add(population.getAgent(x ,y-1).getState());
                neighborhoodStates.add(population.getAgent(x ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y-1).getState());
            } else if (x == width){//right edges
                neighborhoodStates.add(population.getAgent(x-1 ,y-1).getState());
                neighborhoodStates.add(population.getAgent(x-1 ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x-1 ,y).getState());
                neighborhoodStates.add(population.getAgent(x ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x ,y-1).getState());
            } else if (y == 0){//top edges
                neighborhoodStates.add(population.getAgent(x ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x-1 ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y+1).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y).getState());
                neighborhoodStates.add(population.getAgent(x-1 ,y).getState());
            } else if (y == height){//bottom edges
                neighborhoodStates.add(population.getAgent(x ,y-1).getState());
                neighborhoodStates.add(population.getAgent(x-1 ,y-1).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y-1).getState());
                neighborhoodStates.add(population.getAgent(x+1 ,y).getState());
                neighborhoodStates.add(population.getAgent(x-1 ,y).getState());
            }
        } else { //agent is in a moore neighborhood
            neighborhoodStates.add(population.getAgent(x ,y-1).getState());
            neighborhoodStates.add(population.getAgent(x-1 ,y-1).getState());
            neighborhoodStates.add(population.getAgent(x+1 ,y-1).getState());
            neighborhoodStates.add(population.getAgent(x+1 ,y).getState());
            neighborhoodStates.add(population.getAgent(x-1 ,y).getState());
            neighborhoodStates.add(population.getAgent(x ,y+1).getState());
            neighborhoodStates.add(population.getAgent(x-1 ,y+1).getState());
            neighborhoodStates.add(population.getAgent(x+1 ,y+1).getState());

        }
        //State thisAgentState = population.getAgent(x, y).getState();
        //return applyRule(neighborhoodStates, thisAgentState);
        return neighborhoodStates;
    }

    /**
     * @param neighborhood the states of the neighbors of a cell
     * @return the number of infected in a neighborhood
     */
    public int getSickNeighbors(ArrayList<State> neighborhood){
        int sickNeighbors = 0;
        for(State neighbor: neighborhood){
            if(neighbor == State.INFECTED){ sickNeighbors++; }
        }
        return sickNeighbors;
    }

    public void setTransmissionRates(ArrayList<Double> virus){
         probNovelStoI_1 = virus.get(0);
         probNovelStoI_2 = virus.get(1);
         probNovelStoI_3 = virus.get(2);
         probNovelStoI_4 = virus.get(3);
         probNovelStoI_5 =virus.get(4);
         probNovelStoI_6 = virus.get(5);
         probNovelStoI_7 =virus.get(6);
         probNovelStoI_8 = virus.get(7);
    }

    /**
     * @param neighborhood the states of the neighbors of a cell
     * @return the number of infected in a neighborhood
     */
    public int getSickNeighbors2(ArrayList<Agent> neighborhood){
        int sickNeighbors = 0;
        for(Agent neighbor: neighborhood){
            if(neighbor.getNovelState() == State.NOVEL_I){ sickNeighbors++; }
        }
        return sickNeighbors;
    }

    public int getInfected(){
        return population.getCurrInfected();
    }

    public void setMetrics(){ //man I took a lot of inefficient design choices, but hey they work.
        int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(population.getAgent(x, y).getState() == State.INFECTED){ currInfected++; }
                if(population.getAgent(x, y).getState() == State.SUSCEPTIBLE){ susceptible++; }
                if(population.getAgent(x,y).getState() == State.RECOVERED){ recovered ++; }
                if(population.getAgent(x,y).getNovelState() == State.NOVEL_I){  novelInfectd++; }
            }
        }
        population.setNovelInfected(novelInfectd);
        population.setInfected(currInfected);
        population.setRecovered(recovered);
        population.incrementTotalCases(currInfected);
    }
    private void printStatus(){
        int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Agent thisAgent = population.getAgent(x,y);
                if(thisAgent.getState() == State.INFECTED){ currInfected++; this.currInfected++; }
                if(thisAgent.getState() == State.SUSCEPTIBLE){ susceptible++; this.susceptible++; }
                if(thisAgent.getState() == State.RECOVERED){ recovered ++; this.recovered++;}
                if(thisAgent.getNovelState() == State.NOVEL_I){  novelInfectd++; this.novelInfectd++; }
                if(thisAgent.getNovelState() == State.NOVEL_R){ novelRecovered++; this.novelRecovered++; }
                if(thisAgent.getNovelState() == State.NOVEL_S){ novelSusceptible++; this.novelSusceptible++;}
            }
        }
        System.out.println("______Controller STATS at Time t = " + time);
        System.out.println("Current I: " + currInfected);
        System.out.println("Current I': " + novelInfectd);
        System.out.println("Current S: " + susceptible);
        System.out.println("Current S': " + novelSusceptible);
        System.out.println("Current R: " + recovered);
        System.out.println("Current R': " + novelRecovered);
    }


}
