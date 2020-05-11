import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * @author Marcus Trujillo
 */
public class ConfigFileWriter {
//is this really going to save me time? Maybe I'll make some random map generator
    private String outputFilename = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\edges.txt";
    ArrayList<Coordinate> coordinates = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>(); //was going to use set but I'm checking for contains before adding every time anyway...
    String mode = "random"; //"clustered" "tree"
    private int bound = 100;

    class Edge{
        Coordinate startCoordinate; Coordinate endCoordinate;
        private Edge(Coordinate startCoord, Coordinate endCoord){
            this.startCoordinate = startCoord;
            this.endCoordinate = endCoord;
        }
        Coordinate getStartCoordinate(){return startCoordinate;}
        Coordinate getEndCoordinate(){return endCoordinate; }
    }

    private void writeCoordinates(){
        if(mode.equals("random")){
            writeRandom();
        } else if (mode.equals("clustered")){

        } else if (mode.equals("tree")){

        } else if (mode.equals("small world")){

        }

    }

    private void writeRandom(){
        Random random = new Random();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), false))){
            int i = 0;
            int startx = 0; int starty = 0;
            int endx = 1;
            int endy = 1;

            while(i < 1000){
                startx = endx;
                starty = endy;
                Coordinate startCoord = new Coordinate( startx, starty);
                endx = random.nextInt(bound);
                endy = random.nextInt(bound);
                Coordinate endCoord = new Coordinate( endx, endy);
                Edge edge = new Edge(startCoord, endCoord);
                if( !coordinates.contains(startCoord)){
                    coordinates.add(startCoord);
                    writer.write("agent " + startx + " " + starty + "\n");
                }
                if( !coordinates.contains(endCoord)){
                    coordinates.add(endCoord);
                    writer.write("agent " + endx + " " + endy + "\n" );
                }
                if( !edges.contains(edge) ) {
                    edges.add(edge);
                    //if( coordinates.contains(edge.startCoordinate) edge.endCoordinate)
                    writer.write("edge " + startx + " " + starty + " " + endx + " " + endy + "\n");
                }
                i++;
            }
            System.out.println("that's 1k agents");
        }catch(Exception ex){
            System.out.println("couldn't write results");
        }
    }

    private void writeRandomDisconnect(){
        Random random = new Random();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), false))){
            int x = 0;
            while(x < 1000){
                int startx = random.nextInt(bound);
                int starty = random.nextInt( bound);
                Coordinate startCoord = new Coordinate( startx, starty);
                int endx = random.nextInt(bound);
                int endy = random.nextInt(bound);
                Coordinate endCoord = new Coordinate( endx, endy);
                Edge edge = new Edge(startCoord, endCoord);
                if( !coordinates.contains(startCoord)){
                    coordinates.add(startCoord);
                    writer.write("agent " + startx + " " + starty + "\n");
                }
                if( !coordinates.contains(endCoord)){
                    coordinates.add(endCoord);
                    writer.write("agent " + endx + " " + endy + "\n" );
                }
                if( !edges.contains(edge) ) {
                    edges.add(edge);
                    //if( coordinates.contains(edge.startCoordinate) edge.endCoordinate)
                    writer.write("edge " + startx + " " + starty + " " + endx + " " + endy + "\n");
                }
                x++;
            }
            System.out.println("that's 1k agents");
        }catch(Exception ex){
            System.out.println("couldn't write results");
        }
    }

    public String getFilename(){ return outputFilename; }

    public static void main(String[] args){
        ConfigFileWriter cfw = new ConfigFileWriter();
        cfw.writeCoordinates();
        System.out.println("finished writing new config file in " + cfw.getFilename());
    }
}
