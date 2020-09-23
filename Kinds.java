class Kinds{
 public static final int Var = 0;
 public static final int Value = 1;
 public static final int Other = 2;
 public static final int Function  = 3;
 public static final int Constant = 4;
 public static final int ScalarParam = 5;
 public static final int ArrayParam = 6;
 Kinds(int i){val = i;}
 Kinds(){val = Other;}

 public String toString() {
        switch(val){
          case 0: return "Var";
          case 1: return "Value";
          case 2: return "Other";
          case 3: return "Function";
	  case 4: return "Constant";
	  case 5: return "ScalarParam";
	  case 6: return "ArrayParam";
          default: throw new RuntimeException();
        }
 }

 int val;
}
