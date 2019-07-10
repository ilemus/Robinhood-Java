package robinhood;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Test;

public class RobinhoodTest {
	private static final String USERNAME = "test@test.com";
	private static final String PASSWORD = "password";
    @Test
    public void testLoginPage() {
    	Client client = Client.getInstance();
    	assertNotNull(client);
		Response resp = client.loginPage();
		assertNotNull(resp);
		assertEquals(resp.statusCode, 200);
    }
    @Test
    public void testLogin() {
    	Client client = Client.getInstance();
    	client.loginPage();
		Response resp = client.login(USERNAME, PASSWORD);
		assertNotNull(resp);
		assertEquals(resp.statusCode, 400);
    }
    @Test
    public void testChallenge() {
    	Client client = Client.getInstance();
    	client.loginPage();
		Response resp = client.login(USERNAME, PASSWORD);
		assertNotNull(resp);
		assertEquals(resp.statusCode, 400);
		JSONObject obj = (JSONObject) resp.obj.get("challenge");
		assertNotNull(obj);
		String id = obj.getString("id");
		assertNotNull(id);
		client.login(USERNAME, PASSWORD);
    }
}
