package co.edu.icesi.eketal.aspects;
		
import co.edu.icesi.eketal.automaton.*;
import co.edu.icesi.eketal.groupsimpl.*;
import co.edu.icesi.eketal.handlercontrol.*;
import co.edu.icesi.eketal.reaction.*;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.NamedEvent;
import example.src.*;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public aspect DeadlockMessage{
	
	final static Log logger = LogFactory.getLog(DeadlockMessage.class);
	//constructor
	//--------Evento: eventQueueDeleter-------------
	pointcut eventQueueDeleter():
		(pointExamplesrcProducerToolqueueDeleter() && if(GroupsControl.host("localGroup")));
		
	//after() returning (Object o): eventQueueDeleter() {
	//	System.out.println("[Aspectj] Returned normally with " + o);
	//}
	//after() throwing (Exception e): eventQueueDeleter() {
	//	System.out.println("[Aspectj] Threw an exception: " + e);
	//}
	after(): eventQueueDeleter(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		//System.out.println("[Aspectj] After: Returned or threw an Exception");
		logger.debug("[Aspectj] After: Returned or threw an Exception");
	}
	before(): eventQueueDeleter(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventQueueDeleter");
		event.setLocalization(distribuidor.getAsyncAddress());
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			//System.out.println("[Aspectj] Before: Event not recognized by the automaton");
			logger.debug("[Aspectj] Before: Event not recognized by the automaton");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			//System.out.println("[Aspectj] Before: Returned or threw an Exception");
			logger.debug("[Aspectj] Before: Returned or threw an Exception");
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	//--------Evento: eventPurge-------------
	pointcut eventPurge():
		(pointExamplesrcProducerToolpurgeMessages() && if(GroupsControl.host("localGroup")));
		
	//after() returning (Object o): eventPurge() {
	//	System.out.println("[Aspectj] Returned normally with " + o);
	//}
	//after() throwing (Exception e): eventPurge() {
	//	System.out.println("[Aspectj] Threw an exception: " + e);
	//}
	after(): eventPurge(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		//System.out.println("[Aspectj] After: Returned or threw an Exception");
		logger.debug("[Aspectj] After: Returned or threw an Exception");
	}
	before(): eventPurge(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventPurge");
		event.setLocalization(distribuidor.getAsyncAddress());
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			//System.out.println("[Aspectj] Before: Event not recognized by the automaton");
			logger.debug("[Aspectj] Before: Event not recognized by the automaton");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			//System.out.println("[Aspectj] Before: Returned or threw an Exception");
			logger.debug("[Aspectj] Before: Returned or threw an Exception");
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	
	pointcut pointExamplesrcProducerToolpurgeMessages(): call(* example.src.ProducerTool.purgeMessages(String));
	pointcut pointExamplesrcProducerToolqueueDeleter(): call(* example.src.ProducerTool.queueDeleter());
}
