import java.util.ArrayList;

/**
 * @author Marcus Trujillo
 *
 */

public class GeneticAlgorithm{
    private double probNovelStoI_1, probNovelStoI_2, probNovelStoI_3, probNovelStoI_4, probNovelStoI_5,
            probNovelStoI_6, probNovelStoI_7, probNovelStoI_8 ;
    public GeneticAlgorithm() {
        probNovelStoI_1 = Math.random(); //probability of S->R for novel virus
        probNovelStoI_2 = Math.random();
        probNovelStoI_3 = Math.random();
        probNovelStoI_4 = Math.random();
        probNovelStoI_5 = Math.random();
        probNovelStoI_6 = Math.random();
        probNovelStoI_7 = Math.random();
        probNovelStoI_8 = Math.random();
    }

    public ArrayList<Double> createNextGen(ArrayList<Double> prevGenWinner, ArrayList<Double> prevGenRunnerUp){
        //lets do some crossing over
        double crossover = Math.random();
        if(crossover < .5 ){
            //generate random 1-8 to crossover to
        }
        probNovelStoI_1 = Math.random(); //probability of S->R for novel virus
        probNovelStoI_2 = Math.random();
        probNovelStoI_3 = Math.random();
        probNovelStoI_4 = Math.random();
        probNovelStoI_5 = Math.random();
        probNovelStoI_6 = Math.random();
        probNovelStoI_7 = .44;
        probNovelStoI_8 = .75;
        ArrayList<Double> nextGen = new ArrayList<>();
        nextGen.add(probNovelStoI_1);
        return nextGen;
    }
    //public getters just gets the probabilities

}
