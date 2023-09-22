package me.sfclog.simpsyncplayerdata.utils;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/** Serializer & deserializer for bukkit inventories. */
public class InventorySerializer {


    public static String serialize(Inventory value) {
        int invSize = value.getSize();
        if (invSize % 9 != 0) invSize -= 5;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream outputStream1 = new BukkitObjectOutputStream(outputStream)) {
            outputStream1.writeInt(invSize);
            ItemStack[] items = value.getContents();
            for (int i = 0; i < invSize; i++) {
                outputStream1.writeObject(items[i]);
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean deserialize(Inventory inventory, String data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
             BukkitObjectInputStream inputStream1 = new BukkitObjectInputStream(inputStream)) {
            int len = inputStream1.readInt();
            for (int i = 0; i < len; i++) {
                inventory.setItem(i, (ItemStack) inputStream1.readObject());
            }
            return true;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
