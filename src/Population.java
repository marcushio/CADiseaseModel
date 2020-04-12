/**
 * @author Marcus Trujillo
 * @version
 *
 * Models the population. I guess to start we'll have a population of about 400 peeps?
 */

public class Population {
    private int width = 40, height = 40;
    private int startX = 10, startY = 10;
    private int startX2 = 20, startY2 = 20;
    private Agent[][] population;
    private int totalCases, currInfected, recovered, virus2Cases;

    public Population(){
        population = new Agent[width][height];
        totalCases = 1;
        currInfected = 1;
        recovered = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                boolean isCorner = false;
                boolean isEdge = false;
                if ( (x == 0 ) || (x == width - 1 ) || ( y == height -1)|| ( y == 0) ){isEdge = true;}
                if ( (x == 0 && y == 0) || (x == 0 && y == height-1) || (x == width-1  && y == height-1)|| (x == width-1  && y == 0) ){ isCorner = true; isEdge = false; }

                population[x][y] = new Agent(State.SUSCEPTIBLE, State.SUSCEPTIBLE, isEdge, isCorner, x, y);
            }
        }
        //our case0
        population[startX][startY].setState(State.INFECTED);
        population[startX2][startY2].setNovelState(State.NOVEL_I);
    }

    public Agent[][] getPopulation(){
        return this.population;
    }
    public int getTotalCases(){
        return totalCases;
    }
    public int getCurrInfected(){
        return currInfected;
    }
    public int getRecovered(){
        return recovered;
    }
    public int getVirus2Cases(){
        return virus2Cases;
    }
    public Agent getAgent(int x, int y){
        return population[x][y];
    }

    public void setNovelInfected(int virus2Cases){
        this.virus2Cases = virus2Cases;
    }
    public void setInfected(int infected) {
        this.currInfected = infected;
    }
    public void setRecovered(int recovered){
        this.recovered = recovered;
    }
    public void incrementTotalCases(int cases){
        this.totalCases += cases;
    }
}
