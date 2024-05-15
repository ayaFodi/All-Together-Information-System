package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static il.cshaifasweng.OCSFMediatorExample.entities.EmergencyType.*;

public class SimpleServer extends AbstractServer {
	private static Session session;
	private static SessionFactory sessionFactory = getSessionFactory();
	public SimpleServer(int port) {
		super(port);
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			generate();

			session.getTransaction().commit();

		} catch (Exception var5) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			var5.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private static void generate() throws Exception {

		Manager manager1 = new Manager(500, "mona", "wasef", "syria", "demashq500", "3rabt.aldrama", "100200300");
		session.save(manager1);
		session.flush();

		Manager manager2 = new Manager(600, "soaad", "hosne", "eygpt", "eskandarya123", "soso.hosne", "400500600");
		session.save(manager2);
		session.flush();

		User user1 = new User(100, "amal", "arafah", "syria", "demashq123",manager1);
		session.save(user1);
		session.flush();


		User user2 = new User(200, "karees", "bashar", "syria", "halab456",manager1);
		session.save(user2);
		session.flush();

		User user3 = new User(3000, "solaf", "fwakherjy", "syria", "homos789",manager1);
		session.save(user3);
		session.flush();

		RegisteredUser registeredUser1 = new RegisteredUser(7000, "norman", "asaad", "syria", "qamshly999",manager1, "nono.asaad", "700800900");
		session.save(registeredUser1);
		session.flush();


		manager1.addToCommunityMemList(user1);
		manager1.addToCommunityMemList(user2);
		manager1.addToCommunityMemList(user3);
		manager1.addToCommunityMemList(registeredUser1);
		session.save(manager1);
		session.flush();


		User user4 = new User(4000, "hend", "sabre", "eygpt", "qahera111",manager2);
		session.save(user4);
		session.flush();

		manager2.addToCommunityMemList(user4);
		session.save(manager2);
		session.flush();

	}

		private static SessionFactory getSessionFactory() throws
			HibernateException {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Emergency.class);
		configuration.addAnnotatedClass(RegisteredUser.class);
		configuration.addAnnotatedClass(Manager.class);
		configuration.addAnnotatedClass(EmergencyDisplay.class);

		ServiceRegistry serviceRegistry = new
				StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		Message message = (Message) msg;

		if(message.getMessage().startsWith("go to home page"))
		{
			authentication(message,client);
		}
		else if(message.getMessage().startsWith("emergency case"))
		{
			reportEmergency(message,client);
		}
		else if (message.getMessage().startsWith("show community list"))
		{
			showCmmunityList(message,client);
		}
		else if(message.getMessage().startsWith("show emergency list"))
		{
			showEmergencyList(message,client);
		}
	}

	private void authentication(Message message, ConnectionToClient client)
	{
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			String username = message.getUserName();
			String providedPassword = message.getPassword();

			RegisteredUser registeredUser = (RegisteredUser) session.createQuery("FROM RegisteredUser u WHERE u.userName = :username")
					.setParameter("username", username)
					.uniqueResult();
			if (registeredUser == null) {
				// The user does not exist
				// Handle the case of username not found
				System.out.println("Username not found.");
				client.sendToClient(new Warning("Warning: Username not found."));
			} else if (!registeredUser.checkPassword(providedPassword)) {
				// The password does not match
				// Handle the case of incorrect password
				System.out.println("Password does not match.");
				client.sendToClient(new Warning("Warning: Incorrect password."));
			} else {
				// Authentication is successful
				System.out.println("Authentication successful.");
				Integer userId = session.createQuery("SELECT u.userId FROM RegisteredUser u WHERE u.userName = :username", Integer.class)
						.setParameter("username", username)
						.uniqueResult();
				User user = (User)registeredUser;
				UserDTO userDTO = new UserDTO(user);

				client.sendToClient(Message.newLogInMessagetoClient("Authentication successful"
						,userId,registeredUser.getIsManager(),registeredUser.getUserName(),userDTO));

			}
			session.getTransaction().commit();

		} catch (Exception e) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}


private void reportEmergency(Message message, ConnectionToClient client)
{
	Session session = null;
	String locatin = message.getLocation();
	try {
		session = sessionFactory.openSession();
		session.beginTransaction();

		User user = (User) session.createQuery("From User u WHERE u.location = :locatin").setParameter("locatin",locatin).uniqueResult();

		Emergency emergency = new Emergency(medicalDanger,user);
		session.save(emergency);
		session.flush();

		EmergencyDisplay emergencyDisplay = new EmergencyDisplay(user,emergency);
		session.save(emergencyDisplay);
		session.flush();

		user.addToEmergencyList(emergency);
		session.save(user);
		session.flush();

//		User user2 = (User) session.createQuery("From User u WHERE u.location = :locatin").setParameter("locatin","qahera111").uniqueResult();
//		Emergency emergency2 = new Emergency(threat,user2);
//		session.save(emergency2);
//		session.flush();
//
//		EmergencyDisplay emergencyDisplay2 =new EmergencyDisplay(user2,emergency2);
//		session.save(emergencyDisplay2);
//		session.flush();
//
//		user2.addToEmergencyList(emergency2);
//		session.save(user2);
//		session.flush();
//
//		session.getTransaction().commit();

		client.sendToClient(Message.newEmergencyMessage("Emergency case received",message.getLocation()));
	}catch (Exception e) {
		if (session != null && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
		e.printStackTrace();
	} finally {
		if (session != null) {
			session.close();
		}
	}
}

private void showCmmunityList(Message message,ConnectionToClient client)
{
	try {
		session = sessionFactory.openSession();
		session.beginTransaction();

		Manager manager = session.get(Manager.class, message.getClientId());

		List<User> communityMemList=manager.getCommunityMemList();
		List<UserDTO> communityMemListDTO= new ArrayList<>();
		communityMemListDTO=communityMemList.stream().map(UserDTO::new).collect(Collectors.toList());
		client.sendToClient(Message.newUserListMessage("community listPage", communityMemListDTO));

		session.getTransaction().commit();
	} catch (Exception e) {
		if (session != null && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
		e.printStackTrace();
	}
}
private void showEmergencyList(Message message,ConnectionToClient client)
{
	try {
		session = sessionFactory.openSession();
		session.beginTransaction();
		//	List<EmergencyDisplay> emergencyList = session.createQuery("FROM EmergencyDisplay", EmergencyDisplay.class).getResultList();

		// HQL query to fetch emergencies within a specific date range
		List<EmergencyDisplay> emergencyList;
		String newMessage;
		if(message.getMessage().startsWith("show emergency list of my community"))
		{
			String community = session.createQuery("SELECT u.community FROM Manager u WHERE u.userId = :userId", String.class)
					.setParameter("userId", message.getClientId())
					.uniqueResult();


			emergencyList = session.createQuery(
							"FROM EmergencyDisplay e WHERE e.emergencyDateTime BETWEEN :fromDate AND :toDate AND e.community = :community", EmergencyDisplay.class)
					.setParameter("fromDate", message.getFromDateTime())
					.setParameter("toDate", message.getToDateTime())
					.setParameter("community",community)
					.getResultList();
			newMessage="emergency list of your community";
		}
		else
		{
			emergencyList = session.createQuery(
							"FROM EmergencyDisplay e WHERE e.emergencyDateTime BETWEEN :fromDate AND :toDate", EmergencyDisplay.class)
					.setParameter("fromDate", message.getFromDateTime())
					.setParameter("toDate", message.getToDateTime())
					.getResultList();
			newMessage="emergency list of all communitys";
		}
		session.getTransaction().commit();
		client.sendToClient(Message.newEmergencyListMessage(newMessage,emergencyList));

		session.getTransaction().commit();
	} catch (Exception e) {
		if (session != null && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
		e.printStackTrace();
	}
}

}


