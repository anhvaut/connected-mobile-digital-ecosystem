/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.opt4j.benchmark.knapsack;

import static java.lang.Math.max;
import static org.opt4j.core.Objective.RANK_ERROR;
import static org.opt4j.core.Objective.Sign.MAX;
import static org.opt4j.core.Objective.Sign.MIN;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.Priority;

/**
 * The {@link KnapsackOverloadEvaluator} evaluates a given {@link ItemSelection} using one {@link Objective} to sum up
 * all knapsack overloads and one {@link Objective} for the profit of each knapsack.
 * 
 * @author reimann, lukasiewycz
 * 
 */
@Priority(RANK_ERROR)
public class KnapsackOverloadEvaluator implements Evaluator<ItemSelection> {
	protected Objective objectiveOverload = new Objective("overload", MIN, RANK_ERROR);
	protected final Map<Knapsack, Objective> profitObjectives = new HashMap<Knapsack, Objective>();
	protected KnapsackProblem problem;

	/**
	 * Creates a new {@link KnapsackOverloadEvaluator}.
	 * 
	 * @param problem
	 */
	@Inject
	public KnapsackOverloadEvaluator(KnapsackProblem problem) {
		this.problem = problem;
		int i = 0;
		for (Knapsack knapsack : problem.getKnapsacks()) {
			profitObjectives.put(knapsack, new Objective("profit" + i++, MAX));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public void evaluate(ItemSelection itemSelection, Objectives objectives) {
		// evaluate overlead
		double overload = 0;
		for (Knapsack knapsack : problem.getKnapsacks()) {
			overload += getOverload(knapsack, itemSelection);
		}
		objectives.add(objectiveOverload, overload);
		objectives.setFeasible(overload == 0);
	}

	/**
	 * Computes how much the given item exceed the defined capacity of the knapsack.
	 * 
	 * @param knapsack
	 *            the knapsack
	 * @param items
	 *            the items
	 * @return the overload
	 */
	protected double getOverload(Knapsack knapsack, Collection<Item> items) {
		double weight = 0;
		for (Item i : items) {
			weight += knapsack.getWeight(i);
		}
		return max(0, weight - knapsack.getCapacity());
	}
}
