
public class Child extends Agent {
    public Child(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
        this.incStdev = 3.0;
        this.incMean = 7.0;
        this.sympStdev = 3.0;
        this.sympMean = 7.0;
        this.hospStdev = 3.0;
        this.hospMean = 7.0;

        this.asymptomaticRate = 1.0;
        this.hospitalizationRate = 0.2;
        this.asymptomaticMortalityRate = 0.003;
        this.infectedMortalityRate = 0.03;
        this.hospitalMortalityRate = 0.2;
    }

}
