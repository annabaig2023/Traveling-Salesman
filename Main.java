import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
    	
    	System.out.println("Please enter file name:");
    	Scanner scanner = new Scanner(System.in);
    	String path = scanner.next();
    	scanner.close();
    	
    	
    	
        int numberOfCities = 0;
        
       
        String[] cityNames = null;
        int[][] travelingDistance = null;
        
        
        
        try{
        	BufferedReader br = new BufferedReader(new FileReader(path));
        	
        	ArrayList<String> lines = new ArrayList<String>();
        	int elements = 0;
          //String line;
          for(String line; (line = br.readLine()) != null; ) {
                lines.add(line);
                elements++;
            }
            br.close();
            
            
            for(int i = 0; i < elements; i++) {
            	
            
            	if(i == 0) {
            		numberOfCities = Integer.parseInt(lines.get(0));
            		travelingDistance = new int[numberOfCities][numberOfCities];
            		cityNames = new String[numberOfCities];
           
            	} else if (i > 0 && i <= numberOfCities) {
            		cityNames[i-1] = lines.get(i);
            		
           
            	} else {
            		String[] numbers = lines.get(i).split(" ");
            		int[] distances = new int[numbers.length];
            		for(int j = 0; j < numbers.length; j++) {
            			distances[j] = Integer.valueOf(numbers[j]);
            		}
            		travelingDistance[i-numberOfCities-1] = distances;
            	}
            }
        } catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			System.out.println("File has the wrong format");
			System.exit(0);
		}


        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(numberOfCities, travelingDistance, 0, 0);
        Genome result = geneticAlgorithm.optimize();
        
   
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        sb.append(cityNames[result.getStartingCity()]);
        for ( int gene: result.getGenome() ) {
            sb.append(" ");
            sb.append(cityNames[gene]);
        }
        sb.append(" ");
        sb.append("\nLength: ");
        sb.append(result.getFitness());
        
   
        System.out.println(sb.toString());

    }
}