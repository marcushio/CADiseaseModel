import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 * @version 4/28/20
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class StochasticPopulation extends Population {
//    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
//            probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75;
    private double[] probStoI = {0.0, 0.5 , 0.36, 0.7, 0.8, 0.55, 0.59, 0.7, 0.60};

//    int currInfected = 0, additionalInfected = 0, recovered = 0, susceptible = 0, CARRIER = 0, HOSPITALIZED = 0, DEAD = 0, novelInfectd = 0, novelRecovered = 0, novelSusceptible = 0; //here for debugs
    private double carrierConversionChance = 0.05;
    private double carrierRecoveryChance = 0.05;
    private double carrierDeathRate = 0.05;

    private double infectedHostpitalizationRate = 0.05;
    private double infectedDeathRate = 0.05;
    private double infectedRecoveryRate = 0.05;

    private double hospitalizedDeathRate = 0.05;
    private double hospitalizedRecoveryRate = 0.05;

    private int startX, startY;

    public StochasticPopulation() {
        this(40, 40);
    }
    public StochasticPopulation(int height, int width) {
        super(height, width);
        startX = width/2;
        startY = height/2;
        setPatientZero();
    }

    /**
     * updates the population to the next time step
     */
    @Override
    public boolean update(){
        Agent[][] nextPopulation = new Agent[super.getWidth()][super.getHeight()];
        //annoying I have to manually copy agents because I don't want to get a reference to the population
        for(int i = 0; i < super.getWidth(); i++){
            for(int j = 0; j < super.getHeight(); j++){
                nextPopulation[i][j] = new Agent(population[i][j].getState(), population[i][j].isEdge, population[i][j].isCorner, population[i][j].getxPosition(), population[i][j].getyPosition());
            }
        }
        int currInfected = 0, susceptible = 0, recovered = 0,CARRIER = 0, HOSPITALIZED = 0, DEAD = 0;

        for(int x = 0; x < super.getWidth(); x++){
            for(int y = 0; y < super.getHeight(); y++){
                State nextState = applyRule(x,y);
                if(nextState == State.INFECTED){ currInfected++; }
                if(nextState == State.SUSCEPTIBLE){ susceptible++; }
                if(nextState == State.RECOVERED){ recovered++; }
                if(nextState == State.ASYMPTOMATIC_CARRIER){ CARRIER++; }
                if(nextState == State.HOSPITALIZED){ HOSPITALIZED++; }
                if(nextState == State.DEAD){ DEAD++; }
                nextPopulation[x][y].setState( nextState );
            }
        }

        int totalPop = currInfected + susceptible + recovered + CARRIER + HOSPITALIZED;
        System.out.println(susceptible + "," + CARRIER + "," + currInfected + "," + HOSPITALIZED + "," + recovered + "," + DEAD + "," + totalPop );

        //finally we actually change the state of our real population
        for(int x = 0; x < super.getWidth(); x++){
            for(int y = 0; y < super.getHeight(); y++){
                population[x][y].setState(nextPopulation[x][y].getState());
            }
        }
        if(currInfected + CARRIER + HOSPITALIZED > 0)
            return true;
        else
            return false;
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @return the state resulting from applying this populations rules
     */
    public State applyRule(int x, int y){
        //Math.random() produces a double 0<1
        double transition = Math.random();
        int sickNeighbors = countSickNeighbors(x,y);
        State thisAgentState = population[x][y].getState();
        if(thisAgentState == State.RECOVERED){ return State.RECOVERED; }
        if(thisAgentState == State.DEAD){ return State.DEAD; }

        if(thisAgentState == State.SUSCEPTIBLE) { //cover susceptible cases first
            if (transition < probStoI[sickNeighbors]) {
                return State.ASYMPTOMATIC_CARRIER;
            }
        } else if (thisAgentState == State.ASYMPTOMATIC_CARRIER){ //if asymptomatic, then you have chances to recover, die, or display symptoms
            if(Math.random() < carrierConversionChance) //chance to display symptoms
                return State.INFECTED;
            else if(Math.random() < carrierDeathRate) //chance to die without symptoms
                return State.DEAD;
            else if(Math.random() < carrierRecoveryChance) //chance to recover without showing anything
                return State.RECOVERED;
            else
                return State.ASYMPTOMATIC_CARRIER;
        } else if (thisAgentState == State.INFECTED){ //if infected then you have chances to get hospitalized, recover, or die
            if(Math.random() < infectedHostpitalizationRate)//chance to get hospitalized
                return State.HOSPITALIZED;
            else if(Math.random() < infectedDeathRate) //chance to die at home
                return State.DEAD;
            else if(Math.random() < infectedRecoveryRate) //chance to recover at home
                return State.RECOVERED;
            else
                return State.INFECTED;
        } else if (thisAgentState == State.HOSPITALIZED){ //if hospitalized you have chances to recover and die
            if(Math.random() < hospitalizedRecoveryRate)//chance recover per day in the hospital
                return State.HOSPITALIZED;
            else if(Math.random() < hospitalizedDeathRate) //chance to die per day in the hospital
                return State.DEAD;
            else
                return State.HOSPITALIZED;
        }
        return State.SUSCEPTIBLE; //default return all logic above applies to non sus returns
    }

    /**
     * our first case of the virus... dun dun dunnnn
     */
    @Override
    public void setPatientZero(){population[startX][startY].setState(State.INFECTED);}
    public Agent[][] getPopulation(){
        return this.population;
    }
}
