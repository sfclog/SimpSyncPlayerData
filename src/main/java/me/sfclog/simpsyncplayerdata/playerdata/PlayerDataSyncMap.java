package me.sfclog.simpsyncplayerdata.playerdata;

import org.bukkit.entity.Player;

import java.util.HashSet;

public class PlayerDataSyncMap {

    public static HashSet<String> map = new HashSet<>();

    public static void add(Player p) {
        if(!map.contains(p.getName())) {
            map.add(p.getName());
        }
    }
    public static void remove(Player p) {
        if(map.contains(p.getName())) {
            map.remove(p.getName());
        }
    }

    public static boolean have(Player p) {
        return map.contains(p.getName());
    }
}
