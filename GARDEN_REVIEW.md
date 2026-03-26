# THE GARDENER'S REPORT

**quartz-tools** | Deep Assessment | 2026-02-08

---

## WEATHER & SEASON

**Current season:** Late Autumn. This mod has been through its growth phases -- born in 2019 (LICENSE date), recently updated for 1.21.11 with a significant replanting (Polymer conversion). The last five commits are all fixes and cleanup. The energy of creation has passed; this is harvested, bearing fruit on servers, in maintenance mode.

**Weather:** Clear skies. No deadlines visible, no storms incoming. The Minecraft 1.21.11 target is current. Polymer 0.15.1 is recent. The garden is not under pressure.

**Alignment:** The season and work match perfectly. Small fixes, enchantability corrections, tilling bugs -- this is autumn tending. Not trying to plant new beds when the harvest is in.

---

## PLOT ASSESSMENT

### The Root System (Build & Configuration)
**State: Orchard -- mature, producing**

| File | Health |
|------|--------|
| `build.gradle` | Healthy, clean |
| `settings.gradle` | Functional, one weed |
| `gradle.properties` | Clean, well-organized |
| `fabric.mod.json` | Healthy, accurate |
| `.gitignore` | Adequate |
| `.gitattributes` | Minimal but functional |
| `README.md` | Good, slightly stale |

**Soil quality:** The build foundation is solid. Fabric Loom 1.13-SNAPSHOT, Java 21 target, Yarn mappings for 1.21.11. Dependencies are clearly separated: Minecraft/Fabric core, plus Polymer (core, resource-pack, autohost) all bundled via `include()`. This is correct practice for a server-side mod that needs to ship Polymer to servers that don't have it.

**One weed in settings.gradle:** Line 3 uses `jcenter()` which is a deprecated repository. JCenter went read-only in 2021 and could eventually go offline. It likely resolves nothing that Fabric's maven and gradlePluginPortal don't already cover. This is a weed that should be pulled.

**Empty loom block:** `build.gradle` line 17-18 has `loom { }` -- an empty configuration block. Not harmful but dead wood. Either it once held configuration that was removed, or it was scaffolding never filled. Candidate for pruning.

**Empty publishing repositories:** `build.gradle` line 60-61 has `repositories { }` empty. The `maven-publish` plugin is applied but publishing has no target. If this mod isn't published to a Maven repository, the entire publishing block (lines 54-62) and the `maven-publish` plugin (line 3) are dead wood.

**README drift:** The README says "Minecraft 1.21+" and "Fabric Loader 0.15.0+" in Requirements, but `fabric.mod.json` requires `>=0.18.1` and `~1.21.11`. The README has softened the version requirements compared to what the code actually demands. The screenshot reference `img.png` -- I found no `img.png` in the repository root (it is not tracked in git). Broken image reference.

---

### The Main Stem (Registration & Entry Point)
**State: Orchard -- bearing fruit steadily**

| File | Health |
|------|--------|
| `src/main/java/justfatlard/quartz_tools/Main.java` | Healthy with one debug weed |

**Architecture:** `Main.java` is the single point of truth for the entire mod. It defines the tool material, creates all five tool items, registers them, sets up item groups, and hooks into vanilla creative tabs. For a mod of this size, this is the right density. Not overcrowded -- five tools is a natural cluster.

**The tool material definition (lines 25-40):** Clean and readable. Named constants for every stat. The design intent is clear: glass cannon tools. Diamond mining level (`INCORRECT_FOR_DIAMOND_TOOL`), faster than diamond (10.0 vs 8.0), higher damage (+4.0 vs +3.0), triple enchantability (30 vs 10), but only 128 durability (vs diamond's 1561). This is a deliberate, well-balanced design. The comment on line 25 accurately summarizes the intent.

**Item registration (lines 77-81):** Clean, consistent pattern. Each tool gets registered with the same `keyOf()` helper. Good companion planting -- the helper method on line 42-44 prevents identifier string duplication.

**Item group setup (lines 83-107):** Two concerns woven together well. Polymer's custom item group (for modded clients/recipe browsers) AND injection into vanilla creative tabs (Tools + Combat). The ordering in the custom group follows the conventional Minecraft ordering (sword, pickaxe, axe, shovel, hoe). The vanilla tab injection correctly separates tools from combat.

**Debug weed (line 109):** `System.out.println("[quartz-tools] Loaded quartz-tools (server-side with Polymer)");` -- This is a leftover debug/status print using raw `System.out.println` instead of a proper logger. In a Fabric mod, this should either be removed or use `org.slf4j.Logger` via `LoggerFactory.getLogger(MOD_ID)`. Minor weed but it signals amateur soil -- not harmful, just untidy.

**One observation about the magic numbers:** The tool construction uses raw float literals for attack damage and attack speed (`1.0f, -2.8f` for pickaxe, `5.0f, -3.0f` for axe, etc). These follow vanilla conventions exactly (diamond pickaxe is 1.0/-2.8, diamond axe is 5.0/-3.0, etc). They are NOT arbitrary -- they are Minecraft's standard offsets. Naming them as constants would actually hurt readability here because the pattern IS the vanilla pattern. This is correct gardening -- know when NOT to abstract.

---

### The Tool Bed (Item Classes)
**State: Orchard -- but showing monoculture patterns**

| File | Health |
|------|--------|
| `QuartzPickaxeItem.java` | Healthy |
| `QuartzAxeItem.java` | Healthy |
| `QuartzShovelItem.java` | Healthy |
| `QuartzSwordItem.java` | Healthy |
| `QuartzHoeItem.java` | Healthy -- different root stock |

**The pattern:** Four of the five tool classes (pickaxe, axe, shovel, sword) are structurally identical. They all:
1. Extend `Item` (not a tool-specific subclass)
2. Implement `PolymerItem`
3. Store a `modelId` Identifier
4. Call the appropriate `settings.pickaxe()`/`.axe()`/`.shovel()`/`.sword()` in the constructor
5. Map to the diamond equivalent for Polymer's vanilla client representation
6. Return their custom model ID

**The hoe is different.** `QuartzHoeItem` extends `HoeItem` instead of `Item`. This is the fix from commit `1a88b33 fix hoe not tilling soil`. The hoe needs `HoeItem` because `HoeItem` contains the tilling behavior (right-click grass/dirt to create farmland). The other tools get their behavior from the `settings.pickaxe()` etc. calls, but hoeing is a use-action that lives in `HoeItem`, not in the settings builder.

This is architecturally significant: the hoe's constructor calls `super(material, attackDamage, attackSpeed, settings)` -- passing settings WITHOUT calling `settings.hoe()`. This is because `HoeItem`'s own constructor handles the tool material setup internally. The other four call `super(settings.toolType(...))` which configures settings then passes to plain `Item`.

**Monoculture observation:** The four identical classes (pickaxe, axe, shovel, sword) are nearly perfect copies. The only differences: class name, which `settings.xxx()` method is called, which `Items.DIAMOND_XXX` is returned, and which model ID string. This is textbook boilerplate. In many codebases this would cry out for a generic base class or factory. BUT -- in Minecraft modding, each tool type has distinct behavior possibilities (axes strip logs, shovels make paths, swords sweep). Keeping them as separate classes is actually good foresight -- it creates slots for future specialization even if they are currently empty. The monoculture is acceptable because these are separate species that LOOK alike now but may diverge later.

**That said:** If the gardener is confident these tools will never gain unique behavior, a single `QuartzToolItem` class with constructor parameters would eliminate the duplication. This is a judgment call that depends on the gardener's vision for the mod's future.

---

### The Asset Garden (Client Resources)
**State: Orchard -- well-tended, consistent**

| Subsection | Files | Health |
|------------|-------|--------|
| Item definitions (`items/`) | 5 | Healthy |
| Item models (`models/item/`) | 5 | Healthy |
| Item textures (`textures/item/`) | 5 | Present (not inspected visually) |
| Language (`lang/en_us.json`) | 1 | Healthy, with bonus flavor |

**Item definitions (items/*.json):** These are the 1.21+ item model pointer files. Each one correctly points to its model in `models/item/`. Clean, minimal, correct format: `{"model":{"type":"minecraft:model","model":"quartz-tools-justfatlard:item/xxx"}}`. All five present and consistent.

**Item models (models/item/*.json):** All use `item/handheld` parent (correct for tools -- gives the held-in-hand 3D rotation) and reference textures in the mod's namespace. Consistent pattern across all five. No dead references.

**Language file:** Contains all five tool names plus an advancement title and description. The advancement flavor text ("The Lost Tools of the Pigmen") is charming and lore-appropriate -- nether quartz, pigmen, lost civilization. Good seed worth saving. Note the advancement title in en_us.json ("The Lost Tools of the Pigmen") does NOT match what's in the actual advancement JSON file (which says "Smooth Operator" / "Obtain smooth quartz to craft quartz tools"). This is a mismatch -- the lang file defines translation keys that the advancement doesn't use. The advancement uses literal `"text"` strings instead of `"translate"` keys. Those lang file entries for advancements are dead code -- planted but never connected to water.

---

### The Data Beds (Server Data)
**State: Orchard -- thorough, with one notable observation**

| Subsection | Files | Health |
|------------|-------|--------|
| Recipes | 5 | Healthy |
| Mod tags | 2 | Healthy |
| Vanilla tool tags | 5 | Healthy |
| Enchantability tags | 7 | Healthy, thorough |
| Advancement | 1 | Functional, see notes |

**Recipes:** All five follow standard Minecraft crafting patterns using smooth quartz (`minecraft:smooth_quartz`) and sticks. The patterns are correct for each tool type (3-wide for pickaxe, L-shape for axe, single column for shovel/sword, 2-wide for hoe). All reference the mod's namespaced items as results. Clean.

**Mod tags:**
- `quartz_tool.json` -- lists all 5 tools. Used nowhere that I can see in the code. This tag exists for external consumption (other mods, datapacks) or future use. Good foresight, but currently a dormant bed.
- `quartz_tool_repair_items.json` -- lists `minecraft:smooth_quartz`. This is actively used -- it is referenced by `REPAIR_ITEMS` in `Main.java` line 31. This is the tag that tells the anvil what material repairs quartz tools. Correctly wired.

**Vanilla tool type tags (swords.json, pickaxes.json, axes.json, shovels.json, hoes.json):** These append each quartz tool to the appropriate vanilla tool-type tag. This is essential -- without these, quartz tools would not be recognized by the game as tools of their type. All five present, all with `"replace": false` (correct -- appending, not overwriting). Clean.

**Enchantability tags:** This is where the thorough tending shows. Seven tag extensions:
- `durability.json` -- all 5 tools (Unbreaking enchant)
- `vanishing.json` -- all 5 tools (Curse of Vanishing)
- `mining.json` -- 4 tools, no sword (Efficiency, etc.)
- `mining_loot.json` -- 3 tools: pickaxe, axe, shovel (Fortune/Silk Touch). Correctly excludes hoe -- hoes don't get Fortune for block drops in vanilla
- `sword.json` -- sword only (Looting, Knockback, etc.)
- `weapon.json` -- sword and axe (Sharpness, Smite, etc.)
- `sharp_weapon.json` -- sword and axe (same as weapon for sharp-specific enchants)
- `fire_aspect.json` -- sword only

This is careful work. The enchantability tags exactly mirror what vanilla diamond tools have. Every category accounted for. This is why the recent "Fixed enchantability" commit matters -- getting these tags wrong means tools can't be enchanted properly.

**Missing enchantability tag:** There is no `enchantable/damage.json` tag file. In some Minecraft versions, there's a separate damage-related enchantment tag. This may or may not be needed for 1.21.11 -- worth verifying. Also no `enchantable/armor_exclusive.json` or `enchantable/crossbow.json` but those correctly should not include tools.

**Advancement:** `obtain_smooth_quartz_tools.json` is filed under `data/minecraft/advancement/nether/` which injects it into the vanilla advancement tree under the Nether tab. It triggers on obtaining smooth quartz and rewards all 5 tool recipes. The `"hidden": true` is appropriate -- it only appears after being earned.

However: the advancement uses hardcoded text strings (`"text": "Smooth Operator"`, `"text": "Obtain smooth quartz..."`) instead of translation keys (`"translate": "advancements.story.quartz-tools-justfatlard.craft_quartz_tool.title"`). The lang file has entries for advancement title/description that are never used. Either the advancement should use `"translate"` keys, or the lang file entries should be composted.

Also: the advancement icon uses `minecraft:smooth_quartz` (the block item) rather than one of the quartz tools themselves. This is a design choice, but the mod's custom item group uses the pickaxe as icon. The inconsistency is minor but noticeable.

Also: the advancement title/description in the JSON ("Smooth Operator" / "Obtain smooth quartz to craft quartz tools") differs from the lang file entries ("The Lost Tools of the Pigmen" / "Craft a quartz tool"). These are two different creative visions for the same advancement. The "Smooth Operator" version actually plays on the server, since the JSON uses literal text. The "Lost Tools of the Pigmen" version is dead -- beautiful flavor text that never sees sunlight.

---

## PEST & DISEASE CHECK

### Pests Found

**Aphid: `System.out.println` debug statement** -- `Main.java:109`. Should use SLF4J Logger or be removed entirely. Small energy drain, signals to other mod developers that logging practices are casual.

**Aphid: `jcenter()` in settings.gradle** -- Deprecated repository. Likely resolves nothing. Should be removed.

**Aphid: Dead lang file entries** -- Two advancement translation keys in `en_us.json` that are never referenced. Dead roots taking up space.

**Aphid: Empty `loom {}` block** -- `build.gradle:17-18`. Dead scaffolding.

**Aphid: Empty `publishing.repositories {}`** -- `build.gradle:60-61`. Possible dead feature. If publishing isn't used, the whole block and `maven-publish` plugin are dead wood.

### Disease Check

**Nutrient deficiency: No tests.** Zero test files. The mod has no automated verification of any kind. For a mod this small, this is common and not urgent, but it means every change is verified by manual play-testing only.

**Nutrient deficiency: No data generation.** The JSON resources are all hand-written. Fabric has a data generation API (the mod even depends on `fabric-api` which includes it). Using datagen would prevent typos and ensure consistency. For five tools this is manageable, but it is a growth constraint if the mod ever expands.

**Sunlight starvation: Missing img.png.** The README references `img.png` for a screenshot but the file does not exist in the repository. Broken documentation. The screenshot was either never committed or was removed.

**No other disease detected.** The codebase is clean. No circular dependencies, no god objects, no premature abstraction, no rigidity. The simplicity is its health.

---

## SOIL ANALYSIS

**Health:** Fertile. The foundation is modern (Java 21, Fabric 1.21.11, Polymer 0.15.1). Dependencies are minimal and appropriate. The build system works.

**Foundation:** Solid. Fabric Loom handles the heavy lifting. Polymer provides the server-side magic. The mod doesn't fight its infrastructure.

**Drainage:** Good for what exists, but limited. Errors would surface as Minecraft crash reports. No custom error handling or logging (beyond the one println). For a mod this simple, this is adequate.

**pH Balance:** Excellent. Server-side Fabric mod using Polymer for vanilla client compatibility. The technology choices are perfectly suited to the goal: add custom tools to a server without requiring client mods. This is exactly the right soil for this plant.

---

## CHANGE VELOCITY

**Pliability:** High. Want to change tool stats? One constant in `Main.java`. Want to change a recipe? One JSON file. Want to add a new tool? Add one Java class, one set of JSON resources, a few lines in `Main.java`. The structure invites change.

**Removability:** Excellent. Every piece can be removed independently. Delete a tool class, remove its registration lines, remove its JSON files. No tangling. This is healthy design.

**Composability:** Good. The tool pattern is repeatable. Adding a 6th tool (quartz shears? quartz fishing rod?) would follow the established pattern mechanically.

**Isolation:** Each tool is its own island. The only shared state is the `QUARTZ_TOOL_MATERIAL` and the `MOD_ID` constant. Both are appropriate shared resources.

**Regenerative capacity:** High. If you deleted half this mod, you could rebuild it from the remaining pattern. The design is deeply encoded -- once you see one tool's full implementation, you know how to build all of them.

---

## VISION ALIGNMENT

**Plan found:** Yes -- README.md and fabric.mod.json description.

**Plan clarity:** Clear. "Adds tools made out of smooth quartz (server-side with Polymer)." The vision is a focused, single-purpose mod: one new tool material, five tool variants, server-side compatible.

### True to plan:
- Five tools from smooth quartz: delivered
- Server-side with Polymer: delivered
- Glass cannon balance (high power, low durability, high enchantability): delivered
- Standard crafting recipes: delivered
- Vanilla creative tab integration: delivered

### Organic variations:
- The advancement system (recipe unlocking on obtaining smooth quartz) was not mentioned in the README but is a natural quality-of-life addition. Good organic growth.
- The custom Polymer item group goes beyond what the README promises. Useful for modded recipe viewers.

### Wayward growth:
- None detected. Everything in the codebase serves the stated vision.

### Lost seedlings:
- The `en_us.json` advancement translation keys ("The Lost Tools of the Pigmen" / "Craft a quartz tool") are orphaned. They represent a creative direction (lore-rich advancement text) that was planted but never connected. The actual advancement uses different, more utilitarian text.
- The `maven-publish` plugin and empty publishing block suggest an intent to publish to a Maven repository that was never followed through.

### Abandoned beds:
- No client-side entry point. The mod is explicitly server-side (`"environment": "server"` in fabric.mod.json). This is intentional, not abandoned.
- No configuration/settings system. Tool stats are hardcoded constants. This is a design choice -- simplicity over configurability.

---

## COMPANION ASSESSMENT

### Good pairings:
- **Main.java + Polymer** -- The registration code and Polymer's item system work together seamlessly. The PolymerItem interface on each tool class is the clean graft that makes server-side items visible to vanilla clients.
- **Tool classes + vanilla Item/HoeItem** -- Extending vanilla's own classes means the tools inherit all correct behaviors without reimplementation.
- **Enchantability tags + vanilla tag system** -- By appending to vanilla's existing tags rather than trying to register enchantments programmatically, the mod stays simple and compatible.

### Bad pairings:
- **Advancement JSON + lang file** -- These should be companions (advancement uses translation keys from lang file) but they are disconnected. The advancement speaks for itself with hardcoded text while the lang file's advancement entries speak to no one.

### Transplant candidates:
- None. Everything is in its correct bed for a mod of this size.

---

## TENDING NEEDED

### Priority 1: Quick weeds to pull (5 minutes)

- **`settings.gradle` line 3** -- Prune `jcenter()`. It is deprecated and unnecessary. The Fabric maven and gradlePluginPortal cover everything needed.

- **`Main.java` line 109** -- Prune the `System.out.println`. Either remove it entirely (the mod's presence is evident from registration) or replace with a proper SLF4J logger if startup confirmation is desired.

- **`build.gradle` lines 17-18** -- Prune the empty `loom { }` block. Dead scaffolding.

### Priority 2: Alignment fixes (15 minutes)

- **Advancement translation mismatch** -- Either:
  - (a) Update the advancement JSON to use `"translate"` keys and let the lang file's "The Lost Tools of the Pigmen" shine (better flavor), or
  - (b) Remove the dead advancement entries from `en_us.json` (cleaner, simpler)

- **README.md requirements drift** -- Update the Requirements section to match actual dependencies: Minecraft 1.21.11 (not 1.21+), Fabric Loader 0.18.1+ (not 0.15.0+). Add or fix the screenshot (`img.png` is missing from repo).

### Priority 3: Consider composting (decision needed)

- **`maven-publish` plugin + empty publishing block** -- If this mod is not published to a Maven repository, remove the plugin and the publishing block from `build.gradle`. If publishing IS intended, configure the repository target. Do not leave it half-planted.

- **`quartz_tool.json` tag** -- This custom tag listing all quartz tools is not referenced internally. If it exists for other mods or datapacks to consume, document that intent. If it was scaffolding for something never built, consider composting. (Likely worth keeping for ecosystem compatibility -- but acknowledge its purpose.)

### Priority 4: Growth opportunities (when energy permits)

- **Data generation** -- The hand-crafted JSON is maintainable at 5 tools but would not scale. If the mod ever grows (quartz armor? quartz shield? nether quartz variant?), consider implementing Fabric's data generation to auto-generate recipes, tags, models, item definitions, and advancement JSON from code.

- **Hoe constructor consistency** -- The `QuartzHoeItem` does not call `settings.hoe()` because it extends `HoeItem` which handles this internally. This architectural difference from the other four tools is correct but undocumented. A comment explaining why the hoe is different would help future gardeners understand the deliberate choice.

---

## SEEDS WORTH SAVING

**The glass cannon balance formula.** Durability 128 / Speed 10.0 / Damage +4.0 / Enchantability 30. This is a well-thought-out niche: faster than diamond, hits harder, takes enchantments beautifully, but shatters quickly. The tradeoff creates interesting gameplay (enchant with Unbreaking III to compensate? Or treat them as disposable power tools?). This design pattern could be extracted for other "exotic material" tool mods.

**The Polymer server-side pattern.** The way each tool extends vanilla Item, implements PolymerItem, maps to a diamond equivalent, and provides a custom model ID -- this is a clean, repeatable template for any server-side custom item mod. Worth documenting as a starter pattern.

**"The Lost Tools of the Pigmen."** Beautiful lore seed. The nether quartz connection to pigmen/piglins, the idea of a lost civilization's tools -- this is flavor that elevates a utility mod into worldbuilding. Even though it is currently disconnected from the running advancement, the creative seed is worth preserving and reconnecting.

---

## SIDE QUESTS NOTICED

**Implicit tool tier.** The quartz tools use `INCORRECT_FOR_DIAMOND_TOOL` which means they can mine everything diamond can mine (including obsidian). Combined with the mining speed of 10.0 (higher than diamond's 8.0) and the enchantability of 30 (higher than gold's 22), quartz tools are arguably the best mining tools in the game IF you can keep them alive. The mod does not advertise this -- the README mentions "fastest mining speed" but not that they are diamond-tier. This is an emergent identity: the quartz pickaxe is the best short-lived pickaxe in vanilla Minecraft. The mod might lean into this identity more explicitly.

**Server-side mod with full texture support.** The Polymer integration means this mod works on vanilla servers with vanilla clients. The resource pack auto-hosting means clients get custom textures without manual download. This is a significant capability that the README undersells. Most mods require client installation. This one does not.

---

## OVERALL HEALTH

This is a small, well-kept garden in late autumn. It does one thing and does it correctly. The soil is healthy, the architecture is sound, the design intent is clear and fully realized. There are a few weeds (deprecated repo, debug println, dead lang entries, empty build blocks) and one broken path (missing screenshot), but no disease, no pests beyond aphids, and no structural problems.

The monoculture in the tool classes is a conscious choice, not a problem. The hoe's architectural difference is a correct solution to a real behavioral need (tilling), not a inconsistency.

The biggest risk is not in the code but in the documentation -- the README has drifted slightly from the actual requirements, the advancement flavor text is disconnected, and the screenshot is missing. These are the paths that visitors walk, and they should be tended.

**Health rating: Strong.** This garden is bearing fruit and needs only light seasonal maintenance. No heavy lifting required. The gardener clearly knows the soil and the season.

---

*Report filed by the Head Gardener, 2026-02-08*
*44 files surveyed | 6 Java source files | ~25 JSON resources | 3 build files | 5 textures | 16 commits of history*
