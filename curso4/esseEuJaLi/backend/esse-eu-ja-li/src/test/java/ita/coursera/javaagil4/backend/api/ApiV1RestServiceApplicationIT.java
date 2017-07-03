package ita.coursera.javaagil4.backend.api;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(WeldJUnit4Runner.class)
public class ApiV1RestServiceApplicationIT {

    @Inject
	private ApiV1RestServiceApplication application;
	private static UndertowJaxrsServer server;
	private MongoCollection<Document> accountCollection;

	@BeforeClass
	public static void beforeClass() throws NamingException {
		server = new UndertowJaxrsServer().start();
	}

	@Before
	public void setUp() {
		server.deploy(application);
		MongoClient mongoClient = new MongoClient();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("esseEuJaLiTest");
		accountCollection = mongoDatabase.getCollection("account");
		Document accountDocument = new Document("_id", "123")
				.append("name", "lauterio")
				.append("displayName", "Lauterio")
				.append("role", "user")
				.append("password", "cba")
				.append("token", null);
		accountCollection.insertOne(accountDocument);
	}

	@After
	public void tearDown() {
		accountCollection.deleteMany(new Document());
	}
	
	@AfterClass
	public static void afterClass() {
		server.stop();
	}
	
	@Test
	public void paraLoginValidoDeveRetornarUmaContaValida() {
		String basicAuthEncoded = Base64.encodeBytes("lauterio:cba".getBytes());
		Client client = ClientBuilder.newBuilder().build();
		Response response = client.target(TestPortProvider.generateURL("/v1/account/"))
			.request(MediaType.APPLICATION_JSON)
			.header("Authorization", "Basic " + basicAuthEncoded)
			.get();
		System.out.println(response.readEntity(String.class));
		assertNotNull(response);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void paraLoginSemHeaderDeAutenticacaoDeveRetornarErro() {
		Client client = ClientBuilder.newBuilder().build();
		Response response = client.target(TestPortProvider.generateURL("/v1/account/"))
				.request(MediaType.APPLICATION_JSON)
				.get();
		assertNotNull(response);
		assertEquals(400, response.getStatus());
		assertTrue(response.readEntity(String.class).contains(":("));
	}
}
