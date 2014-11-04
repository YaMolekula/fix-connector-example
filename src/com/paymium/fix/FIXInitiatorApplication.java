package com.paymium.fix;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.RuntimeError;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.MsgType;
import quickfix.field.ResetSeqNumFlag;
import quickfix.field.Username;

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
			System.out.println("Place custom handling code here");
		}
		else if (MsgType.MARKET_DATA_INCREMENTAL_REFRESH.equals(message.getHeader().getField(new MsgType()).getValue())) {
			// Handle incremental updates here
			System.out.println("Received an incremental market data update");
			System.out.println("Place custom handling code here");
		}
		
	}

	@Override
	public void onCreate(SessionID arg0) { }

	@Override
	public void onLogon(SessionID arg0)  { }

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

}