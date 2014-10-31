package com.paymium.fix;

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


public class Main {

	public static void main(String[] args) 
			throws Exception {
		System.out.println("Prutendafrischen");

		SocketInitiator socketInitiator = null;
		try {
			SessionSettings initiatorSettings = new SessionSettings("/Users/david/code/java/fix-test/settings.cfg");
			Application initiatorApplication = new FIXInitiatorApplication();
			FileStoreFactory fileStoreFactory = new FileStoreFactory(initiatorSettings);
			ScreenLogFactory fileLogFactory = new ScreenLogFactory(initiatorSettings);
			MessageFactory messageFactory = new DefaultMessageFactory();
			socketInitiator = new SocketInitiator(initiatorApplication, fileStoreFactory, initiatorSettings, fileLogFactory, messageFactory);
			
			System.out.println("************");
			try {
			socketInitiator.start();
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
			
			//MarketDataRequest marketDataRequest = new MarketDataRequest();
			//Session.sendToTarget(marketDataRequest, sessionId);
			
			
			  System.in.read();
			  socketInitiator.stop();
			
			
		} catch (ConfigError e) {
			System.out.println("whooooooops");
			e.printStackTrace();
		}
	}

}
