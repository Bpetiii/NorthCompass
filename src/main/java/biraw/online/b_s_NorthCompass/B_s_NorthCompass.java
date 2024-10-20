package biraw.online.b_s_NorthCompass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class B_s_NorthCompass extends JavaPlugin {

    private static ArrayList<Integer> tasks;
    private static JavaPlugin plugin;
    private static BossBarManager barManager;

    public static void addTask(int i){
        tasks.add(i);
    }

    public static JavaPlugin getInstance(){
        return plugin;
    }

    public static BossBarManager getBarManager() {
        return barManager;
    }

    @Override
    public void onEnable() {
        plugin = this;
        barManager = new BossBarManager();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), plugin);

        // Print the motd
        plugin.getLogger().info(" ");
        plugin.getLogger().info("O=========================================================O");
        plugin.getLogger().info("   The B's NorthCompass plugin has loaded successfully!");
        plugin.getLogger().info("         This is for Minecraft 1.20.5+ and 1.21.x!");
        plugin.getLogger().info("                       Author: BiRaw");
        plugin.getLogger().info("         Discord: https://discord.gg/XwFqu7uahX :>");
        plugin.getLogger().info("O=========================================================O");
        plugin.getLogger().info(" ");

    }

    @Override
    public void onDisable() {
        //Cancel all tasks
        if (tasks != null && !tasks.isEmpty())
        {
            tasks.forEach(task -> {
                Bukkit.getScheduler().cancelTask(task);
            });
        }

        //Delete all bars
        Bukkit.getOnlinePlayers().forEach(player -> {
            barManager.deleteBossBar(player);
        });
    }
}
