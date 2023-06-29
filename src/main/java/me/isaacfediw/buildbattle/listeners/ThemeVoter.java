package me.isaacfediw.buildbattle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
public class ThemeVoter implements Listener {
    public static ArrayList<String> themes = new ArrayList<>();
    public static ArrayList<Player> haveClicked = new ArrayList<>();
    @EventHandler
    public void onVoterClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                return;
            }
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Vote")){
                Inventory vote = Bukkit.createInventory(p, 9, "Voter");
                ItemStack house = new ItemStack(Material.OAK_LOG);
                ItemMeta houseMeta = house.getItemMeta();
                houseMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vote for house");
                house.setItemMeta(houseMeta);

                ItemStack car = new ItemStack(Material.COAL);
                ItemMeta carMeta = car.getItemMeta();
                carMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vote for car");
                car.setItemMeta(carMeta);

                ItemStack mob = new ItemStack(Material.SPAWNER);
                ItemMeta mobMeta = mob.getItemMeta();
                mobMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vote for mob");
                mob.setItemMeta(mobMeta);

                ItemStack book = new ItemStack(Material.BOOK);
                ItemMeta bookMeta = book.getItemMeta();
                bookMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vote for book");
                book.setItemMeta(bookMeta);

                ItemStack food = new ItemStack(Material.GLISTERING_MELON_SLICE);
                ItemMeta foodMeta = food.getItemMeta();
                foodMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vote for food");
                food.setItemMeta(foodMeta);

                ItemStack cancel = new ItemStack(Material.BARRIER);
                ItemMeta cancelMeta = cancel.getItemMeta();
                cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
                cancel.setItemMeta(cancelMeta);

                vote.setItem(0, house);
                vote.setItem(2, car);
                vote.setItem(4, mob);
                vote.setItem(6, book);
                vote.setItem(7, food);
                vote.setItem(8, cancel);

                p.openInventory(vote);
            }
        }
    }

    @EventHandler
    public void onVoteClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Voter")) {
            if (haveClicked.contains(p) && !e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.closeInventory();
                p.sendMessage(ChatColor.DARK_PURPLE + "You can only vote once!");
                return;
            }
            switch (e.getCurrentItem().getType()) {
                case OAK_LOG:
                    themes.add("House");
                    p.closeInventory();
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "Voted for house!");
                    haveClicked.add(p);
                    break;
                case COAL:
                    themes.add("Car");
                    p.closeInventory();
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "Voted for car!");
                    haveClicked.add(p);
                    break;
                case SPAWNER:
                    themes.add("Mob");
                    p.closeInventory();
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "Voted for mob!");
                    haveClicked.add(p);
                    break;
                case BOOK:
                    themes.add("Book");
                    p.closeInventory();
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "Voted for book!");
                    haveClicked.add(p);
                    break;
                case GLISTERING_MELON_SLICE:
                    themes.add("Food");
                    p.closeInventory();
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "Voted for food!");
                    haveClicked.add(p);
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
            }
            e.setCancelled(true);
        }
    }
}
