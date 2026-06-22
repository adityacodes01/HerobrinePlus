package com.adityagadre.herobrineplus.registry;

import com.adityagadre.herobrineplus.HerobrinePlus;
import com.adityagadre.herobrineplus.entity.HerobrineEntity;
import com.adityagadre.herobrineplus.entity.HerobrineVariant;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Registers one {@link EntityType} per {@link HerobrineVariant}. All of them are built from the
 * same {@link HerobrineEntity} factory and the same hitbox; they differ only by registry id.
 */
public final class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HerobrinePlus.MODID);

    private static final EnumMap<HerobrineVariant, RegistryObject<EntityType<HerobrineEntity>>> REGISTRY =
            new EnumMap<>(HerobrineVariant.class);

    /** Built lazily after registration, mapping a baked EntityType back to its variant. */
    private static Map<EntityType<?>, HerobrineVariant> byType;

    static {
        for (HerobrineVariant variant : HerobrineVariant.values()) {
            REGISTRY.put(variant, ENTITY_TYPES.register(variant.getId(),
                    () -> EntityType.Builder.<HerobrineEntity>of(HerobrineEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .clientTrackingRange(10)
                            // Forge 65: build() now requires the entity's ResourceKey,
                            // which the DeferredRegister exposes via key(name).
                            .build(ENTITY_TYPES.key(variant.getId()))));
        }
    }

    private ModEntities() {
    }

    public static RegistryObject<EntityType<HerobrineEntity>> get(HerobrineVariant variant) {
        return REGISTRY.get(variant);
    }

    public static Collection<RegistryObject<EntityType<HerobrineEntity>>> all() {
        return REGISTRY.values();
    }

    /** Resolve which variant a baked EntityType belongs to (used by the renderer for textures). */
    public static HerobrineVariant variantOf(EntityType<?> type) {
        if (byType == null) {
            Map<EntityType<?>, HerobrineVariant> map = new IdentityHashMap<>();
            for (Map.Entry<HerobrineVariant, RegistryObject<EntityType<HerobrineEntity>>> e : REGISTRY.entrySet()) {
                map.put(e.getValue().get(), e.getKey());
            }
            byType = map;
        }
        return byType.getOrDefault(type, HerobrineVariant.HEROBRINE);
    }

    public static void register(BusGroup modBus) {
        ENTITY_TYPES.register(modBus);
    }
}
