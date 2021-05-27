package kr.kua.listener;

import kr.kua.daynight;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
        if (!daynight.isAlreadyBlocked) return;
        if (daynight.isNeedRemovedMob(e.getEntity().getType())) e.setCancelled(true);
    }
}
