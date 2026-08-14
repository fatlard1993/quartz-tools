# Quartz Tools

A Fabric mod that adds a full set of tools (pickaxe, axe, shovel, hoe, and sword) crafted from smooth quartz blocks. Quartz tools hit harder and mine faster than diamond, and take enchantments exceptionally well, but they're fragile: a glass-cannon tool tier that trades durability for raw power.

## Features

- Pickaxe, axe, shovel, hoe, and sword crafted from smooth quartz blocks and sticks using standard vanilla tool crafting patterns
- Mines anything diamond tools can mine (including obsidian)
- Fastest mining speed and highest attack damage bonus of any vanilla-tier tool
- Triple the enchantability of diamond
- Very low durability: the tradeoff for the stat boost
- Repairable with smooth quartz blocks
- Added to the vanilla Tools and Combat creative tabs

### Tool Stats

| Stat | Quartz | Diamond |
|------|--------|---------|
| Durability | 128 | 1561 |
| Mining Speed | 10.0 | 8.0 |
| Attack Damage Bonus | +4.0 | +3.0 |
| Enchantability | 30 | 10 |

## Requirements

Targets the Minecraft, Fabric Loader, and Fabric API versions declared in this mod's `gradle.properties`; check there for the exact currently-supported version.

## Pandorical

Quartz Tools is a server-side mod. If Pandorical is installed on the server, the mod uses `PandoricalApi.content().registerModAssets()` to sync its custom tool textures and models to Pandorical clients. Pandorical must be installed client-side to see the custom quartz tool appearance and item names; without it, the tools still function with all of their stats, but a vanilla client won't have the custom textures.

## Installation

Install alongside its declared dependencies (see `fabric.mod.json`).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
