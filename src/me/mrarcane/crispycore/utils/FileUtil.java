package me.mrarcane.crispycore.utils;

import me.mrarcane.crispycore.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileUtil extends YamlConfiguration {

    public static FileConfiguration cfg = Main.getInstance().getConfig();
    private String name;
    private String dir;

    public FileUtil(String dir, String name){
        this.name = name + (name.endsWith(".yml") ? "" : ".yml");
        this.dir = dir;
        createFile();
    }
    private void createFile() {
        try {
            if (dir == null) {
                File file = new File(Main.getInstance().getDataFolder(), name);
                if (!file.exists()){
                    if (Main.getInstance().getResource(name) != null){
                        Main.getInstance().saveResource(name, false);
                    } else {
                        save(file);
                    }
                } else {
                    load(file);
                    save(file);
                }
                return;
            }
                File file = new File(Main.getInstance().getDataFolder() + "/" + dir + "/", name);
            if (!file.exists()){
                if (Main.getInstance().getResource(name) != null){
                    Main.getInstance().saveResource(name, false);
                } else {
                    save(file);
                }
            } else {
                load(file);
                save(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void save(){
        try {
            if (dir == null) {
                save(new File(Main.getInstance().getDataFolder(), name));
            } else {
                save(new File(Main.getInstance().getDataFolder() + "/" + dir + "/", name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
