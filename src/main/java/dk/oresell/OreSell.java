package dk.oresell;

import dk.orecore.api.utils.Config;
import dk.oresell.commands.Sell;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class OreSell extends JavaPlugin {
    public static OreSell instance;
    private static PluginManager pluginManager;
    public static Config multiplier, material, config;
    public static FileConfiguration multiplierYML, materialYML, configYML;
    @Override
    public void onEnable() {
        pluginManager = getServer().getPluginManager();
        instance = this;

        //MATERIAL.YML
        if (!(new File(getDataFolder(), "material.yml")).exists())
            saveResource("material.yml", false);

        material = new Config(this, null, "material.yml");
        materialYML = material.getConfig();

        //COMMANDS
        getCommand("Sell").setExecutor(new Sell());

    }

    @Override
    public void onDisable() {
        config.saveConfig();
        multiplier.saveConfig();
        material.saveConfig();
    }
}
