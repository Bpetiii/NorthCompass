package biraw.online.b_s_NorthCompass;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

    private BossBarManager bossBarManager = B_s_NorthCompass.getBarManager();

    
    @EventHandler
    private void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        bossBarManager.createBossBar(player, "");

        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(B_s_NorthCompass.getInstance(), () -> {
                    String compass = B_s_NorthCompass.getBarManager().getCompass(player);
                    bossBarManager.updateBossBar(player, compass);
        }, 1, 1);
        B_s_NorthCompass.addTask(taskid);
    }
}
