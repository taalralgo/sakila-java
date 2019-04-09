package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Customer;

@SuppressWarnings("serial")
public class CustomerImpl extends UnicastRemoteObject implements ICustomer
{

	public CustomerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Customer addCustomer(Customer arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public List<Customer> getCustomerByFirstName(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getCustomerByLastName(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomers() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		List<Customer> films = session.createQuery("SELECT c FROM Customer c").list();
		return films;
	}

	@Override
	public Customer modifierCustomer(Customer arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}

}
