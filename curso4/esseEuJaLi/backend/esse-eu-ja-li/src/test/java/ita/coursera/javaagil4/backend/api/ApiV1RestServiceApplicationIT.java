package ita.coursera.javaagil4.backend.api;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ita.coursera.javaagil4.backend.api.exception.DefaultExceptionHandler;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.security.HashUtils;
import ita.coursera.javaagil4.backend.api.security.SecurityInterceptor;
import org.bson.Document;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.test.TestPortProvider;
import org.jboss.resteasy.util.Base64;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(WeldJUnit4Runner.class)
public class ApiV1RestServiceApplicationIT {
	private static final String API_V1_PATH = "/esse-eu-ja-li/api/v1";

    @Inject
	private ApiV1RestServiceApplication application;
    @Inject
	private SecurityInterceptor interceptor;
    @Inject
	private DefaultExceptionHandler exceptionHandler;
	private static UndertowJaxrsServer server;
	private MongoCollection<Document> accountCollection;
	private MongoClient mongoClient;
	private Client client;
	private Document accountDocument;
	private String token;

	@BeforeClass
	public static void beforeClass() throws NamingException {
		System.setProperty("org.jboss.resteasy.port", "8082");
		server = new UndertowJaxrsServer().start();
	}

	@Before
	public void setUp() throws NoSuchAlgorithmException {
		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setApplication(application);
		deployment.setProviders(new ArrayList<>(Arrays.asList(new Object[]{exceptionHandler, interceptor})));

		Map<String, String> contextParams = new HashMap<>();
		contextParams.put("resteasy.scan", "true");

		Map<String, String> initParams = new HashMap<>();
		initParams.put("javax.ws.rs.Application", "ita.coursera.javaagil4.backend.api.ApiV1RestServiceApplication");
		initParams.put("resteasy.servlet.mapping.prefix", "/api/v1");

		server.deploy(deployment, "esse-eu-ja-li", contextParams, initParams);
		token = HashUtils.generateToken();
		mongoClient = new MongoClient();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("esseEuJaLiTest");
		accountCollection = mongoDatabase.getCollection("account");
		accountDocument = new Document("_id", "123")
				.append("name", "lauterio")
				.append("displayName", "Lauterio")
				.append("role", "user")
				.append("password", HashUtils.hashPassword("lauterio", "cba"))
				.append("token", token);
		accountCollection.insertOne(accountDocument);
		client = ClientBuilder.newBuilder().build();
	}

	@After
	public void tearDown() {
	    client.close();
		accountCollection.deleteMany(new Document());
		mongoClient.close();
	}
	
	@AfterClass
	public static void afterClass() {
		server.stop();
	}
	
	@Test
	public void paraLoginValidoDeveRetornarUmaContaValida() {
		String basicAuthEncoded = Base64.encodeBytes("lauterio:cba".getBytes());
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/login"))
			.request(MediaType.APPLICATION_JSON)
			.header("Authorization", "Basic " + basicAuthEncoded)
			.get();
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(accountDocument.get("_id"), document.get("id"));
		assertEquals(accountDocument.get("name"), document.get("name"));
		assertEquals(accountDocument.get("displayName"), document.get("displayName"));
		assertEquals(accountDocument.get("role"), document.get("role"));
		assertNotNull(document.get("token"));
		assertNull(document.get("password"));
	}

	@Test
	public void paraLoginInvalidoDeveRetornarErro() {
		String basicAuthEncoded = Base64.encodeBytes("lauterio:1234".getBytes());
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/login"))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + basicAuthEncoded)
				.get();
		assertNotNull(response);
		assertEquals(401, response.getStatus());
		assertTrue(response.readEntity(String.class).contains("Login inválido"));
	}

	@Test
	public void paraLoginSemHeaderDeAutenticacaoDeveRetornarErro() {
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/login"))
				.request(MediaType.APPLICATION_JSON)
				.get();
		assertNotNull(response);
		assertEquals(400, response.getStatus());
		assertTrue(response.readEntity(String.class).contains(":("));
	}

	@Test
	public void paraBuscaDeContaDeveRetornarUmaContaValida() {
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/123"))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + token)
				.get();
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(accountDocument.get("_id"), document.get("id"));
		assertEquals(accountDocument.get("name"), document.get("name"));
		assertEquals(accountDocument.get("displayName"), document.get("displayName"));
		assertEquals(accountDocument.get("role"), document.get("role"));
		assertEquals(accountDocument.get("token"), document.get("token"));
		assertNull(document.get("password"));
    }

	@Test
	public void paraBuscaDeContaComIdInvalidoDeveRetornarErro() {
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/321"))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + token)
				.get();
		assertNotNull(response);
		assertEquals(404, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(404, document.get("code"));
		assertEquals("Not Found", document.get("status"));
		assertEquals(":(", document.get("data"));
	}

	@Test
	public void deveCriarERemoverUmaNovaConta() {
		Entity<Account> entity = Entity.json(new Account("toya", "Toya", "dog", "4321", null));
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/"))
				.request(MediaType.APPLICATION_JSON)
				.post(entity);
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(201, document.get("code"));
		assertEquals("Created", document.get("status"));
		assertNotNull(document.get("data"));
		client.close();

		client = ClientBuilder.newBuilder().build();
		response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/" + document.get("data")))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + token)
				.delete();
		assertNotNull(response);
		assertEquals(202, response.getStatus());
		client.close();

		client = ClientBuilder.newBuilder().build();
		response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/" + document.get("data")))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + token)
				.get();
		assertNotNull(response);
		assertEquals(404, response.getStatus());
	}

	@Test
	public void deveAtualizarUmaConta() {
	    Account account = new Account("lauterio", "Lauterio", "user", "4321", null);
	    account.setId((String) accountDocument.get("_id"));
		Entity<Account> entity = Entity.json(account);
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/"))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + token)
				.put(entity);
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(accountDocument.get("_id"), document.get("id"));
		assertEquals(accountDocument.get("name"), document.get("name"));
		assertEquals(accountDocument.get("displayName"), document.get("displayName"));
		assertEquals(accountDocument.get("role"), document.get("role"));
		assertEquals(accountDocument.get("token"), document.get("token"));
		assertNull(document.get("password"));
	}

	@Test
	public void deveFazerLogout() {
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/logout"))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + token)
				.get();
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(200, document.get("code"));
		assertEquals("OK", document.get("status"));
		assertEquals("Bye ;)", document.get("data"));
	}
}
