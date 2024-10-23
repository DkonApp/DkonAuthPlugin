package app.Dkon.Auth;

import org.bukkit.plugin.java.JavaPlugin;

public class DkonAuthPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("DkonAuthPlugin включен!");
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getCommand("register").setExecutor(new AuthCommandExecutor());
        getCommand("login").setExecutor(new AuthCommandExecutor());
    }

    @Override
    public void onDisable() {
        getLogger().info("DkonAuthPlugin выключен!");
    }
}
