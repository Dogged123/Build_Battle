package me.isaacfediw.buildbattle.listeners;

import me.isaacfediw.buildbattle.commands.JoinGameCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e){
        Player p = e.getPlayer();
        JoinGameCommand.playersInGame.remove(p);
    }
}
