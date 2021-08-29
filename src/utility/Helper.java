package utility;


public class Helper {
	public static int calculateAttacks(int[] data)
	{
		int cmp=0;
		/*
		 * if two numbers are dupliacted increment, if it's in the diagonal either top or down increment
		 * we ingnore the direction by using Math.abs()
		 * */
		for(int i=0; i<data.length; ++i) {
			int temp = data[i];
			for(int j=i+1; j<data.length; ++j) {
				if(temp == data[j] || ( (j-i) == (int)Math.abs(temp-data[j])) ) {
					cmp++;
				}
			}
		}
		return cmp;
	}
	
	public static int[][] ListToMatrix(int[] data)
	{
		/*to optimize code we will store value of size in variable*/
		int size = data.length;
		int[][] output = new int[size][size];
		for(int i=0;i<size;i++)
		{
			output[data[i]-1][i]=1;
		}
		return output;
	}
	
	public static void displayMatrix(int[][] output)
	{
		for(int []i : output)
		{
			for(int j : i)
				System.out.print(j+" ");
			System.out.println("");
		}
	}
	
	public static int[] generateRandomList(int n)
	{
		int[] data = new int[n];
		int size=data.length;
		for(int i=0;i<size;i++)
			data[i] = (int)(Math.random()*size+1);
		return data;
	}
	
	public static int[][] append(int[][] a, int[][] b) {
        int[][] result = new int[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	public static int[][] sortByFitness(int[][] r)
	{
		int sizeRow = r.length;
		for(int i=0;i<sizeRow;i++)
		{
			for(int j=i+1;j<sizeRow;j++)
			{
				if(Helper.calculateAttacks(r[j])<=Helper.calculateAttacks(r[i]))
				{
					int[] tmp=r[j];
					r[j]=r[i];
					r[i]=tmp;
				}
					
			}
		}
		return r;
	}

	public static void displayVector(int[] is) {
		for(int i : is)
		{
			System.out.print(i+" ");
		}
		System.out.println("");
	}
	
	public static String secondsToString(float s)
	{
		if(s<=1.0f)
			return s+" second";
		return s+" seconds";
	}
	
	public static int[] stringList_TO_intList(char[] s)
	{
		int size = s.length;
		int[] n = new int[size]; 
		for(int i=0; i<size; ++i) {
			n[i] = Integer.parseInt(s[i]+"");
		}
		return n;
	}
	
	public static void DisplayStringInList(char[] s)
	{
		for(int i=0; i<s.length; ++i) {
			System.out.print(s[i]);
		}
		System.out.println("");
	}
	
	public static void smallSeparator()
	{
		System.out.println("-----------------");
	}
	
	public static void bigSeparator()
	{
		System.out.println("*************************************************");
	}
	
	public static void displayMatrixWithSeparators(int[][] output)
	{
		for(int []i : output)
		{
			for(int j : i)
				System.out.print(j+" ");
			System.out.println("");
			smallSeparator();
		}
	}
}
