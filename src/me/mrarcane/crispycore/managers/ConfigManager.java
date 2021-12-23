package me.mrarcane.crispycore.managers;

import com.google.common.base.Charsets;
import me.mrarcane.crispycore.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager {
    public void reloadConfig() {
        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration newConfig;
        newConfig = YamlConfiguration.loadConfiguration(file);
        InputStream defConfigStream = Main.getInstance().getResource("config.yml");
        if(defConfigStream != null) {
            newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        }
        Main.getInstance().reloadConfig();
    }
}
