package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Staff;

@SuppressWarnings("serial")
public class StaffImpl extends UnicastRemoteObject implements IStaff
{

	public StaffImpl() throws RemoteException {
		super();
	}

	@Override
	public Staff addStaff(Staff arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.save(arg0);
		session.getTransaction().commit();
		return arg0;
	}

	@Override
	public List<Staff> getStaffByFirstName(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaffById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Staff> getStaffByLastName(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaffByUsernameAndPassword(String arg0, String arg1) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> getStaffs() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
    	List<Staff> category = session.createQuery("SELECT s FROM Staff s").list();
		return category;
	}

	@Override
	public Staff modifierStaff(Staff arg0) throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		session.beginTransaction();
		session.update(arg0);
		session.getTransaction().commit();
		return arg0;
	}
}
