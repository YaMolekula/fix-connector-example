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
	IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub

	}

	@Override
	public void fromApp(Message arg0, SessionID arg1) throws FieldNotFound,
	IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(SessionID arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLogon(SessionID arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLogout(SessionID arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toAdmin(Message message, SessionID arg1) {
		try {
			if (MsgType.LOGON.equals(message.getHeader().getField(new MsgType()).getValue())) {     
				message.getHeader().setField(new Username("JAVA_TESTS"));
				message.setBoolean(ResetSeqNumFlag.FIELD, true);
			}
		}
		catch(FieldNotFound fnfe) {
			throw new RuntimeError("Error while fucking with logon message");
		}

	}

	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
		// TODO Auto-generated method stub

	}


}