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
    private State prevState;
    private ArrayList<Agent> neighborhood;
    private List<State> history;
    private int xPosition;
    private int yPosition;
    public boolean isEdge;
    public boolean isCorner;

    //alternate values for progression
    private double asymptomaticRate = 0.8;
    private double hospitalizationRate = 0.2;
    private double hospitalMortalityRate = 0.2;
    private double infectedMortalityRate = 0.03;

    private int daysToIncubate;
    private int daysWithSymptoms;
    private int daysInHospital;

    private int timeSenseInfection;
    private boolean showSymptoms;
    private boolean getHospitalized;
    //end instance variables

    //do these fellaz know they're neighborssssss?
    //we can add all kinds of fun things later but for now these guys just have states
    //private float vulnerability; //odds of death if infected

    public Agent(State state, boolean isEdge, boolean isCorner, int xPosition, int yPosition){
        neighborhood = null;
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
        } else if ( newState.equals(State.RECOVERED) ){
            color.set(Color.BLUE);
        } else if ( newState.equals(State.ASYMPTOMATIC_CARRIER) ){
            color.set(Color.DARKRED);
        } else if ( newState.equals(State.DEAD) ){
            color.set(Color.BLACK);
        } else if ( newState.equals(State.HOSPITALIZED) ){
            color.set(Color.ORANGE);
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

    public ArrayList<Agent> getNeighborhood(){return neighborhood;}
    public void setNeighborhood(ArrayList<Agent> neighborhood){this.neighborhood = neighborhood;}

    //alternate progression logic starts here
    public void infect(){
        if(this.state == State.SUSCEPTIBLE){
            //infect this tile and establish its progression through the infection
        }
        //otherwise do nothing
    }

    public void progress(){
        switch(this.state){
            case SUSCEPTIBLE:
                break;
            case ASYMPTOMATIC_CARRIER:
                break;
            case INFECTED:
                break;
            case HOSPITALIZED:
                break;
            default: //for recovered and dead there is no next state, so do nothing
                break;
        }
    }
}
