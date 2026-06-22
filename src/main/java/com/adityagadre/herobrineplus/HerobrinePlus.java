package com.adityagadre.herobrineplus;

import com.adityagadre.herobrineplus.entity.HerobrineVariant;
import com.adityagadre.herobrineplus.event.ClientModEvents;
import com.adityagadre.herobrineplus.event.ModEventBusEvents;
import com.adityagadre.herobrineplus.registry.ModCreativeTabs;
import com.adityagadre.herobrineplus.registry.ModEntities;
import com.adityagadre.herobrineplus.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;


/**
 * Herobrine+ — adds multiple hostile Herobrine variants to Minecraft.
 *
 * <p>Every variant shares one entity class ({@link com.adityagadre.herobrineplus.entity.HerobrineEntity})
 * and one renderer/model; they differ only by texture and registry name. A single spawn egg
 * ({@link com.adityagadre.herobrineplus.item.HerobrineSpawnEggItem}) spawns a random variant.</p>
 */
@Mod(HerobrinePlus.MODID)
public final class HerobrinePlus {

    public static final String MODID = "herobrineplus";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * EventBus 7 / Forge 65: the mod loading context is injected into the constructor, and the
     * mod-specific {@link BusGroup} is obtained from it. DeferredRegisters and event-handler
     * classes are registered against that bus group.
     */
    public HerobrinePlus(FMLJavaModLoadingContext context) {
        BusGroup modBus = context.getModBusGroup();

        ModEntities.register(modBus);
        ModItems.register(modBus);
        ModCreativeTabs.register(modBus);

        // EventBus 7: these setup events each expose their own static EventBus; attach listeners
        // directly to it rather than registering a class on the mod bus group.
        ModEventBusEvents.register();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientModEvents.register();
        }

        LOGGER.info("[Herobrine+] Initialised with {} Herobrine variants.",
                HerobrineVariant.values().length);
    }

    /** Convenience helper for building mod-namespaced resource locations. */
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
