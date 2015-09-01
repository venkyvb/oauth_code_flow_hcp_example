package com.sap.c4c;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import com.sap.security.um.user.PersistenceException;
import com.sap.security.um.user.UnsupportedUserAttributeException;
import com.sap.security.um.user.User;
import com.sap.security.um.user.UserProvider;

@javax.ws.rs.Path("/helloworld")
public class DefaultPingResource {

	@javax.ws.rs.core.Context
	private HttpServletRequest request;

	@javax.ws.rs.GET
	@javax.ws.rs.Produces({ "application/json" })
	public Response get() {

		try {

			InitialContext ctx = new InitialContext();

			UserProvider userProvider = (UserProvider) ctx.lookup("java:comp/env/user/Provider");
			User user = null;
			if (request.getUserPrincipal() != null) {
				user = userProvider.getUser(request.getUserPrincipal().getName());
			}

			HashMap<String, String> result = new HashMap<String, String>();
			result.put("user_name", user.getName());
			result.put("email", user.getAttribute("email"));
			return Response.ok().entity(result).build();
		} catch (NamingException | PersistenceException | UnsupportedUserAttributeException e) {
			return Response.serverError().entity(e.getLocalizedMessage()).build();
		}

	}
}
