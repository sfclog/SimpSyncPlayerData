package me.sfclog.simpsyncplayerdata.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;

import java.time.LocalDate;

public class PlayerDataSync {

    public static boolean cover_json_and_add(Player p, String jsondata) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        PlayerData data = gson.fromJson(jsondata, PlayerData.class);
        if(data != null) {
            data.sync(p);
            return true;
        }
        return false;
    }
}
