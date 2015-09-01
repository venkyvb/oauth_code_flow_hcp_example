package com.sap.c4c;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@javax.ws.rs.Path("/guesthello")
public class DefaultPongResource {

	@javax.ws.rs.core.Context
	private HttpServletRequest request;

	@javax.ws.rs.GET
	@javax.ws.rs.Produces({ "application/json" })
	public Response get() {
		return Response.ok().entity(new String(
				"Hello Guest"))
				.build();

	}
}
