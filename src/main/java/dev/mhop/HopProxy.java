package dev.mhop;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class HopProxy extends Plugin implements Listener {
    private static final String TARGET = "main";
    private final Map<UUID, String> pending = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onMessage(PluginMessageEvent e) {
        if (!e.getTag().equals("BungeeCord")) return;
        if (!(e.getSender() instanceof Server) || !(e.getReceiver() instanceof ProxiedPlayer)) return;
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            if (!in.readUTF().equals("mhop")) return;
            e.setCancelled(true);
            String key = in.readUTF();
            if (!key.matches("[a-z0-9_-]{1,32}")) return;
            ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
            ServerInfo target = getProxy().getServerInfo(TARGET);
            if (target == null) return;
            if (p.getServer() != null && p.getServer().getInfo().getName().equals(TARGET)) {
                send(p, key);
                return;
            }
            pending.put(p.getUniqueId(), key);
            p.connect(target);
        } catch (IOException ignored) {
        }
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (p.getServer() == null || !p.getServer().getInfo().getName().equals(TARGET)) return;
        String key = pending.remove(p.getUniqueId());
        if (key != null) send(p, key);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        pending.remove(e.getPlayer().getUniqueId());
    }

    private void send(ProxiedPlayer p, String key) {
        getProxy().getScheduler().schedule(this, () -> {
            Server s = p.getServer();
            if (s == null || !s.getInfo().getName().equals(TARGET)) return;
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream o = new DataOutputStream(b);
                o.writeUTF("mhop");
                o.writeUTF(key);
                s.sendData("BungeeCord", b.toByteArray());
            } catch (IOException ignored) {
            }
        }, 1, TimeUnit.SECONDS);
    }
}
