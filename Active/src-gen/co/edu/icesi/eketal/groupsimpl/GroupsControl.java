package co.edu.icesi.eketal.groupsimpl;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("all")
public class GroupsControl {
  private static String[] SET_VALUES = {"localGroup"};
  
  private static Set<String> grupos = new TreeSet<String>(
    				Arrays.asList(SET_VALUES)
    				);
  
  public static Boolean addGroup(final String nuevoGrupo) {
    return grupos.add(nuevoGrupo);
  }
  
  public static Boolean removeGroup(final String grupoEliminar) {
    return grupos.remove(grupoEliminar);
  }
  
  public static boolean on(final String grupo) {
    return true;
  }
  
  public static boolean host(final String nombreGrupo) {
    if(grupos==null){
    	return false;
    }
    return grupos.contains(nombreGrupo);
  }
}
