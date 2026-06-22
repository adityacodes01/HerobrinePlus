# Herobrine+

A Forge mod for **Minecraft 26.2 / Forge 65.0.0 / Java 25** that adds 15 hostile Herobrine
variants. Every variant has its own texture and name but shares the same behaviour, and a single
spawn egg spawns a random one.

## What it does

- **15 variants**, each a distinct entity type with a unique registry id and translated name:
  Herobrine, Herobrine HD, Archer, Demon, Enderbrine, Evil, Fire, Ghost, Hunter, Nether, Ninja,
  Robot, Skeleton, Villager, and Zombie Herobrine.
- Every variant has **300 health**, **10 attack damage**, hostile `Monster` AI that **automatically
  targets nearby players, walks toward them, and melees on contact**.
- Each one only ever makes the **ghast "hurt" sound, and only when it takes damage** (no idle or
  death sounds).
- **One shared spawn egg** ("Herobrine Spawn Egg") that spawns a **random** variant on use, in its
  own "Herobrine+" creative tab.

## Project structure

```
herobrineplus/
├─ build.gradle / settings.gradle / gradle.properties   ForgeGradle build
├─ gradle/wrapper/…                                      Gradle wrapper (committed for CI)
├─ .github/workflows/build.yml                           GitHub Actions CI
└─ src/main/
   ├─ java/com/adityagadre/herobrineplus/
   │  ├─ HerobrinePlus.java                 @Mod entry point
   │  ├─ entity/
   │  │  ├─ HerobrineVariant.java           enum of all 15 variants (id, name, texture)
   │  │  └─ HerobrineEntity.java            shared Monster: attributes, goals, sounds
   │  ├─ registry/
   │  │  ├─ ModEntities.java                one EntityType per variant + reverse lookup
   │  │  ├─ ModItems.java                   the spawn egg item
   │  │  └─ ModCreativeTabs.java            the Herobrine+ creative tab
   │  ├─ item/HerobrineSpawnEggItem.java    spawns a random variant on right-click
   │  ├─ client/
   │  │  ├─ model/HerobrineModel.java       humanoid 64×64 model
   │  │  └─ renderer/HerobrineRenderer.java picks each variant's texture
   │  ├─ event/
   │  │  ├─ ModEventBusEvents.java          attribute registration (both sides)
   │  │  └─ ClientModEvents.java            renderer + layer registration (client only)
   │  └─ datagen/
   │     ├─ DataGenerators.java             GatherDataEvent wiring
   │     ├─ ModItemModelProvider.java       spawn egg item model
   │     └─ ModLanguageProvider.java        en_us names
   └─ resources/
      ├─ META-INF/mods.toml
      ├─ pack.mcmeta
      └─ assets/herobrineplus/
         ├─ textures/entity/herobrine/*.png  15 variant skins (normalised to 64×64)
         ├─ textures/item/herobrine_spawn_egg.png
         ├─ models/item/herobrine_spawn_egg.json
         └─ lang/en_us.json
```

## Design notes

- **One class, many types.** All variants use the same `HerobrineEntity` and the same model; they
  differ only by texture and registry id. The renderer maps `entity.getType()` back to its variant
  via `ModEntities.variantOf(...)` to pick the texture.
- **Texture normalisation.** Six of the supplied skins were legacy 64×32; they were padded to 64×64
  so a single model (`LayerDefinition.create(mesh, 64, 64)`) renders every variant correctly,
  including the head-overlay ("hat") layer.
- **Shipped + generated resources.** The `en_us.json` and spawn egg item model are committed so the
  mod works out of the box, and the data generators can regenerate them (`runData`).

## Building (your usual GitHub Actions flow)

You don't compile locally. Commit with GitHub Desktop, then let CI build:

1. Create a repo (e.g. `adityacodes01/herobrineplus`) and commit this whole folder **including
   `gradle/wrapper/gradle-wrapper.jar`** (it's force-kept in `.gitignore`).
2. Push. The **Build Herobrine+** workflow runs automatically, or trigger it from the Actions tab
   (`workflow_dispatch`).
3. The finished mod jar is uploaded as the **`herobrineplus-jar`** artifact on the run page.

The workflow runs Gradle on JDK 21 and compiles with a JDK 25 toolchain, so the daemon runs on a
JDK that ForgeGradle is comfortable with while still producing Java 25 bytecode.

## Using it in game

Open the **Herobrine+** creative tab, grab the **Herobrine Spawn Egg**, and right-click a block.
Each use spawns a random variant that immediately hunts the nearest player.

## Build setup (ForgeGradle 7)

This project mirrors the official **Forge 26.2 MDK**: ForgeGradle `[7.0.17,8)`, Gradle 8.12.1,
Java 25 toolchain, the new `minecraft.dependency("net.minecraftforge:forge:26.2-65.0.0")` syntax,
and a `mods.toml` using `mandatory=true`. CI runs Gradle on JDK 21 and compiles with a JDK 25
toolchain.

## Version notes (read if CI flags something)

The build setup is pinned to the official 26.2 MDK, so dependency resolution should be solid. The
remaining things most likely to need a one-line tweak are source-level API spots that evolved across
recent versions — check these first if a `:compileJava` error names a missing symbol:

- **`EntityType.Builder.build(String)`** in `ModEntities` and **`EntityType.create(Level)`** in the
  spawn egg — a couple of signatures in this area have shifted across recent versions.
- **`pack_format`** in `pack.mcmeta` (currently `48`): bump to match MC 26.2 if it warns.
- **Sound return types**: if `SoundEvents.GHAST_HURT` is a `Holder<SoundEvent>` in 26.2, adjust the
  `getHurtSound` return as needed.
- The EventBus added runtime strict-checks in this era; if a `runClient` log complains, the optional
  `net.minecraftforge:eventbus-validator` annotation processor can be added back to `build.gradle`.
