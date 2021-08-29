package algorithms;

import utility.Helper;

public class HillClimbing implements InterfaceAlgorithm
{
	private int[] lastSolution;
	private boolean solved = false;
	private long timeOut;
	private float duration;
	
	public HillClimbing(int n,int timeout)
	{
		this.lastSolution = new int[n];
		this.timeOut=timeout;
	}
	
	@Override
	public void solve() {
		int size = this.lastSolution.length;
		int iterationNum = 0;
		this.lastSolution=Helper.generateRandomList(size);
		int cost = Helper.calculateAttacks(this.lastSolution);
		long startTime = System.currentTimeMillis();
		if(cost==0)
		{
			this.solved=true;
			this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
			return;
		}
		while((System.currentTimeMillis()-startTime)<this.timeOut)
		{
			iterationNum++;
			int[][] newStates = this.evaluate(this.lastSolution);
			int tmpCost = cost;
			for(int[] c : newStates)
			{
				int newCost = Helper.calculateAttacks(c);
				if(newCost==0)
				{
					this.lastSolution=c;
					this.solved=true;
					this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
					return;
				}
				if(Helper.calculateAttacks(this.lastSolution)>Helper.calculateAttacks(c))
				{
					/*get the minimum from all successors*/
					this.lastSolution=c;
					cost=newCost;
				}
			}
			System.out.print("Iteration "+iterationNum+" : ");
			Helper.displayVector(this.lastSolution);
			Helper.smallSeparator();
			//get rid of stucking in a local minimum
			if(tmpCost==cost)
				this.lastSolution=Helper.generateRandomList(size);
		}
		this.duration=(float)(System.currentTimeMillis()-startTime)/1000;
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

}
