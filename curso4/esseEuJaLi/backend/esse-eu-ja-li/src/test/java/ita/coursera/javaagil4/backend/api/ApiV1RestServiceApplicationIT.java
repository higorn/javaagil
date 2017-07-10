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
	private DefaultExceptionHandler exceptionMapper;
    @Inject
	private SecurityInterceptor interceptor;
    @Inject
	private DefaultExceptionHandler exceptionHandler;
	private static UndertowJaxrsServer server;
	private MongoCollection<Document> accountCollection;
	private MongoClient mongoClient;
	private Client client;
	private Document accountDocument;

	@BeforeClass
	public static void beforeClass() throws NamingException {
		System.setProperty("org.jboss.resteasy.port", "8082");
		server = new UndertowJaxrsServer().start();
	}

	@Before
	public void setUp() {
		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setApplication(application);
		deployment.setProviders(new ArrayList<>(Arrays.asList(new Object[]{exceptionHandler, interceptor})));

		Map<String, String> contextParams = new HashMap<>();
		contextParams.put("resteasy.scan", "true");

		Map<String, String> initParams = new HashMap<>();
		initParams.put("javax.ws.rs.Application", "ita.coursera.javaagil4.backend.api.ApiV1RestServiceApplication");
		initParams.put("resteasy.servlet.mapping.prefix", "/api/v1");

		server.deploy(deployment, "esse-eu-ja-li", contextParams, initParams);
		mongoClient = new MongoClient();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("esseEuJaLiTest");
		accountCollection = mongoDatabase.getCollection("account");
		accountDocument = new Document("_id", "123")
				.append("name", "lauterio")
				.append("displayName", "Lauterio")
				.append("role", "user")
				.append("password", HashUtils.hashPassword("lauterio", "cba"))
				.append("token", null);
		accountCollection.insertOne(accountDocument);
		client = ClientBuilder.newBuilder().build();
	}

	@After
	public void tearDown() {
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
	public void deveCriarUmaNovaConta() {
		Entity<Account> entity = Entity.json(new Account("toya", "Toya", "dog", "4321", null));
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/"))
				.request(MediaType.APPLICATION_JSON)
				.post(entity);
		assertNotNull(response);
		assertEquals(201, response.getStatus());
	}

	@Test
	public void deveAtualizarUmaConta() {
		String basicAuthEncoded = Base64.encodeBytes("lauterio:cba".getBytes());
	    Account account = new Account("lauterio", "Lauterio", "user", "4321", null);
	    account.setId((String) accountDocument.get("_id"));
		Entity<Account> entity = Entity.json(account);
		Response response = client.target(TestPortProvider.generateURL(API_V1_PATH + "/account/"))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + basicAuthEncoded)
				.put(entity);
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		Document document = Document.parse(response.readEntity(String.class));
		assertEquals(accountDocument.get("_id"), document.get("id"));
		assertEquals(accountDocument.get("name"), document.get("name"));
		assertEquals(accountDocument.get("displayName"), document.get("displayName"));
		assertEquals(accountDocument.get("role"), document.get("role"));
		assertNull(document.get("token"));
		assertNull(document.get("password"));
	}
}
