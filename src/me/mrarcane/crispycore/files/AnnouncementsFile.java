package me.mrarcane.crispycore.files;

import me.mrarcane.crispycore.utils.ChatUtil;
import me.mrarcane.crispycore.utils.FileUtil;

import java.util.List;

public class AnnouncementsFile {

    public static void loadAnnouncements() {
        FileUtil file = new FileUtil(null,"Announcements");
        List<String> list = file.getStringList("Messages");
        String msg = "&cDefault message for auto broadcast system";
        if (list.isEmpty()) {
            file.set("Options.Interval", 15);
            list.add(msg);
            file.set("Messages", list);
            file.save();
            ChatUtil.log("Created: Announcements.yml");
        }
    }
}
