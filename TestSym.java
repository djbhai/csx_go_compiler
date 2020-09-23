

import java.util.*;

class TestSym extends Symb {                  //TestSym in our language, Okay!
	 public Kinds kind; // Should always be Var in CSX-lite
	 public Types type; // Should always be Integer or Boolean in CSX-lite

	 public TestSym(String id, Kinds k, Types t){
		super(id);
		kind = k; type = t;};
	 public TestSym(String id, int k, int t){
		super(id);
		kind = new Kinds(k); type = new Types(t);};
	 public String toString(){
	             return "("+name()+": kind=" + kind+ ", type="+  type+")";};
	}

