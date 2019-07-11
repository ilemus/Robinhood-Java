package robinhood.objects;

import org.json.JSONObject;

public class Order {
	public String accountUrl;
	public String side;
	public String type;
	public String symbol;
	public String instrument;
	public String price;
	public String quantity;
	public boolean extended;
	// Defaults
	public String cancelType = "gfd";
	public String trigger = "immediate";
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("time_in_force", cancelType);
		if (price != null) obj.put("price", price);
		obj.put("quantity", quantity);
		obj.put("side", side);
		obj.put("trigger", trigger);
		obj.put("type", type);
		obj.put("account", accountUrl);
		obj.put("instrument", instrument);
		obj.put("symbol", symbol);
		obj.put("extended_hours", extended);
		return obj;
	}
}
