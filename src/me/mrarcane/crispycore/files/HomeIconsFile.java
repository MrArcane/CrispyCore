package me.mrarcane.crispycore.files;

import me.mrarcane.crispycore.utils.ChatUtil;
import me.mrarcane.crispycore.utils.FileUtil;

import java.util.List;

public class HomeIconsFile {

    public static void loadIcons() {
        FileUtil file = new FileUtil(null, "Icons");
        List<String> list = file.getStringList("Icons");
        file.set("Options.Default icon", "dirt");
        file.set("Options.Icon lore", "&cClick to set as home icon!");
        if (list.isEmpty()) {
            list.add("diamond");
            file.set("Icons", list);
            file.save();
            ChatUtil.log("Created: Icons.yml");
        }
    }
}
