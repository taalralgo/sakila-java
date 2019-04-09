package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Category;


public interface ICategory extends Remote
{
	//Modification
	public Category addCategory(Category category) throws RemoteException;
	public Category modifier(Category category) throws RemoteException;
	//Recuperation
	public Category getCategoryById(int categoryId) throws RemoteException;
	public Category getCategoryByName(String name) throws RemoteException;
	public List<Category> getCategories() throws RemoteException;
}
