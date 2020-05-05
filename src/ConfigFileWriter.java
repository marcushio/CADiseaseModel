import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ConfigFileWriter {
//is this really going to save me time? Maybe I'll make some random map generator

    public static void main(String[] args){
        String outputFilename = "C:\\Users\\marcu\\OneDrive - University of New Mexico\\CS423 Complex adaptive systems\\Project3\\config.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFilename), true))){
            //iterate through 100x100 but maybe I'll make something different later 
            for(int i = 0; i <= 100; i++){
                for(int j = 0; j <= 100; j++){

                    if(i < 10 && j < 10 )
                        writer.write("Node " + i + j);
                }
            }
        }catch(Exception ex){
            System.out.println("couldn't write results");
        }
    }
}
