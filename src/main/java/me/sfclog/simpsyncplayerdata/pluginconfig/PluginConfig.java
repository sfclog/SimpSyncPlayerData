package me.sfclog.simpsyncplayerdata.pluginconfig;

import me.sfclog.simpsyncplayerdata.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PluginConfig {

    public static File locate = new File("plugins/SimpSyncPlayerData/", "config.yml");
    public static FileConfiguration DataFile = (FileConfiguration) YamlConfiguration.loadConfiguration(locate);

    public static void loadlang() {
        //config
        addlang("DataBase.MongoDB.Host","0.0.0.0");
        addlang("DataBase.MongoDB.Port",2553);
        addlang("DataBase.MongoDB.User","root");
        addlang("DataBase.MongoDB.Password","njt");
        addlang("DataBase.MongoDB.DataBase","simpsyncplayerdata");

        try {
            DataFile.save(locate);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void save() {
        try {
            DataFile.save(locate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getint(String s) {
        if(DataFile.contains(s)) {
            return DataFile.getInt(s);
        }
        return 0;
    }
    public static double getdoubl(String s) {
        if(DataFile.contains(s)) {
            return DataFile.getDouble(s);
        }
        return 0;
    }
    public static boolean getb(String s) {
        if(DataFile.contains(s)) {
            return DataFile.getBoolean(s);
        }
        return false;
    }
    public static String getlang(String s) {
        if(DataFile.contains(s)) {
            return Color.tran(DataFile.getString(s));
        }
        return s;
    }
    public static void addlang(String s , double s2) {
        if(!DataFile.contains(s)) {
            DataFile.set(s, s2);
        }
    }
    public static void addlang(String s , Boolean s2) {
        if(!DataFile.contains(s)) {
            DataFile.set(s, s2);
        }
    }
    public static void addlang(String s , List<String> s2) {
        if(!DataFile.contains(s)) {
            DataFile.set(s, s2);
        }
    }
    public static void setforcelang(String s , String s2) {
        DataFile.set(s, s2);
        save();
    }
    public static void setforcelang(String s, double x) {
        DataFile.set(s, x);
        save();
    }
    public static void addlang(String s , String s2) {
        if(!DataFile.contains(s)) {
            DataFile.set(s, s2);
        }
    }
    public static void addlang(String s , int s2) {
        if(!DataFile.contains(s)) {
            DataFile.set(s, s2);
        }
    }



}
