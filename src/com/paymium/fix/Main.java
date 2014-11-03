package com.paymium.fix;

import java.io.FileInputStream;

import quickfix.Application;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.MessageFactory;
import quickfix.ScreenLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;


public class Main {

	public static void main(String[] args) 
			throws Exception {

		System.out.println("Starting FIX initiator, testing session management and, MarketDataRequests");

		SocketInitiator socketInitiator = null;
		String fileName = "/Users/david/code/java/fix-test/settings.cfg";

		SessionSettings initiatorSettings = new SessionSettings(new FileInputStream(fileName));
		Application initiatorApplication = new FIXInitiatorApplication();
		FileStoreFactory fileStoreFactory = new FileStoreFactory(initiatorSettings);

		// Log to the Java console
		ScreenLogFactory fileLogFactory = new ScreenLogFactory(initiatorSettings);
		MessageFactory messageFactory = new DefaultMessageFactory();

		socketInitiator = new SocketInitiator(initiatorApplication, fileStoreFactory, initiatorSettings, fileLogFactory, messageFactory);
		socketInitiator.start();

		// At this point we're logged-on, time to send a market data request
		
		Thread.sleep(100000);

		System.out.println("Exited FIX initiator");
	}

}
