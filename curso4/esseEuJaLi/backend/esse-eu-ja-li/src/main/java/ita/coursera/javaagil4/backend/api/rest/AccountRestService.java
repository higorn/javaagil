package ita.coursera.javaagil4.backend.api.rest;


import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.model.ApiResponse;
import ita.coursera.javaagil4.backend.api.service.AccountService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.auth.login.AccountException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/account")
public class AccountRestService {

	private static final Logger logger = Logger.getLogger(AccountRestService.class.getName());

	@Inject
    private AccountService service;
    @Context
    private HttpHeaders headers;

    @GET
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Account login(@HeaderParam("Authorization")
                              @NotNull(message = ":(")
                              final String authorization) throws AccountException {
        logger.info("Start login");
        return service.login(authorization);
    }

	@GET
	@Path("/{id}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(final String id) throws AccountException {
        logger.info("Start getAccount");
        return service.getAccount(id);
    }

    @POST
	@Path("/")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount(final Account account) {

	    service.createAccount(account);

        Response.Status status = Response.Status.CREATED;
	    Response response = Response.status(status)
                .entity(new ApiResponse(status.getStatusCode(), status.toString(), "OK"))
                .type(MediaType.APPLICATION_JSON)
                .build();
        return response;
	}

	@PUT
	@Path("/")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Account updateAccount(final Account account) {
        return service.updateAccount(account);
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
