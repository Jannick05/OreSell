package dk.oresell.commands;

import dk.oresell.OreSell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Sell implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Inventory inventory = player.getInventory();
        int totalSellPrice = 0;

        // Loop through the player's inventory
        for (ItemStack item : inventory.getContents()) {
            if (item == null) {
                continue;
            }
            player.sendMessage(item.toString());
            if (OreSell.materialYML.contains("Sellables."+item.getType())) {
                int quantity = item.getAmount();
                int sellPrice = quantity * OreSell.materialYML.getInt("Sellables." + item.getType() + ".price");
                player.getInventory().removeItem(item);
                totalSellPrice += sellPrice;
            }
        }
        player.sendMessage("solgt for " + totalSellPrice);
        return true;
    }
}
