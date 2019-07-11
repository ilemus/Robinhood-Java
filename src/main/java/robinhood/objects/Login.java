package robinhood.objects;

import org.json.JSONObject;

public class Login {
	public String grantType = "password";
	public String scope = "internal";
	public String clientId;
	public int expiresIn = 86400;
	public String deviceToken;
	public String username;
	public String password;
	public String challengeType = "sms";
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("grant_type", grantType);
		obj.put("scope", scope);
		obj.put("client_id", clientId);
		obj.put("expires_in", expiresIn);
		obj.put("device_token", deviceToken);
	    obj.put("username", username);
	    obj.put("password", password);
	    obj.put("challenge_type", challengeType);
	    return obj;
	}
}
