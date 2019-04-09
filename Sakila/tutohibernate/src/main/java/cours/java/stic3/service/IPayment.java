package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import cours.java.stic3.model.Payment;

public interface IPayment extends Remote 
{
	//Modification
	public Payment addPayment(Payment filmActeur) throws RemoteException;
	public Payment modifierPayment(Payment payment) throws RemoteException;
	//Recuperation
	public List<Payment> getPayments() throws RemoteException;
	public Payment getPaymentById(int paymentId) throws RemoteException;
	public List<Payment> getPaymentByDate(Date date) throws RemoteException;
	public List<Payment> getPaymentByAmount(int amount) throws RemoteException;
}
