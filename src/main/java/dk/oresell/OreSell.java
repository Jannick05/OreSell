package dk.oresell;

import dk.oresell.commands.Sell;
import dk.oresell.utils.Config;
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

        //CONFIG.YML
        if (!(new File(getDataFolder(), "config.yml")).exists())
            saveResource("config.yml", false);

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();

        //MATERIAL.YML
        if (!(new File(getDataFolder(), "material.yml")).exists())
            saveResource("material.yml", false);

        material = new Config(this, null, "material.yml");
        materialYML = material.getConfig();

        //MULTIPLIER.YML
        if (!(new File(getDataFolder(), "multiplier.yml")).exists())
            saveResource("multiplier.yml", false);

        multiplier = new Config(this, null, "multiplier.yml");
        multiplierYML = multiplier.getConfig();

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