
public class Child extends Agent {
    public Child(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
        this.incStdev = 6.0;
        this.incMean = 14.0;
        this.sympStdev = 3.0;
        this.sympMean = 7.0;
        this.hospStdev = 3.0;
        this.hospMean = 7.0;

        this.asymptomaticRate = 0.90; //high asymptomatic rate
//        this.asymptomaticRate = 0.10; //low asymptomatic rate
        this.hospitalizationRate = 0.02;
        this.asymptomaticMortalityRate = 0.0;
        this.infectedMortalityRate = 0.0;
        this.hospitalMortalityRate = 0.0;

    }

}
