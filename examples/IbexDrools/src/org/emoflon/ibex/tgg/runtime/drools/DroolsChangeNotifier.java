package org.emoflon.ibex.tgg.runtime.drools;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class DroolsChangeNotifier extends EContentAdapter {

	@Override
	public void notifyChanged(Notification notification) {
		
		// Handle addition of resources
		if(notification.getNotifier() instanceof ResourceSet && notification.getEventType() == Notification.ADD){
			Resource r = (Resource) notification.getNewValue();
			r.eAdapters().add(this);
			return;
		}
		
		// Handle addition of root objects
		if(notification.getNotifier() instanceof Resource && notification.getEventType() == Notification.ADD){
			System.out.println(notification);
			
			EObject r = (EObject) notification.getNewValue();
			r.eAdapters().add(this);
			return;
		}
		
		// Handle actual changes
		if(notification.getNotifier() instanceof EObject){
			System.out.println(notification);
		}
	}
}
