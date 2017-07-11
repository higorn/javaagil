/*
 * File:   SecurityInterceptorTest.java
 *
 * Created on 09/07/17, 15:58
 */
package ita.coursera.javaagil4.backend.api.security;

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.model.ApiResponse;
import ita.coursera.javaagil4.backend.api.rest.AccountRestService;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;
import org.jboss.resteasy.spi.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author higor
 */
public class SecurityInterceptorTest {
    @Mock
    private HttpRequest request;
    @Mock
    private ResourceMethodInvoker methodInvoker;
    @Mock
    private AccountDao accountDao;
    @Spy
    @InjectMocks
    private SecurityInterceptor interceptor;
    private ContainerRequestContextMock requestContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        requestContext = spy(new ContainerRequestContextMock(request, methodInvoker));
    }

    @Test
    public void paraRequisicaoSemHeaderDeAutorizacaoDeveRetornarErro() throws IOException, NoSuchMethodException {
        when(methodInvoker.getMethod()).thenReturn(AccountRestService.class.getDeclaredMethod("getAccount", String.class));

        interceptor.filter(requestContext);
        Response response = requestContext.getResponseAbortedWith();
        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertTrue(response.getEntity() instanceof ApiResponse);
        ApiResponse<String> apiResponse = (ApiResponse<String>) response.getEntity();
        assertEquals(Integer.valueOf(400), apiResponse.getCode());
        assertEquals("Bad Request", apiResponse.getStatus());
        assertEquals(":(", apiResponse.getData());
    }

    @Test
    public void paraCriacaoDeNovaContaDevePermitirAcesso() throws NoSuchMethodException, IOException {
        when(methodInvoker.getMethod()).thenReturn(AccountRestService.class.getDeclaredMethod("createAccount", Account.class));

        interceptor.filter(requestContext);
        Response response = requestContext.getResponseAbortedWith();
        assertNull(response);
    }

    @Test
    public void paraRequisicaoComAutorizacaoInvalidaDeveRetornarErro() throws IOException, NoSuchMethodException, NoSuchAlgorithmException {
        String token = HashUtils.generateToken();
        String authorization = "Basic " + token + "abc";

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.put("Authorization", new ArrayList<>(Arrays.asList(new String[]{authorization})));

        when(requestContext.getHeaders()).thenReturn(headers);
        when(methodInvoker.getMethod()).thenReturn(AccountRestService.class.getDeclaredMethod("getAccount", String.class));

        Account accountExpected = new Account();
        accountExpected.setName("lauterio");
        accountExpected.setDisplayName("Lauterio");
        accountExpected.setToken(token);

        when(accountDao.findByToken(token + "abc")).thenReturn(Optional.empty());

        interceptor.filter(requestContext);
        verify(accountDao).findByToken(token + "abc");
        Response response = requestContext.getResponseAbortedWith();
        assertNotNull(response);
        assertEquals(401, response.getStatus());
        assertTrue(response.getEntity() instanceof ApiResponse);
        ApiResponse<String> apiResponse = (ApiResponse<String>) response.getEntity();
        assertEquals(Integer.valueOf(401), apiResponse.getCode());
        assertEquals("Unauthorized", apiResponse.getStatus());
        assertEquals(":(", apiResponse.getData());
    }

    @Test
    public void devePermitirRequisicaoComAutorizacaoValida() throws IOException, NoSuchMethodException, NoSuchAlgorithmException {
        String token = HashUtils.generateToken();
        String authorization = "Basic " + token;

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.put("Authorization", new ArrayList<>(Arrays.asList(new String[]{authorization})));

        when(requestContext.getHeaders()).thenReturn(headers);
        when(methodInvoker.getMethod()).thenReturn(AccountRestService.class.getDeclaredMethod("getAccount", String.class));

        Account accountExpected = new Account();
        accountExpected.setName("lauterio");
        accountExpected.setDisplayName("Lauterio");
        accountExpected.setToken(token);

        when(accountDao.findByToken(token)).thenReturn(Optional.of(accountExpected));

        interceptor.filter(requestContext);
        verify(accountDao).findByToken(token);
        Response response = requestContext.getResponseAbortedWith();
        assertNull(response);
    }

    static class ContainerRequestContextMock extends PostMatchContainerRequestContext {

        public ContainerRequestContextMock(HttpRequest request, ResourceMethodInvoker resourceMethod) {
            super(request, resourceMethod);
        }

        @Override
        public Object getProperty(String name) {
            if (name.equals(SecurityInterceptor.RESOURCE_METHOD_INVOKER)) {
                return getResourceMethod();
            }
            return super.getProperty(name);
        }

        @Override
        public MultivaluedMap<String, String> getHeaders() {
            return new MultivaluedHashMap<>();
        }
    }
}
