package robinhood;

import org.json.JSONObject;

public class Client {
	private boolean DEBUG = false;
	private boolean INSECURE = false;
	private static final String VERSION = "1.0";
	private static Client INSTANCE = new Client();
	private Session session = new Session();
	
	private static final String CLIENT_ID = "c82SH0WZOsabOXGP2sxqcj34FxkvfnWRZBKlBjFS";
	
	private Client() {
		session.headers.put("Accept","*/*");
		session.headers.put("Connection", "keep-alive");
		session.headers.put("DNT", "1");
		session.headers.put("TE", "Trailers");
	}
	
	public static Client getInstance() {
		return INSTANCE;
	}
	
	public Response login(String username, String password) {
		// required to go to login page first
		if (!session.cookies.containsKey("device_id")) return null;
		JSONObject obj = new JSONObject();
		obj.put("grant_type", "password");
		obj.put("scope", "internal");
		obj.put("client_id", CLIENT_ID);
		obj.put("expires_in", 86400);
		obj.put("device_token", session.cookies.get("device_id"));
	    obj.put("username", username);
	    obj.put("password", password);
	    obj.put("challenge_type", "sms");
		try {
			return session.post(Urls.login(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response login(String username, String password, String clientId) {
		session.headers.put("X-ROBINHOOD-CHALLENGE-RESPONSE-ID", clientId);
		Response resp = login(username, password);
		session.headers.remove("X-ROBINHOOD-CHALLENGE-RESPONSE-ID");
		return resp;
	}
	
	public Response loginPage() {
		try {
			return session.get(Urls.loginPage());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response challenge(String challengeId, String response) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("response", response);
			return session.get(Urls.challenge(challengeId), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
