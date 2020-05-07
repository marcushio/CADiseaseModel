public class Elderly extends Agent{
    public Elderly(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
        this.incStdev = 3.0;
        this.incMean = 7.0;
        this.sympStdev = 3.0;
        this.sympMean = 7.0;
        this.hospStdev = 3.0;
        this.hospMean = 7.0;

        this.asymptomaticRate = 0.01;
        this.hospitalizationRate = 0.8;
        this.asymptomaticMortalityRate = 0.003;
        this.infectedMortalityRate = 0.03;
        this.hospitalMortalityRate = 0.2;
    }
}
