/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.opt4j.benchmark.knapsack;

import static org.opt4j.core.Objective.INFEASIBLE;
import static org.opt4j.core.Objective.Sign.MAX;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

/**
 * The {@link KnapsackProfitEvaluator} evaluates a given {@link ItemSelection}
 * using one {@link Objective} to sum up all knapsack overloads and one
 * {@link Objective} for the profit of each knapsack.
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class KnapsackProfitEvaluator implements Evaluator<ItemSelection> {
	protected final Map<Knapsack, Objective> profitObjectives = new HashMap<Knapsack, Objective>();
	protected KnapsackProblem problem;

	/**
	 * Creates a new {@link KnapsackProfitEvaluator}.
	 * 
	 * @param problem
	 */
	@Inject
	public KnapsackProfitEvaluator(KnapsackProblem problem) {
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
		// evaluate profit
		for (Knapsack knapsack : problem.getKnapsacks()) {
			if (objectives.isFeasible()) {
				objectives.add(profitObjectives.get(knapsack), getProfit(knapsack, itemSelection));
			} else {
				objectives.add(profitObjectives.get(knapsack), INFEASIBLE);
			}
		}
	}

	/**
	 * Computes the profit for the given items for this knapsack.
	 * 
	 * @param knapsack
	 *            the knapsack
	 * @param items
	 *            the items
	 * @return the profit
	 */
	protected int getProfit(Knapsack knapsack, Collection<Item> items) {
		int profit = 0;
		for (Item i : items) {
			profit += knapsack.getProfit(i);
		}
		return profit;
	}
}
