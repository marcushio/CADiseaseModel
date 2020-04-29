import java.util.ArrayList;

/**
 * @author: Marcus Trujillo
 *
 * This is a population that has two virus' spreading in it
 */
public class TwoVirusPopulation extends Population{
    private int height, width;
    private Agent[][] population;

    private double probStoI_1 = .5 , probStoI_2 = .36, probStoI_3 = .7, probStoI_4 = .8, probStoI_5 = .55,
            probStoI_6 = .59, probStoI_7 = .7, probStoI_8 = .60;
    private double probNovelStoI_1 = Math.random(), probNovelStoI_2 = Math.random(), probNovelStoI_3 = Math.random(),
            probNovelStoI_4 = Math.random(), probNovelStoI_5 = Math.random(), probNovelStoI_6 = Math.random(),
            probNovelStoI_7 = Math.random(), probNovelStoI_8 = Math.random();   //probStoI meaning probability of S->I and prob Novel like probability for the novel virus

    public TwoVirusPopulation(){
        super(40,40);
        setPatientZero();
    }

    public TwoVirusPopulation(int height, int width){
        super(height, width);
        setPatientZero();
    }

    @Override
    public void update() {
        Agent[][] nextPopulation = population;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                State nextState = applyRule(x,y);
                nextPopulation[x][y].setState( nextState );
            }
        }
        this.population = nextPopulation;
    }

    @Override
    public int countSickNeighbors(int x, int y){
        //how to get sickies goes here
        return 0;
    }

    public State applyRule(int x, int y){
        return State.INFECTED;
    }
    //probs limit this to one application rule inthe future
    public State applyRuleRandom(int x, int y){
        return State.INFECTED;
    }

    public State applyRuleVirus2(int x, int y){
        return State.NOVEL_I;
    }

    private StochasticPopulation getNextTwoVirusPopulation(StochasticPopulation pop){
        StochasticPopulation nextStochasticPopulation = new StochasticPopulation();
        boolean hasSick = nextStochasticPopulation.sickies();
        nextStochasticPopulation = pop;
        hasSick = nextStochasticPopulation.sickies();
        pop.printStatus();

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(x == 19 && y == 19){
                    System.out.println("debug rule appl");
                }
                ArrayList<Agent> hood = pop.getNeighborhood(x,y);
                int sickies = countSickNeighbors2(x,y);
                if(sickies > 1){
                    System.out.println("why the hell are there sick neighbors?"); //debug because I was getting neighborhoods with sick people in new populations.
                    pop.getNeighborhood(x,y);
                }
                Agent thisAgent = pop.getAgent(x,y);

                State nextState = applyRuleRandom(x,y );
                State nextNovelState = applyRuleVirus2(x,y );

                nextStochasticPopulation.setAgentNovelState(nextNovelState, x, y);
                nextStochasticPopulation.setAgentState(nextState, x, y);
            }
        }
        return nextStochasticPopulation;
    }

    public int countSickNeighbors2(int x, int y){
        return 0;
    }
    public void setPatientZero(){
        population[20][20].setState(State.INFECTED);
    }
}
