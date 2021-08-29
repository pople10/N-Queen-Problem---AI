package algorithms;

public interface InterfaceAlgorithm {
	public void solve();
	public boolean isSolutionFound();
	public int[][] getStateMatrix();
	public int[] getStateVector();
	public float getDuration();
}
