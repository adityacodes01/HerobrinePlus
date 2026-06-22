package com.adityagadre.herobrineplus.registry;

import com.adityagadre.herobrineplus.HerobrinePlus;
import com.adityagadre.herobrineplus.item.HerobrineSpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HerobrinePlus.MODID);

    /** One spawn egg for every Herobrine — spawns a random variant on use. */
    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG =
            ITEMS.register("herobrine_spawn_egg",
                    () -> new HerobrineSpawnEggItem(
                            // Forge 65: registered items must carry their ResourceKey in Properties.
                            new Item.Properties().setId(ITEMS.key("herobrine_spawn_egg"))));

    private ModItems() {
    }

    public static void register(BusGroup modBus) {
        ITEMS.register(modBus);
    }
}
