import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marcus Trujillo
 *
 */

public class GeneticAlgorithm{
    private ArrayList<ArrayList<Double>> previousGeneration;
    private ArrayList<ArrayList<Double>> currentGeneration;
    private Random random = new Random();
    private double probNovelStoI_1, probNovelStoI_2, probNovelStoI_3, probNovelStoI_4, probNovelStoI_5,
            probNovelStoI_6, probNovelStoI_7, probNovelStoI_8 ;
    public GeneticAlgorithm() {
        probNovelStoI_1 = Math.random(); //probability of S->Igit  for novel virus
        probNovelStoI_2 = Math.random();
        probNovelStoI_3 = Math.random();
        probNovelStoI_4 = Math.random();
        probNovelStoI_5 = Math.random();
        probNovelStoI_6 = Math.random();
        probNovelStoI_7 = Math.random();
        probNovelStoI_8 = Math.random();
    }

    public ArrayList<Double> createNextGen(ArrayList<Double> prevGenWinner, ArrayList<Double> prevGenRunnerUp){
        //lets do some crossing over and mutations oooooie
        for(int i = 0; i < prevGenWinner.size(); i++) {
            if (Math.random() < .1) { //crossover happens at .2 chance for now...
                int crossoverLoci = random.nextInt(8) + 1;
                switch(i) {
                    case 0 :
                        probNovelStoI_1 = Math.random();
                        break;
                    case 1 :
                        probNovelStoI_2 = Math.random();
                        break;
                    case 2 :
                        probNovelStoI_3 = Math.random();
                        break;
                    case 3 :
                        probNovelStoI_4 = Math.random();
                        break;
                    case 4 :
                        probNovelStoI_5 = Math.random();
                        break;
                    case 5 :
                        probNovelStoI_6 = Math.random();
                        break;
                    case 6 :
                        probNovelStoI_7 = Math.random();
                        break;
                    case 7 :
                        probNovelStoI_8 = Math.random();
                        break;
                }

            }
            if (Math.random() < .005) { //mutation happens at incredibly low rate this is actually probably insanely high given I'm basically reduced the genome to 8;
                switch(i) {
                    case 0 :
                        probNovelStoI_1 = Math.random();
                        break;
                    case 1 :
                        probNovelStoI_2 = Math.random();
                        break;
                    case 2 :
                        probNovelStoI_3 = Math.random();
                        break;
                    case 3 :
                        probNovelStoI_4 = Math.random();
                        break;
                    case 4 :
                        probNovelStoI_5 = Math.random();
                        break;
                    case 5 :
                        probNovelStoI_6 = Math.random();
                        break;
                    case 6 :
                        probNovelStoI_7 = Math.random();
                        break;
                    case 7 :
                        probNovelStoI_8 = Math.random();
                        break;
                }
            }
        }

        ArrayList<Double> nextGen = new ArrayList<>();
        nextGen.add(probNovelStoI_1);
        return nextGen;
    }
    //public getters just gets the probabilities

}
