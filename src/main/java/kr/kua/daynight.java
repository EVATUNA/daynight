package kr.kua;

import kr.kua.listener.EventListener;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class daynight extends JavaPlugin {

    private Config config;

    private static daynight plugin;
    public static daynight getInstance() { return plugin; }

    public static Boolean isAlreadyBlocked = false;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        config = new Config(getConfig());
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new EventListener(), this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                        for (World world : daynight.getInstance().getServer().getWorlds()) {
                            if (daynight.getInstance().config.isIncludedWorld(world.getName())) {
                                long worldTime = world.getTime();
                                if (worldTime > 14000 && worldTime < 22500) {
                                    // Night
                                    if (isAlreadyBlocked) {
                                        isAlreadyBlocked = false;
                                        for (Player player : daynight.getInstance().getServer().getOnlinePlayers())
                                            player.sendTitle("§6밤", "§e몬스터가 나타납니다..");
                                    }
                                } else {
                                    // Day
                                    if (!isAlreadyBlocked) {
                                        isAlreadyBlocked = true;
                                        for (Player player : daynight.getInstance().getServer().getOnlinePlayers())
                                            player.sendTitle("§6낮", "§e몬스터가 사라집니다..");
                                    }
                                }
                            } else break;
                        }

                    }
                }, 0L, 600L
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
