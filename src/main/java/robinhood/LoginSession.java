package robinhood;

import java.io.Serializable;

public class LoginSession implements Serializable {
	private static final long serialVersionUID = 1340568698408795592L;
	public String authorizationBasic;
	public String refreshToken;
	public long endValid = 0L;
	
	public LoginSession(String auth, String refresh) {
		authorizationBasic = auth;
		refreshToken = refresh;
	}
}
