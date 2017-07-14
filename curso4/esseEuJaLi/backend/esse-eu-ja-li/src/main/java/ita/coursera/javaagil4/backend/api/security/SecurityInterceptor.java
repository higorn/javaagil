/*
 * File:   SecurityInterceptor.java
 *
 * Created on 09/07/17, 16:48
 */
package ita.coursera.javaagil4.backend.api.security;

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.model.ApiResponse;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.auth.login.AccountException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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

        try {
            Account account = getAccount(authorization);
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAllowed.value()));
                if (!rolesSet.contains(account.getRole())) {

                    logger.warning("Acesso proibido para o usuário '" + account.getName()
                            + "' role '" + account.getRole() + "' ao recurso '" + method.getName() + "'");
                    Response response = buildResponse(Response.Status.FORBIDDEN, ":(");
                    requestContext.abortWith(response);
                }
            }
        } catch (AccountException e) {
            logger.warning(e.getMessage());
            requestContext.abortWith(buildResponse(Response.Status.UNAUTHORIZED, ":("));
        }

    }

    private Account getAccount(List<String> authorization) throws AccountException {
        final String token = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        return accountDao.findByToken(token).orElseThrow(() -> new AccountException("Token inválido: '" + token + "'"));
    }

    private <T> Response buildResponse(Response.Status status, T data) {
        return Response.status(status)
            .entity(new ApiResponse<>(status.getStatusCode(), status.getReasonPhrase(), data))
            .build();
    }
}
