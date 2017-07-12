/*
 * File:   SecurityInterceptor.java
 *
 * Created on 09/07/17, 16:48
 */
package ita.coursera.javaagil4.backend.api.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.model.ApiResponse;

/**
 * @author higor
 */
@Provider
public class SecurityInterceptor implements ContainerRequestFilter {
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class.getName());

    public static final String RESOURCE_METHOD_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";
    private static final String AUTHORIZATION_PROP = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Inject
    private AccountDao accountDao;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        final ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(RESOURCE_METHOD_INVOKER);
        final Method method = methodInvoker.getMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authorization = headers.get(AUTHORIZATION_PROP);

        if (authorization == null || authorization.isEmpty()) {
        	logger.warning("Faltou header Authorization");
            requestContext.abortWith(buildResponse(Response.Status.BAD_REQUEST, ":("));
            return;
        }

        if (!isAuthenticated(authorization)) {
            requestContext.abortWith(buildResponse(Response.Status.UNAUTHORIZED, ":("));
            return;
        }

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAllowed.value()));
            String userRole = "USER";
            if (!rolesSet.contains(userRole)) {

                Response response = buildResponse(Response.Status.FORBIDDEN, ":(");
                requestContext.abortWith(response);
            }
        }
    }

    private boolean isAuthenticated(List<String> authorization) {
        final String token = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        Optional<Account> maybeAccount = accountDao.findByToken(token);
        if (maybeAccount.isPresent()) {
        	return true;
        }
        return false;

//        final String encodedAuthorization = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//        final String decodedAuthorization = new String(Base64.getDecoder().decode(encodedAuthorization));
//        final String[] authInfo = decodedAuthorization.split(":");
//        final String username = authInfo[0];
//        final String password = authInfo[1];
//
//        Account account = accountDao.findByName(username);
//
//        if (account.getPassword().equals(HashUtils.hashPassword(username, password))) {
//            return true;
//        }
//        return false;
    }

    private <T> Response buildResponse(Response.Status status, T data) {
        return Response.status(status)
            .entity(new ApiResponse<>(status.getStatusCode(), status.getReasonPhrase(), data))
            .build();
    }
}
