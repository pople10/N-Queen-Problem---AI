package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
	//will be only of size 4
	private ArrayList<Individual> boards = new ArrayList<Individual>();
	private final int populationSize; 
	private int size;
	
	public ArrayList<Individual> getBoards() {
		return boards;
	}

	public void setBoards(ArrayList<Individual> boards) {
		this.boards = boards;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public Population(int n){
		populationSize = 15;
		this.size=n;
		setPopulationInitial(this.size);
	}
	
	public void setPopulationInitial(int n) {
		for(int i=0; i<populationSize; ++i) {
			boards.add(new Individual(n));
		}
		Collections.sort(boards);
	}
	
	//find if any inidvidual in a population is a solution, and return its index 
	public int estBut() {
		//calls boolean Individual::estBut()
		for (int i=0; i<populationSize; ++i) {
			//si aucune reine n'attaque c-a-d fitness == 0
			if(boards.get(i).estBut()) {
				return i;
			}
		}
		return -1;
	}

	
	//w'll crossover only the first and second elements from boards since they are already sorted
	public void mutation() {
		Random rd = new Random();
		int n = this.size;
		int MutationPosition = rd.nextInt(n);
		int MutationValue = rd.nextInt(n)+1;
		
		//this part prevents the algorithm from mutating with the same number
		//which optimises the algorithm
		while(boards.get(populationSize-1).getEchiquier()[MutationPosition] == (char)(MutationValue+'0')) {
			MutationValue = rd.nextInt(n)+1;
		}
		boards.get(populationSize-1).getEchiquier()[MutationPosition] = (char)(MutationValue+'0');
		MutationPosition = rd.nextInt(n);
		MutationValue = rd.nextInt(n)+1;
		while(boards.get(populationSize-2).getEchiquier()[MutationPosition] == (char)(MutationValue+'0')) {
			MutationValue = rd.nextInt(n)+1;
		}
		boards.get(populationSize-2).getEchiquier()[MutationPosition] = (char)(MutationValue+'0');
		boards.get(populationSize-1).updateFitness();
		boards.get(populationSize-2).updateFitness();
	}

	//w'll crossover only the first and second elements from boards since they are already sorted
	//w'll pick a random point to shuffle 
	public void Croisement() {
		//rd must be between 1--7
		Random rd = new Random();
		int n = this.size;
		int crossOverPoint = rd.nextInt(n-1)+1;
		System.out.println("crossOverPoint: "+crossOverPoint);
		char[] parent1Val = boards.get(0).getEchiquier();
		char[] parent2Val = boards.get(1).getEchiquier();
		
		char[] child1 = new char[n];
		char[] child2 = new char[n];
		
		for(int i=0;i<n;++i) {
			if(i < crossOverPoint) {
				child1[i] = parent1Val[i];
				child2[i] = parent2Val[i];
			}else {
				child1[i] = parent2Val[i];
				child2[i] = parent1Val[i];
			}
		}
		boards.get(populationSize-1).setEchiquier(child1);
		boards.get(populationSize-2).setEchiquier(child2);
	}
	
	public void removeIndividual(int index) {
		
	}
	
}
