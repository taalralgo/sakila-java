package cours.java.stic3.samaneserver;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import cours.java.stic3.service.ActorImpl;
import cours.java.stic3.service.AdressImpl;
import cours.java.stic3.service.CategoryImpl;
import cours.java.stic3.service.CustomerImpl;
import cours.java.stic3.service.FilmActorImpl;
import cours.java.stic3.service.FilmCategoryImpl;
import cours.java.stic3.service.FilmImpl;
import cours.java.stic3.service.InventoryImpl;
import cours.java.stic3.service.LanguageImpl;
import cours.java.stic3.service.LocationImpl;
import cours.java.stic3.service.PaymentImpl;
import cours.java.stic3.service.StaffImpl;
import cours.java.stic3.service.StoreImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws RemoteException, AlreadyBoundException
    {
    	System.setSecurityManager(new SecurityManager());
    	Registry registry = LocateRegistry.createRegistry(1002);
    	
    	ActorImpl iuser = new ActorImpl();    
    	registry.rebind("actorDistant", iuser);
    	
    	FilmImpl ifilm = new FilmImpl();    
    	registry.rebind("filmDistant", ifilm);
    	
    	CategoryImpl icategory = new CategoryImpl();
    	registry.rebind("categoryDistant", icategory);
    	
    	LanguageImpl ilanguage = new LanguageImpl();
    	registry.rebind("languageDistant", ilanguage);
    	
    	FilmActorImpl ifilmActor = new FilmActorImpl();
    	registry.rebind("filmActorDistant", ifilmActor);
    	
    	FilmCategoryImpl ifilmCatego = new FilmCategoryImpl();
    	registry.rebind("filmCategoDistant", ifilmCatego);
    	
    	StoreImpl istore = new StoreImpl();
    	registry.rebind("storeDistant", istore);
    	
    	AdressImpl iadresse = new AdressImpl();
    	registry.rebind("adresseDistant", iadresse);
    	
    	StaffImpl istaff = new StaffImpl();
    	registry.rebind("staffDistant", istaff);
    	
    	CustomerImpl icustomer = new CustomerImpl();
    	registry.rebind("customerDistant", icustomer);
    	
    	InventoryImpl iinventory = new InventoryImpl();
    	registry.rebind("inventoryDistant", iinventory);
    	
    	LocationImpl irental = new LocationImpl();
    	registry.rebind("rentalDistant", irental);
    	
    	PaymentImpl ipayment = new PaymentImpl();
    	registry.rebind("PaymentDistant", ipayment);
    	
    	System.out.println("Serveur lance sur le port 1002");
    }
}
