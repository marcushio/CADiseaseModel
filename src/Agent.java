import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private ArrayList<Agent> neighborhood;
    private List<State> history;
    private int xPosition;
    private int yPosition;

    //alternate values for progression
    private Random r;

    private int t;

    private double incStdev = 3.0;
    private double incMean = 7.0;
    private double sympStdev = 3.0;
    private double sympMean = 7.0;
    private double hospStdev = 3.0;
    private double hospMean = 7.0;

    private double asymptomaticRate = 0.1;
    private double hospitalizationRate = 0.2;
    private double asymptomaticMortalityRate = 0.003;
    private double infectedMortalityRate = 0.03;
    private double hospitalMortalityRate = 0.2;

    private int daysAsymptomatic;
    private int daysWithSymptoms;
    private int daysInHospital;

    private boolean showSymptoms;
    private boolean getHospitalized;
    //end instance variables


    public Agent(){
        this(null, 0,0);
    }

    public Agent(State state, int xPosition, int yPosition){
        neighborhood = null;
        history = new ArrayList<>();
        this.setState(state);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        r = new Random();
    }

    public void setState(State newState){
        state = newState;
        history.add(state);
        if( state.equals(State.INFECTED) ){
            color.set(Color.RED);
        } else if ( state.equals(State.SUSCEPTIBLE) ){
            color.set(Color.GREEN);
        } else if ( state.equals(State.RECOVERED) ){
            color.set(Color.BLUE);
        } else if ( state.equals(State.ASYMPTOMATIC_CARRIER) ){
            color.set(Color.DARKRED);
        } else if ( state.equals(State.DEAD) ){
            color.set(Color.BLACK);
        } else if ( state.equals(State.HOSPITALIZED) ){
            color.set(Color.ORANGE);
        }
    }

    public State getState(){
        return state;
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

    public void makeBad(){
        this.daysAsymptomatic = this.daysAsymptomatic < 7 ? 7 : this.daysAsymptomatic;
    }

    //alternate progression logic starts here
    public void infect(){
        if(this.state == State.SUSCEPTIBLE){
            //infect this tile and establish its progression through the infection
            this.setState(State.ASYMPTOMATIC_CARRIER);
            this.t = 0; //initialize time in this state

            //days this person will spend in each state
            daysAsymptomatic = (int) (r.nextGaussian() * incStdev + incMean);
            daysAsymptomatic = daysAsymptomatic < 1 ? 1 : daysAsymptomatic; //must spend at least one time step in each state

            daysWithSymptoms = (int) (r.nextGaussian() * sympStdev + sympMean);
            daysWithSymptoms = daysWithSymptoms < 1 ? 1 : daysWithSymptoms;

            daysInHospital = (int) (r.nextGaussian() * hospStdev + hospMean);
            daysInHospital = daysInHospital < 1 ? 1 : daysInHospital;

            //states to enter
            if(r.nextDouble() < asymptomaticRate){
                //if this person will show no symptoms
                this.showSymptoms = false;
                this.getHospitalized = false;
            } else {
                //this person will show symptoms
                this.showSymptoms = true;
                if(r.nextDouble() < hospitalizationRate){
                    //this person will be hospitalized
                    getHospitalized = true;
                } else {
                    //this person wont be hospitalized
                    getHospitalized = false;
                }
            }
        }
        //otherwise do nothing
    }

    public boolean isContagious(){
        if(this.state == State.ASYMPTOMATIC_CARRIER && this.t > 0){
            return true;
        } else if(this.state == State.INFECTED || this.state == State.HOSPITALIZED){
            return true;
        } else {
            return false;
        }
    }

    public void incrementTime(){

        switch(this.state){
            case SUSCEPTIBLE:
                //do nothing, this individual is not infected
                break;
            case ASYMPTOMATIC_CARRIER:
                //this person is a carrier, increase time in this state by 1
                this.t += 1;

                //if this person has spent all the time they will in this state
                if(this.t >= this.daysAsymptomatic){
                    if(r.nextDouble() < this.asymptomaticMortalityRate){
                        this.setState(State.DEAD);
                    } else if(this.showSymptoms){
                        //if this person will show symptoms
                        this.setState(State.INFECTED);
                        this.t = 0;
                    } else {
                        this.setState(State.RECOVERED);
                    }
                }
                break;
            case INFECTED:
                //this person is infected, increase time in this state by 1
                this.t += 1;

                //if this person has spent all the time they will in this state
                if(this.t >= this.daysWithSymptoms){
                    if(r.nextDouble() < this.infectedMortalityRate){
                        this.setState(State.DEAD);
                    } else if(this.getHospitalized){
                        //if this person will get hospitalized
                        this.setState(State.HOSPITALIZED);
                        this.t = 0;
                    } else {
                        this.setState(State.RECOVERED);
                    }
                }
                break;
            case HOSPITALIZED:
                //this person is hospitalized, increase time in this state by 1
                this.t += 1;

                //if this person has spent all the time they will in this state
                if(this.t >= this.daysInHospital){
                    if(r.nextDouble() < this.hospitalMortalityRate){
                        this.setState(State.DEAD);
                    } else {
                        this.setState(State.RECOVERED);
                    }
                }
                break;
            default: //for recovered and dead there is no next state, so do nothing
                break;
        }
    }

    public boolean wasInfected(){
        if(this.history.contains(State.INFECTED)){
            return true;
        }
        return false;
    }

    public boolean wasHospitalized() {
        if(this.history.contains(State.HOSPITALIZED)){
            return true;
        }
        return false;
    }
}
