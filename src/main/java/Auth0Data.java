import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class Auth0Data {

    private String client_id;
    private String client_secret;
    private String audience;
    private String grant_type;

    public Auth0Data(String client_id, String client_secret, String audience, String grant_type) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.audience = audience;
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getAudience() {
        return audience;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public static Auth0Data getAuthDataFromCmd(CommandLine cmd) throws ParseException {
        return new Auth0Data(
                cmd.getOptionValue("clientId"),
                cmd.getOptionValue("clientSecret"),
                cmd.getOptionValue("audience"),
                "client_credentials");
    }
}
