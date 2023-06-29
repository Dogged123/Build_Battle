package me.isaacfediw.buildbattle.listeners;

import me.isaacfediw.buildbattle.listeners.UseWand;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
public class BuildJudge implements Listener {
    public static ArrayList<Integer> points = new ArrayList<>();
    private int index = 0;

    @EventHandler
    public void onVoteClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World world = p.getWorld();
        Location loc = p.getLocation();
        PlayerInventory inv = p.getInventory();
        if (e.getAction() == (Action.RIGHT_CLICK_BLOCK) || e.getAction() == (Action.RIGHT_CLICK_AIR)) {
            if (loc.equals(UseWand.arena1Loc)) {
                index = 0;
            } else if (loc.equals(UseWand.arena2Loc)) {
                index = 1;
            } else if (loc.equals(UseWand.arena3Loc)) {
                index = 2;
            } else if (loc.equals(UseWand.arena4Loc)) {
                index = 3;
            } else if (loc.equals(UseWand.arena5Loc)) {
                index = 4;
            } else if (loc.equals(UseWand.arena6Loc)) {
                index = 5;
            }
            if (inv.getItemInMainHand().getType().equals(Material.AIR)) {
                return;
            }
            if (inv.getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "Very Bad")) {
                vote(p, ChatColor.DARK_RED + "Very Bad", 1, world);
            } else if (inv.getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Bad")) {
                vote(p, ChatColor.RED + "Bad", 2, world);
            } else if (inv.getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Okay")) {
                vote(p, ChatColor.YELLOW + "Okay", 3, world);
            } else if (inv.getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GREEN + "Good")) {
                vote(p, ChatColor.DARK_GREEN + "Good", 4, world);
            } else if (inv.getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Very Good")) {
                vote(p, ChatColor.GREEN + "Very Good", 5, world);
            }
        }
    }

    public void vote(Player p, String vote, int votePoints, World world) {
        points.set(index, votePoints);
        p.sendMessage(ChatColor.LIGHT_PURPLE + "You judged this build " + vote);
        world.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        ItemStack okay = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta okayMeta = okay.getItemMeta();
        okayMeta.setDisplayName(ChatColor.YELLOW + "Okay");
        okay.setItemMeta(okayMeta);
        PlayerInventory inv = p.getInventory();
        if (inv.contains(okay)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack okay = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta okayMeta = okay.getItemMeta();
        okayMeta.setDisplayName(ChatColor.YELLOW + "Okay");
        okay.setItemMeta(okayMeta);
        PlayerInventory inv = p.getInventory();
        if (inv.contains(okay)) {
            e.setCancelled(true);
        }
    }
}
