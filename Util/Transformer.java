package Util;

public class Transformer {
    // This Block is needed for communication of The Block class to The Hash class, 
    //else due to the check method it will recur and your computer will die
    protected final String data,date;
    public Transformer(String data,String date){
        this.data = data;
        this.date = date;
    }
}
