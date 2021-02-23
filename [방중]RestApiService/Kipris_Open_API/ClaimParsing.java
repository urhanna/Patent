package Kipris_Open_API;

public class ClaimParsing {
	public String claimParser(String claim) {
		if (claim.length() > 8176) {
			claim = claim.substring(0, 8176);
		}		
		return claim;
	}
}
