package robinhood;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Test;

public class RobinhoodTest {
	private static final String USERNAME = "test@test.com";
	private static final String PASSWORD = "password";
	private static final String SMS_CODE = "000000";
    @Test
    public void testLoginPage() {
    	Gateway client = Gateway.getInstance();
    	assertNotNull(client);
		Response resp = client.loginPage();
		assertNotNull(resp);
		assertEquals(resp.statusCode, 200);
    }
    @Test
    public void testLogin() {
    	Gateway client = Gateway.getInstance();
    	client.loginPage();
		Response resp = client.login(USERNAME, PASSWORD);
		assertNotNull(resp);
		System.out.println(resp);
		assertEquals(resp.statusCode, 400);
    }
    @Test
    public void testChallenge() {
    	Gateway client = Gateway.getInstance();
    	client.loginPage();
		Response resp = client.login(USERNAME, PASSWORD);
		assertNotNull(resp);
		System.out.println(resp);
		assertEquals(resp.statusCode, 400);
		JSONObject obj = (JSONObject) resp.obj.get("challenge");
		assertNotNull(obj);
		String id = obj.getString("id");
		assertNotNull(id);
		resp = client.challenge(id, SMS_CODE);
		assertNotNull(resp);
		assertEquals(resp.statusCode, 400);
    }
}
