package com.paymium.fix;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.MsgType;
import quickfix.field.Username;

public class FIXInitiatorApplication implements Application {


	@Override
	public void onCreate(SessionID sessionId) {
		System.out.println("onCreate");
	}

	@Override
	public void onLogon(SessionID sessionId) {
		System.out.println("onLogon");
	}

	@Override
	public void onLogout(SessionID sessionId) {
		System.out.println("onLogout");
	}

	@Override
	public void toAdmin(Message message, SessionID sessionId) {

		System.out.println("To admin : " + message.toString());

		try {
			if (MsgType.LOGON.equals(message.getHeader().getField(new MsgType()).getValue())) {	
				message.getHeader().setField(new Username("JAVA_TESTS"));
			}
		}
		catch(FieldNotFound fnfe) {
			// NOOP
			System.out.println("fuckendish");
		}
	}


	@Override
	public void fromAdmin(Message message, SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			RejectLogon {
		System.out.println("ADM------------------------------------------------------");
		System.out.println(message.toString());
		System.out.println("ADM------------------------------------------------------");
	}

	@Override
	public void toApp(Message message, SessionID sessionId) throws DoNotSend {
		System.out.println("TO------------------------------------------------------");
		System.out.println(message.toString());
		System.out.println("TO------------------------------------------------------");
	}

	@Override
	public void fromApp(Message message, SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			UnsupportedMessageType {
		System.out.println("FROM------------------------------------------------------");
		System.out.println(message.toString());
		System.out.println("FROM------------------------------------------------------");

	}
}