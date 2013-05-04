package com.antiso.jpwgen.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pwgen")
public interface PwgenService extends RemoteService {

	String [] generatePasswords(String [] options) throws IllegalArgumentException;
}
