package me.sfclog.simpsyncplayerdata.event;

import me.sfclog.simpsyncplayerdata.Main;
import me.sfclog.simpsyncplayerdata.playerdata.PlayerData;
import me.sfclog.simpsyncplayerdata.playerdata.PlayerDataSyncMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class PlayerEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Main.mongo.load_data(e.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Main.mongo.save_data(e.getPlayer());
    }
    @EventHandler
    public void onPlayerCW(PlayerChangedWorldEvent e) {Main.mongo.save_data(e.getPlayer());
    }

    @EventHandler
    public void oBreak(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(PlayerDataSyncMap.have(p)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPickup(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(PlayerDataSyncMap.have(p)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(PlayerDataSyncMap.have(p)) {
            e.setCancelled(true);
        }
    }
}
