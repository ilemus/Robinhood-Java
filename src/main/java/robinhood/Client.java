package robinhood;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Scanner;

import javax.security.sasl.AuthenticationException;

import robinhood.objects.Symbol;

public class Client {
	Gateway gateway = Gateway.getInstance();
	private boolean loggedIn = false;
	private String refreshToken;
	private HashMap<String, Symbol> symbols = new HashMap<>();
	private HashMap<String, String> stockIds = new HashMap<>();
	
	public void login(String username, String password) throws AuthenticationException {
		// Load login page to generate device_id
		Response resp = gateway.loginPage();
		if (resp.statusCode != HttpURLConnection.HTTP_OK) {
			throw new AuthenticationException("Robinhood login page could not be loaded successfully, error code: " + resp.statusCode);
		}
		
		// Fail first
		resp = gateway.login(username, password);
		if (resp.statusCode != HttpURLConnection.HTTP_BAD_REQUEST
				&& resp.statusCode != HttpURLConnection.HTTP_OK) {
			throw new AuthenticationException("Robinhood login proceedure violated, credentials cannot be processed");
		}
		String challengeId = (resp.obj)
				.getJSONObject("challenge")
				.getString("id");
		
		// SMS confirm
		// TODO: read from user any way you wish
		Scanner scan = new Scanner(System.in);
		System.out.print("SMS Code: ");
		String response = scan.next();
		scan.close();
		resp = gateway.challenge(challengeId, response);
		if (resp.statusCode != HttpURLConnection.HTTP_OK) {
			throw new AuthenticationException("SMS Challenge is failed for input code: " + response);
		}

		// Success second
		resp = gateway.login(username, password, challengeId);
		if (resp.statusCode != HttpURLConnection.HTTP_OK) {
			throw new AuthenticationException("Login with challengeId is failed with status code: " + resp.statusCode);
		}
		String token = resp.obj.getString("access_token");
		refreshToken = resp.obj.getString("refresh_token");
		gateway.setAuthorization(token);
		loggedIn = true;
	}
	
	public void logout() {
		gateway.logout(refreshToken);
		refreshToken = null;
		loggedIn = false;
	}
	
	private void symbolLookup(String symbol) {
		String upper = symbol.toUpperCase();
		Response resp = gateway.instrument(symbol);
		// Nothing to process
		if (resp.statusCode != HttpURLConnection.HTTP_OK) return;
		
		String id = (resp.obj)
				.getJSONArray("results")
				.getJSONObject(0)
				.getString("id");
		String instrument = (resp.obj)
				.getJSONArray("results")
				.getJSONObject(0)
				.getString("url");
		stockIds.put(instrument, symbol);
		Symbol s = new Symbol();
		s.id = id;
		s.instrument = instrument;
		symbols.put(symbol, s);
	}
	
	private void instrumentLookup(String instrument) {
		Response resp = gateway.accessInstrument(instrument);
		// Nothing to process
		if (resp.statusCode != HttpURLConnection.HTTP_OK) return;
		
		String symbol = (resp.obj).getString("symbol");
		stockIds.put(instrument, symbol);
		Symbol s = new Symbol();
		s.id = (resp.obj).getString("id");
		s.instrument = instrument;
		s.name = (resp.obj).getString("name");
		s.simpleName = (resp.obj).getString("simple_name");
		symbols.put(symbol, s);
	}
}
