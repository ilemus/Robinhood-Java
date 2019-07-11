package robinhood;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Response {
	JSONObject obj;
	String text;
	int statusCode;
	Map<String, List<String>> headers = new HashMap<>();
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("===========Start===========\n");
		sb.append(statusCode).append('\n');
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			sb.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
		}
		if (text != null) sb.append(text).append('\n');
		if (obj != null) sb.append(obj.toString()).append('\n');
		sb.append("============End============\n");
		return sb.toString();
	}
}
