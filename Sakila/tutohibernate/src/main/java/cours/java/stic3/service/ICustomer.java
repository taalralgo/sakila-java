package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Customer;


public interface ICustomer extends Remote 
{
	//Modification
	public Customer addCustomer(Customer adresse) throws RemoteException;
	public Customer modifierCustomer(Customer customer) throws RemoteException;
	//Recuperation
	public Customer getCustomerById(int customerId) throws RemoteException;
	public List<Customer> getCustomers() throws RemoteException;
	public List<Customer> getCustomerByFirstName(String firstName) throws RemoteException;
	public List<Customer> getCustomerByLastName(String lastName) throws RemoteException;
	
}
