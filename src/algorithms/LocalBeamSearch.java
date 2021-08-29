package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import utility.Helper;

public class LocalBeamSearch implements InterfaceAlgorithm {
	private int[][] states;
	private int[] lastSolution;
	private int k;
	private boolean solved = false;
	private long timeOut;
	private float duration;
	
	public LocalBeamSearch(int n,int k,int timeOut)
	{
		this.k=k;
		this.timeOut=timeOut;
		states = new int[k][];
		for(int i=0;i<k;i++)
			states[i]=Helper.generateRandomList(n);
	}

	@Override
	public void solve() {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		int n = states[0].length;
		int iterationNum = 0;
		while((endTime-startTime)<this.timeOut)
		{
			//System.out.println(endTime-startTime);
			iterationNum++;
			int[][] newStates = new int[n*(n-1)*k][];
			int helpIndex = 0;
			for(int i=0;i<k;i++)
			{
				int cout = Helper.calculateAttacks(this.states[i]);
				if(cout==0)
				{
					this.lastSolution=states[i];
					this.solved=true;
					this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
					return;
				}
				int[][] tmp = this.evaluate(states[i]);
				for(int[] c : tmp)
				{
					newStates[helpIndex]=c;
					helpIndex++;
				}
				//Calculation.displayMatrix(newStates);
			}
			newStates=Helper.sortByFitness(newStates);
            states = Arrays.copyOfRange(newStates,0,this.k);
            System.out.println("Iteration "+iterationNum+" : ");
            Helper.displayMatrixWithSeparators(states);
            Helper.bigSeparator();
            endTime = System.currentTimeMillis();
		}
		this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
		this.lastSolution=states[0];
		return;
	}

	@Override
	public boolean isSolutionFound() {
		return this.solved;
	}

	@Override
	public int[][] getStateMatrix() {
		return Helper.ListToMatrix(lastSolution);
	}

	@Override
	public int[] getStateVector() {
		return lastSolution;
	}

	public int[][] evaluate(int[] data) {
		int size = data.length;
		int[][] newStates = new int[(size-1)*size][];
		int k=0;
		for(int i=0;i<size;i++)
		{
			for(int j=1;j<=size;j++)
			{
				int[] temp = data.clone();
				if(j==temp[i])
					continue;
				temp[i]=j;
				newStates[k]=temp;
				k++;
			}
		}
		//Calculation.displayMatrix(newStates);
		return newStates;
	}

	@Override
	public float getDuration() {
		return this.duration;
	}
	
	
}
