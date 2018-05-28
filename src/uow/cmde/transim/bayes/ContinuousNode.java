package uow.cmde.transim.bayes;

import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import aima.core.probability.RandomVariable;
import aima.core.probability.bayes.ConditionalProbabilityDistribution;
import aima.core.probability.bayes.ConditionalProbabilityTable;
import aima.core.probability.bayes.Node;
import aima.core.probability.bayes.impl.*;

public class ContinuousNode extends AbstractNode {

	private ConditionalProbabilityTable cpt = null;
	private double cumulativeProbability = 0;
	
	public ContinuousNode(RandomVariable var, double[] distribution) {
		this(var, distribution, (Node[]) null);
	}

	public ContinuousNode(RandomVariable var, double[] values, Node... parents) {
		super(var, parents);

		RandomVariable[] conditionedOn = new RandomVariable[getParents().size()];
		int i = 0;
		for (Node p : getParents()) {
			conditionedOn[i++] = p.getRandomVariable();
		}

		cpt = new CPT(var, values, conditionedOn);
	}
	
	public void calculateCumulativeProbability(double value, double expectedValue, double expectedDeviation) throws Exception
	{
		
			NormalDistribution normalDistribution = new NormalDistributionImpl();
			normalDistribution.setMean(expectedValue);
			normalDistribution.setStandardDeviation(expectedDeviation);
			
			cumulativeProbability = normalDistribution.cumulativeProbability(value);
	}
	
	public double getCumulativeProbability()
	{
		return this.cumulativeProbability;
	}
	//
	// START-Node
	@Override
	public ConditionalProbabilityDistribution getCPD() {
		return cpt;
	}
	
}
