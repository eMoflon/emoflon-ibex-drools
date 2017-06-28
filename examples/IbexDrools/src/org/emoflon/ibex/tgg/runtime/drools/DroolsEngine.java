package org.emoflon.ibex.tgg.runtime.drools;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.PatternMatchingEngine;

import language.TGG;

// TODO:  In compilation process, transform patterns to Drools rules
// TODO:  In each Drools rule, when part should reflect Ibex pattern structure, then part should collect objects in the match and register an IMatch

public class DroolsEngine implements PatternMatchingEngine {

	private DroolsChangeNotifier notifier;
	private ResourceSet rs;
	
	@Override
	public void registerInternalMetamodels() {
		// TODO
	}

	@Override
	public void initialise(ResourceSet rs, OperationalStrategy operationalStrategy, TGG tgg, TGG flattenedTgg,
			String projectPath, boolean debug) {
		notifier = new DroolsChangeNotifier();
		this.rs = rs;
		rs.eAdapters().add(notifier);
	}

	@Override
	public ResourceSet createAndPrepareResourceSet(String workspacePath) {
		return createDefaultResourceSet(workspacePath);
	}
	
	private ResourceSet createDefaultResourceSet(String workspaceRootPath) {		
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.setPackageRegistry(EPackage.Registry.INSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
				
		resourceSet.getURIConverter().getURIMap().put(
				URI.createPlatformResourceURI("/", true), URI.createFileURI(workspaceRootPath + File.separatorChar));
		
		return resourceSet;
	}

	@Override
	public void updateMatches() {
		// TODO
		// 1. Retrieve changes from listeners
		// 2. Tell Drools what has changed via the API
		// 3. Fire the rules
	}

	@Override
	public void terminate() {
		// TODO free session
		rs.eAdapters().remove(notifier);
	}

}
