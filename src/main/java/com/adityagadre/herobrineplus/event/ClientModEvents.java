package com.adityagadre.herobrineplus.event;

import com.adityagadre.herobrineplus.client.model.HerobrineModel;
import com.adityagadre.herobrineplus.client.renderer.HerobrineRenderer;
import com.adityagadre.herobrineplus.registry.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;

/**
 * Client-only setup listeners. Loaded and called only when running on the physical client
 * (guarded in the mod constructor), so this class never initialises on a dedicated server.
 * Each renderer event exposes its own static EventBus (EventBus 7).
 */
public final class ClientModEvents {

    private ClientModEvents() {
    }

    /** Called once from the mod constructor, client side only. */
    public static void register() {
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(ClientModEvents::onRegisterLayerDefinitions);
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(ClientModEvents::onRegisterRenderers);
    }

    private static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HerobrineModel.LAYER_LOCATION, HerobrineModel::createBodyLayer);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModEntities.all().forEach(holder ->
                event.registerEntityRenderer(holder.get(), HerobrineRenderer::new));
    }
}
