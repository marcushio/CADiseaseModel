import java.util.ArrayList;

/**
 * @author: Marcus Trujillo
 *
 * This is a population that has two virus' spreading in it
 */
public class TwoVirusPopulation extends Population{
    public TwoVirusPopulation(){
        super(40,40);
        setPatientZero();
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    protected void setPatientZero() {
        population[20][20].setState(State.INFECTED);
//        population[10][10].setState(State.NOVEL_I);
    }

    @Override
    State applyRule(int x, int y) {
        return null;
    }
}
