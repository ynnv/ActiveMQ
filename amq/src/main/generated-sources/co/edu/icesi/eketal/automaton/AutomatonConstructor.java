package co.edu.icesi.eketal.automaton;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.NamedEvent;
import co.edu.icesi.ketal.core.State;
import co.edu.icesi.ketal.core.Transition;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@SuppressWarnings("all")
public class AutomatonConstructor {
  private static Automaton instance;
  
  public static Map<String, State> estados = new HashMap<String, State>();
  
  public static Automaton getInstance() {
    if(instance==null)
    	new AutomatonConstructor();
    return instance;
  }
  
  private AutomatonConstructor() {
    initialize();
  }
  
  private void initialize() {
    //Relación evento caracter
    Map<String, Character> mapping = new TreeMap<String, Character>();
    //Estado inicial
    State inicial = null;
    //lista de estados finales
    Set<State> estadosFinales = new HashSet();
    
    //conjunto de transiciones
    HashSet<Transition> transitionSet = new HashSet();
    //map de expresiones con caracteres
    Hashtable<Expression, Character> expressions = new Hashtable();
    
    int consecutivo = 65;
    Character caracter = (char)consecutivo;
    String nombreEvento = "";
    String estadoLlegada = "";
    
    //Definición del estado: firstState
    String estadoFirstState = "firstState";
    estados.put(estadoFirstState, new State());
    //start start 1
    //Estado inicial: firstState
    inicial = estados.get(estadoFirstState);
    //Definición del estado: finalState
    String estadoFinalState = "finalState";
    estados.put(estadoFinalState, new State());
    //Transicion de eventMessageProducer -> finalState
    estadoLlegada = "finalState";
    if(!estados.containsKey(estadoLlegada)){
    	estados.put(estadoFirstState, new State());
    }
    caracter = (char)consecutivo;
    consecutivo++;
    nombreEvento = "eventMessageProducer";
    if(!mapping.containsKey(nombreEvento)){
    	mapping.put(nombreEvento, caracter);
    	expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
    }
    Transition firstStateEventMessageProducer = new Transition(estados.get(estadoFirstState), estados.get(estadoLlegada), mapping.get(nombreEvento));
    transitionSet.add(firstStateEventMessageProducer);
    
    //Estado final FinalState
    estados.get(estadoFinalState).setAccept(true);
    estadosFinales.add(estados.get(estadoFinalState));
    
    Automaton automata = new Automaton(transitionSet, inicial, estadosFinales, expressions){
    	@Override
    	    	public boolean evaluate(Event event){
    	    		if(event instanceof NamedEvent){
    	    			return super.evaluate(event);
    	    		}
    	    		return false;
    	    	}
    };
    automata.initializeAutomaton();
    instance = automata;
  }
}
