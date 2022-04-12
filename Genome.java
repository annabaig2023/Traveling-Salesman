import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genome implements Comparable<Object> {
	
    private List<Integer> genome;
    private int[][] travelingDistance;
    private int startingCity;
    private int numberOfCities = 0;
    private int fitness;

    // without permutationOfCities
    public Genome(int numberOfCities, int[][] travelingDistance, int startingCity){
        this.travelingDistance = travelingDistance;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        genome = randomSalesman();
        fitness = calculateFitness();
    }

  // With permutationOfCities
    public Genome(List<Integer> permutationOfCities, int numberOfCities, int[][] travelingDistance, int startingCity){
        genome = permutationOfCities;
        this.travelingDistance = travelingDistance;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        fitness = calculateFitness();
    }

   
    public int calculateFitness(){
        int fitness = 0;
        int currentCity = startingCity;
        for ( int gene : genome) {
            fitness += travelingDistance[currentCity][gene];
            currentCity = gene;
        }
        fitness += travelingDistance[genome.get(numberOfCities-2)][startingCity];
        return fitness;
    }

    //Creates a list with all cities except the startingcity and shuffles it
    private List<Integer> randomSalesman(){
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0; i<numberOfCities; i++) {
            if(i!=startingCity)
                result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    

    public List<Integer> getGenome() {
        return genome;
    }

    public int getStartingCity() {
        return startingCity;
    }

    public int getFitness() {
        return fitness;
    }

 
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }


    @Override
    public int compareTo(Object o) {
        Genome genome = (Genome) o;
        if(this.fitness > genome.getFitness())
            return 1;
        else if(this.fitness < genome.getFitness())
            return -1;
        else
            return 0;
    }
}