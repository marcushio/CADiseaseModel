import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class ConfigFileWriter {
//is this really going to save me time? Maybe I'll make some random map generator
    private String outputFilename = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\config.txt";
    ArrayList<Coordinate> coordinates;

    public String getFilename(){ return outputFilename; }

    private class Coordinate{
        int x, y;
        public Coordinate(int x, int y){
            this.x = x; this.y = y;
        }

    }

    private void writeCoordinates(){
        Random random = new Random();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), true))){
            int x = 0;
            while(x < 1000){
                int startx = random.nextInt(1000);
                int starty = random.nextInt( 1000);
                int endx = random.nextInt(1000);
                int endy = random.nextInt(1000);
            }

            /*   //iterate through 100x100 but maybe I'll make something different later
            for(int i = 0; i <= 100; i++){
                for(int j = 0; j <= 100; j++){

                    if(i < 10 && j < 10 )
                        writer.write("Node " + i + j);
                }
            }*/
        }catch(Exception ex){
            System.out.println("couldn't write results");
        }
    }

    public static void main(String[] args){
        ConfigFileWriter cfw = new ConfigFileWriter();
        cfw.writeCoordinates();
        System.out.println("finished writing new config file in " + cfw.getFilename());
    }
}
