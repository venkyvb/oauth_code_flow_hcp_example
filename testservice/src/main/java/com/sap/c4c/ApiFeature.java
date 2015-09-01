package com.sap.c4c;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * JAX RS feature which registers the API resources defined in the RAML.
 */
public class ApiFeature implements Feature
{
	@Override
	public boolean configure(final FeatureContext context)
	{
		context.register(DefaultPingResource.class);
		context.register(DefaultPongResource.class);
		context.register(DefaultCallbackResource.class);
		return true;
	}
}
