package main.java.com;

import java.io.FileInputStream;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	//KieSession kSession = kContainer.newKieSession("ksession-rules");
        	KieSession kSession = createSession();
            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            kSession.insert(message);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
    
    public static KieSession createSession(){
		KieSession kSession = null;
		 try{
		
    	KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
    	FileInputStream fis = new FileInputStream( "src/main/resources/rules/Sample.drl" );
        kfs.write( "src/main/resources/rules/Sample.drl",
                    kieServices.getResources().newInputStreamResource( fis ) );

        KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
        
        

        KieContainer kieContainer =
        kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );

        KieBase kieBase = kieContainer.getKieBase();
        kSession = kieContainer.newKieSession();
    	
    	
//        Collection<KiePackage> kiePackages = kieBase.getKiePackages();
//        for( KiePackage kiePackage: kiePackages ){
//            System.out.println("got package");
//            for( Rule rule: kiePackage.getRules() ){ 
//                System.out.println( rule.getName() );
//            }
//        }
    	
    	
		 } catch (Throwable t) {
	            t.printStackTrace();
	        }
		 
		 return kSession;
	}

}
