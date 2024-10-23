package app.Dkon.Auth;

import okhttp3.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoinListener implements Listener {
    private final DkonAuthPlugin plugin;
    private final OkHttpClient client;

    public PlayerJoinListener(DkonAuthPlugin plugin) {
        this.plugin = plugin;
        this.client = new OkHttpClient();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String username = player.getName();

        // Проверка, зарегистрирован ли игрок
        checkIfRegistered(username, player);
    }

    private void checkIfRegistered(String username, Player player) {
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();

        Request request = new Request.Builder()
                .url("https://api.dkon.app/api/v3/method/app.checkUsername")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                player.sendMessage("Ошибка при проверке регистрации.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    if (responseBody.contains("\"error\":true")) {
                        // Игрок зарегистрирован, предложить авторизацию
                        player.sendMessage("Вы зарегистрированы. Пожалуйста, авторизуйтесь с помощью команды /login <пароль>.");
                    } else {
                        // Игрок не зарегистрирован, предложить регистрацию
                        player.sendMessage("Вы не зарегистрированы. Пожалуйста, пройдите регистрацию с помощью команды /register <логин> <пароль>.");
                    }
                }
            }
        });
    }
}
