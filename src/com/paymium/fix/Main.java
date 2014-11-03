package com.paymium.fix;

import java.io.FileInputStream;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.MessageFactory;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.fix44.Logon;
import quickfix.fix44.MarketDataRequest;


public class Main {

	public static void main(String[] args) 
			throws Exception {
		System.out.println("Prutendafrischen");

		
		
		
		
		SocketInitiator socketInitiator = null;
		String fileName = "/Users/david/code/java/quickfix-test/settings.cfg";
		try {
		    SessionSettings initiatorSettings = new SessionSettings(new FileInputStream(fileName));
		    Application initiatorApplication = new FIXInitiatorApplication();
		    FileStoreFactory fileStoreFactory = new FileStoreFactory(
		            initiatorSettings);
		    ScreenLogFactory fileLogFactory = new ScreenLogFactory(
		            initiatorSettings);
		    MessageFactory messageFactory = new DefaultMessageFactory();
		    socketInitiator = new SocketInitiator(initiatorApplication, fileStoreFactory, initiatorSettings, fileLogFactory, messageFactory);
		    socketInitiator.start();
		    //Message msg = new Message();
		    //msg.setString(1, "Hello this is test Message");


		    SessionID sessionId = (SessionID) socketInitiator.getSessions().get(0);
		    Session.lookupSession(sessionId).logon();
		    initiatorApplication.onLogon(sessionId);
		    //initiatorApplication.toApp(msg, sessionId);
		    
		    
		    Thread.sleep(100000);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		
		
		
		
		
		/*	SocketInitiator socketInitiator = null;
		try {
			SessionSettings initiatorSettings = new SessionSettings("/Users/david/code/java/quickfix-test/settings.cfg");
			Application initiatorApplication = new FIXInitiatorApplication();
			FileStoreFactory fileStoreFactory = new FileStoreFactory(initiatorSettings);
			ScreenLogFactory fileLogFactory = new ScreenLogFactory(initiatorSettings);
			MessageFactory messageFactory = new DefaultMessageFactory();
			socketInitiator = new SocketInitiator(initiatorApplication, fileStoreFactory, initiatorSettings, fileLogFactory, messageFactory);

			System.out.println("************");
			try {
				socketInitiator.start();



	    Thread.sleep(5000);



			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("************");

			SessionID sessionId = socketInitiator.getSessions().get(0);
			Session.lookupSession(sessionId).logon();
			System.out.println("-------");
			System.out.println(sessionId.toString());
			System.out.println(Session.lookupSession(sessionId).toString());
			System.out.println("-------");
			Session.lookupSession(sessionId).logon();


			//Logon logon = new Logon();
			//Session.sendToTarget(logon, sessionId);

			MarketDataRequest marketDataRequest = new MarketDataRequest();
			Session.sendToTarget(marketDataRequest, sessionId);


			  System.in.read();
			  socketInitiator.stop();


		} catch (ConfigError e) {
			System.out.println("whooooooops");
			e.printStackTrace();
		}*/
	}

}
