package me.sfclog.simpsyncplayerdata.mongodb;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReturnDocument;
import dev.morphia.Datastore;
import dev.morphia.ModifyOptions;
import dev.morphia.Morphia;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import me.sfclog.simpsyncplayerdata.Main;
import me.sfclog.simpsyncplayerdata.playerdata.PlayerData;
import me.sfclog.simpsyncplayerdata.playerdata.PlayerDataSync;
import me.sfclog.simpsyncplayerdata.playerdata.PlayerDataSyncMap;
import me.sfclog.simpsyncplayerdata.pluginconfig.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;

public class MongoDataConnect {

    public MongoClient mongoClient;
    public Datastore datastore;

    public MongoDataConnect() {
        String host = PluginConfig.getlang("DataBase.MongoDB.Host");
        int port = PluginConfig.getint("DataBase.MongoDB.Port");
        String user = PluginConfig.getlang("DataBase.MongoDB.User");
        String password = PluginConfig.getlang("DataBase.MongoDB.Password");
        String database = PluginConfig.getlang("DataBase.MongoDB.DataBase");
        try {
            String encodedUser = URLEncoder.encode(user, "UTF-8");
            String encodedPassword = URLEncoder.encode(password, "UTF-8");
            String connectionString = "mongodb://" + encodedUser + ":" + encodedPassword + "@" + host + ":" + port;
            mongoClient =MongoClients.create(connectionString);
            if(mongoClient != null) {
                Main.sendlog("&8(&eSimpSyncPlayerData&8) &aConnect success to MongoDB.");
                MongoDatabase data = mongoClient.getDatabase(database);
                datastore = Morphia.createDatastore( mongoClient, database);
                //load data in sql
                Bukkit.getOnlinePlayers().forEach(p ->  load_data(p));

            } else {
                Main.sendlog("&8(&eSimpSyncPlayerData&8) &aConnect fail to MongoDB.");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Main.sendlog("&8(&eSimpSyncPlayerData&8) &cFailed to encode username or password for MongoDB connection.");
        }
    }



    public void save_data(Player p) {
        PlayerData data = new PlayerData(p);
        if(data != null) {
            datastore.find(MongoData.class)
                    .filter(Filters.eq("player",p.getName()))
                    .modify(UpdateOperators.set("json_data",data.json_cover()))
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
        }
    }
    public void load_data(Player p) {
        PlayerDataSyncMap.add(p);
        Bukkit.getScheduler().runTaskLater(Main.pl, new Runnable() {
            @Override
            public void run() {
                MongoData u = datastore.find(MongoData.class).filter(Filters.eq("player",p.getName())).first();
                if(u != null) {
                    //load data player
                    PlayerDataSync.cover_json_and_add(p,u.getJson());
                } else {
                    //load new player
                    PlayerData data = new PlayerData(p);
                    MongoData md = new MongoData();
                    md.setPlayer(p.getName());
                    md.setJson(data.json_cover());
                    datastore.save(md);
                    PlayerDataSyncMap.remove(p);
                }
            }
        }, 20L);
    }

    public void save_and_close_connect() {
        if(mongoClient != null) {
            CompletableFuture.runAsync(() -> {
                Bukkit.getOnlinePlayers().forEach(p -> save_data(p));
            });
            mongoClient.close();
        }
    }
}
