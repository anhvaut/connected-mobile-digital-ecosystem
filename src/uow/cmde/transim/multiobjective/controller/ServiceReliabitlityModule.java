package uow.cmde.transim.multiobjective.controller;
import org.opt4j.core.problem.ProblemModule;

public class ServiceReliabitlityModule extends ProblemModule {

	@Override
	protected void config() {
		bindProblem(ServiceReliabilityCreator.class, ServiceReliabilityDecoder.class, ServiceReliabilityEvaluator.class);
	}

}
