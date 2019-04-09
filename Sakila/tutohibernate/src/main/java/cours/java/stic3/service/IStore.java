package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Store;

public interface IStore extends Remote 
{
	//Modification
	public Store addStore(Store store) throws RemoteException;
	public Store modifierStore(Store store) throws RemoteException;
	//Recuperation
	public Store getStoreById(int storeId) throws RemoteException;
	public List<Store> getStores() throws RemoteException;
}
