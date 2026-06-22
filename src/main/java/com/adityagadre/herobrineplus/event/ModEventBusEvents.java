package com.adityagadre.herobrineplus.event;

import com.adityagadre.herobrineplus.entity.HerobrineEntity;
import com.adityagadre.herobrineplus.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

/**
 * Common (both-sides) setup listeners. In EventBus 7 each setup event carries its own static
 * {@link net.minecraftforge.eventbus.api.bus.EventBus}, so we attach our listener directly to it
 * instead of registering this class on the mod bus group. Registers the shared attribute supplier
 * for every Herobrine variant.
 */
public final class ModEventBusEvents {

    private ModEventBusEvents() {
    }

    /** Called once from the mod constructor. */
    public static void register() {
        EntityAttributeCreationEvent.BUS.addListener(ModEventBusEvents::onEntityAttributeCreation);
    }

    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        ModEntities.all().forEach(holder ->
                event.put(holder.get(), HerobrineEntity.createAttributes().build()));
    }
}
