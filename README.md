### Description of the DkonAuthPlugin plugin

**DkonAuthPlugin** is a plugin for Minecraft servers that provides a system for authorizing and registering players through the Dkon.app API. The plugin allows players to easily create accounts and log in using their credentials, which simplifies the process of managing users on the server.

#### Main functions:

1. **Registration verification**:
- When a player logs on to the server, the plugin automatically checks whether the player is registered in the Dkon.app system.
- If the player is registered, he is asked to log in.
   - If the player is not registered, he is invited to register.

2. **Registration**:
- Players can register using the command `/register <login> <password>`.
   - The plugin sends a request to create a new account via the Dkon.app API.
- If the login is already occupied, the player receives an appropriate error message.
   - In case of successful registration, the player is informed that he can log in.

3. **Authorization**:
- Players can log in using the command `/login <password>'.
   - The plugin sends an authorization request via the Dkon.app API.
   - If incorrect credentials are entered, the player receives an error message.
   - In case of successful authorization, the player is informed that he has successfully logged in.

4. **Interactive messages**:
- The plugin provides players with clear and understandable messages about the status of their registration and authorization, which improves the user experience.

#### Technical Details:

- The plugin is written in Java and uses the Bukkit/Spigot API to integrate with Minecraft.
- The OkHttp library is used to make HTTP requests.
- The plugin supports the commands `/register` and `/login`, which can be used by players in the chat.

#### Installation:

1. Compile the plugin and get the `.jar` file.
2. Place the file `.jar` to the 'plugins` folder of your Minecraft server.
3. Restart the server to activate the plugin.

#### Note:

The plugin requires internet access to interact with the Dkon.app API. Make sure that your server has the appropriate network settings to make external requests.
