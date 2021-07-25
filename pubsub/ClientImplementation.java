package pubsub;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientImplementation implements Serializable, Client{

    private static final long serialVersionUID = 7526472295622776147L;
    private String serverName;

    public void receive(Cell article){
        System.err.println("Value of article: " + article.get());
    }

    public String getName(){
        return this.serverName;
    }

    public void setName(String name){
        this.serverName = name;
    }

    public static void main(String args[]){
        
        String name = "";
        int sub_key = 0;
        int pub_key = 0; 
        int article = 1;

        if (args.length == 4){
            name = args[0];
            sub_key = Integer.parseInt(args[1]);
            pub_key = Integer.parseInt(args[2]);
            article = Integer.parseInt(args[3]);
        }
        else if (args.length == 3){
            name = args[0];
            sub_key = Integer.parseInt(args[1]);
            pub_key = Integer.parseInt(args[2]);
        }
        else{
            System.err.println ("Args: [user] [sub_key] [pub_key] [article]");
            System.exit(1);
        }

        try{
            Client c = new ClientImplementation();
            c.setName(name);

            Client stubC = (Client)UnicastRemoteObject.exportObject(c, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(name, stubC);

            registry = LocateRegistry.getRegistry("");
            Server stub = (Server) registry.lookup("PubSub");
            
            System.err.println("Subscribind to: " + sub_key + "\nPublishing: " + pub_key + "\n");

            if (sub_key != 0){
                stub.subscribe(c, sub_key);
            }
           
            if (pub_key != 0){
                Cell o = new Cell();
                o.set(article);
                stub.publish(pub_key, o);  
            }

        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}