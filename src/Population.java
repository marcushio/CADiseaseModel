import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 * @version
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class Population {
    private int width = 40, height = 40;
    private int startX = 10, startY = 10;
    private int startX2 = 20, startY2 = 20;
    private Agent[][] population;
    private int totalCases, currInfected, recovered, virus2Cases;

    public Population(){
        population = new Agent[width][height];
        totalCases = 1;
        currInfected = 1;
        virus2Cases = 1;
        recovered = 0;
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
        return virus2Cases;
    }
    public Agent getAgent(int x, int y){
        return population[x][y];
    }

    public void setNovelInfected(int virus2Cases){
        this.virus2Cases = virus2Cases;
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
            neighborhood.add(population[x ][y-1]);
            neighborhood.add(population[x-1 ][y-1]);
            neighborhood.add(population[x+1 ][y-1]);
            neighborhood.add(population[x+1 ][y]);
            neighborhood.add(population[x-1 ][y]);
            neighborhood.add(population[x ][y+1]);
            neighborhood.add(population[x-1 ][y+1]);
            neighborhood.add(population[x+1 ][y+1]);

        }
        //State thisAgentState = population[x][ y
        //return applyRule(neighborhoodStates][ thisAgentState);
        return neighborhood;
    }
}
