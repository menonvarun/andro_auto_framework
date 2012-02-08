# *bot-bot* Server

-----------------
##Introduction:
**Bot-bot server** is a component of bot-bot automation framework. It is used to record, store and modify the user actions taken on the android app at the user end/system.

Available functionalities:

- View recorded sessions
- View recorded entries for a particular session.

------------
##Build the war:

To build the bot-bot-server war you need to have maven isntalled in your system. Maven can be downloaded from the ths [link](http://maven.apache.org/download.html).

Once done run the following command at the root of the bot-bot/server folder
> 
> $mvn clean install
>

------------
## Deploy the war
The war is self contained including the database. So it can be deployed to any of the servlet container like *Apache Tomcat*, *Jboss*, etc.

To deploy the war do the following:

- Create the war as mentioned in the *Build the war* section.
- Copy the war from the target folder under the bot-bot/server to the webapps folder of your serrvlet container.
- Start your server.
- Access the server using the following URL
	
	http://&lt;localhost or systemip&gt;:&lt;port&gt;/bot-bot-server/index.html

------------
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

------------