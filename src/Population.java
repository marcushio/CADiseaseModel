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

    private double percentElderly = 0.147; //percent of the population aged 62 and over in the US in the 2010 US Cencus
    private double percentAdult = 0.647; //percent of the population aged 25 and over in the US in the 2010 US Cencus

    public Population(int height, int width){
        this.height = height;
        this.width = width;
        population = new Agent[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                double ageSelector = Math.random();
                if(ageSelector < percentElderly){
                    population[x][y] = new Elderly(State.SUSCEPTIBLE, x, y);
                } else if(ageSelector < percentAdult){
                    population[x][y] = new Adult(State.SUSCEPTIBLE, x, y);
                } else {
                    population[x][y] = new Child(State.SUSCEPTIBLE, x, y);
                }
            }
        }
    }

    /**
     * how the pop is updated from step to step.
     */
    public abstract boolean update();

    /**
     * our first case of the virus... dun dun dunnnn
     */
    protected void setPatientZero(){
        population[0][0].infect();
        population[0][0].makeBad();
//        population[width / 2][height / 2].infect();
//        population[width / 2][height / 2].makeBad();
    }

    /**
     * apply a rule in order to figure out what an agent's next state should be
     */
    abstract State applyRule(int x, int y);

    /**
     *
     * @return how many sick neighbors a cell has
     */
    public int countSickNeighbors(int x, int y){
        int sickNeighbors = 0;
        ArrayList<Agent> neighborhood = getNeighborhood(x,y);
        for(Agent neighbor: neighborhood){
            if(neighbor.isContagious()){ sickNeighbors++; }
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
     * height getter
     * @return height
     */
    public int getHeight(){return height;}

    /**
     * width getter
     * @return width
     */
    public int getWidth(){return width;}

    /**
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the neighborhood of this particular cell
     */
    public ArrayList<Agent> getNeighborhood (int x, int y){

        Agent agent = population[x][y];
        ArrayList<Agent> neighborhood = agent.getNeighborhood();
        if (neighborhood == null) {
            neighborhood = new ArrayList<Agent>();

            //establish wraparound for the map
            int left = x - 1;
            int right = x + 1;
            int up = y + 1;
            int down = y - 1;
            if (left == -1) //if left is outside of map, wrap around
                left = getWidth() - 1;
            if (right == getWidth()) //if right is outside of map, wrap around
                right = 0;
            if (down == -1) //if down is outside of map, wrap around
                down = getHeight() - 1;
            if (up == getHeight()) //if up is outside of map, wrap around
                up = 0;

            //populate the neighborhood
            neighborhood.add(population[left][up]);
            neighborhood.add(population[left][y]);
            neighborhood.add(population[left][down]);
            neighborhood.add(population[x][up]);
//            neighborhood.add(population[x][y]); //dont need to add yourself to your map
            neighborhood.add(population[x][down]);
            neighborhood.add(population[right][up]);
            neighborhood.add(population[right][y]);
            neighborhood.add(population[right][down]);

            //save neighborhood to prevent recalculation
            agent.setNeighborhood(neighborhood);
        }
        return neighborhood;
    }

    public int getTotalInfected(){
        int counter = 0;
        for(int i = 0; i < population.length; i++){
            for(int j = 0; j < population[0].length; j++){
                if(population[i][j].wasInfected()) counter++;
            }
        }
        return counter;
    }

    public int getTotalHospitalized(){
        int counter = 0;
        for(int i = 0; i < population.length; i++){
            for(int j = 0; j < population[0].length; j++){
                if(population[i][j].wasHospitalized()) counter++;
            }
        }
        return counter;
    }

    public int getTotalDeaths(){
        int counter = 0;
        for(int i = 0; i < population.length; i++){
            for(int j = 0; j < population[0].length; j++){
                if(population[i][j].hasDied()) counter++;
            }
        }
        return counter;
    }

    public int getTotalAgents(){
        return population.length * population[0].length;
    }
}
