package me.sfclog.simpsyncplayerdata.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.sfclog.simpsyncplayerdata.utils.InventorySerializer;
import me.sfclog.simpsyncplayerdata.utils.ItemStackSerializer;
import me.sfclog.simpsyncplayerdata.utils.PotionSerializer;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    public String name;
    public double health;
    public int food;
    public float exp;
    public int hotbar_slot;
    public String gamemode;
    public String inventory;
    public String enderchest;
    public String helmet,chestplace,leggings,boots;
    public String offhand;
    public float run_speed,fly_speed;
    public List<String> potions;

    public PlayerData(Player p) {
        //data
        this.name = p.getName();
        this.health = p.getHealth();
        this.food = p.getFoodLevel();
        this.exp = p.getExp();
        this.hotbar_slot = p.getInventory().getHeldItemSlot();
        this.gamemode = p.getGameMode().name();
        this.run_speed = p.getWalkSpeed();
        this.fly_speed = p.getFlySpeed();

        //inventory
        this.inventory = InventorySerializer.serialize(p.getInventory());
        this.enderchest= InventorySerializer.serialize(p.getEnderChest());

        //armor
        this.helmet = ItemStackSerializer.serialize(p.getInventory().getHelmet());
        this.chestplace = ItemStackSerializer.serialize(p.getInventory().getChestplate());
        this.leggings = ItemStackSerializer.serialize(p.getInventory().getLeggings());
        this.boots = ItemStackSerializer.serialize(p.getInventory().getBoots());

        //offhand
        this.offhand = ItemStackSerializer.serialize(p.getInventory().getItemInOffHand());

        //potion
        potions = new ArrayList<>();
        for(PotionEffect potion : p.getActivePotionEffects()) {
            if(potion != null) {
                potions.add(PotionSerializer.serialize(potion));
            }
        }
    }

    public void sync(Player p) {

      //sync player data
     AttributeInstance healthAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      if (healthAttribute != null) {
            healthAttribute.setBaseValue(this.health <= 1 ? 20 : this.health);
            p.setHealth(this.health <= 1 ? 20 : this.health);
      }

      p.setFoodLevel(this.food);
      p.setExp(this.exp);
      p.setGameMode(GameMode.valueOf(this.gamemode));
      if(this.fly_speed != 0) p.setFlySpeed(this.fly_speed);
      if(this.run_speed != 0) p.setWalkSpeed(this.run_speed);

      //sync inventory
      InventorySerializer.deserialize(p.getInventory(),this.inventory);
      InventorySerializer.deserialize(p.getEnderChest(),this.enderchest);
      p.getInventory().setHeldItemSlot(this.hotbar_slot);


      //sync armor
       p.getInventory().setHelmet(ItemStackSerializer.deserialize(this.helmet));
       p.getInventory().setChestplate(ItemStackSerializer.deserialize(this.chestplace));
       p.getInventory().setLeggings(ItemStackSerializer.deserialize(this.leggings));
       p.getInventory().setBoots(ItemStackSerializer.deserialize(this.boots));

       //offhand
       p.getInventory().setItemInOffHand(ItemStackSerializer.deserialize(this.offhand));

       //potion
        p.getActivePotionEffects().forEach(po -> p.removePotionEffect(po.getType()));
        for(String potion : this.potions) {
            if(potion != null) {
                PotionEffect effect = PotionSerializer.deserialize(potion);
                if(effect != null) {
                    effect.apply(p);
                }
            }
        }
       PlayerDataSyncMap.remove(p);

    }
    public String json_cover() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonString = gson.toJson(this);
        if(jsonString != null) {
            return jsonString;
        }
        return null;
    }


}
