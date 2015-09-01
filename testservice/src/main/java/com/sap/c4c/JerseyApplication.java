package com.sap.c4c;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationFeature;


/**
 * Defines the REST application.
 */
public class JerseyApplication extends ResourceConfig
{
	/**
	 * Initialized the jersey application.
	 */
	public JerseyApplication()
	{
		
		//packages("com.sap.c4c");
		
		register(ApiFeature.class);
		
		// json support
		register(JacksonFeature.class);
		

		// logging
		register(LoggingFilter.class);


        // Add additional features such as support for Multipart.
       register(MultiPartFeature.class);	

		// bean validation
		register(ValidationFeature.class);
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}
}
