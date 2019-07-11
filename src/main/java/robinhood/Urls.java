package robinhood;

public class Urls {
	private static String API = "https://api.robinhood.com";
	public static void updateUrl(String root) {
		API = root;
	}
	
    public static String accounts() {
        return API + "/accounts/";
    }
    
    public static String book(String s_id) {
        return API + "/marketdata/pricebook/snapshots/" + s_id + "/";
    }
    public static String challenge(String c_id) {
        return API + "/challenge/" + c_id + "/respond/";
    }
    
    public static String instruments(String symbol) {
        return API + "/instruments/?symbol=" + symbol;
    }
    
    public static String login() {
        return API + "/oauth2/token/";
    }
        
    public static String loginPage() {
        return "https://robinhood.com/login";
    }
    
    public static String logout() {
        return API + "/oauth2/revoke_token/";
    }
    
    public static String order() {
        return API + "/orders/";
    }
    
    public static String positions() {
        return API + "/positions/?nonzero=true";
    }
    
    public static String quote(String s_id) {
        return API + "/marketdata/quotes/" + s_id + "/?include_inactive=true";
    }
}
