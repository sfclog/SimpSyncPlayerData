package me.sfclog.simpsyncplayerdata;

import me.sfclog.simpsyncplayerdata.event.PlayerEvent;
import me.sfclog.simpsyncplayerdata.mongodb.MongoDataConnect;
import me.sfclog.simpsyncplayerdata.pluginconfig.PluginConfig;
import me.sfclog.simpsyncplayerdata.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Plugin pl;
    public static MongoDataConnect mongo;

    @Override
    public void onEnable() {
        pl = this;
        PluginConfig.loadlang();
        sendlog("");
        sendlog("&#65bcfb&lS&#65befb&li&#65bffb&lm&#66c1fb&lp&#66c3fb&lS&#66c5fc&ly&#66c6fc&ln&#67c8fc&lc&#67cafc&lP&#67cbfc&ll&#67cdfc&la&#68cffc&ly&#68d0fc&le&#68d2fd&lr&#68d4fd&lD&#69d6fd&la&#69d7fd&lt&#69d9fd&la &7| &fPlayer Data Sync");
        sendlog("&fAuthor: &#fbf445S&#fbf552F&#fcf65eC&#fcf76b_&#fcf778L&#fdf884o&#fdf991g");
        sendlog("");
        sendlog("&#4ffb56P&#51fb5al&#54fb5du&#56fb61g&#59fc64i&#5bfc68n &#5dfc6co&#60fc6fn &#62fc73E&#64fc77n&#67fd7aa&#69fd7eb&#6cfd81l&#6efd85e");
        sendlog("");
        mongo = new MongoDataConnect();

        getServer().getPluginManager().registerEvents(new PlayerEvent(),this);
    }

    @Override
    public void onDisable() {
        mongo.save_and_close_connect();
        sendlog("");
        sendlog("&#98fb5b&lP&#96fb66&lL&#94fb71&lA&#91fc7c&lY&#8ffc87&lE&#8dfc92&lR &#8bfc9d&lS&#89fca8&lT&#86fdb3&lA&#84fdbe&lT&#82fdc9&lS &7| &fStats Data System");
        sendlog("&fAuthor: &#fbf445S&#fbf552F&#fcf65eC&#fcf76b_&#fcf778L&#fdf884o&#fdf991g");
        sendlog("");
        sendlog("&#fb3131P&#fb3434l&#fb3636u&#fb3939g&#fc3b3bi&#fc3e3en &#fc4040o&#fc4343n &#fc4646D&#fc4848i&#fc4b4bs&#fd4d4da&#fd5050b&#fd5252l&#fd5555e");
        sendlog("");
        pl = null;
    }

    public static void sendlog(String msg) {
        Bukkit.getConsoleSender().sendMessage(Color.tran(msg));
    }
}
