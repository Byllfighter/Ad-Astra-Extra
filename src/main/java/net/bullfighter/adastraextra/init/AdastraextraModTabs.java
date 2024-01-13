
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.bullfighter.adastraextra.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.bullfighter.adastraextra.AdastraextraMod;

public class AdastraextraModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdastraextraMod.MODID);
	public static final RegistryObject<CreativeModeTab> AD_ASTRA_EXTRA_TAB = REGISTRY.register("ad_astra_extra_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.adastraextra.ad_astra_extra_tab")).icon(() -> new ItemStack(AdastraextraModBlocks.JUPITER_ROCK.get())).displayItems((parameters, tabData) -> {
				tabData.accept(AdastraextraModBlocks.GAS_CLOUD.get().asItem());
				tabData.accept(AdastraextraModBlocks.JUPITER_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.SATURN_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.URANUS_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.NEPTUNE_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.JUPITER_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.SATURN_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.URANUS_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.NEPTUNE_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.JUPITER_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.SATURN_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.URANUS_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.NEPTUNE_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.JUPITER_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.SATURN_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.URANUS_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.NEPTUNE_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.PHOBOS_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.DEIMOS_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.PHOBOS_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.DEIMOS_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.PHOBOS_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.DEIMOS_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.PHOBOS_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.DEIMOS_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.METIS_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.ADRASTEA_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.AMALTHEA_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.THEBE_ROCK.get().asItem());
				tabData.accept(AdastraextraModBlocks.METIS_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.ADRASTEA_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.AMALTHEA_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.THEBE_ROCK_SLAB.get().asItem());
				tabData.accept(AdastraextraModBlocks.METIS_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.ADRASTEA_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.AMALTHEA_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.THEBE_ROCK_STAIRS.get().asItem());
				tabData.accept(AdastraextraModBlocks.METIS_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.ADRASTEA_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.AMALTHEA_ROCK_WALL.get().asItem());
				tabData.accept(AdastraextraModBlocks.THEBE_ROCK_WALL.get().asItem());
			})

					.build());
}
