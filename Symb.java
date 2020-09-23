
import java.util.*;

public class Symb {
    String namesake;               /*Name of the identifier*/
    public Symb(String s){
        
        namesake=s;
    }
    
    String name(){
       
       return namesake;
   }
   
   private String toStrings()
   {
       
    return  this.toString();
   }
}
