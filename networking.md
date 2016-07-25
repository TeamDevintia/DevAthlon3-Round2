# Networking

Modul einbinden
```
        <dependency>
            <groupId>io.github.teamdevintia.round2</groupId>
            <artifactId>network</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
```

vars anlegen
```
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8000;

    private EventBus eventBus;
    private ClientNetHandler clientNetHandler;
```

connection aufbauen

```
`       // init event bus
        eventBus = new EventBus();                            // die klasse muss erstellt werden!
        eventBus.registerEvent(new PacketEventHandler(new ClientPacketListener(), () -> getLogger().info("Event called"), "ClientEventHandler"));

        // init connection
        clientNetHandler = new ClientNetHandler(eventBus);
clientNetHandler.establishConnection(IP, PORT, streamHandler -> getLogger().info("Channel established"));
```

event handler

```
public class ClientPacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
    // check if it is right packet
        if (event.getReceivedPacket() instanceof CreatedServerPacket) {
        // cast it 
            CreatedServerPacket packet = (CreatedServerPacket) event.getReceivedPacket();
            // do stuff with it
            API.addServer(packet.getName(), "localhost", packet.getPort());
            Main.getInstance().getLogger().info("Added server " + packet.getName() + " with port " + packet.getPort());
        }
    }
}
```

send packet 
```
StartServerPacket packet = new StartServerPacket(name); // or whatever packet
clientNetHandler.addToSendQueue(packet);
```
