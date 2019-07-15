package robinhood.objects;

import org.json.JSONObject;

public class Account {
	public long rhsAccountNumber;
	public String margin;
	public String withdrawableCash;
	public String portfolio;
	public String url;
	public String cash;
	public Account(JSONObject obj) {
		
	}
}
