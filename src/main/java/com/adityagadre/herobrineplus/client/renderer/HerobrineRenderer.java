package com.adityagadre.herobrineplus.client.renderer;

import com.adityagadre.herobrineplus.client.model.HerobrineModel;
import com.adityagadre.herobrineplus.entity.HerobrineEntity;
import com.adityagadre.herobrineplus.registry.ModEntities;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;

/**
 * Renders a {@link HerobrineEntity} with the texture that matches its variant. Because all variants
 * share one model, only the texture lookup differs between them.
 *
 * <p>Modern rendering uses a render-state object: {@link #extractRenderState} copies the variant
 * texture out of the entity each frame, and {@link #getTextureLocation} reads it back from state.</p>
 */
public class HerobrineRenderer
        extends HumanoidMobRenderer<HerobrineEntity, HerobrineRenderState, HerobrineModel> {

    private static final float SHADOW_RADIUS = 0.5F;

    public HerobrineRenderer(EntityRendererProvider.Context context) {
        super(context,
                new HerobrineModel(context.bakeLayer(HerobrineModel.LAYER_LOCATION)),
                SHADOW_RADIUS);
    }

    @Override
    public HerobrineRenderState createRenderState() {
        return new HerobrineRenderState();
    }

    @Override
    public void extractRenderState(HerobrineEntity entity, HerobrineRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.texture = ModEntities.variantOf(entity.getType()).getTexture();
    }

    @Override
    public Identifier getTextureLocation(HerobrineRenderState state) {
        return state.texture;
    }
}
