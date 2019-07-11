package robinhood.objects;

import org.json.JSONObject;

public class Quote {
	private String askPrice;
	private String bidPrice;
	private int askSize;
	private int bidSize;
	private String lastPrice;
	private String prevClose;
	private String prevCloseDate;
	private boolean tradingHalted;
	private boolean traded;
	private String updatedTime;
	
	public Quote(JSONObject obj) {
		
	}
	
	
}
