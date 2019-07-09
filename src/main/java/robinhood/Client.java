package robinhood;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	private boolean DEBUG = false;
	private boolean INSECURE = false;
	private static final String VERSION = "1.0";
	private static Client INSTANCE = new Client();
	private static final int STREAM_BUFFER_SIZE = 128;
	private Client() {
		
	}
	
	public static Client getInstance() {
		return INSTANCE;
	}
	
	HttpsURLConnection makeConnection(String url, String method, List<String[]> headers) throws MalformedURLException, IOException {
		HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
		conn.setRequestMethod(method);
		for (String[] header : headers) {
			conn.setRequestProperty(header[0], header[1]);
		}
		return conn;
	}
	
	// RESTful request
	JSONObject makeRequest(HttpsURLConnection conn, JSONObject obj) {
		// Posting data to server
		String body = obj.toString();
		OutputStream os;
		try {
			os = conn.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			bos.write(body.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Reading data from server
		InputStream is;
		try {
			is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[STREAM_BUFFER_SIZE];
			int read = 0;
			while ((read = isr.read(buffer)) > 0) {
				sb.append(buffer, 0, read);
			}
			JSONObject response = new JSONObject(sb.toString());
			return response;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// RESTful request
	JSONObject makeRequest(HttpsURLConnection conn) {
		// Reading data from server
		InputStream is;
		try {
			is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[STREAM_BUFFER_SIZE];
			int read = 0;
			while ((read = isr.read(buffer)) > 0) {
				sb.append(buffer, 0, read);
			}
			JSONObject response = new JSONObject(sb.toString());
			return response;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
