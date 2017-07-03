package ita.coursera.javaagil4.backend.api;


import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.service.AccountService;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Base64;
import java.util.logging.Logger;

@Path("/account")
public class AccountRestService {

	private static final Logger logger = Logger.getLogger(AccountRestService.class.getName());

	@Inject
    private AccountService service;

	@GET
	@Path("/")
    @Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@HeaderParam("Authorization")
                              @NotNull(message = ":(")
                              final String authorization) {
        logger.info("Start getAccount");

        String[] authInfo = getAuthInfo(authorization);
        return service.getAccount(authInfo[0], authInfo[1]);
	}

    private String[] getAuthInfo(String authorization) throws ValidationException {
        String basicAuthEncoded = authorization.split(" ")[1];
        String basicAuthDecoded = new String(Base64.getDecoder().decode(basicAuthEncoded));
        return basicAuthDecoded.split(":");
    }

    @POST
	@Path("/")
    @Produces(MediaType.TEXT_PLAIN)
	public String postSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {

			logger.info("Start postSomething");
			logger.info("data: '" + request + "'");
			logger.info("version: '" + version + "'");

		String response = null;

        try{			
            switch(version){
	            case 1:
	                logger.info("in version 1");

	                response = "Response from RESTEasy Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
            logger.info("result: '"+response+"'");
            logger.info("End postSomething");
        return response;
	}

	@PUT
	@Path("/")
    @Produces(MediaType.TEXT_PLAIN)
	public String putSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
			logger.info("Start putSomething");
			logger.info("data: '" + request + "'");
			logger.info("version: '" + version + "'");

		String response = null;

        try{			
            switch(version){
	            case 1:
	                logger.info("in version 1");

	                response = "Response from RESTEasy Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
            logger.info("result: '"+response+"'");
            logger.info("End putSomething");
        return response;
	}

	@DELETE
	@Path("/")
	public void deleteSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		
			logger.info("Start deleteSomething");
			logger.info("data: '" + request + "'");
			logger.info("version: '" + version + "'");


        try{			
            switch(version){
	            case 1:
	                logger.info("in version 1");

                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
            logger.info("End deleteSomething");
	}
}
