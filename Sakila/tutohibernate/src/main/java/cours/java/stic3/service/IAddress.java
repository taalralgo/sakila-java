package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Address;

public interface IAddress extends Remote 
{
	//Modification
	public Address addAdresse(Address adresse) throws RemoteException;
	public Address ModifierAddress(Address address) throws RemoteException;
	//Recuperation
	public Address getAddressById(int addressId) throws RemoteException;
	public List<Address> getAddress() throws RemoteException;
	public List<Address> getAddressByNom(String Nom) throws RemoteException;
	public List<Address> getAddressByDistrict(String district) throws RemoteException;
}
