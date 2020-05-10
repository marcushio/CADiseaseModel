public class Elderly extends Agent{
    public Elderly(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
        this.incStdev = 3.0;
        this.incMean = 14.0;
        this.sympStdev = 1.0;
        this.sympMean = 7.0;
        this.hospStdev = 1.0;
        this.hospMean = 4.0;

        this.asymptomaticRate = 0.10;
        this.hospitalizationRate = 0.30;
        this.asymptomaticMortalityRate = 0.0001;
        this.infectedMortalityRate = 0.05;
        this.hospitalMortalityRate = 0.30;
    }
}
