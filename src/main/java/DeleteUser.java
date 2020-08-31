import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;


public class DeleteUser {

    public static void main(String[] args) {

        Options options = new Options();

        Option importFile = new Option("i", "importFile", true, "Auth0 import file");
        importFile.setRequired(true);
        options.addOption(importFile);

        Option audience = new Option("a", "audience", true, "Auth0 audience, e.g. https://dev-1234.eu.auth0.com");
        audience.setRequired(true);
        options.addOption(audience);

        Option clientId = new Option("c", "clientId", true, "Auth0 client name");
        clientId.setRequired(true);
        options.addOption(clientId);

        Option clientSecret = new Option("s", "clientSecret", true, "Auth0 client secret");
        clientSecret.setRequired(true);
        options.addOption(clientSecret);

        CommandLineParser commandLineParser = new DefaultParser();

        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;


        BufferedReader reader;

        try {

            cmd = commandLineParser.parse(options, args);

            Auth0Data auth0Data = Auth0Data.getAuthDataFromCmd(cmd);
            Auth0Client auth0Client = new Auth0Client(auth0Data);


            reader = new BufferedReader(new FileReader(cmd.getOptionValue("importFile")));

            String line = reader.readLine();

            Set<Auth0User> auth0Users = new HashSet<Auth0User>();

            while (line != null) {
                Auth0User user = new ObjectMapper().readValue(line, Auth0User.class);
                System.out.println(String.format("Found: %s", user.user_id));
                auth0Users.add(user);
                line = reader.readLine();
            }

            auth0Client.deleteUsers(auth0Users);

        } catch(IOException ex) {
           ex.printStackTrace();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("auth0-user-delete", options);
            System.exit(1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
