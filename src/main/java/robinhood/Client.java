package robinhood;

import org.json.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Client {
	private boolean DEBUG = false;
	private boolean INSECURE = false;
	private static final String VERSION = "1.0";
	private Client INSTANCE = new Client();
	private Client() {
		
	}
	private static HttpsURLConnection getRequest(String url, String method, List<String[]> headers) throws MalformedURLException, IOException {
		HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
		
		return conn;
	}
	
	private static Object makeRequest(HttpsURLConnection conn, JSONObject obj) {
		return new Object();
	}
}
