package factory;

import algorithms.Genitic;
import algorithms.HillClimbing;
import algorithms.HillClimbingV2;
import algorithms.InterfaceAlgorithm;
import algorithms.LocalBeamSearch;
import utility.Methods;

public class MethodFactoryBuilder {
	private static final int maxTime = 300000000;
	public static InterfaceAlgorithm build(Methods method,int n)
	{
		if(method==Methods.HILLCLIMBINGV2)
			return new HillClimbingV2(n,maxTime);
		if(method==Methods.HILLCLIMBING)
			return new HillClimbing(n,maxTime);
		if(method==Methods.LOCAL_BEAM_SEARCH)
		{
    		int k = (int)(Math.random()*100+2);
			return new LocalBeamSearch(n,k,maxTime);
		}
		if(method==Methods.GENETIC)
		{
			return new Genitic(n,500000000);
		}
		return null;
	}
}
