import jdk.jfr.StackTrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Trujillo
 * @version 5/1/20
 */

public class MapPopulation extends Population{
    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
                   probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75;
    private int height, width;
    private String filePath = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\edges.txt" ; //definitely take this in from commandline in the future
    private ArrayList<MapAgent> population = new ArrayList<>();
    private List<String[]> edgeSpecs = new ArrayList<>(); //Each entry has start and end coordinates for each edge i.e ["1", "2", "3", "4"]

    public MapPopulation(int height, int width){
        this.height = height;
        this.width = width;
        readAgentPositions(filePath);
        for(String[] edge : edgeSpecs){
            int startX = Integer.parseInt(edge[0]);
            int startY = Integer.parseInt(edge[1]);
            int endX = Integer.parseInt(edge[2]);
            int endY = Integer.parseInt(edge[3]);

            MapAgent agent1 = new MapAgent(State.SUSCEPTIBLE, startX, startY);
            MapAgent agent2 = new MapAgent(State.SUSCEPTIBLE, endX, endY);
            agent1.addNeighbor( agent2 );
            agent2.addNeighbor( agent1 );
            population.add( agent1 );
            population.add( agent2 );
        }
        //setPatientZero();
    }

    public void update() {
        ArrayList<MapAgent> nextGeneration = new ArrayList<>();
        for(MapAgent agent : population){
            State nextState = getNextState(agent);
            MapAgent nextStateAgent = new MapAgent(nextState , agent.getX(), agent.getY());
            nextGeneration.add(nextStateAgent);
        }
        for(MapAgent agent : nextGeneration){

        }
    }

    /**
     * @return our collection of Agents for this population
     */
    public List<MapAgent> getPopulation(){
        return population;
    }

    /**
     * set the first person with the disease
     */
    public void setPatientZero() {
        population.get(50).setState(State.INFECTED);
    }

    /**
     * @return our list detailing all edges of the graph
     */
    public List<String[]> getEdgeSpecs() {return edgeSpecs; }

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
                if(transition > .5) {return State.RECOVERED;}
                return State.INFECTED;
            } else if(sickNeighbors >=5  ){ //if there are 5 peeps around this sick person they've probably had it long enough to recover
                return State.RECOVERED;
            }
        }

        return State.SUSCEPTIBLE;
    }


    private void readAgentPositions(String filePath){
        String line = "";
        try( BufferedReader reader = new BufferedReader( new FileReader(filePath)) ){
            while( (line = reader.readLine()) != null ){
                String[] pieces = line.split(" ");
                edgeSpecs.add(pieces);
                //I don't like that name pieces
            }
        } catch(IOException ex){
            System.out.println("Error: " + filePath + "couldn't be read");
            ex.printStackTrace();
        }

    }

    public static void main(String[] args ){
        MapPopulation pop = new MapPopulation(40 , 40 );

    }

}
