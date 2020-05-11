import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Trujillo
 */
public class ConfigFileReader {
    private List<String> nodeSpecs = new ArrayList<>();
    private List<String> edgeSpecs = new ArrayList<>();
    private String filePath = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\edges.txt" ; //definitely take this in from commandline in the future

    public void setSpecs(){
        List<String> lines = new ArrayList<>();
        String  content = "";
        String line;
        try(BufferedReader reader = new BufferedReader((new FileReader(filePath)))){
            while((line = reader.readLine())!=null){
                if(line.matches("agent.*")) nodeSpecs.add(line);
                if(line.matches("edge.*")) edgeSpecs.add(line);
            }
        }
        catch(IOException e){
            System.out.println("The path "+filePath+"could not be read");
        }
    }

    /**
     * @return specs about the nodes
     */
    public List<String> getAgentSpecs() {
        return nodeSpecs;
    }

    /**
     * @return the specs about the edges
     */
    public List<String> getEdgeSpecs() {
        return edgeSpecs;
    }


}
