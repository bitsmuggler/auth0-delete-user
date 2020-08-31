public class Auth0Token {

    public String access_token;
    public String scope;
    public int expires_in;
    public String token_type;

    @Override
    public String toString() {
        return this.access_token;
    }
}
