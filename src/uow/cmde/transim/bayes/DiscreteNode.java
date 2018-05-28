package uow.cmde.transim.bayes;

import aima.core.probability.RandomVariable;
import aima.core.probability.bayes.ConditionalProbabilityDistribution;
import aima.core.probability.bayes.ConditionalProbabilityTable;
import aima.core.probability.bayes.FiniteNode;
import aima.core.probability.bayes.Node;
import aima.core.probability.bayes.impl.AbstractNode;
import aima.core.probability.bayes.impl.CPT;

public class DiscreteNode extends AbstractNode  implements FiniteNode{

	private ConditionalProbabilityTable cpt = null;

	public DiscreteNode(RandomVariable var, double[] distribution) {
		this(var, distribution, (Node[]) null);
	}

	public DiscreteNode(RandomVariable var, double[] values, Node... parents) {
		super(var, parents);

		RandomVariable[] conditionedOn = new RandomVariable[getParents().size()];
		int i = 0;
		for (Node p : getParents()) {
			conditionedOn[i++] = p.getRandomVariable();
		}

		cpt = new CPT(var, values, conditionedOn);
	}

	//
	// START-Node
	@Override
	public ConditionalProbabilityDistribution getCPD() {
		return getCPT();
	}

	@Override
	public ConditionalProbabilityTable getCPT() {
		return cpt;
	}

	// END-FiniteNode
	//
}
