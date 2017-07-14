package co.edu.icesi.eketal.aspects;
		
import co.edu.icesi.eketal.automaton.*;
import co.edu.icesi.eketal.groupsimpl.*;
import co.edu.icesi.eketal.handlercontrol.*;
import co.edu.icesi.eketal.reaction.*;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.NamedEvent;
import example.src.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.util.IndentPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public aspect ProducerAMQ{
	
	final static Log logger = LogFactory.getLog(ProducerAMQ.class);
	//automatonConstructor
	//--------Evento: eventMessageProducer-------------
	pointcut eventMessageProducer():
		(pointExamplesrcProducerToolshowParameters() && if(GroupsControl.host("localGroup")));
		
	//after() returning (Object o): eventMessageProducer() {
	//	System.out.println("[Aspectj] Returned normally with " + o);
	//}
	//after() throwing (Exception e): eventMessageProducer() {
	//	System.out.println("[Aspectj] Threw an exception: " + e);
	//}
	after(): eventMessageProducer(){
		Automaton automata = AutomatonConstructor.getInstance();
		Reaction.verifyAfter(automata);
		//System.out.println("[Aspectj] After: Returned or threw an Exception");
		logger.debug("[Aspectj] After: Returned or threw an Exception");
	}
	before(): eventMessageProducer(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = AutomatonConstructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventMessageProducer");
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
	//--------Evento: eventMessageConsumer-------------
	pointcut eventMessageConsumer():
		(pointExamplesrcConsumerToolshowParameters() && if(GroupsControl.host("localGroup")));
		
	//after() returning (Object o): eventMessageConsumer() {
	//	System.out.println("[Aspectj] Returned normally with " + o);
	//}
	//after() throwing (Exception e): eventMessageConsumer() {
	//	System.out.println("[Aspectj] Threw an exception: " + e);
	//}
	after(): eventMessageConsumer(){
		Automaton automata = AutomatonConstructor.getInstance();
		Reaction.verifyAfter(automata);
		//System.out.println("[Aspectj] After: Returned or threw an Exception");
		logger.debug("[Aspectj] After: Returned or threw an Exception");
	}
	before(): eventMessageConsumer(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = AutomatonConstructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventMessageConsumer");
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
	//--------Evento: eventMessageConsumer2-------------
	pointcut eventMessageConsumer2():
		(pointExamplesrcConsumerTool2showParameters() && if(GroupsControl.host("localGroup")));
		
	//after() returning (Object o): eventMessageConsumer2() {
	//	System.out.println("[Aspectj] Returned normally with " + o);
	//}
	//after() throwing (Exception e): eventMessageConsumer2() {
	//	System.out.println("[Aspectj] Threw an exception: " + e);
	//}
	after(): eventMessageConsumer2(){
		Automaton automata = AutomatonConstructor.getInstance();
		Reaction.verifyAfter(automata);
		//System.out.println("[Aspectj] After: Returned or threw an Exception");
		logger.debug("[Aspectj] After: Returned or threw an Exception");
	}
	before(): eventMessageConsumer2(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = AutomatonConstructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventMessageConsumer2");
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
	
	pointcut pointExamplesrcConsumerTool2showParameters(): call(* example.src.ConsumerTool2.showParameters());
	pointcut pointExamplesrcConsumerToolshowParameters(): call(* example.src.ConsumerTool.showParameters());
	pointcut pointExamplesrcProducerToolshowParameters(): call(* example.src.ProducerTool.showParameters());
}
