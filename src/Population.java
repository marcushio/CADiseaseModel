import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 *
 * This is the abstract class for populations
 *
 */
public abstract class Population {
    private int height, width;
    public Agent[][] population;

    public Population(int height, int width){
        this.height = height;
        this.width = width;
        population = new Agent[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                boolean isCorner = false;
                boolean isEdge = false;
                if ( (x == 0 ) || (x == width - 1 ) || ( y == height -1)|| ( y == 0) ){isEdge = true;}
                if ( (x == 0 && y == 0) || (x == 0 && y == height-1) || (x == width-1  && y == height-1)|| (x == width-1  && y == 0) ){ isCorner = true; isEdge = false; }
                //population[x][y] = new Agent(State.SUSCEPTIBLE][ State.SUSCEPTIBLE][ isEdge][ isCorner][ x][ y); this was before 2 virus
                population[x][y] = new Agent(State.SUSCEPTIBLE, isEdge, isCorner, x, y);
            }
        }
    }

    protected Population() {
    }

    /**
     * how the pop is updated from step to step.
     */
    public abstract void update();

    /**
     *  set the first patients that have the virus(es)
     */
    abstract void setPatientZero();


    /**
     * apply a rule in order to figure out what an agent's next state should be
    abstract State applyRule(int x, int y);
     */

    /**
     *
     * @return how many sick neighbors a cell has
     */
    public int countSickNeighbors(int x, int y){
        int sickNeighbors = 0;
        ArrayList<Agent> neighborhood = getNeighborhood(x,y);
        for(Agent neighbor: neighborhood){
            if(neighbor.getState() == State.INFECTED){ sickNeighbors++; }
        }
        return sickNeighbors;
    }

    /**
     * get an agent at a particular coordinate
     */
    public Agent getAgent(int x, int y){
        return population[x][y];
    }


    /**
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the neighborhood of this particular cell
     */
    public ArrayList<Agent> getNeighborhood (int x, int y){
        ArrayList<Agent> neighborhood = new ArrayList<Agent>();
        Agent agent = population[x][y];
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
}
