package com.paymium.fix;

import java.io.FileInputStream;

import quickfix.Application;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.MessageFactory;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.field.Currency;
import quickfix.field.MDEntryType;
import quickfix.field.MDReqID;
import quickfix.field.MDUpdateType;
import quickfix.field.MarketDepth;
import quickfix.field.NoMDEntryTypes;
import quickfix.field.NoRelatedSym;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.fix44.MarketDataRequest;


public class Main {

	public static void main(String[] args) 
			throws Exception {

		System.out.println("Starting FIX initiator, testing session management and, MarketDataRequests");

		SocketInitiator socketInitiator = null;
		String fileName = "./settings.cfg";

		SessionSettings initiatorSettings = new SessionSettings(new FileInputStream(fileName));
		Application initiatorApplication = new FIXInitiatorApplication();
		FileStoreFactory fileStoreFactory = new FileStoreFactory(initiatorSettings);

		// Log to the Java console
		ScreenLogFactory fileLogFactory = new ScreenLogFactory(initiatorSettings);
		MessageFactory messageFactory = new DefaultMessageFactory();

		socketInitiator = new SocketInitiator(initiatorApplication, fileStoreFactory, initiatorSettings, fileLogFactory, messageFactory);
		socketInitiator.start();

		SessionID sessionId = socketInitiator.getSessions().get(0);

		// Give 30s to the engine for it to properly connect
		Thread.sleep(30000);
		
		// At this point we're logged-on, time to send a market data request
		sendMarketDataRequest(sessionId);

		Thread.sleep(100000);

		System.out.println("Exited FIX initiator");
	}

	private static void sendMarketDataRequest(SessionID sessionId) 
			throws SessionNotFound {
		
		// We generate a new request ID
		MDReqID reqId = new MDReqID("MDRQ-" + String.valueOf(System.currentTimeMillis()));

		// We want EUR/XBT data
		String currencyPair = "EUR/XBT";

		// We want to get a snapshot and also subscribe to the market depth updates
		SubscriptionRequestType subscriptionType = new SubscriptionRequestType(SubscriptionRequestType.SNAPSHOT_PLUS_UPDATES);

		// We want the full book here, not just the top
		MarketDepth depthType = new MarketDepth(1);

		// We'll want only incremental refreshes when new data is available
		MDUpdateType updateType = new MDUpdateType(MDUpdateType.INCREMENTAL_REFRESH);

		MarketDataRequest mdr = new MarketDataRequest(reqId, subscriptionType, depthType);

		mdr.setField(updateType);

		MarketDataRequest.NoRelatedSym instruments = new MarketDataRequest.NoRelatedSym();
		instruments.set(new Symbol(currencyPair));
		mdr.addGroup(instruments);

		// Specify that we'll want the bids and asks
		mdr.setField(new NoMDEntryTypes(2));	
		MarketDataRequest.NoMDEntryTypes group = new MarketDataRequest.NoMDEntryTypes();
		group.set(new MDEntryType(MDEntryType.BID));
		group.set(new MDEntryType(MDEntryType.OFFER));
		mdr.addGroup(group);

		Session.sendToTarget(mdr, sessionId);
	}


}
