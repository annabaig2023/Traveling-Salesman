import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
	
	    private int generationSize;
	    private int genomeSize;
	    private int numberOfCities;
	    private int reproductionSize;
	    private int maxIterations;
	    private double mutationRate;
	    private int[][] travelPrices;
	    private int startingCity;
	    private int travelingDistance;

	    
	    public GeneticAlgorithm(int numberOfCities, int[][] travelingDistance, int startingCity, int targetFitness){
	        this.numberOfCities = numberOfCities;
	        this.genomeSize = numberOfCities-1;
	        this.travelPrices = travelingDistance;
	        this.startingCity = startingCity;
	        this.travelingDistance = targetFitness;

	        generationSize = 5000;
	        reproductionSize = 200;
	        maxIterations = 200;
	        
	        mutationRate = 0.1;
	    }

	    public List<Genome> initialPopulation(){
	        List<Genome> population = new ArrayList<>();
	        for(int i=0; i<generationSize; i++){
	            population.add(new Genome(numberOfCities, travelPrices, startingCity));
	        }
	        return population;
	    }

	    
	    public List<Genome> selection(List<Genome> population){
	        List<Genome> selected = new ArrayList<>();
	        for(int i=0; i<reproductionSize; i++){
	        	selected.add(Collections.min(pickAmountOfRandomElements(population, 40)));
	        }

	        return selected;
	    }

	    
	    public <E> List<E> pickAmountOfRandomElements(List<E> list, int n) {
	        Random r = new Random();
	        int length = list.size();

	        if (length < n) return null;

	        for (int i = length - 1; i >= length - n; --i)
	        {
	            Collections.swap(list, i , r.nextInt(i + 1));
	        }
	        return list.subList(length - n, length);
	    }

	   
	    public Genome mutate(Genome salesman){
	        Random random = new Random();
	        float mutate = random.nextFloat();
	        if(mutate<mutationRate) {
	            List<Integer> genome = salesman.getGenome();
	            Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
	            return new Genome(genome, numberOfCities, travelPrices, startingCity);
	        }
	        return salesman;
	    }

	    
	    public List<Genome> createGeneration(List<Genome> population){
	        List<Genome> generation = new ArrayList<>();
	        int currentGenerationSize = 0;
	        while(currentGenerationSize < generationSize){
	            List<Genome> parents = pickAmountOfRandomElements(population,2);
	            List<Genome> children = crossover(parents);
	            children.set(0, mutate(children.get(0)));
	            children.set(1, mutate(children.get(1)));
	            generation.addAll(children);
	            currentGenerationSize+=2;
	        }
	        return generation;
	    }

	  
	    public List<Genome> crossover(List<Genome> parents){
	    	
	        Random random = new Random();
	        int breakpoint = random.nextInt(genomeSize);
	        
	        
	        List<Genome> children = new ArrayList<>();
	        List<Integer> parent1Genome = new ArrayList<>(parents.get(0).getGenome());
	        List<Integer> parent2Genome = new ArrayList<>(parents.get(1).getGenome());

	       
	        for(int i = 0; i<breakpoint; i++){
	            int newVal;
	            newVal = parent2Genome.get(i);
	            Collections.swap(parent1Genome,parent1Genome.indexOf(newVal),i);
	        }
	        children.add(new Genome(parent1Genome,numberOfCities,travelPrices,startingCity));
	        parent1Genome = parents.get(0).getGenome();

	      
	        for(int i = breakpoint; i<genomeSize; i++){
	            int newVal = parent1Genome.get(i);
	            Collections.swap(parent2Genome,parent2Genome.indexOf(newVal),i);
	        }
	        children.add(new Genome(parent2Genome,numberOfCities,travelPrices,startingCity));

	        return children;
	    }

	   
	    public Genome optimize(){
	        List<Genome> population = initialPopulation();
	        Genome globalBestGenome = population.get(0);
	        
	        for(int i=0; i<maxIterations; i++){
	        	
	            List<Genome> selected = selection(population);
	            population = createGeneration(selected);
	            globalBestGenome = Collections.min(population);
	            
	            System.out.println("Generation " + (i + 1) + ": Shortest Distance :" + globalBestGenome.getFitness());
	            
	            if(globalBestGenome.getFitness() < travelingDistance) break;
	        }
	        return globalBestGenome;
	    }

	}