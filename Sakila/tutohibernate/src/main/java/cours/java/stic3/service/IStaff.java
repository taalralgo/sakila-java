package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Staff;

public interface IStaff extends Remote 
{
	//Modification
	public Staff addStaff(Staff staff) throws RemoteException;
	public Staff modifierStaff(Staff staff) throws RemoteException;
	//Recuperations
	public Staff getStaffById(int staffId) throws RemoteException;
	public Staff getStaffByUsernameAndPassword(String username, String password) throws RemoteException;
	public List<Staff> getStaffs() throws RemoteException;
	public List<Staff> getStaffByFirstName(String firstName) throws RemoteException;
	public List<Staff> getStaffByLastName(String lastName) throws RemoteException;
}
