package com.adityagadre.herobrineplus.entity;

import com.adityagadre.herobrineplus.HerobrinePlus;
import net.minecraft.resources.Identifier;

/**
 * Every Herobrine variant. Each one becomes its own {@code EntityType} (so it has a unique
 * registry id and unique translated name) but they all share {@link HerobrineEntity} for
 * behaviour and attributes.
 *
 * <p>The {@code id} is both the entity registry path and the texture file name under
 * {@code assets/herobrineplus/textures/entity/herobrine/&lt;id&gt;.png}.</p>
 */
public enum HerobrineVariant {
    HEROBRINE("herobrine", "Herobrine"),
    HEROBRINE_HD("herobrine_hd", "Herobrine HD"),
    ARCHER("archer", "Archer Herobrine"),
    DEMON("demon", "Demon Herobrine"),
    ENDER("ender", "Enderbrine"),
    EVIL("evil", "Evil Herobrine"),
    FIRE("fire", "Fire Herobrine"),
    GHOST("ghost", "Ghost Herobrine"),
    HUNTER("hunter", "Hunter Herobrine"),
    NETHER("nether", "Nether Herobrine"),
    NINJA("ninja", "Ninja Herobrine"),
    ROBOT("robot", "Robot Herobrine"),
    ZOMBIE("zombie", "Zombie Herobrine");

    private final String id;
    private final String displayName;
    private final Identifier texture;

    HerobrineVariant(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
        this.texture = HerobrinePlus.id("textures/entity/herobrine/" + id + ".png");
    }

    public String getId() {
        return id;
    }

    /** Default English name, emitted into en_us.json by the data generator. */
    public String getDisplayName() {
        return displayName;
    }

    /** Translation key used by the entity ("entity.herobrineplus.&lt;id&gt;"). */
    public String getTranslationKey() {
        return "entity." + HerobrinePlus.MODID + "." + id;
    }

    public Identifier getTexture() {
        return texture;
    }
}
