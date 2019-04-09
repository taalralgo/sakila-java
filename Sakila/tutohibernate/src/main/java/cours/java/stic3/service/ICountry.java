package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Country;

public interface ICountry extends Remote 
{
	//Modification
	public int ModifierCountry(int CountryId, Country country) throws RemoteException;
	//Recuperation
	public Country getCountryById(int countryId) throws RemoteException;
	public Country getCountryByName(String name) throws RemoteException;
	public List<Country> getCountries() throws RemoteException;
}
