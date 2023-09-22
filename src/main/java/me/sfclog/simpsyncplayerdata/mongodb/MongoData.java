package me.sfclog.simpsyncplayerdata.mongodb;


import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("MongoData")
public class MongoData {
    @Id
    public String player;
    public String json_data;

    public void setPlayer(String player) {
        this.player = player;
    }
    public void setJson(String json) {
        this.json_data = json;
    }
    public String getPlayer() {
        return this.player;
    }
    public String getJson() {
        return this.json_data;
    }
}
