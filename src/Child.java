
public class Child extends Agent {
    //alternate values for progression
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
    //end instance variables

    //do these fellaz know they're neighborssssss?
    //we can add all kinds of fun things later but for now these guys just have states
    //private float vulnerability; //odds of death if infected

    public Child(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
    }

}
