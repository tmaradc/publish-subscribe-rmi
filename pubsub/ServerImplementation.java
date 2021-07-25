package pubsub;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ServerImplementation implements Server{
    HashMap<Integer, Cell> articles = new HashMap<Integer, Cell>();
    HashMap<Integer, HashSet<Client>> subsClients = new HashMap<Integer, HashSet<Client>>();

    public void subscribe(Client c, Integer articleKey){
        //Inscreve o cliente no artigo
        HashSet<Client> hs = subsClients.get(articleKey);
        if(hs == null) hs = new HashSet<Client>();
        hs.add(c);
        subsClients.put(articleKey, hs);

        //Verifica se já existe publicação para o artigo e envia para o cliente
        Cell article = articles.get(articleKey);
        if(article!=null){
            try{
                Registry registry = LocateRegistry.getRegistry();
                Client stub = (Client) registry.lookup(c.getName());
                stub.receive(article);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void publish(Integer articleKey, Cell article){
        //Cadastra a publicação
        articles.put(articleKey, article);

        //Envia o artigo para os clientes inscritos
        HashSet<Client> subs = subsClients.get(articleKey);

        if(subs!=null){
            for(Client c : subs){
                try{
                    Registry registry = LocateRegistry.getRegistry();
                    Client stub = (Client) registry.lookup(c.getName());
                    stub.receive(article);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]){
        try{
            ServerImplementation obj = new ServerImplementation();
            Server stub = (Server)UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();

            registry.bind("PubSub", stub);

            System.err.println("Server ready.");
        } catch(Exception e){
            System.err.println("Server exception: " + e.toString());
        }
    }

}