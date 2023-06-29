package me.isaacfediw.buildbattle.commands;

import me.isaacfediw.buildbattle.BuildBattle;
import me.isaacfediw.buildbattle.listeners.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class JoinGameCommand implements CommandExecutor {
    public static ArrayList<Player> playersInGame = new ArrayList<>();
    private ArrayList<Location> prevLocs = new ArrayList<>();
    BuildBattle plugin;
    public JoinGameCommand(BuildBattle plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 0){
                if (p.isOp()){
                    p.sendMessage(ChatColor.DARK_PURPLE + "Commands:");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/bb join: joins the que");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/bb leave: leaves the que");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/wand: supplies the build battle wand");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/bb addWin: adds a win to yourself");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/bb removeWins: removes all your wins");
                }else{
                    p.sendMessage(ChatColor.DARK_PURPLE + "Commands:");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/bb join: joins the que");
                    p.sendMessage(ChatColor.DARK_PURPLE + "/bb leave: leaves the que");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("join")){
                Location queLoc = plugin.getConfig().getLocation("Que");
                Location arena1Loc = plugin.getConfig().getLocation("Arena1");
                if (queLoc == null){
                    p.sendMessage(ChatColor.DARK_PURPLE + "There is no que set up at this time!");
                    return true;
                }
                if (arena1Loc == null){
                    p.sendMessage(ChatColor.DARK_PURPLE + "There are no arenas set up at this time!");
                    return true;
                }
                if (playersInGame.contains(p)){
                    p.sendMessage(ChatColor.DARK_PURPLE + "You are already in the game!");
                    return true;
                }
                playersInGame.add(p);
                prevLocs.add(p.getLocation());
                tpPlayer(p, "Que");
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(ChatColor.LIGHT_PURPLE + "You joined the game!");
                ScoreBoard.setInitialScoreboard(p);
                ItemStack voter = new ItemStack(Material.CLOCK);
                ItemMeta voterMeta = voter.getItemMeta();
                voterMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vote");
                voter.setItemMeta(voterMeta);
                p.getInventory().clear();
                p.getInventory().setItem(4, voter);

                //Change to .size() == 2 for multiplayer testing
                if (playersInGame.size() == 2) {
                    new BukkitRunnable() {
                        int timeLeft = 16;
                        @Override
                        public void run() {
                            //change to .size() < 2 for multiplayer testing
                            if (JoinGameCommand.playersInGame.size() < 2){
                                cancel();
                                return;
                            }
                            if (timeLeft == 0){
                                for (Player player : playersInGame){
                                    switch (playersInGame.indexOf(player)){
                                        case 0:
                                            tpPlayer(player, "Arena1");
                                            break;
                                        case 1:
                                            tpPlayer(player, "Arena2");
                                            break;
                                        case 2:
                                            tpPlayer(player, "Arena3");
                                            break;
                                        case 3:
                                            tpPlayer(player, "Arena4");
                                            break;
                                        case 4:
                                            tpPlayer(player, "Arena5");
                                            break;
                                        case 5:
                                            tpPlayer(player, "Arena6");
                                            break;
                                    }
                                    player.sendMessage(ChatColor.GREEN + "Game Started!");
                                }
                                startGame();
                                cancel();
                                return;
                            }
                            timeLeft --;
                            for (Player player : playersInGame){
                                player.setLevel(timeLeft);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("leave")){
                if (!playersInGame.contains(p)){
                    p.sendMessage(ChatColor.DARK_PURPLE + "You are not in the game!");
                    return true;
                }
                p.setLevel(0);
                p.sendMessage(ChatColor.LIGHT_PURPLE + "You left the game!");
                p.teleport(prevLocs.get(playersInGame.indexOf(p)));
                prevLocs.remove(playersInGame.indexOf(p));
                playersInGame.remove(p);
                p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                p.getInventory().clear();
                return true;
            }

            if (args[0].equalsIgnoreCase("addWin")){
                if (!p.isOp()){
                    p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                    return true;
                }
                ScoreBoard.addWin(p);
                p.sendMessage(ChatColor.GREEN + "Win added!");
            }
            if (args[0].equalsIgnoreCase("removeWins")){
                if (!p.isOp()){
                    p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                    return true;
                }
                ScoreBoard.setWins(0, p);
            }
        }
        return true;
    }
    public void startGame(){
        BuildJudge.points.set(0, 0);
        BuildJudge.points.set(1, 0);
        BuildJudge.points.set(2, 0);
        BuildJudge.points.set(3, 0);
        BuildJudge.points.set(4, 0);
        BuildJudge.points.set(5, 0);

        for (Player player : playersInGame){
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
        }
        int theme = (int) (Math.random() * ThemeVoter.themes.size()) + 1;
        String theTheme = ThemeVoter.themes.get(theme);

        for (Player pl : playersInGame){
            pl.sendTitle(ChatColor.LIGHT_PURPLE + theTheme, ChatColor.LIGHT_PURPLE + "is the theme!", 10, 30, 10);
            pl.sendMessage(ChatColor.LIGHT_PURPLE + "The theme is " + theTheme + "!");
        }
        ThemeVoter.haveClicked.clear();
        ThemeVoter.themes.clear();
        ThemeVoter.themes.add("House");
        ThemeVoter.themes.add("Car");
        ThemeVoter.themes.add("Mob");
        ThemeVoter.themes.add("Book");

        ItemStack veryBad = new ItemStack(Material.RED_WOOL);
        ItemMeta veryBadMeta = veryBad.getItemMeta();
        veryBadMeta.setDisplayName(ChatColor.DARK_RED + "Very Bad");
        veryBad.setItemMeta(veryBadMeta);

        ItemStack bad = new ItemStack(Material.ORANGE_WOOL);
        ItemMeta badMeta = bad.getItemMeta();
        badMeta.setDisplayName(ChatColor.RED + "Bad");
        bad.setItemMeta(badMeta);

        ItemStack okay = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta okayMeta = okay.getItemMeta();
        okayMeta.setDisplayName(ChatColor.YELLOW + "Okay");
        okay.setItemMeta(okayMeta);

        ItemStack good = new ItemStack(Material.GREEN_WOOL);
        ItemMeta goodMeta = good.getItemMeta();
        goodMeta.setDisplayName(ChatColor.DARK_GREEN + "Good");
        good.setItemMeta(goodMeta);

        ItemStack veryGood = new ItemStack(Material.LIME_WOOL);
        ItemMeta veryGoodMeta = veryGood.getItemMeta();
        veryGoodMeta.setDisplayName(ChatColor.GREEN + "Very Good");
        veryGood.setItemMeta(veryGoodMeta);

        new BukkitRunnable(){
            Integer timeLeft = 8;
            @Override
            public void run() {
                if (timeLeft == 0){
                    for (Player pl : playersInGame){
                        tpPlayer(pl, "Arena1");
                        pl.getInventory().clear();
                        pl.getInventory().setItem(2, veryBad);
                        pl.getInventory().setItem(3, bad);
                        pl.getInventory().setItem(4, okay);
                        pl.getInventory().setItem(5, good);
                        pl.getInventory().setItem(6, veryGood);
                    }
                    new BukkitRunnable(){
                        int arena = 1;
                        Integer viewTimeLeft = 15;
                        @Override
                        public void run() {
                            if (arena == playersInGame.size() + 1){
                                int votes = 0;
                                boolean anyVotes = false;
                                for (int ignored : BuildJudge.points) {
                                    votes ++;
                                }
                                if (votes > 0) anyVotes = true;
                                if (!anyVotes){
                                    for (Player pl : playersInGame){
                                        pl.sendMessage(ChatColor.LIGHT_PURPLE + "Since no one voted, the game is a tie!");
                                    }
                                    endGame();
                                    cancel();
                                    return;
                                }
                                Integer p1Points = BuildJudge.points.get(0);
                                Integer p2Points = BuildJudge.points.get(1);
                                Integer p3Points = BuildJudge.points.get(2);
                                Integer p4Points = BuildJudge.points.get(3);
                                Integer p5Points = BuildJudge.points.get(4);
                                Integer p6Points = BuildJudge.points.get(5);
                                Integer highest = 0;

                                Player winner = null;

                                for (int i = 0; i < BuildJudge.points.size(); i++){
                                    if (BuildJudge.points.get(i) > highest){
                                        highest = BuildJudge.points.get(i);
                                    }
                                    if (Objects.equals(p1Points, highest)){
                                        winner = playersInGame.get(0);
                                    }else if (Objects.equals(p2Points, highest)){
                                        winner = playersInGame.get(1);
                                    }else if (Objects.equals(p3Points, highest)){
                                        winner = playersInGame.get(2);
                                    }else if (Objects.equals(p4Points, highest)){
                                        winner = playersInGame.get(3);
                                    }else if (Objects.equals(p5Points, highest)){
                                        winner = playersInGame.get(4);
                                    }else if (Objects.equals(p6Points, highest)) {
                                        winner = playersInGame.get(5);
                                    }
                                }

                                ScoreBoard.addWin(winner);
                                endGame();
                                for (Player player : playersInGame) {
                                    player.sendTitle(ChatColor.GREEN + winner.getName() + " wins!", "", 10, 40, 10);
                                    player.sendMessage(ChatColor.GREEN + "The winner is " + winner.getName() + "!");
                                }
                                playersInGame.clear();
                                cancel();
                                return;
                            }

                            if (viewTimeLeft == 0){
                                arena ++;
                                switch(arena){
                                    case 2:
                                        for (Player player : playersInGame) {
                                            tpPlayer(player, "Arena2");
                                        }
                                        break;
                                    case 3:
                                        for (Player player : playersInGame) {
                                            tpPlayer(player, "Arena3");
                                        }
                                        break;
                                    case 4:
                                        for (Player player : playersInGame) {
                                            tpPlayer(player, "Arena4");
                                        }
                                        break;
                                    case 5:
                                        for (Player player : playersInGame) {
                                            tpPlayer(player, "Arena5");
                                        }
                                        break;
                                    case 6:
                                        for (Player player : playersInGame) {
                                            tpPlayer(player, "Arena6");
                                        }
                                        break;
                                }
                                viewTimeLeft = 15;
                            }
                                for (Player pl : playersInGame){
                                    pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(viewTimeLeft.toString()));
                                }
                                viewTimeLeft --;
                            }
                    }.runTaskTimer(plugin, 0, 20);
                    cancel();
                    return;
                }
                if (playersInGame.size() < 2){
                    for (Player player : playersInGame){
                        player.sendMessage(ChatColor.GREEN + "Since everyone except you has left the game, the game is now over and you win by default!");
                        player.getInventory().clear();
                        player.setGameMode(GameMode.ADVENTURE);
                        tpPlayer(player, "Que");
                    }
                    playersInGame.clear();
                    cancel();
                    return;
                }
                for (Player pl : playersInGame){
                    pl.sendMessage(ChatColor.LIGHT_PURPLE + timeLeft.toString() + " minutes left!");
                }
                timeLeft --;
            }
        }.runTaskTimer(plugin, 0, 1200);
    }

    public void endGame(){
        for (Player pl : playersInGame){
            pl.setLevel(0);
            tpPlayer(pl, "Que");
            pl.setGameMode(GameMode.ADVENTURE);
            pl.getInventory().clear();
        }
        for (Block b : MapResetAndBlockSafety.placedBlocks){
            b.setType(Material.AIR);
        }
        MapResetAndBlockSafety.placedBlocks.clear();
    }

    public void tpPlayer(Player p, String locationName){
        Location loc = plugin.getConfig().getLocation(locationName);
        if (loc!= null) p.teleport(loc);
    }
}
