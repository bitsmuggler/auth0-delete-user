import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class Auth0Client {

    private Auth0Data auth0Data;

    public Auth0Client(Auth0Data auth0Data) {
        this.auth0Data = auth0Data;
    }

    private Auth0Token getToken() throws URISyntaxException {

        Response response = ClientBuilder.newClient()
                .target("https://" + new URI(this.auth0Data.getAudience()).getHost())
                .path("/oauth/token")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(auth0Data, MediaType.APPLICATION_JSON));

        return response.readEntity(Auth0Token.class);
    }

    public void deleteUsers(Set<Auth0User> auth0Users) throws URISyntaxException, InterruptedException {
        Auth0Token auth0Token = getToken();

        for (Auth0User user : auth0Users) {

            // Throttling
            Thread.sleep(500);

            if (!this.deleteUser(auth0Token, user.user_id)) {
                System.out.println(String.format("Could not delete user %s", user.user_id));
            }
        }
    }

    public boolean deleteUser(Auth0Token token, String auth0UserId) {
        Response response = ClientBuilder.newClient()
                .target(this.auth0Data.getAudience())
                .path(String.format("/users/%s", auth0UserId.substring(auth0UserId.indexOf("auth0|"))))
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", String.format("Bearer %s", token.toString()))
                .delete();

        return (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode());
    }
}
