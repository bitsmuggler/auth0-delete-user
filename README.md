# auth0-delete-user

Delete Auth0 users using the Auth0 user export file

## Preparation

1. Install the `User Import / Export` extension
1. Export the desired users to delete as JSON file 

## Delete users on auth0 according the specified JSON file

1. Call this tool with the following cmdline arguments

`java -jar auth0-delete-user-cli.jar 
    -i <<path to the import file>> 
    -c <<client id>>
    -s <<client secret 
    -a <<audience id>>` o
