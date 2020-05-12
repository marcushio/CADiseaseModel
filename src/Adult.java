public class Adult extends Agent{
    public Adult(State state, int xPosition, int yPosition){
        super(state, xPosition, yPosition);
        this.incStdev = 3.0;
        this.incMean = 14.0;
        this.sympStdev = 1.0;
        this.sympMean = 7.0;
        this.hospStdev = 1.0;
        this.hospMean = 4.0;

        this.asymptomaticRate = 0.60; //high asymptomatic rate
//        this.asymptomaticRate = 0.05; //low asymptomatic rate
        this.hospitalizationRate = 0.25;
        this.asymptomaticMortalityRate = 0.0001;
        this.infectedMortalityRate = 0.002;
        this.hospitalMortalityRate = 0.015;
    }
}
