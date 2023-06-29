package me.isaacfediw.buildbattle.listeners;

import me.isaacfediw.buildbattle.BuildBattle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.*;
public class ScoreBoard {

    private static ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static BuildBattle plugin;
    private static Score displayWins;
    public ScoreBoard(BuildBattle plug){
        plugin = plug;
    }
    public static void setInitialScoreboard(Player p) {
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("buildBattle", "dummy", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Build Battle");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        PersistentDataContainer stats = p.getPersistentDataContainer();
        NamespacedKey wins = new NamespacedKey(plugin, "wins");

        if (!stats.has(wins, PersistentDataType.INTEGER)){
            stats.set(wins, PersistentDataType.INTEGER, 0);
        }
        Integer playerWins = stats.get(wins, PersistentDataType.INTEGER);

        displayWins = objective.getScore(ChatColor.GOLD + "Wins: " + playerWins);
        displayWins.setScore(0);
        p.setScoreboard(scoreboard);
    }

    public static void addWin(Player p){
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("buildBattle", "dummy", ChatColor.LIGHT_PURPLE + "Build Battle");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        PersistentDataContainer stats = p.getPersistentDataContainer();
        NamespacedKey wins = new NamespacedKey(plugin, "wins");

        if (!stats.has(wins, PersistentDataType.INTEGER)){
            stats.set(wins, PersistentDataType.INTEGER, 0);
        }
        int playerWins = stats.get(wins, PersistentDataType.INTEGER) + 1;
        stats.set(wins, PersistentDataType.INTEGER, playerWins);

        displayWins = objective.getScore(ChatColor.GOLD + "Wins: " + playerWins);
        displayWins.setScore(0);
        p.setScoreboard(scoreboard);
    }

    public static void setWins(int amount, Player p){
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("buildBattle", "dummy", ChatColor.LIGHT_PURPLE + "Build Battle");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        PersistentDataContainer stats = p.getPersistentDataContainer();
        NamespacedKey wins = new NamespacedKey(plugin, "wins");

        if (!stats.has(wins, PersistentDataType.INTEGER)){
            stats.set(wins, PersistentDataType.INTEGER, 0);
        }
        stats.set(wins, PersistentDataType.INTEGER, amount);
        Integer playerWins = stats.get(wins, PersistentDataType.INTEGER);

        displayWins = objective.getScore(ChatColor.GOLD + "Wins: " + playerWins);
        displayWins.setScore(0);
        p.setScoreboard(scoreboard);
    }
}
