import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Trujillo
 * @version 4/28/20
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class StochasticPopulation extends Population {
//    private double probStoI_1 = .25, probStoI_2 = .3, probStoI_3 = .33, probStoI_4 = .4, probStoI_5 = .55,
//            probStoI_6 = .6, probStoI_7 = .69, probStoI_8 = .75;

    //*high chance of carrier
    private double[] probStoI = {0.0, 0.5 , 0.36, 0.7, 0.8, 0.55, 0.59, 0.7, 0.60};

    private double carrierConversionChance = 0.15/14.0;
    private double carrierRecoveryChance = 0.5/14.0;
    private double carrierDeathRate = 0.0005 /14.0;

    private double infectedHostpitalizationRate = 0.2 / 14.0;
    private double infectedDeathRate = 0.005/14.0;
    private double infectedRecoveryRate = 1.0/14.0;

    private double hospitalizedDeathRate = 0.05/14.0;
    private double hospitalizedRecoveryRate = 0.95/14.0;
    //*/

    /*low chance of no symptoms, roughly 3% mortality
    private double[] probStoI = {0.0, 0.5 , 0.36, 0.7, 0.8, 0.55, 0.59, 0.7, 0.60};

    private double carrierConversionChance = 0.95;
    private double carrierRecoveryChance = 0.005;
    private double carrierDeathRate = 0.00025;

    private double infectedHostpitalizationRate = 0.008 ;
    private double infectedDeathRate = 0.003 ;
    private double infectedRecoveryRate = 0.12;

    private double hospitalizedDeathRate = 0.005;
    private double hospitalizedRecoveryRate = 0.05;
    //*/

    private int startX, startY;
    private int totalInfected = 0, totalHospitalized = 0;

    public StochasticPopulation() {
        this(400, 400);
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
        int currInfected = 0, susceptible = 0, recovered = 0,CARRIER = 0, HOSPITALIZED = 0, DEAD = 0;

        //walk through the population, updating current states, progressing the virus
        for(int i = 0; i < super.getWidth(); i++){
            for(int j = 0; j < super.getHeight(); j++){
                population[i][j].incrementTime();
            }
        }

        //walk through the population, infecting those exposed to the virus, counting the numbers of each state
        for(int i = 0; i < super.getWidth(); i++){
            for(int j = 0; j < super.getHeight(); j++){
                State curState = population[i][j].getState();

                if(curState == State.SUSCEPTIBLE){
                    //if susceptible, check if getting exposed and infected
                    if(Math.random() < probStoI[countSickNeighbors(i, j)]){
                        population[i][j].infect();
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
        }

        System.out.println(susceptible + "," + recovered  + "," + CARRIER + "," + currInfected + "," + HOSPITALIZED + "," + DEAD);

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
        //do nothing
        return population[x][y].getState();
    }

    /**
     * our first case of the virus... dun dun dunnnn
     */
    @Override
    public void setPatientZero(){
        population[startX][startY].infect();
        population[startX][startY].makeBad();
    }
    public Agent[][] getPopulation(){
        return this.population;
    }

    public ArrayList<Agent> getNeighborhood (int x, int y) {

        Agent agent = population[x][y];
        ArrayList<Agent> neighborhood = agent.getNeighborhood();
        if (neighborhood == null) {
            neighborhood = new ArrayList<Agent>();

            //establish wraparound for the map
            int left = x - 1;
            int right = x + 1;
            int up = y + 1;
            int down = y - 1;
            if (left == -1) //if left is outside of map, wrap around
                left = super.getWidth() - 1;
            if (right == super.getWidth()) //if right is outside of map, wrap around
                right = 0;
            if (down == -1) //if down is outside of map, wrap around
                down = super.getHeight() - 1;
            if (up == super.getHeight()) //if up is outside of map, wrap around
                up = 0;

            //populate the neighborhood
            neighborhood.add(population[left][up]);
            neighborhood.add(population[left][y]);
            neighborhood.add(population[left][down]);
            neighborhood.add(population[x][up]);
//            neighborhood.add(population[x][y]); //dont need to add yourself to your map
            neighborhood.add(population[x][down]);
            neighborhood.add(population[right][up]);
            neighborhood.add(population[right][y]);
            neighborhood.add(population[right][down]);

            //save neighborhood to prevent recalculation
            agent.setNeighborhood(neighborhood);
        }
        return neighborhood;
    }

    public int getTotalInfected(){return totalInfected;}
    public int getTotalHospitalized(){return totalHospitalized;}
}
