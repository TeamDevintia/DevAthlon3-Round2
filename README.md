# DevAthlon3-Round2 [![Build Status](http://bender.minidigger.me:9090/buildStatus/icon?job=DevAthlon3-Round2)](http://bender.minidigger.me:9090/job/DevAthlon3-Round2/)
https://www.gommehd.net/forum/threads/devathlon-nummer-3-sei-dabei.501442/

## Mitglieder

### Developer [@TeamDevintia/devs](https://github.com/orgs/TeamDevintia/teams/devs)

* [@MiniDigger](https://github.com/MiniDigger) https://beam.pro/MiniDigger
* [@Shad0wCore](https://github.com/Shad0wCore)

### Designer [@TeamDevintia/designer](https://github.com/orgs/TeamDevintia/teams/designer)

* [@HerrPixel](https://github.com/HerrPixel)
* [@batthomas](https://github.com/batthomas)


## Building

This project uses maven. 

````
git clone https://github.com/TeamDevintia/DevAthlon3-Round2.git
mvn clean install
```

Or just get it from my ci http://ci.minidigger.me/job/DevAthlon3-Round2/

## Quickstart

Get this zip (http://files.minidigger.me/serverwrappertest.zip) as a quickstart.

Start the wrapper with the following command

``java -jar serverwrapper-1.0-SNAPSHOT-jar-with-dependencies.jar -interactive -autoattach``

## Using

When the wrapper starts up it will automaticaly start up bungeecord. Once that is started you can connect to it using a subdomain and it will create a server with the name of that subdomain and connect you to it once it is started.<br>
eg: I have a bungeecord started using the wrapper on minidigger.me:25577 (the default port). If I connect using test.minidigger.me:25577 bungee will tell the wrapper to create a new server called test for me and will connect me to it once that is started up. Other players can join the same server now (using the name subdomain) without waiting. They also see in the motd that the server is running. Once the last player on a sever leaves, that server gets completly deleted again. 

The wrapper will create fresh servers, but use predefined files and folders as a 'template'. This can be usefull for configurations or worlds. Everything that is located in <root>/repo/server will be copied to the newly created server.

The wrapper can start multiple versions of minecraft. They need to be in the repo folder and registered here: https://github.com/TeamDevintia/DevAthlon3-Round2/blob/master/serverwrapper/src/main/java/io/github/teamdevintia/round2/serverwrapper/server/ServerJarManager.java#L32

## Commands

These commands can only be used in the wrapper console if the -interactive flag was set!

* stop - Stops the wrapper and all running servers (including bungee!)
* stopserver <port/name> - Stops the server with that port/name
* startserver <port/name> - Starts the server with that port/name, needs to be created first
* createserver <name> - Creates a new server (using Spigot 1.10) and starts it
* deleteserver <name> - Deletes the server with that name
* attach <name> - Attaches to the console of that server. (Shows output from that server in the wrapper console)
* detach <name> - Detaches from the console of that server. (Don't show the output of that server)
* command <server> <commands ...> - Sends the command to that server's console
* mcsignondoor - Proof of concept. Shows a motd on port 40000 without starting a server

## Startup flags

These commands can be used as arguments for wrapper while starting it.

* -root - changes the root dir of the wrapper. the wrapper searches for jars in <root>/repo and will create new server folders in <root> by default. Defaults to "." (the location the jar is laying in)
* -interactive - allows to use commands
* -debug - changes the log format to include class and version name of outputs
* -autoattach - enables the autoattach mode. every newly created server will be automaticly attached (so that you can see the console)

## GUI

(tba)
