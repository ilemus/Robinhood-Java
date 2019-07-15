package robinhood.objects;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Bid/Ask spread has thousands of values and is time sensitive.
 * Doing too many transformations is time consuming so this class is a wrapper for bid/ask spreads.
 * Documentation about bid/ask spreads are as follows:
 * 'bids' {
    	[{'side': 'bid', 'price': {'amount': '198.000000', 'currency_code': 'USD'}, 'quantity': 500}]
    },
    'asks' {
        [{'side': 'ask', 'price': {'amount': '198.010000', 'currency_code': 'USD'}, 'quantity': 500}]
    }
 */
public class Book {
	JSONObject obj;
	// pool cannot be setup
	// private LinkedList<Row> pool = new LinkedList<>();
	
	public Book(JSONObject obj) {
		this.obj = obj;
	}

	public Row getBid(int i) {
		JSONArray arr = obj.getJSONArray("bids");
		JSONObject row = arr.getJSONObject(i);
		return new Row(row.getJSONObject("price").getString("amount"), row.getInt("quantity"));
	}
	
	public int getBidLength() {
		return obj.getJSONArray("bids").length();
	}
	
	public Row getAsk(int i) {
		JSONArray arr = obj.getJSONArray("asks");
		JSONObject row = arr.getJSONObject(i);
		Row r = new Row(row.getJSONObject("price").getString("amount"), row.getInt("quantity"));
				/* pool.isEmpty()
				? new Row(row.getJSONObject("price").getString("amount"), row.getString("quantity"))
				: pool.pop().setAmount(row.getJSONObject("price").getString("amount")).setQuantity(row.getString("quantity"));
				*/
		return r;
	}
	
	public int getAskLength() {
		return obj.getJSONArray("asks").length();
	}
	
	public static class Row {
		public String amount;
		public int quantity;
		
		public Row(String amount, int quantity) {
			this.amount = amount;
			this.quantity = quantity;
		}
		
		Row setAmount(String amount) {
			this.amount = amount;
			return this;
		}
		
		Row setQuantity(int quantity) {
			this.quantity = quantity;
			return this;
		}
	}
}
