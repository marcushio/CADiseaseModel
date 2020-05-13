import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Marcus Trujillo
 */
public class ConfigFileWriter {
    //is this really going to save me time? Maybe I'll make some random map generator
    private String outputFilename = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\edges.txt";
    //these are so we know if there's an agent at certain coords
    ArrayList<Coordinate> coordinates = new ArrayList<>();
    //these are so we know there's already a connection there
    ArrayList<Edge> edges = new ArrayList<>(); //was going to use set but I'm checking for contains before adding every time anyway...
    String mode = "small world"; // "tree" "clustered" "random"
    private int bound = 100;

    class Edge{
        public Coordinate startCoordinate; Coordinate endCoordinate;
        private Edge(Coordinate startCoord, Coordinate endCoord){
            this.startCoordinate = startCoord;
            this.endCoordinate = endCoord;
        }
        @Override
        public boolean equals(Object o){
            if( !(o instanceof Edge) ) return false;
            Edge other = (Edge) o;
            if( !other.startCoordinate.equals(this.startCoordinate)  ){return  false;}
            if( !other.endCoordinate.equals(this.endCoordinate) ){return false;}
            return true;
        }
        Coordinate getStartCoordinate(){return startCoordinate;}
        Coordinate getEndCoordinate(){return endCoordinate; }
    }

    private void writeCoordinates(){
        if(mode.equals("random")){
            writeRandom();
        } else if (mode.equals("clustered")){
            writeClustered();
        } else if (mode.equals("tree")){

        } else if (mode.equals("small world")){
            writeSmallWorld();
        }

    }

    /**
     * writea a config file with clusters
     */
    private void writeClustered(){
        //fuck this just write some clusters then connect em
        Coordinate coordinate1 = new Coordinate( 2,2);
        writeMooreCluster(coordinate1, 10);
    }

    /**
     * a fully connected graph. Most of the time though, these are mostly just 2ary nodes.
     */
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

    /**
     * just note, probably don't give an x or y close to boundaries because there isn't any logic right now.
     * clusters are written starting in the upper left and then go down and to the right, so you're "central" agent is
     * always in the upper left corner of the cluster
     * Type 1 clusters are like highly connected in a down right fashion
     * @param coord that you start the cluster around
     * @param size how far away you want to extend from the central Agent
     */
    private void writeACluster1(Coordinate coord , int size){
        int x = coord.getX(); int y = coord.getY();
        for(int i = 0 ; i <= size; i+=2){ //these loops track the current node we're connecting to everyone
            for (int j = 0 ; j <= size; j+=2){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), true))) { //append is true because we're adding to a file here.
                    Coordinate current = new Coordinate(x + i , y + j);

                    for(int tempx = i; tempx <= size; tempx++){ //these loops are tracking the node that are being connected to our current node tracked by the outside loop
                        for(int tempy = j; tempy <= size; tempy++ ) {
                            Coordinate temp = new Coordinate(current.getX() + tempx, current.getY() + tempy);
                            Edge newEdge = new Edge(current, temp);
                            if (!coordinates.contains(current)) {
                                coordinates.add(current);
                                writer.write("agent " + current.getX() + " " + current.getY() + "\n");
                            }
                            if (!coordinates.contains(temp)){
                                coordinates.add(temp);
                                writer.write("agent " + temp.getX() + " " + temp.getY() + "\n");
                            }
                            if ( !edges.contains(new Edge(current, temp)) && !current.equals(temp) ) {
                                edges.add(newEdge);
                                writer.write("edge " + current.getX() + " " + current.getY() + " " + temp.getX() + " " + temp.getY() + "\n");
                            }

                        }
                    }

                } catch (IOException ex) {
                    System.out.println("Couldn't write this cluster");
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Cluster type 2 is a fully connected cluster where there is there is a central node that is the only one
     * connected to the real world.
     * ok we're going for a moore neighborhood here.
     * @param coord
     * @param size
     */
    private void writeMooreCluster(Coordinate coord, int size){ //fudge it, I'm not protecting this with logic.
        size = size*2; //scale by two - ok I'll be the first to say I wrote this in a confusing ass way
        int x = coord.getX(); int y = coord.getY();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), true))){
            for(int i = 0; i < size; i+=2 ){
                for(int j = 0; j < size; j+=2){
                    int xHere = x+i; int yHere = y+j;

                    Coordinate here = new Coordinate( x+i, y+j);
                    if( !coordinates.contains(here) ) {
                        coordinates.add(here);
                        writer.write("agent " + (x+i) + " " + (y+j) + "\n");
                    }
                    //if ( xHere != size ) { //if we're not at our right bound
                    Coordinate right = new Coordinate(x + i + 2, y + j);
                    if (!coordinates.contains(right)) {
                        coordinates.add(right);
                        writer.write("agent " + (x + i + 2) + " " + (y + j) + "\n");
                    }
                    Edge connectRight = new Edge(here, right);
                    if (!edges.contains(connectRight)) {
                        edges.add(connectRight);
                        writer.write("edge " + here.getX() + " " + here.getY() + " " + right.getX() + " " + right.getY() + "\n");
                    }
                    //}
                    //if( yHere != size ) { //if we're not at our lower bound
                    Coordinate down = new Coordinate(x + i, y + j + 2);
                    if (!coordinates.contains(down)) {
                        coordinates.add(down);
                        writer.write("agent " + (x + i) + " " + (y + j + 2) + "\n");
                    }
                    Edge connectDown = new Edge(here, down);
                    if (!edges.contains(connectDown)) {
                        edges.add(connectDown);
                        writer.write("edge " + here.getX() + " " + here.getY() + " " + down.getX() + " " + down.getY() + "\n");
                    }
                    //}

                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void writeSmallWorld(){
        //just make something that's kinda well distributed
        ArrayList<Coordinate> centers = new ArrayList<>();
        Random random = new Random();
        for(int x = 0; x < 3; x++ ){ //write the clusters
            for(int y =0 ; y <3; y++){
                Coordinate here = new Coordinate(x*40,y*40); ////scale em a little so they're far enough apart
                centers.add(here);
                writeMooreCluster(here, (random.nextInt(12) + 4) ); //have a bit of randomness for size of cluster
            }
        }
        int i=0;
        for(Coordinate here : centers){ //connect to all predecessors
            Coordinate next = null;
            if(i<centers.size()-1)  next = centers.get(i+1);
            if( !here.equals(next) && (next != null)) writeEdge( new Edge(here, next) ); //idk how they would have been the same...
            i++;
        }
        //eh needs a little more connection
        writeEdge( new Edge (centers.get(0), centers.get(3)));
        writeEdge( new Edge (centers.get(4), centers.get(7)));
        writeEdge( new Edge (centers.get(5), centers.get(8)));
        writeEdge( new Edge (centers.get(1), centers.get(4)));
        for(int r=0;r<100;r++){//let's increase connectivity so let's make some random edges
            int nextIndex1 = random.nextInt( coordinates.size() -1 );
            int nextIndex2 = random.nextInt( coordinates.size() -1 );
            writeEdge( new Edge( coordinates.get(nextIndex1), coordinates.get(nextIndex2) ));
        }
    }

    private void writeEdge(Edge edge){
        if( !edges.contains(edge) ) {
            edges.add(edge);
            int startx = edge.startCoordinate.getX();
            int starty = edge.startCoordinate.getY();
            int endx = edge.endCoordinate.getX();
            int endy = edge.endCoordinate.getY();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), true))) {
                writer.write("edge " + startx + " " + starty + " " + endx + " " + endy + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
