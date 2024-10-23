package app.Dkon.Auth;

import okhttp3.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class AuthCommandExecutor implements CommandExecutor {
    private final OkHttpClient client;

    public AuthCommandExecutor() {
        this.client = new OkHttpClient();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда доступна только игрокам.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("register")) {
            if (args.length != 2) {
                player.sendMessage("Используйте: /register <логин> <пароль>");
                return true;
            }
            String username = args[0];
            String password = args[1];
            registerPlayer(username, password, player);
        } else if (command.getName().equalsIgnoreCase("login")) {
            if (args.length != 1) {
                player.sendMessage("Используйте: /login <пароль>");
                return true;
            }
            String password = args[0];
                        loginPlayer(player.getName(), password, player);
        }

        return true;
    }

    private void registerPlayer(String username, String password, Player player) {
        String email = username + "@mplug.dkon.app"; // Пример email

        RequestBody formBody = new FormBody.Builder()
                .add("clientId", "1302")
                .add("fcm_regId", generateRandomString(6, 10))
                .add("username", username)
                .add("fullname", username)
                .add("password", password)
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url("https://api.dkon.app/api/v2/method/account.signUp")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                player.sendMessage("Ошибка при регистрации. Попробуйте еще раз.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    if (responseBody.contains("\"error\":true")) {
                        player.sendMessage("Ошибка: Логин уже занят.");
                    } else {
                        player.sendMessage("Регистрация прошла успешно! Теперь вы можете войти.");
                    }
                }
            }
        });
    }

    private void loginPlayer(String username, String password, Player player) {
        RequestBody formBody = new FormBody.Builder()
                .add("clientId", "1302")
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("https://api.dkon.app/api/v3/method/account.signIn")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                player.sendMessage("Ошибка при авторизации. Попробуйте еще раз.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    if (responseBody.contains("\"error\":true")) {
                        player.sendMessage("Ошибка: Неверный логин или пароль.");
                    } else {
                        player.sendMessage("Вы успешно авторизовались!");
                        // Здесь можно добавить логику для сохранения состояния игрока
                    }
                }
            }
        });
    }

    private String generateRandomString(int minLength, int maxLength) {
        int length = (int) (Math.random() * (maxLength - minLength + 1)) + minLength;
        StringBuilder sb = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
