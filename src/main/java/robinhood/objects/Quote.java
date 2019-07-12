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
		this.askPrice = obj.getString("ask_price");
		this.bidPrice = obj.getString("bid_price");
		this.askSize = obj.getInt("bid_size");
		this.bidSize = obj.getInt("bid_size");
		this.lastPrice = obj.getString("last_trade_price");
		this.prevClose = obj.getString("previous_close");
		this.prevCloseDate = obj.getString("previous_close_date");
		this.tradingHalted = obj.getBoolean("trading_halted");
		this.traded = obj.getBoolean("has_traded");
		this.updatedTime = obj.getString("updated_at");
	}
	
	public String getAskPrice() { return askPrice; }
	public String getBidPrice() { return bidPrice; }
	public int getAskSize() { return askSize; }
	public int getBidSize() { return bidSize; }
	public String getLastPrice() { return lastPrice; }
	public String getPrevClose() { return prevClose; }
	public String getPrevCloseDate() { return prevCloseDate; }
	public boolean getTradingHalted() { return tradingHalted; }
	public boolean getTraded() { return traded; }
	public String getUpdatedTime() { return updatedTime; }
}
