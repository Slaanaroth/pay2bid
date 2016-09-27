package com.alma.pay2bid.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E122371M on 27/09/16.
 */
public interface IClient extends Remote {
    void soumettre() throws RemoteException;
    void objet_vendu(IClient acheteur) throws RemoteException;
    void nouveau_prix(int prix) throws RemoteException;
}
