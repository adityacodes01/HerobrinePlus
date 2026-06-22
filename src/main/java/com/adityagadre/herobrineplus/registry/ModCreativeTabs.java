package com.adityagadre.herobrineplus.registry;

import com.adityagadre.herobrineplus.HerobrinePlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HerobrinePlus.MODID);

    public static final RegistryObject<CreativeModeTab> HEROBRINE_TAB =
            CREATIVE_MODE_TABS.register("herobrineplus", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + HerobrinePlus.MODID))
                    .icon(() -> new ItemStack(ModItems.HEROBRINE_SPAWN_EGG.get()))
                    .displayItems((parameters, output) ->
                            output.accept(ModItems.HEROBRINE_SPAWN_EGG.get()))
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(BusGroup modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}
