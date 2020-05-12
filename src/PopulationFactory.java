import java.util.List;
import java.util.Scanner;

public class PopulationFactory {
    private String getPathToConfigFile = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\edges.txt";
    private Population network = new StochasticPopulation();
    private ConfigFileReader handler = new ConfigFileReader();
    private String pathToConfigFile;
    private double percentElderly = 0.147; //percent of the population aged 62 and over in the US in the 2010 US Cencus
    private double percentAdult = 0.742; //percent of the population aged 18 and over in the US in the 2010 US Cencus

    /**
     * constructor
     * @param pathToConfigFile
     */
    public PopulationFactory(String pathToConfigFile){
        this.pathToConfigFile = pathToConfigFile;
    }
    public PopulationFactory(){};
    /**
     * build the network and
     * @return the network
     */
    public Population build(){
        handler.setSpecs(); //handler.setSpecs(pathToConfigFile); use if you go back to feeding in filename right now it's hardcoded
        setNodes(handler.getAgentSpecs());
        setEdges(handler.getEdgeSpecs());
        return network;
    }

    private void setNodes(List<String> nodeSpecs){
        for(String nodeSpec : nodeSpecs){
            Scanner scanner = new Scanner(nodeSpec);
            scanner.next();
            //Coordinate coordinate = new Coordinate(scanner.nextInt(), scanner.nextInt()); change this to jive with Ian
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Coordinate thisLocation = new Coordinate(x,y);

            //new agegroup feature
            double ageSelector = Math.random();
            if(ageSelector < percentElderly){
                Elderly geezer = new Elderly(State.SUSCEPTIBLE, x, y); //see I called 'em geezer, we like to have fun here
                network.addAgent(geezer);
                network.put(thisLocation, geezer);
            } else if(ageSelector < percentAdult){
                Adult regJoe = new Adult(State.SUSCEPTIBLE, x, y);
                network.addAgent(regJoe);
                network.put(thisLocation, regJoe);
            } else {
                Child munchkin = new Child(State.SUSCEPTIBLE, x, y);
                network.addAgent(munchkin);
                network.put(thisLocation, munchkin);
            }


            /* Marcus' old way of adding people to the pop
            Agent node = new Agent();
            node.setCoordinate(coordinate);
            network.put(coordinate,node);
            network.addAgent(node);
            */
        }

    }
    private void setEdges(List<String> edgeSpecs){
        for(String edgeSpec : edgeSpecs){
            Scanner scanner = new Scanner(edgeSpec);
            scanner.next();
            Coordinate coordinateA = new Coordinate(scanner.nextInt(), scanner.nextInt());
            Coordinate coordinateB = new Coordinate(scanner.nextInt(), scanner.nextInt());
            network.addEdge(coordinateA,coordinateB);
        }
    }
}
