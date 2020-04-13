import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 * @version
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class Population {
    int time = 0;
    int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0; //here for debugs
    private int width = 40, height = 40;
    private int startX = 10, startY = 10;
    private int startX2 = 20, startY2 = 20;
    private Agent[][] population;
    private int totalCases = 0; //, currInfected, recovered, virus2Cases;

    public Population(){
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
        population[startX2][startY2].setNovelState(State.NOVEL_I);
    }

    public Agent[][] getPopulation(){
        return this.population;
    }
    public int getTotalCases(){
        return totalCases;
    }
    public int getCurrInfected(){
        return currInfected;
    }
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
        population[x][y].setNovelState(novelState);
    }
    public void setAgentState(State state, int x, int y){
        population[x][y].setState(state);
    }

    public void setNovelInfected(int virus2Cases){
        this.novelInfectd = virus2Cases;
    }
    public void setInfected(int infected) {
        this.currInfected = infected;
    }
    public void setRecovered(int recovered){
        this.recovered = recovered;
    }
    public void incrementTotalCases(int cases){
        this.totalCases += cases;
    }

    /**
     * @param neighborhood the states of the neighbors of a cell
     * @return the number of infected in a neighborhood
     */
    public int getSickNeighbors(ArrayList<Agent> neighborhood){
        int sickNeighbors = 0;
        for(Agent neighbor: neighborhood){
            if(neighbor.getState() == State.INFECTED){ sickNeighbors++; }
        }
        return sickNeighbors;
    }

    /**
     * for getting the members of a neighborhood that are sick with the SECONDVIRUS
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
    /**
     * Apply a rule to update a cell's State at a given position
     * @param x the x position of the cell
     * @param y the y position of the cell
     * @return the new State the cell should be in given it's position (by analyzing neighbors)
     */
    public ArrayList<Agent> getNeighborhood(int x, int y){
        //not sure if it's actually easier but I'm hoping using logic to decide next states rather than writing all rules is shorter
        Agent agent = population[x][y];
        ArrayList<Agent> neighborhood = new ArrayList<Agent>();
        //go through and find the status of this agent's neighborhoods
        if(agent.isCorner){
            if(x == 0){
                if(y == 0){ //upper left corner
                    neighborhood.add(population[x+1][y]);
                    neighborhood.add(population[x+1][y+1]);
                    neighborhood.add(population[x][y+1]);
                } else { //lower left corner
                    neighborhood.add(population[x+1][y]);
                    neighborhood.add(population[x+1][y-1]);
                    neighborhood.add(population[x][y-1]);
                }
            } else if (x == 20){
                if(y == 0){//upper right corner
                    neighborhood.add(population[x-1 ][y]);
                    neighborhood.add(population[x-1 ][y+1]);
                    neighborhood.add(population[x ][y+1]);
                } else { //lower right corner
                    neighborhood.add(population[x-1 ][y-1]);
                    neighborhood.add(population[x-1 ][y]);
                    neighborhood.add(population[x ][y-1]);
                }
            }

        } else if (agent.isEdge){
            if(x == 0){//left edges
                neighborhood.add(population[x ][y-1]);
                neighborhood.add(population[x ][y+1]);
                neighborhood.add(population[x+1 ][y+1]);
                neighborhood.add(population[x+1 ][y]);
                neighborhood.add(population[x+1 ][y-1]);
            } else if (x == width){//right edges
                neighborhood.add(population[x-1 ][y-1]);
                neighborhood.add(population[x-1 ][y+1]);
                neighborhood.add(population[x-1 ][y]);
                neighborhood.add(population[x ][y+1]);
                neighborhood.add(population[x ][y-1]);
            } else if (y == 0){//top edges
                neighborhood.add(population[x ][y+1]);
                neighborhood.add(population[x-1 ][y+1]);
                neighborhood.add(population[x+1 ][y+1]);
                neighborhood.add(population[x+1 ][y]);
                neighborhood.add(population[x-1 ][y]);
            } else if (y == height){//bottom edges
                neighborhood.add(population[x ][y-1]);
                neighborhood.add(population[x-1 ][y-1]);
                neighborhood.add(population[x+1 ][y-1]);
                neighborhood.add(population[x+1 ][y]);
                neighborhood.add(population[x-1 ][y]);
            }
        } else { //agent is in a moore neighborhood
            neighborhood.add(population[x-1][y]);
            neighborhood.add(population[x-1][y-1]);
            neighborhood.add(population[x-1][y+1]);
            neighborhood.add(population[x+1 ][y]);
            neighborhood.add(population[x+1 ][y-1]);
            neighborhood.add(population[x+1 ][y+1]);
            neighborhood.add(population[x][y-1]);
            neighborhood.add(population[x][y+1]);
        }
        return neighborhood;
    }

    public void printStatus(){
        time++;
        int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Agent thisAgent = population[x][y];
                if(thisAgent.getState() == State.INFECTED){ currInfected++; this.currInfected++; }
                if(thisAgent.getState() == State.SUSCEPTIBLE){ susceptible++; this.susceptible++; }
                if(thisAgent.getState() == State.RECOVERED){ recovered ++; this.recovered++;}
                if(thisAgent.getNovelState() == State.NOVEL_I){  novelInfectd++; this.novelInfectd++; }
                if(thisAgent.getNovelState() == State.NOVEL_R){ novelRecovered++; this.novelRecovered++; }
                if(thisAgent.getNovelState() == State.NOVEL_S){ novelSusceptible++; this.novelSusceptible++;}
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

    public boolean sickies(){
        boolean truth = false;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if( (x != 10 && y != 10) && (x != 20 && y!= 20) ) {
                    if (population[x][y].getNovelState() == State.NOVEL_I) {
                        truth = true;
                        System.out.println("found sicky at " + x + " " + y);
                    }
                }
            }
        }
        return truth;
    }
}
