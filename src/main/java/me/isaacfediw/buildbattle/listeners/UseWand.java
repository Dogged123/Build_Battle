package me.isaacfediw.buildbattle.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UseWand implements Listener {
    public static Location arena1Loc;
    public static Location arena2Loc;
    public static Location arena3Loc;
    public static Location arena4Loc;
    public static Location arena5Loc;
    public static Location arena6Loc;
    public static Location queLoc;
    @EventHandler
    public void wandClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == (Action.RIGHT_CLICK_BLOCK) || e.getAction() == (Action.RIGHT_CLICK_AIR)){
            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                return;
            }
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "BuildBattle Map Wand")){
                Inventory queOrArena = Bukkit.createInventory(p, 9, "MakeQueOrArena");

                ItemStack arena1 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena1Meta = arena1.getItemMeta();
                arena1Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 1");
                arena1.setItemMeta(arena1Meta);

                ItemStack arena2 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena2Meta = arena2.getItemMeta();
                arena2Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 2");
                arena2.setItemMeta(arena2Meta);

                ItemStack arena3 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena3Meta = arena3.getItemMeta();
                arena3Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 3");
                arena3.setItemMeta(arena3Meta);

                ItemStack arena4 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena4Meta = arena4.getItemMeta();
                arena4Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 4");
                arena4.setItemMeta(arena4Meta);

                ItemStack arena5 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena5Meta = arena5.getItemMeta();
                arena5Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 5");
                arena5.setItemMeta(arena5Meta);

                ItemStack arena6 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena6Meta = arena6.getItemMeta();
                arena6Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 6");
                arena6.setItemMeta(arena6Meta);

                ItemStack que = new ItemStack(Material.GLASS);
                ItemMeta queMeta = que.getItemMeta();
                queMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Que");
                que.setItemMeta(queMeta);

                queOrArena.setItem(0, arena1);
                queOrArena.setItem(1, arena2);
                queOrArena.setItem(2, arena3);
                queOrArena.setItem(3, arena4);
                queOrArena.setItem(4, arena5);
                queOrArena.setItem(5, arena6);
                queOrArena.setItem(8, que);
                p.openInventory(queOrArena);
            }
        }
        if (e.getAction() == (Action.LEFT_CLICK_BLOCK) || e.getAction() == (Action.LEFT_CLICK_AIR)){
            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                return;
            }
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "BuildBattle Map Wand")){
                Inventory DelQueOrArena = Bukkit.createInventory(p, 9, "DeleteQueOrArena");

                ItemStack arena1 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena1Meta = arena1.getItemMeta();
                arena1Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 1");
                arena1.setItemMeta(arena1Meta);

                ItemStack arena2 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena2Meta = arena2.getItemMeta();
                arena2Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 2");
                arena2.setItemMeta(arena2Meta);

                ItemStack arena3 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena3Meta = arena3.getItemMeta();
                arena3Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 3");
                arena3.setItemMeta(arena3Meta);

                ItemStack arena4 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena4Meta = arena4.getItemMeta();
                arena4Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 4");
                arena4.setItemMeta(arena4Meta);

                ItemStack arena5 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena5Meta = arena5.getItemMeta();
                arena5Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 5");
                arena5.setItemMeta(arena5Meta);

                ItemStack arena6 = new ItemStack(Material.OAK_PLANKS);
                ItemMeta arena6Meta = arena6.getItemMeta();
                arena6Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arena 6");
                arena6.setItemMeta(arena6Meta);

                ItemStack que = new ItemStack(Material.GLASS);
                ItemMeta queMeta = que.getItemMeta();
                queMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Que");
                que.setItemMeta(queMeta);

                DelQueOrArena.setItem(0, arena1);
                DelQueOrArena.setItem(1, arena2);
                DelQueOrArena.setItem(2, arena3);
                DelQueOrArena.setItem(3, arena4);
                DelQueOrArena.setItem(4, arena5);
                DelQueOrArena.setItem(5, arena6);
                DelQueOrArena.setItem(8, que);
                p.openInventory(DelQueOrArena);
            }
        }
    }
}
