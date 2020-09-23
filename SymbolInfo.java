/**************************************************
*  class used to hold information associated w/
*  Symbs (which are stored in SymbolTables)
*
****************************************************/

import java.util.*;

class SymbolInfo extends Symb {                 
 public Kinds kind; 
 public Types type; 
 public int ArraySize;
 public int varIndex;
 public List<ArgPairs> pair;
 public int number_of_params;
 public boolean local_find=false;
 public SymbolInfo(String id, Kinds k, Types t){
	super(id);
	kind = k; type = t;};
 public SymbolInfo(String id, int k, int t){
	super(id);
	kind = new Kinds(k); type = new Types(t);};
 public SymbolInfo(String id, Kinds k, Types t, List<ArgPairs> ap) {
	super(id);
	kind = k;
	type = t;
 	pair = ap;
		};
 public SymbolInfo(String id, Kinds k, Types t, int s) {
	super(id);
	kind = k;
	type = t;
 	ArraySize = s;
		};
 public int getType( ){
	return type.val;
			}
 public String toString(){
             return "("+name()+": kind=" + kind+ ", type="+  type+ ", varIndex= "+varIndex;};
}

