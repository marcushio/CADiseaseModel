import java.util.List;
import java.util.Scanner;

public class PopulationFactory {
    private String getPathToConfigFile = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\edges.txt";
    private MapPopulation network = new MapPopulation(100, 100);
    private ConfigFileReader handler = new ConfigFileReader();
    private String pathToConfigFile;

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
    public MapPopulation build(){
        handler.setSpecs(); //handler.setSpecs(pathToConfigFile); use if you go back to feeding in filename right now it's hardcoded
        setNodes(handler.getAgentSpecs());
        setEdges(handler.getEdgeSpecs());
        return network;
    }

    private void setNodes(List<String> nodeSpecs){
        for(String nodeSpec : nodeSpecs){
            Scanner scanner = new Scanner(nodeSpec);
            scanner.next();
            Coordinate coordinate = new Coordinate(scanner.nextInt(), scanner.nextInt());
            MapAgent node = new MapAgent();
            node.setCoordinate(coordinate);
            network.put(coordinate,node);
            network.addAgent(node);
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