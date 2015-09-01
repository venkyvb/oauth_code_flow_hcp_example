package com.sap.c4c;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

@javax.ws.rs.Path("/callback")
public class DefaultCallbackResource {

	@javax.ws.rs.core.Context
	private HttpServletRequest request;

	@javax.ws.rs.GET
	@javax.ws.rs.Produces({ "application/json" })
	public Response get(@javax.ws.rs.QueryParam("code") String authCode) {

		final MultivaluedMap<String, String> formData = new MultivaluedStringMap();
		
		formData.add("grant_type", "authorization_code");
		formData.add("code", authCode);
		formData.add("redirect_uri", "_REDIRECT_URI_MAINTAINED_IN_CLIENT_REGISTRATION_");
		formData.add("client_id", "_CLIENT_ID_GENERATED_IN_CLIENT_REGISTRATION_");
		formData.add("client_secret", "_CLIENT_SECRET_MAINTAINED_IN_CLIENT_REGISTRATION_");
		
		final Response response = ClientBuilder.newClient().target("https://oauthasservices-<ACCOUNT_NAME>.hana.ondemand.com/oauth2/api/v1/token")
				.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(formData));
		
		return response;

	}
}
