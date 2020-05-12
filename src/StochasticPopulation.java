import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Marcus Trujillo
 * @version 4/28/20
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class StochasticPopulation extends Population {
    private double[] probStoI = {0.0, 0.5 , 0.36, 0.7, 0.8, 0.55, 0.59, 0.7, 0.60};

    public StochasticPopulation(){ }
//old    public StochasticPopulation() {
//        this(200, 200);
//    }
//    public StochasticPopulation(int height, int width) {
//        super(height, width);
//        setPatientZero();
//    }


    /**
     * apply a rule in order to figure out what an agent's next state should be
     * @deprecated this should no longer be used
     */
    @Override
    public State applyRule(int x, int y){
        return null;
    }

    /**
     * updates the population to the next time step
     */
    @Override
    public boolean update(){
        int currInfected = 0, susceptible = 0, recovered = 0,CARRIER = 0, HOSPITALIZED = 0, DEAD = 0;

        //walk through the population, updating current states, progressing the virus
        for(Agent agent : population) {
            agent.incrementTime();
        }

        //walk through the population, infecting those exposed to the virus, counting the numbers of each state
        for(Agent agent : population){
                State curState = agent.getState();
                if(curState == State.SUSCEPTIBLE){
                    //if susceptible, check if getting exposed and infected
                    if(Math.random() < probStoI[countSickNeighbors(agent.getX(),agent.getY())]){
                        agent.infect();
                    } else {
                        //if not exposed, still count as susceptible
                        susceptible++;
                    }
                }

                if(curState == State.ASYMPTOMATIC_CARRIER){ CARRIER++; }
                if(curState == State.INFECTED){ currInfected++; }
                if(curState == State.HOSPITALIZED){ HOSPITALIZED++; }
                if(curState == State.RECOVERED){ recovered++; }
                if(curState == State.DEAD){ DEAD++; }

        }

        System.out.println(susceptible + "," + recovered  + "," + CARRIER + "," + currInfected + "," + HOSPITALIZED + "," + DEAD);

        if(currInfected + CARRIER + HOSPITALIZED > 0)
            return true;
        else
            return false;
    }

    public ArrayList<Agent> getPopulation(){
        return this.population;
    }

    @Override
    public Map<Coordinate, Agent> getCoordinateAgentMap() {
        return super.getCoordinateAgentMap();
    }
}
