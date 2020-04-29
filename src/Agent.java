import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ColorPicker;
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
    private ObjectProperty<Color> color = new ColorPicker(Color.GREEN).valueProperty() ;
    private State state;
    private State prevState;
    private ArrayList<State> history;
    private int xPosition;
    private int yPosition;
    public boolean isEdge;
    public boolean isCorner;
    //do these fellaz know they're neighborssssss?
    //we can add all kinds of fun things later but for now these guys just have states
    //private float vulnerability; //odds of death if infected

    public Agent(State state, boolean isEdge, boolean isCorner, int xPosition, int yPosition){
        this.state = state;
        this.isEdge = isEdge;
        this.isCorner = isCorner;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    public Agent(State prevState, State state, State novelState, boolean isEdge, boolean isCorner, int xPosition, int yPosition){
        //this.novelState = novelState; switching to ONE STATE
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
        if( newState.equals(State.INFECTED) ){
            color.set(Color.RED);
        } else if ( newState.equals(State.SUSCEPTIBLE) ){
            color.set(Color.GREEN);
        } else if ( newState.equals(State.NOVEL_S) ){
            color.set(Color.BLUE);
        } else if ( newState.equals(State.NOVEL_I) ){
            color.set(Color.ORANGE);
        } else if ( newState.equals(State.NOVEL_R) ){
            color.set(Color.BLACK);
        } else if ( newState.equals(State.DUAL_R) ){
            color.set(Color.BLUEVIOLET);
        } else if ( newState.equals(State.DUAL_I) ){
            color.set(Color.ORANGERED);
        }
    }

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
