package com.antiso.jpwgen.server;

import com.antiso.jpwgen.client.PwgenService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.rrze.jpwgen.utils.PwHelper;

public class PwgenServiceImpl extends RemoteServiceServlet implements PwgenService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2727656404203371528L;

	public String[] generatePasswords(String [] options) throws IllegalArgumentException {
		return PwHelper.process(
				options, null).toArray(new String []{});
	}

}
