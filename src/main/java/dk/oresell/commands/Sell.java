package dk.oresell.commands;

import dk.orecore.core.Multiplier;
import dk.orecore.api.hooks.VaultHook;
import dk.oresell.OreSell;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Sell implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        int multiplier = Multiplier.getMultiplier(player);
        player.sendMessage(String.valueOf(multiplier));
        Inventory inventory = player.getInventory();
        int totalSellPrice = 0;

        // Loop through the player's inventory
        for (ItemStack item : inventory.getContents()) {
            if (item == null) {
                continue;
            }
            player.sendMessage(item.toString());
            Material itemType = item.getType();
            short itemData = item.getDurability();
            if (OreSell.materialYML.contains("Sellables." + itemType.name())) {
                ConfigurationSection sellableSection = OreSell.materialYML.getConfigurationSection("Sellables." + itemType.name());
                if (sellableSection.contains("data") && sellableSection.getInt("data") != itemData) {
                    continue; // data value doesn't match
                }
                int quantity = item.getAmount();
                int sellPrice = multiplier * (quantity * sellableSection.getInt("price"));
                player.getInventory().removeItem(item);
                totalSellPrice += sellPrice;
            }
        }
        VaultHook.addBalance(player, totalSellPrice);
        player.sendMessage("solgt for " + totalSellPrice + " med " + multiplier + "x");
        return true;
    }
}
