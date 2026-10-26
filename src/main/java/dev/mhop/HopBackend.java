package dev.mhop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

public final class HopBackend extends JavaPlugin implements PluginMessageListener, CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        getCommand("hop").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /hop <player> <destination>");
            return true;
        }
        Player p = Bukkit.getPlayerExact(args[0]);
        if (p == null) {
            sender.sendMessage("Player not found.");
            return true;
        }
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream o = new DataOutputStream(b);
            o.writeUTF("mhop");
            o.writeUTF(args[1].toLowerCase());
            p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        } catch (IOException ignored) {
        }
        return true;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            if (!in.readUTF().equals("mhop")) return;
            String key = in.readUTF().toLowerCase();
            String world = getConfig().getString("destinations." + key);
            if (world == null) {
                getLogger().warning("Unknown destination: " + key);
                return;
            }
            long delay = getConfig().getLong("teleport-delay-ticks", 20L);
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (player.isOnline()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + player.getName() + " " + world);
                }
            }, delay);
        } catch (IOException ignored) {
        }
    }
}
