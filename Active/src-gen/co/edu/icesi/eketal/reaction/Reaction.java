package co.edu.icesi.eketal.reaction;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.State;

@SuppressWarnings("all")
public class Reaction {
  public static void reactionconstructorinitState() {
    String msg = "Reaction detected connection";
    System.out.println("----------------------------------------");
    System.out.println(msg);
    System.out.println("----------------------------------------");
  }
  
  public static void reactionconstructorfirstState() {
    String msg = "Reaction detected messages";
    System.out.println("----------------------------------------");
    System.out.println(msg);
    System.out.println("----------------------------------------");
  }
  
  public static void verifyBefore(final Automaton automaton) {
    
  }
  
  public static void verifyAfter(final Automaton automaton) {
    State actual = automaton.getCurrentState();
    if(actual.equals(co.edu.icesi.eketal.automaton.Constructor.estados.get("firstState"))){
    	reactionconstructorfirstState();
    }
    if(actual.equals(co.edu.icesi.eketal.automaton.Constructor.estados.get("initState"))){
    	reactionconstructorinitState();
    }
  }
}
