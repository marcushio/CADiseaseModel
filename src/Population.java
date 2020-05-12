import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcus Trujillo
 *
 * This is the abstract class for populations
 *
 */
public abstract class Population {
    private int height, width;
    public ArrayList<Agent> population = new ArrayList<>();
    public Map<Coordinate, Agent> coordinateAgentMap = new HashMap<>();


    private double percentElderly = 0.147; //percent of the population aged 62 and over in the US in the 2010 US Cencus
    private double percentAdult = 0.742; //percent of the population aged 18 and over in the US in the 2010 US Cencus
    public Population(){}//I moved this to the factory

    public Population(int height, int width){
        this.height = height;
        this.width = width;
        for(int x = 0; x < width; x++){ //sure why not just keep that? it gives me the correct number of people to make
            for(int y = 0; y < height; y++){
                double ageSelector = Math.random();
                if(ageSelector < percentElderly){
                    population.add( new Elderly(State.SUSCEPTIBLE, x, y));
                } else if(ageSelector < percentAdult){
                    population.add(new Adult(State.SUSCEPTIBLE, x, y));
                } else {
                    population.add(new Child(State.SUSCEPTIBLE, x, y));
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
//        population[0][0].infect();
//        population[0][0].makeBad();
//old        population[width / 2][height / 2].infect();
//old        population[width / 2][height / 2].makeBad();
        int popsize = population.size();
        population.get(popsize/2).infect();
        population.get(popsize/2).makeBad();
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


    public Map<Coordinate, Agent> getCoordinateAgentMap(){ return coordinateAgentMap; }

    /**
     * get an agent at a particular coordinate
     */
    public Agent getAgent(int x, int y){
        Coordinate here = new Coordinate(x,y);
        return coordinateAgentMap.get(here);
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
        Agent agent = coordinateAgentMap.get(new Coordinate(x,y));
        ArrayList<Agent> neighborhood = agent.getNeighborhood();
        return neighborhood;
    }

    public int getTotalInfected(){
        int counter = 0;
        for(Agent agent : population)
                if(agent.wasInfected()) counter++;
        return counter;
    }

    public int getTotalHospitalized(){
        int counter = 0;
        for(Agent agent : population)
            if(agent.wasHospitalized()) counter++;
        return counter;
    }

    public int getTotalDeaths(){
        int counter = 0;
        for(Agent agent : population)
            if(agent.hasDied()) counter++;
        return counter;
    }

    public int getTotalAgents(){
        return population.size();
    }

    /**
     *
     * @param coord
     * @param agent
     */
    public void put(Coordinate coord, Agent agent){
        coordinateAgentMap.put(coord, agent);
    }

    /**
     *
     * @param agent
     */
    public void addAgent(Agent agent){
        population.add(agent);
    }

    /**
     * add an edge to the network
     * @param node1Coordinate
     * @param node2Coordinate
     */
    public void addEdge(Coordinate node1Coordinate, Coordinate node2Coordinate){
        Agent node1 = coordinateAgentMap.get(node1Coordinate);
        Agent node2 = coordinateAgentMap.get(node2Coordinate);
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
    }

}
