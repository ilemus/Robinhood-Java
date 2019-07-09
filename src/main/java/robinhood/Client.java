package robinhood;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class Client {
	private boolean DEBUG = false;
	private boolean INSECURE = false;
	private static final String VERSION = "1.0";
	private static Client INSTANCE = new Client();
	private Client() {
		
	}
	
	public static Client getInstance() {
		return INSTANCE;
	}
	
	private static HttpsURLConnection getRequest(String url, String method, List<String[]> headers) throws MalformedURLException, IOException {
		HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
		
		return conn;
	}
	
	private static Object makeRequest(HttpsURLConnection conn, JSONObject obj) {
		return new Object();
	}
}
