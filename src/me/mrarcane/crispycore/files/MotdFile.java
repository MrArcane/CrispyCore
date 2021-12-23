package me.mrarcane.crispycore.files;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.utils.ChatUtil;
import me.mrarcane.crispycore.utils.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MotdFile {

    public static void loadMotd() {
        try {
            File myObj = new File(Main.getInstance().getDataFolder() + File.separator + "motd.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                    FileWriter myWriter = new FileWriter(myObj);
                    myWriter.write("Default MOTD.. Change this in motd.txt");
                    myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
