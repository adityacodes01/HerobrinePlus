package com.adityagadre.herobrineplus.client.model;

import com.adityagadre.herobrineplus.HerobrinePlus;
import com.adityagadre.herobrineplus.client.renderer.HerobrineRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

/**
 * Standard humanoid (Steve-shaped) model for every Herobrine variant. The mesh uses the classic
 * humanoid UV layout with a 64x64 texture so it accepts modern player-format skins, including the
 * "hat"/head overlay layer. Limb animation is inherited from {@link HumanoidModel}.
 */
public class HerobrineModel extends HumanoidModel<HerobrineRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(HerobrinePlus.id("herobrine"), "main");

    public HerobrineModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        return LayerDefinition.create(mesh, 64, 64);
    }
}
