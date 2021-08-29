package utility;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable, Cloneable {
	
	private char[] echiquier;
	private int fitness;
	//ctor intialises the board with random numbers 0<n<9
	//and calculates the fitness
	public Individual(int n)
	{
		Random rd = new Random();
		echiquier = new char[n];
		for(int i=0; i<n; ++i) {
			//converting int to char by adding the ascii of '0' == 48
			echiquier[i] = (char)((rd.nextInt(n)+1)+'0');
		}
		this.fitness = fitness();
	}
	
	
    ///calcule les paires des reines qui ne s'attaquent pas	
	public int fitness() {
		//converting char array to int 
		int size = this.echiquier.length;
		int[] n = new int[size]; 
		for(int i=0; i<size; ++i) {
			n[i] = Character.valueOf(echiquier[i]);
		}
		return Helper.calculateAttacks(n);
	}
	
	public boolean estBut() {
		return fitness==0? true: false;
	}
	
	
	public char[] getEchiquier() {
		return echiquier;
	}
	public void setEchiquier(char[] echiquier) {
		this.echiquier = echiquier;
	}


	public int getFitness() {
		return fitness;
	}


	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public void updateFitness() {
		this.fitness = this.fitness();
	}
	
	//pour trier la collection des individus
	@Override
	public int compareTo(Object o) {
		Individual indi = (Individual)o;
		
		if(indi == null) {
			return -111;
		}
		
		if(this.getFitness() < indi.getFitness()) {
			return -1;
		}else if(this.getFitness() > indi.getFitness()) {
			return 1;
		}else {
			return 0;
		}
	}
}
