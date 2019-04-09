package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Inventory;

public interface IIventory extends Remote 
{
	//Modification
	public Inventory addInventory(Inventory filmActeur) throws RemoteException;
	public Inventory modifierInventory(Inventory inventory) throws RemoteException;
	//Recuperation
	public List<Inventory> getInventories() throws RemoteException;
	public List<Inventory> getInventoryById(int inventoryId) throws RemoteException;
	public List<Inventory> getInventoryByStore(int storeId) throws RemoteException;
}
