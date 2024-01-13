
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.bullfighter.adastraextra.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.bullfighter.adastraextra.AdastraextraMod;

public class AdastraextraModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, AdastraextraMod.MODID);
	public static final RegistryObject<Item> GAS_CLOUD = block(AdastraextraModBlocks.GAS_CLOUD);
	public static final RegistryObject<Item> JUPITER_ROCK = block(AdastraextraModBlocks.JUPITER_ROCK);
	public static final RegistryObject<Item> SATURN_ROCK = block(AdastraextraModBlocks.SATURN_ROCK);
	public static final RegistryObject<Item> URANUS_ROCK = block(AdastraextraModBlocks.URANUS_ROCK);
	public static final RegistryObject<Item> NEPTUNE_ROCK = block(AdastraextraModBlocks.NEPTUNE_ROCK);
	public static final RegistryObject<Item> JUPITER_ROCK_SLAB = block(AdastraextraModBlocks.JUPITER_ROCK_SLAB);
	public static final RegistryObject<Item> SATURN_ROCK_SLAB = block(AdastraextraModBlocks.SATURN_ROCK_SLAB);
	public static final RegistryObject<Item> URANUS_ROCK_SLAB = block(AdastraextraModBlocks.URANUS_ROCK_SLAB);
	public static final RegistryObject<Item> NEPTUNE_ROCK_SLAB = block(AdastraextraModBlocks.NEPTUNE_ROCK_SLAB);
	public static final RegistryObject<Item> JUPITER_ROCK_STAIRS = block(AdastraextraModBlocks.JUPITER_ROCK_STAIRS);
	public static final RegistryObject<Item> SATURN_ROCK_STAIRS = block(AdastraextraModBlocks.SATURN_ROCK_STAIRS);
	public static final RegistryObject<Item> URANUS_ROCK_STAIRS = block(AdastraextraModBlocks.URANUS_ROCK_STAIRS);
	public static final RegistryObject<Item> NEPTUNE_ROCK_STAIRS = block(AdastraextraModBlocks.NEPTUNE_ROCK_STAIRS);
	public static final RegistryObject<Item> JUPITER_ROCK_WALL = block(AdastraextraModBlocks.JUPITER_ROCK_WALL);
	public static final RegistryObject<Item> SATURN_ROCK_WALL = block(AdastraextraModBlocks.SATURN_ROCK_WALL);
	public static final RegistryObject<Item> URANUS_ROCK_WALL = block(AdastraextraModBlocks.URANUS_ROCK_WALL);
	public static final RegistryObject<Item> NEPTUNE_ROCK_WALL = block(AdastraextraModBlocks.NEPTUNE_ROCK_WALL);
	public static final RegistryObject<Item> PHOBOS_ROCK = block(AdastraextraModBlocks.PHOBOS_ROCK);
	public static final RegistryObject<Item> DEIMOS_ROCK = block(AdastraextraModBlocks.DEIMOS_ROCK);
	public static final RegistryObject<Item> PHOBOS_ROCK_SLAB = block(AdastraextraModBlocks.PHOBOS_ROCK_SLAB);
	public static final RegistryObject<Item> DEIMOS_ROCK_SLAB = block(AdastraextraModBlocks.DEIMOS_ROCK_SLAB);
	public static final RegistryObject<Item> PHOBOS_ROCK_STAIRS = block(AdastraextraModBlocks.PHOBOS_ROCK_STAIRS);
	public static final RegistryObject<Item> DEIMOS_ROCK_STAIRS = block(AdastraextraModBlocks.DEIMOS_ROCK_STAIRS);
	public static final RegistryObject<Item> PHOBOS_ROCK_WALL = block(AdastraextraModBlocks.PHOBOS_ROCK_WALL);
	public static final RegistryObject<Item> DEIMOS_ROCK_WALL = block(AdastraextraModBlocks.DEIMOS_ROCK_WALL);
	public static final RegistryObject<Item> METIS_ROCK = block(AdastraextraModBlocks.METIS_ROCK);
	public static final RegistryObject<Item> ADRASTEA_ROCK = block(AdastraextraModBlocks.ADRASTEA_ROCK);
	public static final RegistryObject<Item> AMALTHEA_ROCK = block(AdastraextraModBlocks.AMALTHEA_ROCK);
	public static final RegistryObject<Item> THEBE_ROCK = block(AdastraextraModBlocks.THEBE_ROCK);
	public static final RegistryObject<Item> METIS_ROCK_SLAB = block(AdastraextraModBlocks.METIS_ROCK_SLAB);
	public static final RegistryObject<Item> ADRASTEA_ROCK_SLAB = block(AdastraextraModBlocks.ADRASTEA_ROCK_SLAB);
	public static final RegistryObject<Item> AMALTHEA_ROCK_SLAB = block(AdastraextraModBlocks.AMALTHEA_ROCK_SLAB);
	public static final RegistryObject<Item> THEBE_ROCK_SLAB = block(AdastraextraModBlocks.THEBE_ROCK_SLAB);
	public static final RegistryObject<Item> METIS_ROCK_STAIRS = block(AdastraextraModBlocks.METIS_ROCK_STAIRS);
	public static final RegistryObject<Item> ADRASTEA_ROCK_STAIRS = block(AdastraextraModBlocks.ADRASTEA_ROCK_STAIRS);
	public static final RegistryObject<Item> AMALTHEA_ROCK_STAIRS = block(AdastraextraModBlocks.AMALTHEA_ROCK_STAIRS);
	public static final RegistryObject<Item> THEBE_ROCK_STAIRS = block(AdastraextraModBlocks.THEBE_ROCK_STAIRS);
	public static final RegistryObject<Item> METIS_ROCK_WALL = block(AdastraextraModBlocks.METIS_ROCK_WALL);
	public static final RegistryObject<Item> ADRASTEA_ROCK_WALL = block(AdastraextraModBlocks.ADRASTEA_ROCK_WALL);
	public static final RegistryObject<Item> AMALTHEA_ROCK_WALL = block(AdastraextraModBlocks.AMALTHEA_ROCK_WALL);
	public static final RegistryObject<Item> THEBE_ROCK_WALL = block(AdastraextraModBlocks.THEBE_ROCK_WALL);

	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
