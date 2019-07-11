package robinhood;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Session {
	private static boolean DEBUG = true;
	private static final int STREAM_BUFFER_SIZE = 128;

	Map<String, String> headers = new HashMap<>();
	Map<String, String> cookies = new HashMap<>();
	
	Response post(String url) throws MalformedURLException, JSONException, IOException {
		HttpsURLConnection conn = makeConnection(url, "POST");
		return getResponse(conn);
	}
	
	Response post(String url, JSONObject obj) throws MalformedURLException, JSONException, IOException {
		HttpsURLConnection conn;
		synchronized(this) {
			headers.put("Content-Type", "application/json");
			conn = makeConnection(url, "POST");
			headers.remove("Content-Type");
		}
		writeJson(conn, obj);
		return getResponse(conn);
	}
	
	Response get(String url) throws MalformedURLException, JSONException, IOException {
		HttpsURLConnection conn = makeConnection(url, "GET");
		return getResponse(conn);
	}
	
	Response get(String url, JSONObject obj) throws MalformedURLException, JSONException, IOException {
		HttpsURLConnection conn;
		synchronized(this) {
			headers.put("Content-Type", "application/json");
			conn = makeConnection(url, "GET");
			headers.remove("Content-Type");
		}
		writeJson(conn, obj);
		return getResponse(conn);
	}
	
	// Setup connection with headers, URL, and HTTP method
	private HttpsURLConnection makeConnection(String url, String method) throws MalformedURLException, IOException {
		HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
		conn.setRequestMethod(method);
		for (Map.Entry<String, String> header: headers.entrySet()) {
			conn.setRequestProperty(header.getKey(), header.getValue());
		}
		return conn;
	}
	
	// Using connection, get server response
	private Response getResponse(HttpsURLConnection conn) throws IOException {
		Response response = new Response();
		// Set response headers
		for (Map.Entry<String, List<String>> header: conn.getHeaderFields().entrySet()) {
			response.headers.put(header.getKey(), header.getValue());
		}
		
		// Set response status code
		response.statusCode = conn.getResponseCode();
		
		// Set session cookies
		if (response.headers.containsKey("Set-Cookie")) setCookies(response.headers.get("Set-Cookie").get(0));
		
		// Get response Content-Type
		String contentType = conn.getContentType();
		if (contentType == null) return response;
		
		// Read body data
		InputStream is = null;
		if (response.statusCode >= 300) is = conn.getErrorStream();
		else is = conn.getInputStream();
		if (is == null) return response;
		InputStreamReader isr = new InputStreamReader(is);
		StringBuilder sb = new StringBuilder();
		char[] buffer = new char[STREAM_BUFFER_SIZE];
		int read = 0;
		while ((read = isr.read(buffer)) > 0) {
			sb.append(buffer, 0, read);
		}

		// Store body content as either JSON or text based on Content-Type
		if (contentType.equalsIgnoreCase("application/json")) {
			JSONObject obj = new JSONObject(sb.toString());
			response.obj = obj;
		} else if (contentType.startsWith("text")) {
			response.text = sb.toString();
		}
		
		return response;
	}
	
	// Using connection, write JSON object to output stream
	void writeJson(HttpsURLConnection conn, JSONObject obj) {
		// Posting data to server
		String body = obj.toString();
		byte[] data = body.getBytes();
		conn.setDoOutput(true);
		OutputStream os;
		try {
			os = conn.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			bos.write(data);
			bos.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private synchronized void setCookies(String cookies) {
		String[] fields = cookies.split(";\\s*");
        for (String field : fields) {
        	if ("secure".equalsIgnoreCase(field)) {
        		this.cookies.put("secure", "true");
        	} else if (field.indexOf('=') > 0) {
        		String[] f = field.split("=");
        		this.cookies.put(f[0], f[1]);
        	}
        }
	}
}
