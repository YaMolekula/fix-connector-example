package com.paymium.fix;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.RuntimeError;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.UnsupportedMessageType;
import quickfix.field.MDEntryType;
import quickfix.field.MDReqID;
import quickfix.field.MDUpdateType;
import quickfix.field.MarketDepth;
import quickfix.field.MsgType;
import quickfix.field.NoMDEntryTypes;
import quickfix.field.ResetSeqNumFlag;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.field.Username;
import quickfix.fix44.MarketDataRequest;

public class FIXInitiatorApplication implements Application {

	@Override
	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
	IncorrectDataFormat, IncorrectTagValue, RejectLogon  { }

	@Override
	public void fromApp(Message message, SessionID sessionId) throws FieldNotFound,
	IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType  {
		if (MsgType.MARKET_DATA_SNAPSHOT_FULL_REFRESH.equals(message.getHeader().getField(new MsgType()).getValue())) {
			// Handle snapshot here
			System.out.println("Received a full market data snapshot");
		}
		else if (MsgType.MARKET_DATA_INCREMENTAL_REFRESH.equals(message.getHeader().getField(new MsgType()).getValue())) {
			// Handle incremental updates here
			System.out.println("Received an incremental market data update");
		}
		
	}

	@Override
	public void onCreate(SessionID arg0) { }

	@Override
	public void onLogon(SessionID sessionId)  { 
		try {
			sendMarketDataRequest(sessionId);
		} catch (SessionNotFound e) {
			// By definition this should never happen
			System.out.println("Unable to find session <" + sessionId.toString() + ">");
		}
	}

	@Override
	public void onLogout(SessionID arg0)  { }

	@Override
	public void toAdmin(Message message, SessionID sessionId) {
		try {
			if (MsgType.LOGON.equals(message.getHeader().getField(new MsgType()).getValue())) {     
				message.getHeader().setField(new Username("JAVA_TESTS"));
				message.setBoolean(ResetSeqNumFlag.FIELD, true);
			}
		}
		catch(FieldNotFound fnfe) {
			throw new RuntimeError();
		}

	}

	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend  { }
	
	private void sendMarketDataRequest(SessionID sessionId) 
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