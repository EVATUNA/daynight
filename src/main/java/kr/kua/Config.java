package kr.kua;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class Config {

    private final List<String> worldList;

    public Config(FileConfiguration conf) {
        this.worldList = Arrays.asList(conf.getString("worlds").split(","));
    }

    public List<String> getWorldList() {
        return worldList;
    }

    public Boolean isIncludedWorld(String worldName) {
        for (String world : worldList) {
            if (world.equals(worldName)) return true;
        }
        return false;
    }
}
