import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marcus Trujillo
 *
 */

public class GeneticAlgorithm{
    private ArrayList<ArrayList<Double>> previousGeneration = new ArrayList<>();
    private ArrayList<ArrayList<Double>> currentGeneration = new ArrayList<>();
    private Random random = new Random();
    double probNovelStoI_1, probNovelStoI_2, probNovelStoI_3, probNovelStoI_4, probNovelStoI_5,
            probNovelStoI_6, probNovelStoI_7, probNovelStoI_8 ;
    public boolean running;

    public GeneticAlgorithm() {
        running = true;
        for(int i = 0; i < 10 ; i++){ //make 10 members of the current gen
            ArrayList<Double> competitor = new ArrayList<>();
            for(int j = 1; j <= 8; j++ ) {//make 8 state transition probabilities
                competitor.add(Math.random());
            }
            currentGeneration.add(competitor);
        }
    }

    public ArrayList<ArrayList<Double>> getCurrentGeneration(){
        return currentGeneration;
    }

    public ArrayList<ArrayList<Double>> createNextGen(ArrayList<Double> prevGenWinner, ArrayList<Double> prevGenRunnerUp){
        ArrayList<ArrayList<Double>> newGeneration = new ArrayList<>();
        newGeneration.add(prevGenWinner);
        newGeneration.add(prevGenRunnerUp);
        for(int j = 0; j < 8; j++){
        //lets do some crossing over between prevGenWinner and the runner up
        int crossoverLoci = random.nextInt(8) + 1;
        for(int i = 0; i < prevGenWinner.size(); i++) {
            if (Math.random() < .05) { //crossover happens at .05 chance for now...
                switch (i) {
                    case 0:
                        probNovelStoI_1 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 1:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_2 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 2:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_3 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 3:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_4 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 4:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_5 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 5:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_6 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 6:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_7 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                    case 7:
                        crossoverLoci = random.nextInt(8) + 1;
                        probNovelStoI_8 = prevGenRunnerUp.get(crossoverLoci);
                        break;
                }

            }
            if (Math.random() < .001) { //mutation happens at incredibly low rate this is actually probably insanely high given I'm basically reduced the genome to 8;
                switch (i) {
                    case 0:
                        probNovelStoI_1 = Math.random();
                        break;
                    case 1:
                        probNovelStoI_2 = Math.random();
                        break;
                    case 2:
                        probNovelStoI_3 = Math.random();
                        break;
                    case 3:
                        probNovelStoI_4 = Math.random();
                        break;
                    case 4:
                        probNovelStoI_5 = Math.random();
                        break;
                    case 5:
                        probNovelStoI_6 = Math.random();
                        break;
                    case 6:
                        probNovelStoI_7 = Math.random();
                        break;
                    case 7:
                        probNovelStoI_8 = Math.random();
                        break;
                }
            }
        }

        }

        ArrayList<Double> nextGen = new ArrayList<>();

        return newGeneration;
    }
    public ArrayList testViruses(ArrayList<ArrayList<Double>> competitors ){
        ArrayList<ArrayList<Double>> topTwo = new ArrayList<>();
        float maxDifference = 0;
        float minDifference = 1600; //can't be off by more than that in a 40X40

        for(ArrayList<Double> competitor : competitors) {
            Controller simulator = new Controller();
            simulator.setTransmissionRates(competitor);
            for (int i = 0; i < 25; i++) {
                simulator.step();
            }
            float thisDifference = Math.abs( simulator.getInfected() - 1245); //1245 was the average over like 50 samples for our model
            if(thisDifference < minDifference){
                minDifference = thisDifference;
                topTwo.add(0, competitor);
            }
            if(thisDifference > maxDifference){
                maxDifference = thisDifference;
            }
            if(maxDifference < 20 ){ running = false;} //if every single one is within 20 of our desired target turn off our running boolean

        }

        return topTwo;
    }

    public static void main(String[] args){
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ArrayList<ArrayList<Double>> topTwo = new ArrayList<>(); //I'll make an actual class later for now each virus is an ArrayList of Doubles
        while(ga.running){ //until the little fellaz don't have significantly different performance
            topTwo = ga.testViruses(ga.getCurrentGeneration());
            ga.createNextGen(topTwo.get(0), topTwo.get(1));
        }
    }

}
