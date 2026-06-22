package com.adityagadre.herobrineplus.item;

import com.adityagadre.herobrineplus.entity.HerobrineEntity;
import com.adityagadre.herobrineplus.entity.HerobrineVariant;
import com.adityagadre.herobrineplus.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * A single spawn egg shared by every Herobrine. Right-clicking a block spawns one randomly
 * chosen {@link HerobrineVariant} at the clicked face.
 */
public class HerobrineSpawnEggItem extends Item {

    public HerobrineSpawnEggItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            // Client side: report success so the arm swings; the server does the real spawn.
            return InteractionResult.SUCCESS;
        }

        BlockPos clicked = context.getClickedPos();
        Direction face = context.getClickedFace();
        // Spawn against the clicked face so the entity isn't embedded in the block.
        BlockPos spawnPos = level.getBlockState(clicked).getCollisionShape(level, clicked).isEmpty()
                ? clicked
                : clicked.relative(face);

        HerobrineVariant[] variants = HerobrineVariant.values();
        HerobrineVariant variant = variants[serverLevel.getRandom().nextInt(variants.length)];
        EntityType<HerobrineEntity> type = ModEntities.get(variant).get();

        HerobrineEntity entity = type.create(serverLevel, EntitySpawnReason.SPAWNER);
        if (entity == null) {
            return InteractionResult.FAIL;
        }

        entity.setPos(
                spawnPos.getX() + 0.5D,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5D);
        entity.setYRot(serverLevel.getRandom().nextFloat() * 360.0F);
        serverLevel.addFreshEntity(entity);

        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}
