package co.edu.icesi.eketal.handlercontrol;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;
import co.edu.icesi.ketal.distribution.KetalMessageHandler;
import co.edu.icesi.ketal.distribution.ReceiverMessageHandler;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;
import java.util.Map;
import java.util.Vector;
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
    		Object handle = super.handle(event, metadata, msg, typeOfMsgSent);
    		Automaton automaton = co.edu.icesi.eketal.automaton.Constructor.getInstance();
    		if(!automaton.evaluate(event)){
    			    			System.out.println("[Handle] Evento no reconocido por el autómata");
    			    		}else{
    			    			System.out.println("[Handle] Returned or threw an Exception");							
    			    			co.edu.icesi.eketal.reaction.Reaction.verifyBefore(automaton);					
    			    			co.edu.icesi.eketal.reaction.Reaction.verifyAfter(automaton);					
    			    		}
    		co.edu.icesi.eketal.reaction.Reaction.verifyBefore(automaton);					
    		co.edu.icesi.eketal.reaction.Reaction.verifyAfter(automaton);					
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
  
  private static BrokerMessageHandler ketalMessageHandler = new KetalMessageHandler();
  
  private static EventBroker eventBrokerHandler = new JGroupsEventBroker("Eketal", ketalMessageHandler);
  
  public void multicastSync(final Event evento, final Map map) {
    eventBrokerHandler.multicastSync(evento, map);
  }
  
  public static Vector getEvents(final Event evento) {
    return ((((KetalMessageHandler) ketalMessageHandler).getVectorEvents()));
  }
}
