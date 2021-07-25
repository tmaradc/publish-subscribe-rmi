package pubsub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void subscribe(Client c, Integer articleKey) throws RemoteException;
    void publish(Integer articleKey, Cell article) throws RemoteException;
}