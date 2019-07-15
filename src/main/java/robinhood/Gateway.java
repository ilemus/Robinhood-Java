package robinhood;

import org.json.JSONObject;

import robinhood.objects.Login;
import robinhood.objects.Order;

public class Gateway {
	private static final String ROBINHOOD_API_VERSION = "1.275.0";
	private static Gateway INSTANCE = new Gateway();
	private Session session = new Session();
	
	private static final String CLIENT_ID = "c82SH0WZOsabOXGP2sxqcj34FxkvfnWRZBKlBjFS";
	
	private Gateway() {
		session.headers.put("Accept","*/*");
		session.headers.put("Connection", "keep-alive");
		session.headers.put("X-Robinhood-API-Version", ROBINHOOD_API_VERSION);
		// Do not track (paranoid anonymous)
		session.headers.put("DNT", "1");
		session.headers.put("TE", "Trailers");
	}
	
	public static Gateway getInstance() {
		return INSTANCE;
	}
	
	public Response login(String username, String password) {
		// required to go to login page first
		if (!session.cookies.containsKey("device_id")) return null;
		Login login = new Login();
		login.username = username;
		login.password = password;
		login.clientId = CLIENT_ID;
		login.deviceToken = session.cookies.get("device_id");
		try {
			return session.post(Urls.login(), login.toJson());
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

	public Response logout(String refreshToken) {
		JSONObject obj = new JSONObject();
		obj.put("client_id", CLIENT_ID);
		obj.put("token", refreshToken);
		try {
			return session.post(Urls.logout(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response order(Order order) {
		try {
			return session.post(Urls.order(), order.toJson());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response instrument(String symbol) {
		try {
			return session.get(Urls.instruments(symbol));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response accountInfo() {
		try {
			return session.get(Urls.accounts());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response quote(String id) {
		try {
			return session.get(Urls.quote(id));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response book(String stockId) {
		try {
			return session.get(Urls.book(stockId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response positions() {
		try {
			return session.get(Urls.positions());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response cancelOrder(String url) {
		try {
			return session.post(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response accessInstrument(String url) {
		try {
			return session.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setAuthorization(String token) {
		session.headers.put("Authorization", "Bearer " + token);
	}
}
