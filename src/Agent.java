import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;
/**
 * @author Marcus Trujillo
 * @version 4/2/2020
 *
 * An agent in the model. Has one of 3 states following the SIR model
 */


public class Agent {
    //why didn't I just add a dual infected state under a single state var. Why did I add this novelState var?
    private ObjectProperty<Color> color ; 
    private State novelState;
    private State state;
    private State prevState;
    private int xPosition;
    private int yPosition;
    public boolean isEdge;
    public boolean isCorner;
    //do these fellaz know they're neighborssssss?
    //we can add all kinds of fun things later but for now these guys just have states
    //private float vulnerability; //odds of death if infected

    public Agent(){
        state = State.SUSCEPTIBLE;
        prevState = State.SUSCEPTIBLE;
        isEdge = false;
    }
    public Agent(State prevState, State state, boolean isEdge, boolean isCorner, int xPosition, int yPosition){
        this.prevState = prevState;
        this.state = state;
        this.isEdge = isEdge;
        this.isCorner = isCorner;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    public Agent(State prevState, State state, State novelState, boolean isEdge, boolean isCorner, int xPosition, int yPosition){
        this.novelState = novelState;
        this.prevState = prevState;
        this.state = state;
        this.isEdge = isEdge;
        this.isCorner = isCorner;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void setState(State newState){
        prevState = state;
        state = newState;
        //this.xPosition = xPosition; why tf did I actually put those there? I don't remember a reason
        //this.yPosition = yPosition;
    }
    public void setNovelState(State newNovelState){
        novelState = newNovelState;
    }
    public State getState(){
        return state;
    }
    public State getNovelState(){
        return novelState;
    }
    public void setxPosition(int xPosition){
        this.xPosition = xPosition;
    }
    public void setyPosition(int yPosition){
        this.yPosition = yPosition;
    }

}
