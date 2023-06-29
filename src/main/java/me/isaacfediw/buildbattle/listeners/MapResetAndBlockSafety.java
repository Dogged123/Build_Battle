package me.isaacfediw.buildbattle.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static me.isaacfediw.buildbattle.commands.JoinGameCommand.playersInGame;

public class MapResetAndBlockSafety implements Listener {
    public static ArrayList<Block> placedBlocks = new ArrayList<>();
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if (e.getPlayer().isOp()){
            return;
        }
        if (e.getBlock().getType().equals(Material.TNT)){
            if (playersInGame.contains(e.getPlayer())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You cannot place tnt!");
                return;
            }
        }
        placedBlocks.add(e.getBlock());
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        if (!placedBlocks.contains(e.getBlock())) {
            if (playersInGame.contains(e.getPlayer())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (p.getInventory().getItemInMainHand().equals(new ItemStack(Material.ENDER_PEARL))){
                if (playersInGame.contains(p)){
                    p.sendMessage(ChatColor.RED + "You cannot use ender pearls here!");
                    e.setCancelled(true);
                }
            }
        }
    }
}
