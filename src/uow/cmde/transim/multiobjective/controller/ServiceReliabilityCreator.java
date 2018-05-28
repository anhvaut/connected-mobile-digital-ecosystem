package uow.cmde.transim.multiobjective.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opt4j.core.problem.Creator;
import org.opt4j.genotype.SelectGenotype;
import uow.cmde.transim.util.*;

public class ServiceReliabilityCreator implements Creator<SelectGenotype<Character>> {

	List<Character> chars = new ArrayList<Character>();
	{
		chars.add('0');
		chars.add('1');
		chars.add('2');
		chars.add('3');
		chars.add('4');
	}
	
	Random random = new Random();

	@Override
	public SelectGenotype<Character> create() {
		SelectGenotype<Character> genotype = new SelectGenotype<Character>(chars);
		genotype.init(random, AppConfig.MO_NUMBER_OF_DIMENSIONS);
		return genotype;
	}

}
