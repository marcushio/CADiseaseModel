import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Trujillo
 * @version 5/1/20
 */
public class MapAgent {
    private List<MapAgent> neighbors;
    private int x, y;
    private State state;
    private ObjectProperty<Color> color = new ColorPicker(Color.GREEN).valueProperty() ;

    public MapAgent(State state, int x, int y){
        neighbors = new ArrayList<MapAgent>();
        this.x = x;
        this.y = y;
        this.state = state;
    }

    /**
     * adds a node to this node's list of neighbors if it's not already there
     * @param neighbor
     * @return true if a node was added, else false
     */
    public boolean addNeighbor(MapAgent neighbor){
        if(neighbors.contains(neighbor)){ return false; }
        neighbors.add(neighbor);
        return true;
    }

    /**
     *
     * @return the number of sick neighbors this agent has
     */
    public int countSickNeighbors(){
        int sickNeighbors = 0;
        for(MapAgent neighbor : neighbors){
            if(neighbor.getState() == State.INFECTED)
                sickNeighbors++;
        }
        return sickNeighbors;
    }

    /**
     * set the state of this agent
     * @param newState
     */
    public void setState(State newState){
        this.state = newState;
        if( newState.equals(State.INFECTED) ){
            color.set(Color.RED);
        } else if ( newState.equals(State.SUSCEPTIBLE) ){
            color.set(Color.GREEN);
        } else if ( newState.equals(State.RECOVERED) ){
            color.set(Color.BLUE);
        }
    }

    /**
     *
     * @return this agent's x coordinate
     */
    public int getX(){ return x; }

    /**
     *
     * @return this agent's y coordinate
     */
    public int getY(){ return y; }

    /**
     *
     * @return this agent's state
     */
    public State getState(){ return state; }

    /**
     * @return this agent's color which is an object property
     */
    public ObjectProperty<Color> getColor() { return color; }

    /**
     *
     */
    public List<MapAgent> getNeighbors(){ return neighbors; }




}
