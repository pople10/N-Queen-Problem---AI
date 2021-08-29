package algorithms;

import java.util.Collections;

import utility.Helper;
import utility.Population;

public class Genitic implements InterfaceAlgorithm {
	private int n;
	private long maxIteration;
	private int[] lastSolution;
	private boolean solved = false;
	private float duration;
	
	public Genitic(int n,long maxIteration)
	{
		this.n=n;
		this.maxIteration=maxIteration;
	}
	
	@Override
	public void solve() {
		Population p = new Population(n);
		int iterationNum = 0;
		long startTime = System.currentTimeMillis();
		while(iterationNum< this.maxIteration) 
		{
			iterationNum++;
			System.out.println("Iteration "+iterationNum+" : ");
			for(int j=0; j<p.getPopulationSize(); ++j) {
				Helper.DisplayStringInList(p.getBoards().get(j).getEchiquier());
				Helper.smallSeparator();
			}
			Helper.bigSeparator();
			p.Croisement();
			p.mutation();
			Collections.sort(p.getBoards());
			int index;
			index = p.estBut();
			if(index != -1) {
				//System.out.println("echiquier: "+ p.getBoards().get(index).toString());
				//Helper.DisplayStringInList(p.getBoards().get(index).getEchiquier());
				this.lastSolution=Helper.stringList_TO_intList(p.getBoards().get(index).getEchiquier());
				this.solved = true;
				this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
				return;
			}
		}
		this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
		this.lastSolution=Helper.stringList_TO_intList(p.getBoards().get(0).getEchiquier());
		return;
	}

	@Override
	public boolean isSolutionFound() {
		return this.solved;
	}

	@Override
	public int[][] getStateMatrix() {
		return Helper.ListToMatrix(this.lastSolution);
	}

	@Override
	public int[] getStateVector() {
		return this.lastSolution;
	}

	@Override
	public float getDuration() {
		return this.duration;
	}

}
