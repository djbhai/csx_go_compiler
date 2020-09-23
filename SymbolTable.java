

import java.util.HashMap;
import java.util.ArrayList;
import java.io.PrintStream;
import java.util.Iterator;
public class SymbolTable {
         ArrayList<ArrayList<HashMap<String,Symb>>> scopes;   /*contains all the scopes*/
         ArrayList<HashMap<String,Symb>> a;      // for creating the first scope upon creation of the symbol table object
         HashMap<String,Symb> m;   
         EmptySTException e;
         DuplicateException d;
         
         
 public SymbolTable()
    {
        scopes=new ArrayList<ArrayList<HashMap<String,Symb>>>();
        a= new ArrayList<HashMap<String,Symb>>();
        scopes.add(a);
        EmptySTException e= new EmptySTException();
        DuplicateException d= new DuplicateException();
                
    }
    
    void openScope()
    {
      ArrayList<HashMap<String,Symb>> newbie= new ArrayList<HashMap<String,Symb>>(); /*new scope*/
      scopes.add(newbie);    /*new scope added to the list of scopes*/
        
    }
    void closeScope() throws EmptySTException
    {
        
      e=new EmptySTException();
        if(scopes.isEmpty())
        {
            throw e;
        }
        
        else{
            scopes.remove(scopes.size()-1);
        }
    }
    void insert(Symb s) throws DuplicateException,EmptySTException
    {
        
       Iterator individual_scope;         /*Iterator that walks through individual scope's map objects */
       ArrayList<HashMap<String,Symb>> temp;   /*variable for current scope*/
       HashMap<String,Symb> use_map;                
       
       
                                                    
                                                        /*variable for holding the current symbol object within the
                                                                present scope */
       HashMap<String,Symb> use_map_2;
       use_map_2= new HashMap<String,Symb>();       
       
       String Id_name;                            /*name of the identifier to be inserted*/
       
       Symb test;                                 
       
       Id_name= s.namesake;                       
       
       
     if(scopes.isEmpty())                      // If the scope is empty empty st exception is thrown
     {
         throw e;
     }
     
     temp = scopes.get(scopes.size()-1);    // returns the current scope
     individual_scope = temp.iterator();    // iterator for the current scope
     
     while(individual_scope.hasNext())
     {
         use_map= (HashMap<String,Symb>)individual_scope.next();     
         test=use_map.get(Id_name);
         if(test==null){
             break;                              /*  if there is no object with the name of the identifier
                                                  to be inserted the while loop is exited */
         }
         if(test.namesake.equals(s.namesake))
         {
          throw d;                               /* duplicate exception is thrown if an identifier with
                                                    the same name exists in the current scope
          
           
                                                           */
         
         }
         }
     use_map_2.put(Id_name,s); 
     temp.add(use_map_2);   
                                                         /* If the duplicate exception isn't thrown the  
                                                          given symbol object is added to the current scope      
                                                                                  */
         }
    
     
    Symb localLookup(String n)
    {
        
        if(scopes.isEmpty())
     {

         return null;
     }
        ArrayList<HashMap<String,Symb>> temp;
        HashMap<String,Symb> usemap;
        Symb usesymb;
        temp= scopes.get(scopes.size()-1);      
        Iterator it= temp.iterator();
        while(it.hasNext())
        {

            usemap= (HashMap<String,Symb>)it.next();
             if(usemap.containsKey(n)){                              

                usesymb= usemap.get(n);                     /* If the current scope contains the identifier
                                                               this code returns the symbol object assosiated with it*/
               return usesymb;

            }

        }

                  return null;  

    }
    
    Symb globalLookup(String n)
    {
        
   ArrayList<HashMap<String,Symb>> temp;
   HashMap<String,Symb> usemap;
   Symb usesymb;
   Iterator it;

   int i=1;
   while(scopes.size()-(i) >=0){                     /* begins searching for the identifier from the most
                                                    recently added scope to the earliest scope  */

   temp=scopes.get(scopes.size()-(i));
   it= temp.iterator();
   while(it.hasNext()){
       usemap=(HashMap<String,Symb>)it.next();
       if(usemap.containsKey(n)){
           
           usesymb= usemap.get(n);
           return usesymb;
                
                              }

   }
  i = i+1;
   }
   return null;
   

      
    }
    
    void dump(PrintStream p){
      
        
      String format= scopes.toString();
       
       
       p.format(format);
       p.println();
   
        
        
        
    }
  String to_String()
    {
      return  scopes.toString();
    }
  
    
    }
