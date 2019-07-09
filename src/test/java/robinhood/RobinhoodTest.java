package robinhood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobinhoodTest {
    @Test public void testSomeLibraryMethod() {
    	Client client = Client.getInstance();
    	List<String[]> headers = new ArrayList<>();
    	HttpsURLConnection conn = null;
		try {
			conn = client.makeConnection("https://www.google.com", "GET", headers);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			assertTrue(false);
			return;
		}
    	int responseCode = 0;
    	try {
			responseCode = conn.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
			return;
		}
    	System.out.println(responseCode);
    	assertEquals(responseCode, 200);
    	
    	JSONObject obj = client.makeRequest(conn);
    	assertEquals(obj, null);
    }
}
