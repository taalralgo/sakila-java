package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.City;

public interface ICity extends Remote 
{
	//Modification
	public City addCity(City city) throws RemoteException;
	public City modifierCity(City city) throws RemoteException;
	//Recuperation
	public City getCityById(int cityId) throws RemoteException;
	public City getCityByName(String name) throws RemoteException;
	public List<City> getCities() throws RemoteException;
}
