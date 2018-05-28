package uow.cmde.transim.multiobjective.controller;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.PhenotypeWrapper;
import uow.cmde.transim.multiobjective.functions.*;

public class  ServiceReliabilityEvaluator implements Evaluator<PhenotypeWrapper<String>>{

	
	public double passengerWaitTime_F1(String solution)
	{
		double waitTime = 0;
		
		for(int i=0;i<solution.length();i++)
		{
			waitTime += Integer.parseInt("" + solution.charAt(i)) * 5.0;
		}
		
		return waitTime;
	}
	
	public double passengerComfort_F2(String solution)
	{
		double passengerComfort = 0;
		
		for(int i=0;i<solution.length();i++)
		{
			passengerComfort += Integer.parseInt("" + solution.charAt(i)) % 2;
		}
		
		return passengerComfort;
		
	}
	
	public double actionImpact_F3(String solution)
	{
		double impact = 0;
		
		for(int i=0;i<solution.length();i++)
		{
			impact *= Integer.parseInt("" + solution.charAt(i)) * 0.25;
		}
		
		return impact;
		
	}
	
	@Override
	public Objectives evaluate(PhenotypeWrapper<String> phenotype) {
		
		String s = phenotype.get();
		
		final double f1 = (new PassengerWaitTime_F1()).evaluate(s);
		final double f2 = (new PassengerComfort_F2()).evaluate(s);;
		final double f3 = (new ActionImpact_F3()).evaluate(s);;
		
		Objective objective1 = new Objective("Passenger Wait Time (F1)", Sign.MIN);
		Objective objective2 = new Objective("Passenger Comfort (F2)", Sign.MIN);
		Objective objective3 = new Objective("Action Impact (F3)", Sign.MIN);
		
		Objectives obj = new Objectives();
		obj.add(objective1, f1);
		obj.add(objective2, f2);
		obj.add(objective3, f3);
		
		
		return obj;
	}
}
