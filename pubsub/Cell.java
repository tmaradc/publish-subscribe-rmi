package pubsub;

import java.io.Serializable;

public class Cell implements Serializable{

    private static final long serialVersionUID = 7526442295522776847L;
    
    private int content = 0;

    public void set(int v){
        content = v;
    }

    public int get(){
        return content;
    }
}