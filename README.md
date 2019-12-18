# Portal

A multi-proxy player queue.

## How It Works
* Player joins a queue through a hub.
* Hub sends a message to the Independent module to add that player to that queue.
* Independent module constantly checks if a queue can send a player. If a player can be sent, a message is broadcasted to all Bukkit instances that the next player in the queue (based on rank priority) should be sent to the server.
* Any Bukkit instance that contains the player will use plugin messages to send the player.

## Commands
| Command syntax                   | Description                             | Permission       |
| ----------------------------     | ----------------------------------------| ---------------- |
| /queueclear \<queue\>            | Clear the list of a queue               | portal.clear     |
| /queuetoggle \<queue\>           | Toggle (pause) a queue                  | portal.toggle    |
| /forcesend \<player\> \<server\> | Force sends a player to a server        | portal.forcesend |
| /datadump                        | Displays all server data and queues     | portal.datadump  |
| /hub                             | Sends a player to a hub                 | N/A |
| /queuemenu                       | Opens queue status/toggle menu          | portal.menu |
| /leavequeue                      | Leaves the current queue                | N/A |
| /joinqueue <queue>               | Joins a queue                           | N/A |      
| /status <server>                 | Checks the status of a server           | portal.status
## Priority
Queue priority can be assigned through permissions (config.yml) or by using your own implementation.

To implement your own priority system, extend `PriorityProvider` and set the instance provider using `Portal.getInstance().setPriorityProvider(provider)`.

## Bypass
Players that have the `portal.bypass` permission will immediately be sent to a server instead of joining a queue.

## Installation 
Installation is pretty straight forward, follow my steps and you'll have Portal running in no time!

First, open CMD or Terminal, depending on your OS and create a folder. Go to that folder and place portal-independent in the folder. Then run ``java -jar portal-independent.jar``. Once that's finished, use screen to exit, that way the independent module stays running in the background. Then, place portal-bukkit on all servers except proxy. Now on to configuration. Open the folder containing portal-independent, and open the config.properties. Then, configure it accordingly, restart the independent module and you're done with the independent module! Now, open the bukkit configuration in the plugins folder and configure it accordingly, reboot and bam! It's all setup and ready for players!

## API
|Method          |Input                         |Return Value                            |
|----------------|------------------------------|----------------------------------------|
|statusString()  |Server name (String)          |Server status (String)                  |
|getServerData() |Server name (String)          |Gets server & it's data (N/A)           |
|containsPlayer()|UUID (UUID)                   |Checks if a players in a queue (Boolean)|
|getPosition()   |UUID (UUID)                   |Gets players queue position (int)       |
|sendPlayer()    |Player (Player), Queue (Queue)|Sends a player (N/A)