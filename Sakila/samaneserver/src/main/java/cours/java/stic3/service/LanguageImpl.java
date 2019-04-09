package cours.java.stic3.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;

import cours.java.stic3.model.Language;

@SuppressWarnings("serial")
public class LanguageImpl extends UnicastRemoteObject implements ILanguage
{

	public LanguageImpl() throws RemoteException {
		super();
	}

	@Override
	public Language getLanguageById(int arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language getLanguageByNom(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Language> getLanguages() throws RemoteException {
		Session session = Utilitaire.buildSessionFactory().openSession();
		List<Language> language = session.createQuery("SELECT l FROM Language l").list();
		return language;
	}

	@Override
	public Language modifierLanguage(Language arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
