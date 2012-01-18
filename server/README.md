# bot-bot server

This server application is meant to be used with bot-bot aandroid test automation framework.

## Build the war
To build the war run
>
> $mvn dist
>

## Deploy the war
The war is self contaained including the database. So just deploy the war in your servlet contaainer aand you are ready to go.

## Available URLs

### /api/*
This is the url base where the REST services for CRUD on RecordSession and RecordEntries are exposed. Please look into /src/test/RetClient/ files to understand the services.

The flow is -
Create Record Session and then Create Record Entries for that Session.

//TODO: Document the services further

### /h2/
This URL will open the H2 Web Console. You can connect to the databse here using, 
>
>	Driver - H2 Network, 
>	DB URL - jdbc:h2:tcp://localhost/~/botbot
>	User - sa
>	Password - 12345678
>

Feel free to log any issues or feature requests, I'll try to get to them asap!

