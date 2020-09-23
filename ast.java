import java.util.*;				
import java.io.PrintStream;                     
abstract class ASTNode {			
					
// abstract superclass; only subclasses are actually created

	int linenum;
	int colnum;

	static PrintStream afile;	// File to generate JVM code into
        static String className;
	static int typeErrors = 0; 
	static int cgErrors =  0;       // Total number of code gen errors 
	
	static int numberOfLocals =  0; // Total number of local CSX vars

	static int labelCnt = 0;	// counter used to gen unique labels
		
	static String typeGetter(int type_val)	{
	String ty;
	switch(type_val){
		case 0:
		ty ="C";
		break;
		case 1:
		ty ="I";
		break;
		case 2:
		ty ="Z";
		break;
		default:
		ty = "V";
		break;
			}
		return ty;
					}  // returns type  corresponding to integer in Types  as a string

	static void mustBe(boolean assertion) {
		if (! assertion) {
			throw new RuntimeException();
		}
	} // mustBe
	static void typeMustBe(int testType,int requiredType,String errorMsg) {
		if ((testType != Types.Error) && (testType != requiredType)) {
			System.out.println(errorMsg);
			typeErrors++;
		}
	} // typeMustBe
	static boolean typesMustBeEqual(int type1,int type2,String errorMsg) {
			boolean ans = false;
		if ((type1 != Types.Error) && (type2 != Types.Error) &&
				(type1 != type2)) {


			System.out.println(errorMsg);

			typeErrors++;
			ans = true; 
		}
		return ans;
	} // typesMustBeEqual

	static boolean kindsMustBeEqual(int kind1,int kind2,String errorMsg) {
			boolean ans = false;
		if (kind1 != kind2) {


			System.out.println(errorMsg);

			typeErrors++;
			ans = true; 
		}
		return ans;
	} // KindsMustBeEqual
	String error() {
		return "Error (line " + linenum + "): ";
	} // error



	// generate an instruction w/ 0 operands
	static void    gen(String opcode){
        	afile.println("\t"+opcode);
	}

        // generate an instruction w/ 1 operand
	static void  gen(String opcode, String operand){
        	afile.println("\t"+opcode+"\t"+operand);
	}

        // generate an instruction w/ 1 operand
	static void  gen(String opcode, int operand){
        	afile.println("\t"+opcode+"\t"+operand);
	}


	//  generate an instruction w/ 2 operands
	static void  gen(String opcode, String operand1, String operand2){
        	afile.println("\t"+opcode+"\t"+ operand1+"  "+ operand2);
	}

	//  generate an instruction w/ 2 operands
	static void  gen(String opcode, String operand1, int operand2){
        	afile.println("\t"+opcode+"\t"+ operand1+"  "+operand2);
	}

	//      build label of form labeln
	String   buildlabel(int suffix){
                return "label"+suffix;
	}

	//      generate a label for an instruction
	void    genlab(String label){
        	afile.println(label+":");
	}
	
	public static SymbolTable st = new SymbolTable();

	ASTNode() {
		linenum = -1;
		colnum = -1;
	} // ASTNode()

	ASTNode(int line, int col) {
		linenum = line;
		colnum = col;
	} // ASTNode(line, col)

	boolean isNull() {
		return false; // often redefined in a subclass
	} // isNull()

	void Unparse(int indent) {
		// This routine is normally redefined in a subclass
	} // Unparse()
} // class ASTNode

abstract class ExprNode extends ASTNode {
ExprNode() {

}
ExprNode(int line,int col){
   super(line , col);
   

			  }
    
ArgPairs CheckTypes() {

ArgPairs rand =  new ArgPairs(new Types(4),new Kinds(2));
return rand;

		      }   
void cg()	{};

static nullExprNode NULL = new nullExprNode();
protected Types type; // Used for typechecking: the type of this node
protected Kinds kind; // Used for typechecking: the kind of this node
					}


class nullExprNode extends ExprNode {
nullExprNode() {

 super();
}
boolean isNull() { return true;}
void Unparse(int indent) { }
void cg() {};
}
abstract class StmtNode extends ASTNode {

StmtNode() {

super();
}
StmtNode(int line, int col){
super(line,col);
}

void CheckTypes() { };
void cg(){ };

static nullStmtNode NULL = new nullStmtNode();
				

					}
class nullStmtNode extends StmtNode {

nullStmtNode() {
super();
}
}
abstract class DeclNode extends ASTNode {
DeclNode() {
super();
}

DeclNode(int line,int col){
super(line,col);

			}
static nullDeclNode NULL = new nullDeclNode();

 void CheckTypes() {  } ;					
 void cg(){ };

                                       }
class nullDeclNode extends DeclNode {
nullDeclNode(){
super();

}
}
abstract class ArgDeclNode extends ASTNode {

  ArgDeclNode() {

 super();
  }

ArgDeclNode(int line, int col) {

super(line,col);

	}
SymbolInfo CheckTypes() {
 
Kinds k = new Kinds(Kinds.ScalarParam);

 return new SymbolInfo(argName.idname,k,argType.type);

			}

protected  identNode argName;
protected  TypeNode  argType;
protected final boolean scalar = true;
static nullArgDeclNode NULL = new nullArgDeclNode();

					}  
 
class nullArgDeclNode extends ArgDeclNode {

nullArgDeclNode(){
super();
}

}


class ProgramNode extends ASTNode  {

         ProgramNode ( NameNode id, List<DeclNode> m, 
List<FuncDeclNode> f,int line, int col) {                                                           

          super(line,col);
          packageName = id;
          members = m;
          functions = f;

          }

void Unparse( int indent) {

            ListIterator ltr = members.listIterator();
	    System.out.print(linenum + ":"+"\t"+"package");
            packageName.Unparse(0);
            while(ltr.hasNext()){
 		((DeclNode)ltr.next()).Unparse(0);

				}
	ListIterator lt = functions.listIterator();
    
     while(lt.hasNext()){

	((FuncDeclNode)lt.next()).Unparse(0);


			}
			}

boolean isTypeCorrect() {

 CheckTypes();
 return(typeErrors == 0);
			}
void CheckTypes() {

ListIterator lm = members.listIterator();
	while(lm.hasNext()){
	((DeclNode)lm.next()).CheckTypes();

			   }
ListIterator lf = functions.listIterator();
	while(lf.hasNext()){
	((FuncDeclNode)lf.next()).CheckTypes();

		} 
SymbolInfo id = (SymbolInfo)st.localLookup("main");   

if (id == null)
{
System.out.println(error()+"Function main is not declared");
typeErrors++;
}  //checks for main() declaration
else{
Types t = id.type;
Kinds k = id.kind;
if(t.val != Types.Void && k.val !=Kinds.Function){

System.out.println(error()+"The last function in a csx_go program"+
		"should be of main and it should not return any value");
typeErrors++;
}// main return type check
}
}

boolean codegen(PrintStream asmfile) {
        	afile = asmfile;
        	cg();
        	return (cgErrors == 0);
 	}


void cg() 	{
		className = packageName.varName.idname; 
        	gen(".class","public",className);
        	gen(".super","java/lang/Object");
		String ty;
		ArgPairs temp;
		int type_val = 4;
		if(members.size() > 1)	{				/* global variables fields are declared as fields */
		for(DeclNode member : members)	{
		if(member instanceof nullDeclNode)
		break;
		// .field declarations go here
		if(member instanceof ConstDeclNode)	{
		temp = ((ConstDeclNode)member).constValue.CheckTypes();
		ty = typeGetter(temp.type.val);
		gen(".field static",((ConstDeclNode)member).constName.idname,ty);
							}
		else{
		if(member instanceof VarDeclNode)	{
		type_val = ((VarDeclNode)member).varType.type.val;
							}
		else if(member instanceof ArrayDeclNode)	{
		type_val = ((ArrayDeclNode)member).elementType.type.val;
								}
		ty = typeGetter(type_val);
		if(member instanceof VarDeclNode)	{
		gen(".field static",((VarDeclNode)member).varName.varName.idname,ty);
							}
		else if(member instanceof ArrayDeclNode){
		gen(".field static",((ArrayDeclNode)member).arrayName.varName.idname+" "+"["+ty);
							}
			}  
						}
					}		
		gen(".method","public static main([Ljava/lang/String;)V");
		if(members.size() > 1)	{				/*global variables initialization 
									in case they have inline assignments */
		for(DeclNode member : members)	{
		if(member instanceof nullDeclNode)
		break;
		if(member instanceof ConstDeclNode)	{
		 temp = (((ConstDeclNode)member).constValue).CheckTypes();
		type_val = (temp.getType()).val;
		((ConstDeclNode)member).constValue.cg();
		ty = typeGetter(type_val);
		gen("putstatic",className+"/"+((ConstDeclNode)member).constName.idname,ty);    
		

							}
		if(member instanceof VarDeclNode)	{
		if(!(((VarDeclNode)member).initValue instanceof nullExprNode))
		{
		 temp = (((VarDeclNode)member).initValue).CheckTypes();
		type_val = (temp.getType()).val;
		((VarDeclNode)member).initValue.cg();
		ty = typeGetter(type_val);
		gen("putstatic",className+"/"+((VarDeclNode)member).varName.varName.idname,ty);
		}
		
							}
		if(member instanceof ArrayDeclNode)	{
		((ArrayDeclNode)member).arraySize.cg();
		switch(((ArrayDeclNode)member).elementType.type.val)	{
			case 0:
			ty = "char";
			break;
			case 1:
			ty = "int";
			break;
			case 2:
			ty = "boolean";
			break;
			default:
			ty =" ";
			break;
							}
		gen("newarray"+" "+ty);
	
							}
					}
					}
		int stackSize = members.size()+25;
		gen(".limit locals",1);
		gen("invokestatic"+" "+className+"/"+"main()V");
		gen("return");
		gen(".limit stack"+" "+stackSize);
		gen(".end","method");
		for(FuncDeclNode function : functions)	{
		function. cg(); 
							}
		} //cg
private final NameNode packageName;
private final List<DeclNode> members;
private final List<FuncDeclNode> functions;
                                   }

class VarDeclNode extends DeclNode {

      VarDeclNode ( NameNode id, TypeNode t, ExprNode v,int line,
int col)
               {
             super(line, col);
             varName = id;
             varType = t;
             initValue = v;

 
               }

    

     void Unparse( int indent) {

				int i = indent;
			     System.out.print("\n");
			while(i !=0) 
				{

			System.out.print(" ");
			 i = i-1;

				}
			
                              System.out.print(linenum + ":"+"\t"); 
                              System.out.print(" " + "VAR");
                             varName.Unparse(0);
                             varType.Unparse(0);
			    if(initValue != ExprNode.NULL)
				{
				System.out.print(" = ");
				initValue.Unparse(0);

				}
                        System.out.print(";");
				


                               }
     
 void CheckTypes() {
 SymbolInfo id;
 boolean ty_error;
 identNode n= varName.varName;
 id = (SymbolInfo) st.localLookup(n.idname);
 if (id == null) {

	      ArgPairs temp = initValue.CheckTypes();
	      if ( initValue instanceof nullExprNode){
		id = new SymbolInfo(n.idname,
			new Kinds(Kinds.Var),new Types(varType.type.val));

		} // is assignment nullExprNode?
	      else{
              if((temp.getKind()).val == 6) {
			System.out.println(error() + "array param cannot be"+
				"assigned to scalar variable"+id.name());
				typeErrors++;

			varName.type = new Types(Types.Error);
			id = new SymbolInfo(n.idname,new Kinds(Kinds.Var), 
						new Types(Types.Error));
				
		} // If array variable is assigned to scalar var
	      else {
	         String errorMessage = error()+ "the declared variable type"+
			"and the assigned expression type do not match";
		 ty_error=typesMustBeEqual(varType.type.val,
					(temp.getType()).val,errorMessage);
		 if(ty_error)	{
		 id = new SymbolInfo(n.idname,new Kinds(Kinds.Var),
						 new Types(Types.Error));
			   	}
		 else	{
		id = new SymbolInfo(n.idname,new Kinds(Kinds.Var),
						new Types(varType.type.val));

		     	}
			} // if assigned type matches the declared type 
		
			} // control flow if assignment isn't null
		
		try {
			st.insert(id);                    
		} catch (DuplicateException d) {
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}
		varName.idinfo = id;     // for next phase
		varName.idinfo.varIndex = numberOfLocals++;
		} // If id is null
		
 	else {
		System.out.println(error() + id.name() + 
				" is already declared.");
		typeErrors++;

	} // If id isn't null
 	} // checkTypes

void cg()	{
	if(!(initValue instanceof nullExprNode))
		{
	initValue.cg();
	gen("istore",varName.idinfo.varIndex);
		}   
		}	

protected final NameNode varName;
protected final TypeNode  varType;
protected final ExprNode  initValue;
					}//varDeclNode

class ConstDeclNode extends DeclNode {

      ConstDeclNode ( identNode cname, ExprNode cvalue, int line, int col)
               {
            super(line, col);
            constName = cname;
            constValue = cvalue;


               }

void Unparse( int indent) {

                        int i = indent;
			System.out.print("\n");
			while(i !=0) 
				{

			System.out.print(" ");
			 i = i-1;

				}
			  System.out.print(linenum + ":"+"\t"); 
                          System.out.print(" " + "CONST");
			  constName.Unparse(0);
			  System.out.print(" = ");
			  constValue.Unparse(0);
 			  System.out.print(";");


                          } //Unparse

void CheckTypes() {

	SymbolInfo id;
  	id = (SymbolInfo) st.localLookup(constName.idname);
 	if (id == null) {
		if ( constValue instanceof nullExprNode){
		id = new SymbolInfo(constName.idname,
			new Kinds(Kinds.Constant),new Types(4));}
              else {
	 		ArgPairs temp = constValue.CheckTypes();
	     if((temp.getKind()).val == 6)
		{
		System.out.println(error() + "array param cannot be"+ 
		"assigned to constant variable");
		typeErrors++;

		id = new SymbolInfo(constName.idname,
					new Kinds(Kinds.Constant),
					new Types(Types.Error));
                } //if array var is assigned to scalar var
	      else{
		id = new SymbolInfo(constName.idname,
			new Kinds(Kinds.Constant),temp.type);
		}
		}

                try {
			st.insert(id);
			constName.info = id;
			constName.info.varIndex = numberOfLocals;
			numberOfLocals++;
		} catch (DuplicateException d) {
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}
		}//control flow if id isn't already found in lookup
 	else 	{
	System.out.println(error() + id.name() + " is already declared.");
		typeErrors++;
		}  

        	} //CheckTypes

void cg() 	{
	constValue.cg();
	gen("istore",constName.info.varIndex); 
		}

protected final identNode constName;
protected final ExprNode  constValue;

                                     } //ConstDeclNode

class ArrayDeclNode extends DeclNode {
  
    ArrayDeclNode(NameNode id, TypeNode et, IntLitNode as,int line, int col) {
           super(line, col);
           arrayName = id;
           elementType = et;
           arraySize  =  as;


               }

void Unparse( int indent) {

		        int i = indent;
			System.out.print("\n");
			while(i !=0) 
				{

			System.out.print(" ");
			 i = i-1;

				}
			
                        System.out.print(linenum + ":"+"\t"); 
                        System.out.print(" " + "VAR");
			arrayName.Unparse(0);
			elementType.Unparse(0);
			System.out.print("[");
		        arraySize.Unparse(0);
		        System.out.print("]");
 		        System.out.print(";");

                           }

void CheckTypes()  {

		SymbolInfo id;
		identNode n= arrayName.varName;
 		id = (SymbolInfo) st.localLookup(n.idname);
		if (id == null) {
                if(arraySize.intval == 0) {
		System.out.println(error()+" The size of an array" 
					+"must be greater than zero");
		typeErrors++;
		id = new SymbolInfo(n.idname,
			new Kinds(Kinds.Other),new Types(Types.Error));
					} //if size of array is > 0
		else{
		id = new SymbolInfo(n.idname,
			new Kinds(Kinds.ArrayParam),
			new Types(elementType.type.val),arraySize.intval);
			}
				}
		else{
		System.out.println(error()+" The variable is already" 
					+"declared");
		typeErrors++;
		id = new SymbolInfo(n.idname,
			new Kinds(Kinds.Other),new Types(Types.Error));
			}
		
		try {
			st.insert(id);
		} catch (DuplicateException d) {
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}
		
		arrayName.idinfo = id;
		arrayName.idinfo.varIndex = numberOfLocals;
		numberOfLocals++;

		}//checkTypes
void cg()	{
	String ty;
	arraySize.cg();
	switch(elementType.type.val)	{
	case 0:
	ty = "char";
	break;
	case 1:
	ty = "int";
	break;
	case 2:
	ty = "boolean";
	break;
	default:
	ty = " ";
	break;
					}
	gen("newarray"+" "+ty);
	gen("astore",arrayName.idinfo.varIndex);

		}
protected final NameNode arrayName;
protected final TypeNode  elementType;
protected final IntLitNode arraySize;


                                    }

abstract class TypeNode extends ASTNode {
	// abstract superclass; only subclasses are actually created
		TypeNode(Types t) 
		{super(); type=t;}
		TypeNode(int l,int c, Types t) {super(l,c);type = t;}
		static nullTypeNode NULL = new nullTypeNode();
		final Types type; // Used for typechecking -- the type of this typeNode
              
              
                              } 
class nullTypeNode extends TypeNode {
nullTypeNode() {
super(new Types(Types.Void));
	     }

				   }

class IntTypeNode extends TypeNode {

            IntTypeNode( int line , int col )
                   {

                  super(line , col,new Types(Types.Integer));

                   }
   

void Unparse(int indent) 	{
           System.out.print(" " + "int");
			 	}
				}

class BoolTypeNode extends TypeNode {
          BoolTypeNode( int line , int col )
                   {

           super(line , col,new Types(Types.Boolean) );

                   }
void Unparse(int indent){

System.out.println(" " + "bool");
			
			}
					}

class CharTypeNode extends TypeNode {

      CharTypeNode( int line , int col )
                   {

                  super(line , col,new Types(Types.Character) );

                   }

void Unparse(int indent) {



      System.out.print(" " +"char");


                          }

 					}

class VoidTypeNode extends TypeNode {

     VoidTypeNode( int line , int col )
                   {

                  super(line , col,new Types(Types.Void) );
                   }

    
                                    }

class FuncDeclNode extends DeclNode  {

FuncDeclNode( identNode i, List<ArgDeclNode> ad, 
				TypeNode r, BlockNode b, int line, int col) {
            
             super(line, col);

             name = i;
             args = ad;
             returnType = r;
             body       = b;

             }

void Unparse(int indent) {
int i;
System.out.print("\n");
ListIterator ir= args.listIterator();


i=0;

System.out.print(linenum + " : ");
System.out.print("\t");
System.out.print("Func");
name.Unparse(0);
System.out.print("(");
while(ir.hasNext())
{
 i=i+1;
 if(i >= 2) {
 System.out.print(","+ " ");
		}
 ((ArgDeclNode)ir.next()).Unparse(0);

 }
System.out.print(")");

returnType.Unparse(0);

body.Unparse(colnum);
                        }// Unparse

void CheckTypes() {
	numberOfLocals = 0;
	SymbolInfo id;
	int number_of_params =0;
	boolean localerrors = false;
	id = (SymbolInfo) st.localLookup(name.idname);
  	if(id == null) {                                              
  	ArrayList<ArgPairs> pairs =null; 
  	if(args.size() >=1){
  	pairs = new ArrayList<ArgPairs>();
  	ListIterator lt = args.listIterator();
  	while(lt.hasNext()) {					/*  ArgPairs stores the kind and type information of an arguments, all         the    arguments kind and type information is stored in the list and this list is stored in the symbol table for future       reference						*/
    	ArgDeclNode arg = (ArgDeclNode)lt.next();
    	if(arg instanceof nullArgDeclNode)
		break;
     	Types t;
     	Kinds k;
    	ArgPairs pair;
    	number_of_params++;
     	SymbolInfo e = arg.CheckTypes();
     	if(arg instanceof ValArgDeclNode) {
        k = new Kinds(5);                 
	t = e.type;
        pair = new ArgPairs(t,k);                   
        pairs.add(pair);
	
		    } // scalar pair
	else if(arg instanceof ArrayArgDeclNode){
	k = new Kinds(6);
	t = e.type;
	pair = new ArgPairs(t,k);
	pairs.add(pair);
	 	} // array pair
                } // argument inspection ends, pairs ready
                } // if pairs need to be created

       id = new SymbolInfo(name.idname,
			new Kinds(Kinds.Function),
		new Types(returnType.type.val),pairs);
  	id.number_of_params = number_of_params;
		try {
			st.insert(id);
		} catch (DuplicateException d) {
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}
		} // end of second if

 	else {
	System.out.println(error() + id.name() + " is already declared.");
		typeErrors++;
		return;
		}
	//another pass to check if there are any duplicates in the arguments

     	st.openScope();			// new scope is created for the function
  	if(args.size() >=1 ){  
	SymbolInfo io;
    	ListIterator li = args.listIterator();

    while(li.hasNext()){
	ArgDeclNode ag=(ArgDeclNode)li.next();
	if(ag instanceof nullArgDeclNode)
	break;
        boolean s = ag.scalar;
	int kindval = 4;
	if(ag instanceof ValArgDeclNode){
	kindval = 5;
					}
	else if(ag instanceof ArrayArgDeclNode){
	kindval = 6;
						}
        SymbolInfo es = ag.CheckTypes();
        io = (SymbolInfo)st.localLookup(es.name());
	
        if(io == null)
	 {	      
        io = new SymbolInfo(es.name(),new Kinds(kindval) ,es.type);    
   
        try {
			st.insert(io);
			io.varIndex = numberOfLocals;
			numberOfLocals++;
		} catch (DuplicateException d) {      //args are added iteratively as long as there are no duplicates
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}
 
		} // if io i.e args does not contain duplicates
        else{
	System.out.println(error() + io.name() + " is already declared.");
        localerrors = true;
	typeErrors++;
	break;
	
	     }// if args contain duplicates
	     } //while the args are still present
	     } //args.size()>1

	 if(!localerrors)                   //local errors is true only when there is a duplicate in the arguments
		{
          id = new SymbolInfo("return_type",
	new Kinds(Kinds.Other),returnType.type);
	  try {
			st.insert(id);
		} catch (DuplicateException d) {
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}

          body.CheckTypes();                 // Further body is type-checked only when the declaration is type-correct
        
	try{
	st.closeScope();				//the functions scope is closed after body is type-checked
	     }
	catch(EmptySTException e){

		}
		}
  		} // checktypes


void cg()	{

	String returns;
	String parameters="";
	String type= "";
	int i = args.size();
	for(ArgDeclNode arg:args){
	if(arg instanceof nullArgDeclNode){
	break;
		}
	SymbolInfo info = arg.CheckTypes();

	if(arg instanceof ArrayArgDeclNode)
	parameters = parameters+"[";               //parameters variable is built to represent the parameters the function is expecting
	switch(info.type.val){
	case 0:
	parameters = parameters+"C";
	break;
	case 1:
	parameters= parameters+"I";
	break;
	case 2:
	parameters =parameters+"Z";
	break;
	default:
	parameters = parameters+"V";
	break;
				}
				}
	switch(returnType.type.val){
	case 0:
	returns = "C";
	break;
	case 1:
	returns = "I";
	break;
	case 2:
	returns = "Z";
	break;
	default:
	returns = "V";
	break;
				}
	
	gen(".method","public static"+" "+name.idname+
				"("+parameters+")"+returns);
	
	int totalLocals = args.size() + body.decls.size();
	int stackLimit =  args.size() + body.decls.size() + 25;
	gen(".limit","locals"+ " " + totalLocals);    
	body.cg();
	gen("return");
	gen(".limit stack"+" "+stackLimit);	
	gen(".end method");
		}

private final identNode name;
private final List<ArgDeclNode> args;   //can be a null node
private final TypeNode  returnType;     
private final BlockNode body;

                      }

class ArrayArgDeclNode extends ArgDeclNode {



ArrayArgDeclNode(identNode a , TypeNode e) {

 argName  = a;
 argType = e;
					   }

 void Unparse(int indent) {
                     argName.Unparse(0);
		     System.out.print(" " + "["+"]");
		     argType.Unparse(0);

			  }
	SymbolInfo CheckTypes() {
 							
	Kinds k = new Kinds(Kinds.ArrayParam);
	return new SymbolInfo(argName.idname,k,argType.type);
		 	}

protected final identNode argName;
protected final TypeNode argType;
protected final boolean scalar = false;
					}

class ValArgDeclNode extends ArgDeclNode {

ValArgDeclNode(identNode a , TypeNode e) {

 argName  = a;
 argType = e;
					   }

void Unparse(int indent)
{
              argName.Unparse(0);
	      System.out.print(" ");
	      argType.Unparse(0);
	      
}


SymbolInfo CheckTypes() {
 
Kinds k = new Kinds(Kinds.ScalarParam);

 return new SymbolInfo(argName.idname,k,argType.type);
 

			}

protected final identNode argName;
protected final TypeNode  argType;
protected final boolean scalar = true;
                                         }

class AsgNode extends StmtNode {

AsgNode(NameNode t , ExprNode s,int line,int col) {
                super(line,col);
                target = t;
	         source = s;


			          }
private final NameNode target;
private final ExprNode source;
private boolean lorg;
private int stack_depth = 1;

void Unparse(int indent) {
      int i= indent;
     System.out.print("\n");

     System.out.print(linenum + ":");
      while(i !=0)
       {
       System.out.print(" ");
	i= i-1;
	}

      target.Unparse(0);
     System.out.print(" = ");
     source.Unparse(0);
     System.out.print(";");

			}

void CheckTypes() {

ArgPairs lhs = target.CheckTypes();		
ArgPairs rhs = source.CheckTypes();
SymbolInfo id;
id = (SymbolInfo)st.localLookup(target.varName.idname);
lorg = true;				//lorg keeps track of whether the target is found in local or global phase
stack_depth = st.scopes.size();		    // lorg is used in codegeneration phase to determine whether a field should be called or local var
if(id == null)	{
lorg = false;
id = (SymbolInfo)st.globalLookup(target.varName.idname);

		}
Types lt = lhs.getType();
Types rt = rhs.getType();
Kinds lk = lhs.getKind();
Kinds rk = rhs.getKind();

if(lk.val == Kinds.Constant){

System.out.print(error()+"a constant cannot be assigned a value");
typeErrors++;
return;

} // if lhs is constant 
if(lt.val == Types.Error){

return;
} // if lhs is error
else
{
if((lt.val == Types.Unknown)){

id = new SymbolInfo(target.varName.idname, Kinds.Other,Types.Error);

try {
			st.insert(id);                    
		} catch (DuplicateException d) {
			/* can't happen */
		} catch (EmptySTException e) {
			/* can't happen */
		}
System.out.println(error() + "The left hand side "+
"of the assignment is not declared");

} //undeclared

if((lt.val != rt.val) && (rt.val != Types.Error)) {

System.out.println(error() + "The lhs and rhs should"+
	"be of the same type in an assignment statement");
 typeErrors++; 
		}//identical_type_check
else if((lk.val == rk.val) &&(rk.val == Kinds.ArrayParam))
{
if((lt.val == Types.Character)){

if(source instanceof StrLitNode){

id = (SymbolInfo)st.localLookup(target.varName.idname);
if(id == null)
{
id = (SymbolInfo)st.globalLookup(target.varName.idname);
}

int size = id.ArraySize;
int source_size = ((StrLitNode)source).strval.length() -2;
if( source_size != size){

System.out.print("The character array variable can only be"+
"assigned to string literal whose size is same as the size of array");

}//strlength equals size of array
}//target is stringlit
}//character

if(lt.val == rt.val) {

id = (SymbolInfo)st.localLookup(target.varName.idname);

if(id == null){

id =(SymbolInfo) st.globalLookup(target.varName.idname);

}
if(source instanceof NameNode){

SymbolInfo id2 = (SymbolInfo)st.localLookup(((NameNode)source).varName.idname);


if(id2 == null){

id2 = (SymbolInfo)st.globalLookup(target.varName.idname);  
}

if(id2.ArraySize != id.ArraySize){
System.out.println(error() + "Array assignment should be such"+ 
	"that both lhs and rhs have the same size");
typeErrors++;
}
}
}
}
}
		 }// checkTypes

void cg()	{

if(lorg == true && stack_depth>1)	{			//local lookup finds globals when symbol table length=1
	if(target.idinfo.kind.val != Kinds.ArrayParam)	{
	source.cg();
	gen("istore",target.idinfo.varIndex);

							}
	else if(!(target.subscriptVal instanceof nullExprNode))	{
	gen("aload",target.idinfo.varIndex);
	target.subscriptVal.cg();
	source.cg();
	switch(target.idinfo.type.val)
	{
	case 0:
	gen("castore");
	break;
	case 1:
	gen("iastore");
	break;
	case 2:
	gen("bastore");
	break;
	default:
	break;
	} //switch
					
								}
	else	{
	gen("astore",target.idinfo.varIndex);
		}
			}
else	{
	String type = typeGetter(target.idinfo.type.val);		
	if(target.idinfo.kind.val == Kinds.ArrayParam)	{
	if(target.subscriptVal instanceof nullExprNode)	{
	source.cg();							/*if there is no  subscriptval an array reference is copied in to target*/
	gen("putstatic",className+"/"+target.varName.idname,"["+type);
							}
	else	{
	gen("getstatic",className+"/"+target.varName.idname,"["+type);    //array field assignment
	target.subscriptVal.cg();
	source.cg();
	switch(target.idinfo.type.val)
	{
	case 0:
	gen("castore");
	break;
	case 1:
	gen("iastore");
	break;
	case 2:
	gen("bastore");
	break;
	default:
	break;
	} //switch
	
		}

								}
	else	{
	source.cg();
	gen("putstatic",className+"/"+target.varName.idname,type);		//scalar field assingment
		}
	}
		}//cg
                               }

class IfThenNode extends StmtNode {

 IfThenNode( ExprNode e, BlockNode t, BlockNode el,int line,int col) {
super(line,col);
condition = e;
thenPart  = t;
elsePart  = el;
}

private final ExprNode condition;
private final BlockNode thenPart;
private final BlockNode elsePart;

void Unparse(int indent)
{
int i =indent;
System.out.print("\n");
System.out.print(linenum);
System.out.print( ":");
while(i !=0)
       {
       System.out.print(" ");
	i= i-1;
	}
System.out.print("if" + " ");
condition.Unparse(indent);
thenPart.Unparse(indent);
if(elsePart == thenPart)
{
return;
}

else{
System.out.print("\n");
System.out.print(linenum);
System.out.print(":");
i=indent;
while(i !=0)
       {
       System.out.print(" ");
	i= i-1;
	}
System.out.print("else" + " ");
elsePart.Unparse(indent);




}
}
  
void CheckTypes(){
ArgPairs temp = condition.CheckTypes();

if((temp.getType()).val != Types.Boolean)
{
System.out.println(error() + "the condition for an if"+
				"should be of type boolean");
typeErrors++;


}
thenPart.CheckTypes();
elsePart.CheckTypes();
		}    

void cg()	{
	labelCnt =labelCnt+1;
	String lab_else = "#"+labelCnt;
	labelCnt =labelCnt+1;
	String lab_exit = "#"+labelCnt;
	condition.cg();

	if(elsePart == thenPart)	{             //both elsePart and thenPart were assigned to the same instance when there is no else
	                                                                   
	gen("ifeq",lab_exit);				
	thenPart.cg();
                         		}                             
	else	{
	gen("ifeq",lab_else);
	thenPart.cg();
	gen("goto",lab_exit);
	gen(lab_else+":");
	elsePart.cg();
		}
	gen(lab_exit+":");
		}   //cg     
		}


class ForNode extends StmtNode {

ForNode ( ExprNode c, BlockNode b,int line,int col){

super(line,col);
condition = c;
loopBody = b;
}


private final ExprNode condition;
private final BlockNode loopBody;

void Unparse(int indent)
{
int  i = indent;
System.out.print("\n");
System.out.print(linenum + ":");
while(i !=0)
{
System.out.print(" ");
i = i-1;

}
System.out.print("for");
condition.Unparse(0);
loopBody.Unparse(indent);


}
void CheckTypes(){
ArgPairs temp = condition.CheckTypes();

if((temp.getType()).val != Types.Boolean)
{
System.out.println(error() + "the condition of for should be of type boolean");
typeErrors++;


}
loopBody.CheckTypes();
		} 

void cg()	{
	labelCnt++;
	String loop_head = "#"+labelCnt;
	labelCnt++;
	String loop_exit = "#"+labelCnt;
	gen(loop_head,":");
	condition.cg();
	gen("ifeq",loop_exit);
	loopBody.cg();
	gen("goto",loop_head);
	gen(loop_exit,":");
		} //cg
                               }

class In extends StmtNode {

In() {


}
In(In n, int line, int col,String t)
{
super(line,col);
node = n;
text = t;
}
private  String text;
private  In node;
void Unparse( int indent){

int  i = indent;
System.out.print("\n");
System.out.print(linenum + ":");
while(i !=0)
{
System.out.print(" ");
i = i-1;

}
System.out.print(text);
System.out.print(" ");
node.Unparse(0);
}
void CheckTypes()	{
	node.CheckTypes();}
void cg()	{
	node.cg();}

}


class ReadNode extends In {

ReadNode ( NameNode n , ReadNode r) {


targetVar = n;
moreReads = r;
}

private final NameNode targetVar;
private final ReadNode moreReads; // can be null

void Unparse(int indent)
{


targetVar.Unparse(0);

if(moreReads == null)
{
System.out.print(";");
return;
}

else {
System.out.print(",");
moreReads.Unparse(0);

}
}

void CheckTypes(){

ArgPairs temp = targetVar.CheckTypes();
if((temp.getType()).val >1){

System.out.println(error()+ "The input parameters for a read"+
			" statement can only be of type int or char");
typeErrors++;
}

if(moreReads == null){
return;

}
else {

moreReads.CheckTypes();
}
		} // CheckTypes

void cg()	{
	boolean globalFind = false;
	
	SymbolInfo id = (SymbolInfo)st.globalLookup(targetVar.varName.idname);
	if(id !=null)
	globalFind =true;
	String ty  = typeGetter(targetVar.type.val);
	
	if(targetVar.type.val == 0)
	{
	gen("invokestatic CSXLib/readChar()C");
	if(globalFind)
	gen("putstatic",className+"/"+targetVar.varName.idname,ty);
	else
	gen("istore",targetVar.idinfo.varIndex);
	}
	if(targetVar.type.val == 1)
	{
	gen("invokestatic CSXLib/readInt()I");
	if(globalFind)
	gen("putstatic",className+"/"+targetVar.varName.idname,ty);
	else
	gen("istore",targetVar.idinfo.varIndex);
	}
	if(moreReads == null)
	return;
	else
	moreReads.cg();
		}
                                 }

class Out extends StmtNode {


Out() {


}

Out(Out n, int line, int col,String t)
{
super(line,col);
node = n;
text = t;
}
private  String text;
private  Out node;

void Unparse( int indent){

int  i = indent;
System.out.print("\n");
System.out.print(linenum + ":");
while(i !=0)
{
System.out.print(" ");
i = i-1;

}
System.out.print(text);
System.out.print(" ");
node.Unparse(0);
}

void CheckTypes()	{
	node.CheckTypes();			
			}	
void cg()	{
	node.cg();
		}
}


class DisplayNode extends Out {

DisplayNode ( ExprNode e, DisplayNode m){
outputValue = e;
moreDisplays = m;

					}


private  ExprNode outputValue;
private  DisplayNode moreDisplays; // can be null


void Unparse(int indent)
{
outputValue.Unparse(0);
if(moreDisplays == null)
{
System.out.print(";");

return;
}

else {
System.out.print(",");
moreDisplays.Unparse(0);

}
}

void CheckTypes()
{
ArgPairs temp = outputValue.CheckTypes();
if((temp.getType()).val >2){
System.out.println(error()+ "print only accepts variables"+
			"or arrays of type int,boolean or character types");
typeErrors++;

}
if(moreDisplays == null)
{
return;

}
else
{

moreDisplays.CheckTypes();

}
                                    }//checkTypes

void cg()	{
	Types ty;
	Kinds ki;

	outputValue.cg();
	if(outputValue instanceof NameNode)	{
	ty = ((NameNode)outputValue).idinfo.type;    // The idinfo set in the checktypes phase of a name node is used here
	ki = ((NameNode)outputValue).idinfo.kind;	// scopes are closed but the idinfo in name node retains the information
						}
	else	{
	ArgPairs temp = outputValue.CheckTypes();
	ty = temp.getType();
	ki = temp.getKind();	}
	if(ty.val == Types.Integer)
	{
	gen("invokestatic CSXLib/printInt(I)V");  
	} 
	if(ty.val == Types.Character && ki.val == Kinds.ScalarParam)
	{
	gen("invokestatic CSXLib/printChar(C)V");  
	}
	if(ty.val == Types.Character && ki.val == Kinds.ArrayParam)
	{
	gen("invokestatic CSXLib/printString(Ljava/lang/String;)V");  
	}
	if(ty.val == Types.Boolean)
	gen("invokestatic CSXLib/printBool(Z)V");
	if(moreDisplays == null)
	{
	return;
	}
	else
	{

	moreDisplays.cg();
	}
		} //cg
				    }



class CallNode extends StmtNode {


CallNode ( identNode m, ArgsNode a,int line,int col) {
super(line,col);
methodName = m;
args = a;
 }

List<ArgPairs> call_params;
private final identNode methodName;
private final ArgsNode  args;

void Unparse(int ident){
System.out.println("\n");
System.out.print(linenum + ":");
int i=ident;

while(i !=0) 
{

System.out.print(" ");
i= i-1;

}

methodName.Unparse(0);
System.out.print("(");
if(args != null){
args.Unparse(0);
}
System.out.print(")");

                      }

void CheckTypes(){

SymbolInfo id;
Kinds k;
Types t;
id = (SymbolInfo)st.globalLookup(methodName.idname);

if(id == null)
{

System.out.println(error() + "The method name is not declared");
typeErrors++;
return;
} // func_id not found

else {

k = id.kind;
t = id.type;
if(t.val == Types.Error){
return;

			} // if func_id is error
if(k.val != Kinds.Function){
System.out.println(error() + "There is a no function with"+
					"the associated call");
typeErrors++;
return;
			} // if func_id is not a function
else {
if(id.pair == null){

if(args instanceof nullArgsNode){

return;
				}
else 		{

System.out.println(error()+"The declaration and the call"+
			"have unequal number of arguments");
typeErrors++;
return;
		}
		}

List<ArgPairs> decl_params = id.pair;
call_params = args.CheckTypes();
ListIterator lt = decl_params.listIterator();
ListIterator ct = call_params.listIterator();
while((lt.hasNext()) &&(ct.hasNext())){

ArgPairs actual = (ArgPairs)lt.next();
ArgPairs call   = (ArgPairs)ct.next();
Types ta = actual.getType();
Types tc = call.getType();
Kinds ka = actual.getKind();
Kinds kc = call.getKind();
if(ta.val != tc.val){
System.out.println(error()+"The parameters should be of same type");
typeErrors++;
return;

		   } //type mismatch between called and declared arguments

if(ka.val == Kinds.ArrayParam){
if(kc.val != Kinds.ArrayParam){
System.out.println(error()+"an array param is expected"+ 
			"but isn't provided in the call");
typeErrors++;
return;
 
				} 

}//arrayparamexpected

else if(ka.val == Kinds.ScalarParam){

if(kc.val == Kinds.ArrayParam){ 
				
System.out.println(error() +"a scalar param is expected but"+
		"an arrayparam is provided in the call");
typeErrors++;
return;
}

} //scalar param expected

	}//while
		}

		}
		}

void cg()	{
	String returnType="";
	String parameters="";
	SymbolInfo info;
	args.cg();
	info = (SymbolInfo)st.globalLookup(methodName.idname);
	if(info == null){
	info=	(SymbolInfo)st.localLookup(methodName.idname);
			}
	returnType=typeGetter(info.type.val);
	if(info.kind.val ==6){
	returnType ="["+returnType;
				}
	if(call_params != null)
	{
	for(ArgPairs pair :call_params)
	{
	Types ty  = pair.getType();
	Kinds k   = pair.getKind();
	if(k.val == Kinds.ArrayParam)
	parameters=parameters+"[";
	switch(ty.val)		{
	case 0:
	parameters = parameters+"C";
	break;
	case 1:
	parameters= parameters+"I";
	break;
	case 2:
	parameters =parameters+"Z";
	break;
	default:
	break;
				}
	
	}
		
	gen("invokestatic",className+"/"+methodName.idname+
				"("+parameters+")"+returnType);
	}			
	        }      
		}              
                


class ReturnNode extends StmtNode {


ReturnNode(ExprNode e,int line,int col)
{
super(line,col);
returnVal = e;
}

private ExprNode returnVal;
void Unparse(int indent)
{
int  i = indent;
System.out.print("\n");
System.out.print(linenum + ":");
while(i !=0)
{
System.out.print(" ");
i = i-1;

}
if(returnVal instanceof nullExprNode)
{

System.out.print("return");
System.out.print(";");
}
else
{

System.out.print("return");
System.out.print(" ");
returnVal.Unparse(0);
System.out.print(";");

}
}
                  
void CheckTypes(){

SymbolInfo id = (SymbolInfo)st.localLookup("return_type");

if(returnVal instanceof nullExprNode){
if((id.type.val)!= Types.Void){

System.out.println(error()+"return statements without expressions"+
	"can only appear in functions without a return value defined");
typeErrors++;
return;
}

}

ArgPairs ap = returnVal.CheckTypes();
Types t =  ap.getType();
if(t.val == Types.Error){

return;
			}
if((id.type.val) != t.val){

System.out.println(error() + "the return value doesn't match"+
			" the functions return value");
typeErrors++;
return;
}

                }//checktypes

void cg()	{
	if(returnVal instanceof nullExprNode)
	gen("return");
	else	{
	returnVal.cg();
		}
	gen("ireturn");   			
		}//cg

		}
class BlockNode extends StmtNode {

BlockNode(List<DeclNode> d,List<StmtNode> s,int line,int col) {
                    super(line,col);
                    decls = d;
		    stmts = s;
			    }

protected final List<DeclNode> decls;
private final List<StmtNode> stmts;

void Unparse(int indent)
{
int i = indent;
System.out.print("\n");
System.out.print(linenum + ":");
while(i !=0)
{

System.out.print(" ");
i = i-1;
}
ListIterator iv = decls.listIterator();
ListIterator  is =  stmts.listIterator();                 
                                     
System.out.print("{");

Object vardecl = iv.next();
while(iv.hasNext())
{
if( vardecl instanceof nullDeclNode)
{
break;

}

 ((VarDeclNode)vardecl).Unparse(indent);
}
while(is.hasNext())
{


 ((StmtNode)is.next()).Unparse(indent);

}
i = indent;
System.out.print("\n");
System.out.print(linenum + ":");
while(i !=0)
{

System.out.print(" ");
i = i-1;
}


System.out.println("}");


}

void CheckTypes(){
ListIterator di = decls.listIterator();
ListIterator si = stmts.listIterator();
while(di.hasNext()){

 ((DeclNode)di.next()).CheckTypes();
}
while(si.hasNext()){

((StmtNode)si.next()).CheckTypes();
	}
}

void cg()	{

for (DeclNode d : decls)
	{
d.cg();   // May need to be changed
	}

for(StmtNode s : stmts)
	{
s.cg();
	}
		}
             			 }

class ArgsNode extends ASTNode {
ArgsNode()	{
	argVal = new nullExprNode();
	moreArgs = NULL;
		}

ArgsNode(ExprNode e, ArgsNode a) {


argVal = e;
moreArgs = a;
 }


protected final ExprNode argVal;
protected final ArgsNode moreArgs;
protected static final nullArgsNode NULL = new nullArgsNode();

void Unparse(int ident){

argVal.Unparse(0);

if(moreArgs == null)
{
return;
}
else
{
moreArgs.Unparse(0);

}
			}


List<ArgPairs> CheckTypes() {

  List<ArgPairs> tempa = new ArrayList<ArgPairs>();
  ArgPairs ap = argVal.CheckTypes();   
 tempa.add(ap);
  if(moreArgs == null){
  return tempa;			/* args node typechecker creates a list of argpairs for its arguments,the parent nodes use the list in        typechecking  */
		     }
 else {

  tempa.addAll(moreArgs.CheckTypes());
 
	}
		return tempa;		}//checkTypes

void cg()	{
	argVal.cg();
	if(moreArgs == null)
	return;
	else
	moreArgs.cg();
		}
				}

class nullArgsNode extends ArgsNode	{
nullArgsNode(){ };			}


class StrLitNode extends ExprNode {
StrLitNode ( String s,int line,int col) {
super(line,col);
strval = s;

}

protected final String strval;

void Unparse(int indent){

 int i = indent;
 while(i!=0)
  {

   System.out.print(" ");
  i = i-1;
	}

  System.out.print(" " + strval);
  

			}

ArgPairs CheckTypes(){

ArgPairs temp;
type = new Types(Types.Character);
kind = new Kinds(Kinds.ArrayParam);
temp = new ArgPairs(type,kind);
return temp;
			}

void cg()	{

	gen("ldc"+" "+strval);
		}
				}


class BinaryOpNode extends ExprNode {

BinaryOpNode ( ExprNode l, ExprNode r, int o,int line,int col) {
super(line,col);
leftOperand = l;
rightOperand = r;
operatorCode = o;

}
private final ExprNode leftOperand;
private final ExprNode rightOperand;
private final int operatorCode;

void Unparse(int indent)
{

System.out.print("(");
leftOperand.Unparse(indent);

switch(operatorCode)
{
case 1:
System.out.print(" + ");
break;
case 2:
System.out.print(" - ");
break;
case 3:
System.out.print(" * ");
break;
case 4:
System.out.print(" / ");
break;
case 5:
System.out.print(" < ");
break;
case 6:
System.out.print(" > ");              
break;
case 7:
System.out.print(" <= ");
break;
case 8:
System.out.print(" >= ");
break;
case 9:
System.out.print(" == ");
break;
case 10:
System.out.print(" != ");
break;
case 11:
System.out.print("||");
break;
case 12:
System.out.print("&&");
break;
default:
break;
}
rightOperand.Unparse(indent);
System.out.print(")");
}


ArgPairs CheckTypes() {
ArgPairs temp;
ArgPairs f1;
ArgPairs f2;
Types t1;
Kinds k1;
String errorMessage;
switch(operatorCode)
{
case 1:
 f1 = leftOperand.CheckTypes();
 f2 = rightOperand.CheckTypes();
 if((f1.getType()).val > 1 || (f2.getType()).val > 1)
 {
  errorMessage = "the operands for an arithmetic operation"+
		"should be either of type integer or character";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);                      //change t1,k1 to default type and kind of the exprNode
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;
 }
 else {
 t1 = new Types(Types.Integer);
 k1 = new Kinds(Kinds.ScalarParam);
 temp = new ArgPairs(t1,k1);
 return temp;
      }

case 2:
f1 = leftOperand.CheckTypes();
 f2 = rightOperand.CheckTypes();
 if((f1.getType()).val > 1 || (f2.getType()).val >1)
 {
  errorMessage = "the operands for an arithmetic operation" +
		"should be either of type integer or character";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;
 }
 else {
 t1 = new Types(Types.Integer);
 k1 = new Kinds(Kinds.ScalarParam);
 temp = new ArgPairs(t1,k1);
 return temp;
      }

case 3:
f1 = leftOperand.CheckTypes();
 f2 = rightOperand.CheckTypes();
 if((f1.getType()).val > 1 || (f2.getType()).val >1)
 {
  errorMessage = "the operands for an arithmetic operation should"+
			" be either of type integer or character";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;
 }
 else {
 t1 = new Types(Types.Integer);
 k1 = new Kinds(Kinds.ScalarParam);
 temp = new ArgPairs(t1,k1);
 return temp;
      }

case 4:
f1 = leftOperand.CheckTypes();
 f2 = rightOperand.CheckTypes();
 if((f1.getType()).val > 1 || (f2.getType()).val >1)
 {
  errorMessage = "the operands for an arithmetic operation"+ 
		"should be either of type integer or character";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;
 }
 else {
 t1 = new Types(Types.Integer);
 k1 = new Kinds(Kinds.ScalarParam);
 temp = new ArgPairs(t1,k1);
 return temp;
      }

case 5:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val <=2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "the operands for a relational operator can be"+
"either a pair of integer or chars or a pair of boolean values";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}

case 6:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val <=2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "the operands for a relational operator can"+
"be either a pair of integer or chars or a pair of boolean values";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}

case 7:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val <=2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "the operands for a relational operator can"+
" be either a pair of integer or chars or a pair of boolean values";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}

case 8:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val <=2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "the operands for a relational operator can be"+ 
"either a pair of integer or chars or a pair of boolean values";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}

case 9:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val <=2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "the operands for a relational operator can"+
" be either a pair of integer or chars or a pair of boolean values";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}

case 10:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val <=2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "the operands for a relational operator can be either"+ 
"a pair of integer or chars or a pair of boolean values";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}
case 11:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val ==2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "both the operands for a logical operator"+
" should be of type boolean";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}

case 12:
f1 = leftOperand.CheckTypes();
f2 = rightOperand.CheckTypes();
if(((f1.getType()).val == (f2.getType()).val) && ((f1.getType()).val ==2))
{

t1 = new Types(Types.Boolean);
k1 = new Kinds(Kinds.ScalarParam);
temp = new ArgPairs(t1,k1);
return temp;
}
else {
errorMessage = "both the operands for a logical"+
" operator should be of type boolean";
  typeErrors++;
  System.out.println(error() + errorMessage);
  t1 = new Types(Types.Error);
  k1 = new Kinds(Kinds.Other);
  temp = new ArgPairs(t1,k1);
  return temp;

	}


default:
t1 = new Types(Types.Error);
k1 = new Kinds(Kinds.Other);
temp = new ArgPairs(t1,k1);
return temp; 

}
			}// checkTypes


void cg()	{

switch(operatorCode)	{
	case 1:
	leftOperand.cg();
	rightOperand.cg();
	gen("iadd");
	break;
	case 2:
	leftOperand.cg();
	rightOperand.cg();
	gen("isub");
	break;
	case 3:
	leftOperand.cg();
	rightOperand.cg();
	gen("imul");
	break;
	case 4:
	leftOperand.cg();
	rightOperand.cg();
	gen("idiv");
	break;
	// relational operators
	case 5:
	leftOperand.cg();
	gen("i2l");				 
	rightOperand.cg();
	gen("i2l");
	gen("lcmp");
	labelCnt++;
	String labElse ="#"+labelCnt;
	labelCnt++;
	String labExit  = "#"+labelCnt;
	gen("iflt",labElse);
	gen("iconst_0");
	gen("goto",labExit);
	gen(labElse+":");
	gen("iconst_1");
	gen(labExit+":");
	break;

	case 6:
	leftOperand.cg();
	gen("i2l");
	rightOperand.cg();
	gen("i2l");
	gen("lcmp");
	labelCnt++;
	labElse ="#"+labelCnt;
	labelCnt++;
	labExit  = "#"+labelCnt;
	gen("ifgt",labElse);
	gen("iconst_0");
	gen("goto",labExit);
	gen(labElse+":");
	gen("iconst_1");
	gen(labExit+":");
	break;

	case 7:
	leftOperand.cg();
	gen("i2l");
	rightOperand.cg();
	gen("i2l");
	gen("lcmp");
	labelCnt++;
	labElse ="#"+labelCnt;
	labelCnt++;
	labExit  = "#"+labelCnt;
	gen("ifle",labElse);
	gen("iconst_0");
	gen("goto",labExit);
	gen(labElse+":");
	gen("iconst_1");
	gen(labExit+":");
	break;
	
	case 8:
	leftOperand.cg();
	gen("i2l");
	rightOperand.cg();
	gen("i2l");
	gen("lcmp");
	labelCnt++;
	labElse ="#"+labelCnt;
	labelCnt++;
	labExit  = "#"+labelCnt;
	gen("ifge",labElse);
	gen("iconst_0");
	gen("goto",labExit);
	gen(labElse+":");
	gen("iconst_1");
	gen(labExit+":");
	break;
	
	case 9:					
	leftOperand.cg();
	gen("i2l");				 
	rightOperand.cg();
	gen("i2l");
	gen("lcmp");
	labelCnt++;
	labElse ="#"+labelCnt;
	labelCnt++;
        labExit  = "#"+labelCnt;
	gen("ifeq",labElse);
	gen("iconst_0");
	gen("goto",labExit);
	gen(labElse+":");
	gen("iconst_1");
	gen(labExit+":");
	break;

	case 10:
	leftOperand.cg();
	gen("i2l");
	rightOperand.cg();
	gen("i2l");
	gen("lcmp");
	labelCnt++;
        labElse ="#"+labelCnt;
	labelCnt++;
	labExit  = "#"+labelCnt;
	gen("ifne",labElse);
	gen("iconst_0");
	gen("goto",labExit);
	gen(labElse+":");
	gen("iconst_1");
	gen(labExit+":");
	break;
	case 11:                     
	leftOperand.cg();
	rightOperand.cg();
	gen("ior");
	break;
	case 12:                      
	leftOperand.cg();
	rightOperand.cg();
	gen("iand");
	break;
			}


		}//cg
 			            }

class UnaryOpNode extends ExprNode {

UnaryOpNode ( ExprNode op, int oc, int line,int col) {
super(line,col);
operand = op;
operatorCode = oc;
 }

private final ExprNode operand;
private final int operatorCode;

void Unparse(int indent)
{
System.out.print("!");
operand.Unparse(0);
}				   
	
ArgPairs CheckTypes() {

     ArgPairs temp;
     temp = operand.CheckTypes();
      type = temp.getType();
     String errorMessage =  error() + "operator ! can only be"+ 
				"applied to a boolean operand";
      boolean a = typesMustBeEqual(type.val,Types.Boolean,errorMessage);
       if(a){
               typeErrors++;
               type = new Types(Types.Error);
	       kind = new Kinds(Kinds.Other);
				} //if
       else{

 	kind = new Kinds(Kinds.ScalarParam);

		}//else
        temp = new ArgPairs(type,kind);
        return temp;
		  }//CheckTypes		

void cg()	{
	operand.cg();
	gen("ldc 1");
	gen("ixor");			
		}
		}

class CastNode extends ExprNode {

CastNode ( TypeNode t, ExprNode e,int line, int col) {
super(line,col);
resultType = t;
operand = e;
}

private final TypeNode resultType;
private final ExprNode operand;

void Unparse(int ident)
{

resultType.Unparse(0);
System.out.print(" ( ");
operand.Unparse(0);

System.out.print(" ) ");
}
ArgPairs CheckTypes(){

  ArgPairs temp;
  temp = operand.CheckTypes();
  type = temp.getType();
  kind = temp.getKind();
  if(type.val > 2)
 {

 typeErrors++;
 type = new Types(Types.Error);
 System.out.println(error() + "invalid cast attempt");
 }//if

 else
 {
 type = new Types(resultType.type.val);
 }// else 
   
    temp = new ArgPairs(type,kind);
    return temp;
		      }//checkTypes

void cg()	{
	ArgPairs pair = operand.CheckTypes();
	
	int op_type;
 	op_type = (pair.getType()).val;
	operand.cg();
	
	
	switch(op_type)	{
	case 0:
	if(resultType instanceof CharTypeNode)
	{
	/*do nothing*/
	}
	else if(resultType instanceof IntTypeNode)
	{
	/*do nothing*/
	}
	else	{
	//not sure	
	}
	break;
	case 1:
	if(resultType instanceof CharTypeNode)
	{
	gen("i2c");
	}
	else if(resultType instanceof IntTypeNode)
	{
	/*do nothing*/
	}
	else	
	{
	

	}
	break;
	case 2:
	if(resultType instanceof CharTypeNode)
	{
	// do nothing
	}
	else if(resultType instanceof IntTypeNode)
	{
	// do nothing not sure
	}
	else
	{
	// do nothing
	}
	break;
		}
	
			}//cg
				
		}

class FuncCallNode extends ExprNode {

FuncCallNode ( identNode f, ArgsNode a) {

funcName = f;
funcArgs = a;
}

private final identNode funcName;
private final ArgsNode  funcArgs;  
protected List<ArgPairs> call_params; 

ArgPairs CheckTypes() {
	ArgPairs returnVal;
	call_params = funcArgs.CheckTypes();
	SymbolInfo id  = (SymbolInfo)st.globalLookup(funcName.idname);
	if(id == null)	{
	type = new Types(Types.Error);
	kind = new Kinds(Kinds.Other);
	typeErrors++;
	try{
	st.insert(new SymbolInfo(funcName.idname,kind.val,type.val));
	} catch(DuplicateException d){

			} catch(EmptySTException s){


			}
			}
	
	List<ArgPairs> decl_params = id.pair;
	if(funcArgs instanceof nullArgsNode)
			{
	if(decl_params.size()== 0)
	{
	type = id.type;
	kind = id.kind;
	returnVal = new ArgPairs(type,kind);
	return returnVal;
	}
	else	{
	System.out.println(error()+ "The declaration and the call"+
			"have unequal number of arguments");
	typeErrors++;
	type = new Types(Types.Error);
	kind = new Kinds(Kinds.Other);
	returnVal= new ArgPairs(type,kind);
	return returnVal;

		}
			}
	
	else	{
        ListIterator lt = decl_params.listIterator();
        ListIterator ct = call_params.listIterator();
        while((lt.hasNext()) &&(ct.hasNext())){
        ArgPairs actual = (ArgPairs)lt.next();
 	ArgPairs call   = (ArgPairs)ct.next();
	Types ta = actual.getType();
	Types tc = call.getType();
	Kinds ka = actual.getKind();
	Kinds kc = call.getKind();
	if(ta.val != tc.val){
	System.out.println(error()+"The parameters should be of same type");
	typeErrors++;
	type = new Types(Types.Error);
	kind = new Kinds(Kinds.Other);
	returnVal= new ArgPairs(type,kind);
	return returnVal;

		   } //type mismatch between called and declared arguments
	if(ka.val == Kinds.ArrayParam){
	if(kc.val != Kinds.ArrayParam){
	System.out.println(error()+"an array param is expected"+ 
			"but isn't provided in the call");
	typeErrors++;
	type = new Types(Types.Error);
	kind = new Kinds(Kinds.Other);
	returnVal= new ArgPairs(type,kind);
	return returnVal;
 				} 
				}//arrayparamexpected
	else if(ka.val == Kinds.ScalarParam){
	if(kc.val == Kinds.ArrayParam){ 
	System.out.println(error() +"a scalar param is expected but"+
		"an arrayparam is provided in the call");
	typeErrors++;
	type = new Types(Types.Error);
	kind = new Kinds(Kinds.Other);
	returnVal= new ArgPairs(type,kind);
	return returnVal;
			      	}
				} //scalar param expected

	}//while
		}
	type = id.type;
	kind = id.kind;
	returnVal = new ArgPairs(type,kind);
	return returnVal;
	

			}//checkTypes

void cg()	{
	String returnType="";
	String parameters="";
 	SymbolInfo info;
	funcArgs.cg();
	
	info = (SymbolInfo)st.globalLookup(funcName.idname);
	if(info == null){
	info=	(SymbolInfo)st.localLookup(funcName.idname);
			}
	switch(info.type.val){
	case 0:
	returnType = "C";
	break;
	case 1:
	returnType = "I";
	break;
	case 2:
	returnType = "Z";
	break;
	default:
	returnType = " ";
	break;		}
	if(info.kind.val ==6){
	returnType ="["+returnType;
				}
	if(call_params !=null)	{
	for(ArgPairs pair: funcArgs.CheckTypes())
	{
	Types ty  = pair.getType();
	Kinds k   = pair.getKind();
	if(k.val == Kinds.ArrayParam)
	parameters=parameters+"[";
	switch(ty.val)		{
	case 0:
	parameters = parameters+"C";
	break;
	case 1:
	parameters= parameters+"I";
	break;
	case 2:
	parameters =parameters+"Z";
	break;
	default:
	// parameters = parameters+"V";
	break;
				}
	
	}
				}
	
	
	gen("invokestatic",className+"/"+funcName.idname+
					"("+parameters+")"+returnType); 
		} //cg								

                                    }
class identNode extends ExprNode {

 identNode(String id, int line , int col){
           super(line,col);

           idname = id;
 		     }
protected final String idname;

void Unparse(int indent) {

System.out.print(" "+idname);

				}

protected SymbolInfo info;
				}

class NameNode extends ExprNode {
    NameNode ( identNode i , ExprNode e,int line,int col) {
    super(line,col);
    varName = i;
    subscriptVal = e;
    type = new Types(4);               
    kind = new Kinds(2);  
    idinfo = new SymbolInfo(varName.idname,kind,type);
                               							
					 }

    void Unparse(int indent) {

                        varName.Unparse(indent);
			if(subscriptVal instanceof nullExprNode){
				
				/*do nothing*/

						}
			else	{

			System.out.println("[");
			subscriptVal.Unparse(0);
			System.out.println("]");

				}
				}
    
    ArgPairs CheckTypes() {
    ArgPairs temp = subscriptVal.CheckTypes();
    SymbolInfo info;
    boolean ty;
    info = (SymbolInfo)st.localLookup(varName.idname);
    
     	if(info != null)	{
		type = info.type;
       		kind = info.kind;
		idinfo.type.val = info.type.val;
		idinfo.kind.val = info.kind.val;
		idinfo.varIndex = info.varIndex;
		idinfo.local_find = true;
		stack_depth =st.scopes.size();
			}
	else 	{
  
 	info = (SymbolInfo)st.globalLookup(varName.idname);
	if(info != null) 	{
	type = info.type;
	kind = info.kind;
	idinfo.type.val = info.type.val;
	idinfo.kind.val = info.kind.val;
	idinfo.local_find = false;

				}

		}  
    	
	if(!(subscriptVal instanceof nullExprNode))
     	{
    	String em = "no array declared with given id";
     	ty  = kindsMustBeEqual(kind.val,Kinds.ArrayParam,em);
        if(ty)	{

	kind = new Kinds(Kinds.Other);
	type = new Types(Types.Error);
		}
        else {
	if((temp.getType()).val == Types.Integer || (temp.getType()).val ==         Types.Character)									 
			                                                   
		{

	kind = new Kinds(Kinds.ScalarParam);
		}
	else    {

	System.out.println(error()+" An array can be indexed"+
			" by an integer or a character");
	typeErrors++;
	type = new Types(Types.Error);
		}
	  	}
		}
	ArgPairs rv = new ArgPairs(type,kind);
    	return rv;
    		}//checktypes
	
	void cg()	{

	
	
 	if(idinfo.local_find && stack_depth >1)	{

	if(idinfo.kind.val != Kinds.ArrayParam)
	{
	gen("iload",idinfo.varIndex);
	}	
	else	{
	gen("aload",idinfo.varIndex);
	if(!(subscriptVal instanceof nullExprNode))
	{
	subscriptVal.cg();
	switch(idinfo.type.val)
	{
	case 0:
	gen("caload");
	break;
	case 1:
	gen("iaload");
	break;
	case 2:
	gen("baload");
	break;
	default:
	break;
	} //switch
		}
		}
				
				}
	else 	{
	String type = typeGetter(idinfo.type.val);
	if((idinfo.kind.val == Kinds.ScalarParam)||
			(idinfo.kind.val ==  Kinds.Var))
	gen("getstatic",className+"/"+varName.idname,type);
	if(idinfo.kind.val == Kinds.ArrayParam)
	if(subscriptVal instanceof nullExprNode)
	gen("getstatic",className+"/"+varName.idname,"["+type);
	else	{
	gen("getstatic",className+"/"+varName.idname,"["+type);
 	subscriptVal.cg();
	switch(idinfo.type.val)	{
	case 0:
	gen("caload");
	break;
	case 1:
	gen("iaload");
	break;
	case 2:
	gen("baload");
	break;
	default:
	break;
					}
		}
				}			
			}//cg
			
protected final identNode varName;
protected final ExprNode subscriptVal;
protected SymbolInfo idinfo;
protected int stack_depth =1;					
 				}

class IntLitNode extends ExprNode {

IntLitNode( int line, int col,int val) {
         super(line,col);
	 intval = val;
       	 
    
		   }
protected final int intval;

void Unparse(int indent){

 int i = indent;
 while(i!=0)
  {

   System.out.print(" ");
  i = i-1;
	}    // indenting

  System.out.print(" " + intval);
  

	} //Unparse

ArgPairs CheckTypes() {
   ArgPairs temp = new ArgPairs(new Types(1),new Kinds(1));

  return temp;

                  }

void cg()	{

	gen("ldc"+" "+intval);

		}
   
		       }

class CharLitNode extends ExprNode {
CharLitNode( char c,int line,int col) {
super(line,col);
charval = c;
Types type = new Types(0);
Kinds kind = new Kinds(1);
}

private final char charval;
void Unparse(int indent){

  System.out.print(" " + charval);
			}

ArgPairs CheckTypes() {
   ArgPairs temp = new ArgPairs(new Types(0),new Kinds(1));
  return temp;

                  }
void cg()	{

	int charInt = (int)charval;
	gen("ldc",charInt);
		}
				   }

class TrueNode extends ExprNode {

TrueNode(int line ,int col) {
super(line,col);
type = new Types(2);
kind = new Kinds(1);
}
void Unparse(int indent){

  System.out.print(" " + "True");


			}
ArgPairs CheckTypes() {
   ArgPairs temp = new ArgPairs(type,kind);
  return temp;

                  }
void cg()	{
	gen("iconst_1");

		}

				}
class FalseNode extends ExprNode {
FalseNode ( int line, int col) {
super(line, col);
type = new Types(2);
kind = new Kinds(1);
}
void Unparse(int indent) {

System.out.print(" " + "False");
			}

ArgPairs CheckTypes() {
   ArgPairs temp = new ArgPairs(type,kind);
  return temp;

                  }
void cg()	{
	gen("iconst_0");

		}


				 }
class nullNode extends ASTNode {

	nullNode() {
		super();
	}

	boolean isNull() {
		return true;
	}

	void Unparse(int indent) {
		// no action
	}
} 



class parenNode extends ExprNode {


parenNode(int line,int col,ExprNode e)
{
super(line,col);
node = e;
}

void Unparse(int indent)
{

System.out.print("(");
node.Unparse(0);
System.out.print(")");

}

ArgPairs CheckTypes()	{
 return	node.CheckTypes();		
			}
void cg()	{
node.cg();
		}

private final ExprNode node;

}




