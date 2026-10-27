
# Bungee MVTP

A Minecraft plugin project designed to support teleportation between
servers connected through a BungeeCord network and worlds managed by
Multiverse-Core.

## Features

- BungeeCord network support
- Lobby-to-Main server switching
- Multiverse-Core world teleportation on the Main server
- Configurable destination worlds
- Support for custom server and world names

## Requirements

- BungeeCord proxy
- Paper or Spigot backend servers
- Multiverse-Core installed on the Main server
- Compatible Java version for your Minecraft server

## Example Network

- Lobby server: `lobby`
- Main server: `main`
- Survival world: `Survival`
- PvP world: `Pvp`
- Lifesteal world: `lifesteal`

## Intended Workflow

1. A player selects a destination in the Lobby.
2. The plugin sends the player to the configured backend server.
3. The destination server teleports the player to the selected world.
4. The plugin reports success or failure to the player.

## Installation

1. Build the plugin using Maven.
2. Install the proxy component in the BungeeCord plugins folder.
3. Install the backend component on the required Paper servers.
4. Configure the backend server names and destination worlds.
5. Restart the proxy and backend servers.
6. Test each destination before opening the network to players.

## Important

This README describes the intended project functionality.
Features must be implemented in the source code before they can be
considered operational.


