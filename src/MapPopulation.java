import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Marcus Trujillo
 * @version 5/9/20
 */

public class MapPopulation extends Population{
    private ConfigFileReader fileReader;
    private ArrayList<MapAgent> population = new ArrayList<>();
    private Map<Coordinate, MapAgent> coordinateAgentMap = new HashMap<>();
    private int height, width;
    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
                    probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75;

    public MapPopulation(int height, int width){
        this.height = height;
        this.width = width;
    }

    public void update() {
        ArrayList<MapAgent> nextGeneration = new ArrayList<>();
        for(MapAgent agent : population){
            State nextState = getNextState(agent);
            MapAgent nextStateAgent = new MapAgent(nextState , agent.getCoordinate());
            nextGeneration.add(nextStateAgent);
        }
        int index = 0;
        for(MapAgent agent : nextGeneration){

            population.get(index).setState( agent.getState() );
            index++;
        }
    }

    /**
     *
     * @param coord
     * @param agent
     */
    public void put(Coordinate coord, MapAgent agent){
        coordinateAgentMap.put(coord, agent);
    }

    /**
     *
     * @param agent
     */
    public void addAgent(MapAgent agent){
        population.add(agent);
    }

    /**
     * add an edge to the network
     * @param node1Coordinate
     * @param node2Coordinate
     */
    public void addEdge(Coordinate node1Coordinate, Coordinate node2Coordinate){
        MapAgent node1 = coordinateAgentMap.get(node1Coordinate);
        MapAgent node2 = coordinateAgentMap.get(node2Coordinate);
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
    }

    /**
     * @return our collection of Agents for this population
     */
    public List<MapAgent> getPopulation(){
        return population;
    }

    public Map<Coordinate, MapAgent> getCoordinateAgentMap(){ return coordinateAgentMap; }

    /**
     * set the first person with the disease
     */
    public void setPatientZero() {
        population.get(0).setState(State.INFECTED);
    }

    private State getNextState(MapAgent agent){
        double transition = Math.random();
        State thisAgentState = agent.getState();
        int sickNeighbors = agent.countSickNeighbors();

        if(thisAgentState == State.RECOVERED){
            return State.RECOVERED;
        } else if(thisAgentState == State.SUSCEPTIBLE) { //cover susceptible cases first
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
                //if(transition > .5) {return State.RECOVERED;}
                return State.INFECTED;
            } else if(sickNeighbors >=5  ){ //if there are 5 peeps around this sick person they've probably had it long enough to recover
                return State.RECOVERED;
            }
        }

        return State.SUSCEPTIBLE;
    }
}
