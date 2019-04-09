package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Payment;

@SuppressWarnings("serial")
public class PaymentImpl extends UnicastRemoteObject implements IPayment
{

	public PaymentImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Payment addPayment(Payment arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public List<Payment> getPaymentByAmount(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getPaymentByDate(Date arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Payment getPaymentById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getPayments() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Payment> actors = session.createQuery("SELECT p FROM Payment p").list();
		return actors;
	}

	@Override
	public Payment modifierPayment(Payment arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}

}
