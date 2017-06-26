package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
import org.emoflon.ibex.tgg.runtime.drools.DroolsEngine;

public class MODELGEN_App extends MODELGEN {

	public MODELGEN_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public MODELGEN_App(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
		super(projectName, workspacePath, flatten, debug);
	}

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();

		MODELGEN_App generator = new MODELGEN_App("DroolsHelloWorld", "./../", true);
		generator.registerPatternMatchingEngine(new DroolsEngine());

		MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.tgg);
		stop.setTimeOutInMS(100);
		stop.setMaxRuleCount("BuildingAndRouter", 5);
		generator.setStopCriterion(stop);

		logger.info("Starting MODELGEN");
		long tic = System.currentTimeMillis();
		generator.run();
		long toc = System.currentTimeMillis();
		logger.info("Completed MODELGEN in: " + (toc - tic) + " ms");

		generator.saveModels();
		generator.terminate();
	}

	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/"  + "BuildingLanguage.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/" + "NetworkInfrastructure.ecore");
		
		// Register correspondence metamodel last
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}
}
