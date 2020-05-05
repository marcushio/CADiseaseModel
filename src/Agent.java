import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Trujillo
 * @version 4/2/2020
 *
 * An agent in the model. Has one of 3 states following the SIR model
 */

public class Agent {
    //why didn't I just add a dual infected state under a single state var. Why did I add this novelState var?
    private ObjectProperty<Color> color = new ColorPicker(Color.GREEN).valueProperty() ;
    private State state;
    private List<Agent> neighbors = new ArrayList<Agent>();
    private int xPosition;
    private int yPosition;
    public boolean isEdge;
    public boolean isCorner;

    //these didn't end up getting used in the implementation
    private State prevState;
    private ArrayList<State> history;
    /**
     * this constructor is used for a classical grid configuration of a CA
     * @param state
     * @param isEdge
     * @param isCorner
     * @param xPosition
     * @param yPosition
     */
    public Agent(State state, boolean isEdge, boolean isCorner, int xPosition, int yPosition){
        this.state = state;
        this.isEdge = isEdge;
        this.isCorner = isCorner;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * this constructor is used for our map setup
     * @param state
     * @param x
     * @param y
     */
    public Agent(State state, int x, int y){
        this.state = state;
        this.xPosition = x;
        this.yPosition = y;
        this.isEdge = false; this.isCorner = false; //these are useless in the map paradigm
    }

    /**
     * set the state of this agent
     * @param newState
     */
    public void setState(State newState){
        prevState = state;
        state = newState;
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
     * @return the number of sick neighbors this agent has
     */
    public int countSickNeighbors(){
        int sickNeighbors = 0;
        for(Agent neighbor : neighbors){
            if(neighbor.getState() == State.INFECTED)
                sickNeighbors++;
        }
        return sickNeighbors;
    }

    /**
     * add an agent to this agent's neighbors list
     * @param agent
     */
    public void addNeighbor(Agent agent){
        neighbors.add(agent);
    }

    /**
      * @return this agent's current state
     */
    public State getState(){
        return state;
    }

    public boolean isCorner() {
        return isCorner;
    }

    public boolean isEdge() {
        return isEdge;
    }

    public int getxPosition(){
        return xPosition;
    }

    public int getyPosition(){
        return yPosition;
    }

    public List<Agent> getNeighbors(){ return neighbors; }

    public ObjectProperty<Color> getColor(){
        return color;
    }

    public void setxPosition(int xPosition){
        this.xPosition = xPosition;
    }
    public void setyPosition(int yPosition){
        this.yPosition = yPosition;
    }

}
