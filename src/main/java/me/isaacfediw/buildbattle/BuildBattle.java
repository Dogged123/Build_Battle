package me.isaacfediw.buildbattle;

import me.isaacfediw.buildbattle.commands.JoinGameCommand;
import me.isaacfediw.buildbattle.commands.ReceiveWand;
import me.isaacfediw.buildbattle.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuildBattle extends JavaPlugin {
    @Override
    public void onEnable() {
        ThemeVoter.themes.add("House");
        ThemeVoter.themes.add("Car");
        ThemeVoter.themes.add("Mob");
        ThemeVoter.themes.add("Book");
        ThemeVoter.themes.add("Food");
        BuildJudge.points.add(0);
        BuildJudge.points.add(0);
        BuildJudge.points.add(0);
        BuildJudge.points.add(0);
        BuildJudge.points.add(0);
        BuildJudge.points.add(0);

        //config.yml
        //getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("buildbattle").setExecutor(new JoinGameCommand(this));
        getCommand("wand").setExecutor(new ReceiveWand());
        Bukkit.getServer().getPluginManager().registerEvents(new UseWand(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new QueOrArenaHandler(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ThemeVoter(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MapResetAndBlockSafety(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BuildJudge(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        new ScoreBoard(this);
    }
}
