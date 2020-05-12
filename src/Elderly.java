public class Elderly extends Agent{
    public Elderly(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
        this.incStdev = 6.0;
        this.incMean = 14.0;
        this.sympStdev = 3.0;
        this.sympMean = 7.0;
        this.hospStdev = 3.0;
        this.hospMean = 7.0;

        this.asymptomaticRate = 0.40; //high asymptomatic rate
//        this.asymptomaticRate = 0.01; //low asymptomatic rate
        this.hospitalizationRate = 0.01;
        this.asymptomaticMortalityRate = 0.0001;
        this.infectedMortalityRate = 0.0001;
        this.hospitalMortalityRate = 0.10;
    }
}
