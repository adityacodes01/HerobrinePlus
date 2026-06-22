package com.adityagadre.herobrineplus.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

/**
 * Per-frame render state for a Herobrine. Modern Minecraft separates entity data from rendering by
 * extracting everything the renderer needs into a state object each frame. We add the variant's
 * texture so the shared renderer can pick the right skin without touching the live entity.
 */
public class HerobrineRenderState extends HumanoidRenderState {
    public Identifier texture;
}
