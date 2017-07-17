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

public aspect ProducerConsumer{
	
	//constructor
	//--------Evento: eventMessageProducer-------------
	pointcut eventMessageProducer():
		(pointExamplesrcProducerToolshowParameters() && if(GroupsControl.host("localGroup")));
		
	after() returning (Object o): eventMessageProducer() {
		System.out.println("[Aspectj] Returned normally with " + o);
	}
	after() throwing (Exception e): eventMessageProducer() {
		System.out.println("[Aspectj] Threw an exception: " + e);
	}
	after(): eventMessageProducer(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		System.out.println("[Aspectj] Returned or threw an Exception");
	}
	before(): eventMessageProducer(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventMessageProducer");
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			System.out.println("[Aspectj] Evento no reconocido por el autómata");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			System.out.println("[Aspectj] Returned or threw an Exception");							
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	//--------Evento: eventMessageConsumer-------------
	pointcut eventMessageConsumer():
		(pointExamplesrcConsumerToolshowParameters() && if(GroupsControl.host("localGroup")));
		
	after() returning (Object o): eventMessageConsumer() {
		System.out.println("[Aspectj] Returned normally with " + o);
	}
	after() throwing (Exception e): eventMessageConsumer() {
		System.out.println("[Aspectj] Threw an exception: " + e);
	}
	after(): eventMessageConsumer(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		System.out.println("[Aspectj] Returned or threw an Exception");
	}
	before(): eventMessageConsumer(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventMessageConsumer");
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			System.out.println("[Aspectj] Evento no reconocido por el autómata");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			System.out.println("[Aspectj] Returned or threw an Exception");							
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	//--------Evento: eventMessageConsumer2-------------
	pointcut eventMessageConsumer2():
		(pointExamplesrcConsumerTool2showParameters() && if(GroupsControl.host("localGroup")));
		
	after() returning (Object o): eventMessageConsumer2() {
		System.out.println("[Aspectj] Returned normally with " + o);
	}
	after() throwing (Exception e): eventMessageConsumer2() {
		System.out.println("[Aspectj] Threw an exception: " + e);
	}
	after(): eventMessageConsumer2(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		System.out.println("[Aspectj] Returned or threw an Exception");
	}
	before(): eventMessageConsumer2(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventMessageConsumer2");
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			System.out.println("[Aspectj] Evento no reconocido por el autómata");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			System.out.println("[Aspectj] Returned or threw an Exception");							
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	//--------Evento: eventRun-------------
	pointcut eventRun():
		(pointExamplesrcProducerToolonMessage() && if(GroupsControl.host("localGroup")));
		
	after() returning (Object o): eventRun() {
		System.out.println("[Aspectj] Returned normally with " + o);
	}
	after() throwing (Exception e): eventRun() {
		System.out.println("[Aspectj] Threw an exception: " + e);
	}
	after(): eventRun(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		System.out.println("[Aspectj] Returned or threw an Exception");
	}
	before(): eventRun(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventRun");
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			System.out.println("[Aspectj] Evento no reconocido por el autómata");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			System.out.println("[Aspectj] Returned or threw an Exception");							
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	//--------Evento: eventRun1-------------
	pointcut eventRun1():
		(pointExamplesrcConsumerToolonMessage() && if(GroupsControl.host("localGroup")));
		
	after() returning (Object o): eventRun1() {
		System.out.println("[Aspectj] Returned normally with " + o);
	}
	after() throwing (Exception e): eventRun1() {
		System.out.println("[Aspectj] Threw an exception: " + e);
	}
	after(): eventRun1(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		System.out.println("[Aspectj] Returned or threw an Exception");
	}
	before(): eventRun1(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventRun1");
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			System.out.println("[Aspectj] Evento no reconocido por el autómata");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			System.out.println("[Aspectj] Returned or threw an Exception");							
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	//--------Evento: eventRun2-------------
	pointcut eventRun2():
		(pointExamplesrcConsumerTool2onMessage() && if(GroupsControl.host("localGroup")));
		
	after() returning (Object o): eventRun2() {
		System.out.println("[Aspectj] Returned normally with " + o);
	}
	after() throwing (Exception e): eventRun2() {
		System.out.println("[Aspectj] Threw an exception: " + e);
	}
	after(): eventRun2(){
		Automaton automata = Constructor.getInstance();
		Reaction.verifyAfter(automata);
		System.out.println("[Aspectj] Returned or threw an Exception");
	}
	before(): eventRun2(){
		EventHandler distribuidor = EventHandler.getInstance();
		Automaton automata = Constructor.getInstance();
		Map map = new HashMap<String, Object>();
		//map.put("Automata", automata);
		Event event = new NamedEvent("eventRun2");
		distribuidor.multicast(event, map);
		if(!automata.evaluate(event)){
			System.out.println("[Aspectj] Evento no reconocido por el autómata");
			//Debería parar
		}else{
			Reaction.verifyBefore(automata);
			System.out.println("[Aspectj] Returned or threw an Exception");							
		}
		//while(!automata.evaluate(event)){
		//	wait(100);
		//	
		//}
	}
	
	pointcut pointExamplesrcConsumerTool2onMessage(): call(* example.src.ConsumerTool2.onMessage(Message));
	pointcut pointExamplesrcConsumerTool2showParameters(): call(* example.src.ConsumerTool2.showParameters());
	pointcut pointExamplesrcConsumerToolonMessage(): call(* example.src.ConsumerTool.onMessage(Message));
	pointcut pointExamplesrcConsumerToolshowParameters(): call(* example.src.ConsumerTool.showParameters());
	pointcut pointExamplesrcProducerToolonMessage(): call(* example.src.ProducerTool.onMessage(MessageProducer,TextMessage));
	pointcut pointExamplesrcProducerToolshowParameters(): call(* example.src.ProducerTool.showParameters());
}
