package kr.kua;

import kr.kua.listener.EventListener;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
                                        for (Player player : world.getPlayers())
                                            player.sendTitle("§6밤", "§e몬스터가 나타납니다..");
                                    }
                                } else {
                                    // Day
                                    if (!isAlreadyBlocked) {
                                        isAlreadyBlocked = true;
                                        for (Entity entity : world.getEntities())
                                            if (entity.isValid() && isNeedRemovedMob(entity.getType())) entity.remove();

                                        for (Player player : world.getPlayers())
                                            player.sendTitle("§6낮", "§e몬스터가 나타나지 않습니다..");
                                    }
                                }
                            } else break;
                        }

                    }
                }, 0L, 60L
        );
    }

    public static Boolean isNeedRemovedMob(EntityType et) {
        switch (et) {
            case SKELETON:
            case SPIDER:
            case ENDERMAN:
            case DROWNED:
            case HUSK:
            case STRAY:
            case ZOMBIE:
            case CREEPER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
