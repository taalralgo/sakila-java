package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISpecialAdd extends Remote
{
	public int add(Object object) throws RemoteException;
	public int modifier(int objectId, Object object) throws RemoteException;
	public List<Object> getAll() throws RemoteException;
}
