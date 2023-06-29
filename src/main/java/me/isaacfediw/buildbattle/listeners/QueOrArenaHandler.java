package me.isaacfediw.buildbattle.listeners;

import me.isaacfediw.buildbattle.BuildBattle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QueOrArenaHandler implements Listener {

    BuildBattle plugin;
    public QueOrArenaHandler(BuildBattle p){
        plugin = p;
    }
    @EventHandler
    public void onQueOrArenaClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("MakeQueOrArena")){
            switch (e.getSlot()){
                case 0:
                    p.closeInventory();
                    setArena("Arena1",p);
                    p.sendMessage();
                    break;
                case 1:
                    p.closeInventory();
                    setArena("Arena2", p);
                    break;
                case 2:
                    p.closeInventory();
                    setArena("Arena3", p);
                    break;
                case 3:
                    p.closeInventory();
                    setArena("Arena4", p);
                    break;
                case 4:
                    p.closeInventory();
                    setArena("Arena5", p);
                    break;
                case 5:
                    p.closeInventory();
                    setArena("Arena6", p);
                    break;
                case 8:
                    p.closeInventory();
                    setArena("Que", p);
                    break;
            }
            e.setCancelled(true);
        }
        if (e.getView().getTitle().equalsIgnoreCase("DeleteQueOrArena")){
            switch (e.getSlot()){
                case 0:
                    if (plugin.getConfig().get("Arena1") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Arena is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Arena1", p);
                    break;
                case 1:
                    if (plugin.getConfig().get("Arena2") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Arena is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Arena2", p);
                    break;
                case 2:
                    if (plugin.getConfig().get("Arena3") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Arena is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Arena3", p);
                    break;
                case 3:
                    if (plugin.getConfig().get("Arena4") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Arena is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Arena4", p);
                    break;
                case 4:
                    if (plugin.getConfig().get("Arena5") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Arena is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Arena5", p);
                    break;
                case 5:
                    if (plugin.getConfig().get("Arena6") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Arena is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Arena6", p);
                    break;
                case 8:
                    if (plugin.getConfig().get("Que") == null){
                        p.sendMessage(ChatColor.DARK_PURPLE + "Que is not set up!");
                        p.closeInventory();
                        return;
                    }
                    p.closeInventory();
                    removeArena("Que", p);
                    break;

            }
            e.setCancelled(true);
        }
    }

    public void setArena(String locationName, Player p){
        Location loc = p.getLocation();

        plugin.getConfig().set(locationName, loc);
        plugin.saveConfig();
        p.sendMessage(ChatColor.LIGHT_PURPLE + locationName + " Location set to your location!");
    }

    public void removeArena(String locationName, Player p){
        plugin.getConfig().set(locationName, null);
        plugin.saveConfig();
        p.sendMessage(ChatColor.LIGHT_PURPLE + locationName + " removed!");
    }
}
