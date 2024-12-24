package de.lecuutex.christmasEvent.commands;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if (!player.isOp()) return true;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("set")) {
                if (ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) {
                    return true;
                }

                ChristmasEvent.getInstance().getCanBuild().add(player.getUniqueId().toString());

                player.sendMessage(ChristmasEvent.PREFIX + "§7Du kannst mit Köpfen interagieren!");
                player.setGameMode(GameMode.CREATIVE);
                player.getInventory().clear();

                player.getInventory().setItem(0, getUpdateItem());
                player.getInventory().setItem(1, getSkullItem());
                player.getInventory().setItem(2, getCheckItem());

            } else if (args[0].equalsIgnoreCase("cancel")) {
                if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return true;
                ChristmasEvent.getInstance().getCanBuild().remove(player.getUniqueId().toString());

                player.getInventory().clear();

                ItemStack spawn = new ItemStack(Material.FIREWORK_CHARGE);
                ItemMeta meta = spawn.getItemMeta();
                meta.setDisplayName("§fZum §cSpawn §fteleportieren");
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                spawn.setItemMeta(meta);
                player.getInventory().setItem(4, spawn);

                player.sendMessage(ChristmasEvent.PREFIX + "§7Du kannst nicht mehr mit Köpfen interagieren!");

            } else if (args[0].equalsIgnoreCase("show")) {
                if (ChristmasEvent.getInstance().getShowParticle().contains(player.getUniqueId().toString())) {
                    ChristmasEvent.getInstance().getShowParticle().remove(player.getUniqueId().toString());
                    player.sendMessage(ChristmasEvent.PREFIX + "§7Dir werden die Partikel ausgeblendet!");

                } else {
                    player.sendMessage(ChristmasEvent.PREFIX + "§7Dir werden die Partikel eingeblendet!");
                    ChristmasEvent.getInstance().getShowParticle().add(player.getUniqueId().toString());
                }
            }
        } else {
            player.sendMessage(ChristmasEvent.PREFIX + "§7/head set");
            player.sendMessage(ChristmasEvent.PREFIX + "§7/head cancel");
            player.sendMessage(ChristmasEvent.PREFIX + "§7/head show");
        }
        return true;
    }

    public ItemStack getUpdateItem() {
        ItemStack itemStack = new ItemStack(Material.GOLD_SPADE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§chead_update");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack getSkullItem() {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner("snownights4ever");
        skullMeta.setDisplayName("§chead_create");
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getCheckItem() {
        ItemStack itemStack = new ItemStack(Material.REDSTONE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§chead_check");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
