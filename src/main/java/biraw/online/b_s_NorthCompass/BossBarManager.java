package biraw.online.b_s_NorthCompass;

import net.kyori.adventure.chat.ChatType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BossBarManager {

    private final Map<Player, BossBar> playerBossBars = new HashMap<>();

    //Create a boss bar for player
    public void createBossBar(Player player, String title) {
        // Check if a boss bar already exists for the player
        if (playerBossBars.containsKey(player)) {
            return;
        }
        BossBar bossBar = Bukkit.createBossBar(title, BarColor.WHITE, BarStyle.SEGMENTED_20);
        bossBar.setProgress(0);
        bossBar.addPlayer(player);
        playerBossBars.put(player, bossBar);
    }

    //Update the title of the boss bar
    public void updateBossBar(Player player, String newTitle) {
        BossBar bossBar = playerBossBars.get(player);
        if (bossBar != null) {
            bossBar.setTitle(newTitle);
        } else {
            createBossBar(player,"");
        }
    }

    //Delete the boss bar for a player
    public void deleteBossBar(Player player) {
        BossBar bossBar = playerBossBars.get(player);
        if (bossBar != null) {
            bossBar.removePlayer(player);
            playerBossBars.remove(player);
        } else {
            Bukkit.getLogger().warning("No boss bar found for player " + player.getName() + " to delete.");
        }
    }

    //Length of the compass string, for default use 48
    private static final int COMPASS_LENGTH = 48;

    public String getCompass(Player player) {
        //Get player's yaw and normalize it
        float yaw = player.getLocation().getYaw();
        yaw = (yaw + 360) % 360; // Normalize yaw to be in the range of 0 to 360 degrees

        //Initialize a compass string
        StringBuilder compass = new StringBuilder("-".repeat(COMPASS_LENGTH));

        //Define the characters for directions
        String directions = "NWSE";

        //Calculate the starting index based on the player's yaw
        int startIndex = Math.round((yaw / 360) * COMPASS_LENGTH);

        //Place each direction in the compass string
        for (int i = 0; i < directions.length(); i++) {
            int position = (startIndex + i * (COMPASS_LENGTH / 4)) % COMPASS_LENGTH;
            compass.setCharAt(position, directions.charAt(i));
        }

        //Trim the string so only the middle is shown
        String res = compass.substring((COMPASS_LENGTH/4)-2,((COMPASS_LENGTH/4)*3)+3);

        //Add a little indicator for the middle
        StringBuilder modifiedRes = new StringBuilder(res);
        if (res.charAt(13)=='-') modifiedRes.setCharAt(13, '✕');
        if (res.charAt(15)=='-') modifiedRes.setCharAt(15, '✕');
        res = modifiedRes.toString();

        //Add color to the compass
        res = res.replace("N", ChatColor.RED + "N" + ChatColor.RESET);

        return res;
    }
}

