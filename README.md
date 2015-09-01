### OAuth 2.0 Auth Code Flow example using SAP Hana Cloud Platform (HCP)

Say you have implemented a cool service using SAP HCP, and now you want to open up the service for others to use. The question comes up as to how you can handle the authentication of clients to your service. One of the common ways of doing this is to use OAuth 2.0 protocol. SAP HCP support OAuth Auth Code flow grant (in addition to Client Credentials grant) which allows web-apps to do a OAuth based sign-in securely.

The key software components involved would be:
  * Resource server (your cool HCP service) which typically exposes the capabilities via REST APIs
  * Authorization server (your configured IDP @ the HCP account)
  * Client (app that is interested in consuming your service)

#### Register OAuth Client

You can refer to the standard HCP documentation around this [here](https://goo.gl/IsiVvK).

Note that in order to register a client, you would have to have a Redirect URI in your web-app that can accept the authorization code send by the Authorization server. In the current app this is the (DefaultCallbackResource)[https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/java/com/sap/c4c/DefaultCallbackResource.java]. This accepts a query parameter called "code".

#### Create an OAuth scope

You can refer to the standard HCP doumentation about creating OAuth Scopes [here](https://goo.gl/sCBLdT).

Once the Client registration is done and the OAuth scopes are created you are good to go :)

#### Overall flow

1) The client creates a "Logon Link" using the "Authorization Endpoint" (available at your HCP account). The URL should be fomulated as follows:
```
https://<hostname>/oauth2/api/v1/authorize?client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=DEFINED_SCOPE&response_type=code
```

2) The end-user, who is interacting with the client, will click the above link and would be redirected to the Authorization server logon page and on presenting valid credentials, would be presented with the famous "Allow access" popup with options for "Accept" or "Deny".

3) If the end-user clicks on Accept, the Auth server would generate an **authentication code** which would be posted to the Redirect URI with the code itself being part of the Query Parameter.

4) The Redirect URI (of the web-app) would then invoke the Token Endpoint of the Auth server as is done in the [DefaultCallbackResource](https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/java/com/sap/c4c/DefaultCallbackResource.java).

5) The web-app can then query information using HCP libraries to get information about the user and then use the same in the app.

6) The Resource server is then accessed by the client with the authorization header **__Authorization: Bearer OAUTH_TOKEN__**.
This is represented by the ubiquitous /helloworld end-point implemented by the Jersey resource [DefaultPingResource](https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/java/com/sap/c4c/DefaultPingResource.java).

#### Anatomy of the example

  * [DefaultPingResource](https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/java/com/sap/c4c/DefaultPingResource.java) => Represents the Resource server that uses OAuth for authentication
  * [DefaultPongResource](https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/java/com/sap/c4c/DefaultPongResource.java) => Represents a Public resource that DOES NOT use Authentication
  * [DefaultCallbackResource](https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/java/com/sap/c4c/DefaultCallbackResource.java) => Represents the web-app that is exchanging an auth code for an auth token.

OAUTH is one of the OOB Authentication Configurations supported by HCP. You can activate this via web.xml <login-config> setting. Please refer to the [web.xml](https://github.com/venkyvb/oauth_code_flow_hcp_example/blob/master/testservice/src/main/webapp/WEB-INF/web.xml) for details around this.

Happy coding !!
  
