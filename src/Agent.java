import java.util.ArrayList;
/**
 * @author Marcus Trujillo
 * @version 4/2/2020
 *
 * An agent in the model. Has one of 3 states following the SIR model
 */


public class Agent {
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


    public void setState(State newState){
        prevState = state;
        state = newState;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    public State getState(){
        return state;
    }
    public void setxPosition(int xPosition){
        this.xPosition = xPosition;
    }
    public void setyPosition(int yPosition){
        this.yPosition = yPosition;
    }

}
