package cours.java.stic3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cours.java.stic3.model.Language;

public interface ILanguage extends Remote 
{
	//Modification
	public Language modifierLanguage(Language language) throws RemoteException;
	//Recuperation
	public Language getLanguageById(int languageId) throws RemoteException;
	public Language getLanguageByNom(String nom) throws RemoteException;
	public List<Language> getLanguages() throws RemoteException;
}
