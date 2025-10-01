package src.commom.keycloak;

import js.annotations.NoAuto;
import js.annotations.Support;

@Support @NoAuto
public class KeyCloakResult {
	public String access_token;
	public int expires_in;
	public int refresh_expires_in;
	public String refresh_token;
	public String token_type;
	public int not_before_policy;
	public String session_state;
}
