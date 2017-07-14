package co.edu.icesi.eketal.handlercontrol;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;
import co.edu.icesi.ketal.distribution.ReceiverMessageHandler;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.jgroups.Message;

@SuppressWarnings("all")
public class EventHandler {
  private static EventHandler instance = new EventHandler();
  
  private static BrokerMessageHandler brokerMessageHandler;
  
  private static EventBroker eventBroker;
  
  private EventHandler() {
    brokerMessageHandler = new ReceiverMessageHandler(){
    	@Override
    	public Object handle(Event event, Map metadata, Message msg,
    	    			int typeOfMsgSent){
    	    if(event.getLocalization().equals(eventBroker.getAsyncAddress())){
    	    	return null;
    	    }
    		Object handle = super.handle(event, metadata, msg, typeOfMsgSent);
    		Automaton automaton = co.edu.icesi.eketal.automaton.AutomatonConstructor.getInstance();
    		if(!automaton.evaluate(event)){
    			ReceiverMessageHandler.getLogger().info("[Handle] Evento no reconocido por el autómata");
    			    			//System.out.println("[Handle] Evento no reconocido por el autómata");
    			    		}else{
    			    			ReceiverMessageHandler.getLogger().info("[Handle] Returned or threw an Exception");
    			    			//System.out.println("[Handle] Returned or threw an Exception");							
    			    			co.edu.icesi.eketal.reaction.Reaction.verifyBefore(automaton);					
    			    			co.edu.icesi.eketal.reaction.Reaction.verifyAfter(automaton);					
    			    		}
    		return handle;
    	}
    };
    eventBroker = new JGroupsEventBroker("Eketal", brokerMessageHandler);
  }
  
  public static EventHandler getInstance() {
    if(instance==null)
    	instance = new EventHandler();
    return instance;
  }
  
  public void multicast(final Event evento, final Map map) {
    eventBroker.multicast(evento, map);
  }
  
  public URL getAsyncAddress() {
    URL url = eventBroker.getAsyncAddress();
    if(url!=null){
    	return url;
    }else{
    	ReceiverMessageHandler.getLogger().error("[Handle] Could not obtain JGroups ip Address for the async monitor");
    	try{
    		return new URL("http:127.0.0.1");
    	}catch(MalformedURLException e){
    		ReceiverMessageHandler.getLogger().error("[Handle] "+e.getMessage());
    		e.printStackTrace();
    		return null;
    	}
    }
  }
}
