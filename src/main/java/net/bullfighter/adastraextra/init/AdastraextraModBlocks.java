
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.bullfighter.adastraextra.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.bullfighter.adastraextra.block.UranusRockWallBlock;
import net.bullfighter.adastraextra.block.UranusRockStairsBlock;
import net.bullfighter.adastraextra.block.UranusRockSlabBlock;
import net.bullfighter.adastraextra.block.UranusRockBlock;
import net.bullfighter.adastraextra.block.ThebeRockWallBlock;
import net.bullfighter.adastraextra.block.ThebeRockStairsBlock;
import net.bullfighter.adastraextra.block.ThebeRockSlabBlock;
import net.bullfighter.adastraextra.block.ThebeRockBlock;
import net.bullfighter.adastraextra.block.SaturnRockWallBlock;
import net.bullfighter.adastraextra.block.SaturnRockStairsBlock;
import net.bullfighter.adastraextra.block.SaturnRockSlabBlock;
import net.bullfighter.adastraextra.block.SaturnRockBlock;
import net.bullfighter.adastraextra.block.PhobosRockWallBlock;
import net.bullfighter.adastraextra.block.PhobosRockStairsBlock;
import net.bullfighter.adastraextra.block.PhobosRockSlabBlock;
import net.bullfighter.adastraextra.block.PhobosRockBlock;
import net.bullfighter.adastraextra.block.NeptuneRockWallBlock;
import net.bullfighter.adastraextra.block.NeptuneRockStairsBlock;
import net.bullfighter.adastraextra.block.NeptuneRockSlabBlock;
import net.bullfighter.adastraextra.block.NeptuneRockBlock;
import net.bullfighter.adastraextra.block.MetisRockWallBlock;
import net.bullfighter.adastraextra.block.MetisRockStairsBlock;
import net.bullfighter.adastraextra.block.MetisRockSlabBlock;
import net.bullfighter.adastraextra.block.MetisRockBlock;
import net.bullfighter.adastraextra.block.JupiterRockWallBlock;
import net.bullfighter.adastraextra.block.JupiterRockStairsBlock;
import net.bullfighter.adastraextra.block.JupiterRockSlabBlock;
import net.bullfighter.adastraextra.block.JupiterRockBlock;
import net.bullfighter.adastraextra.block.GasCloudBlock;
import net.bullfighter.adastraextra.block.DeimosRockWallBlock;
import net.bullfighter.adastraextra.block.DeimosRockStairsBlock;
import net.bullfighter.adastraextra.block.DeimosRockSlabBlock;
import net.bullfighter.adastraextra.block.DeimosRockBlock;
import net.bullfighter.adastraextra.block.AmaltheaRockWallBlock;
import net.bullfighter.adastraextra.block.AmaltheaRockStairsBlock;
import net.bullfighter.adastraextra.block.AmaltheaRockSlabBlock;
import net.bullfighter.adastraextra.block.AmaltheaRockBlock;
import net.bullfighter.adastraextra.block.AdrasteaRockWallBlock;
import net.bullfighter.adastraextra.block.AdrasteaRockStairsBlock;
import net.bullfighter.adastraextra.block.AdrasteaRockSlabBlock;
import net.bullfighter.adastraextra.block.AdrasteaRockBlock;
import net.bullfighter.adastraextra.AdastraextraMod;

public class AdastraextraModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK, AdastraextraMod.MODID);
	public static final DeferredHolder<Block, Block> JUPITER_ROCK = REGISTRY.register("jupiter_rock", () -> new JupiterRockBlock());
	public static final DeferredHolder<Block, Block> GAS_CLOUD = REGISTRY.register("gas_cloud", () -> new GasCloudBlock());
	public static final DeferredHolder<Block, Block> SATURN_ROCK = REGISTRY.register("saturn_rock", () -> new SaturnRockBlock());
	public static final DeferredHolder<Block, Block> URANUS_ROCK = REGISTRY.register("uranus_rock", () -> new UranusRockBlock());
	public static final DeferredHolder<Block, Block> NEPTUNE_ROCK = REGISTRY.register("neptune_rock", () -> new NeptuneRockBlock());
	public static final DeferredHolder<Block, Block> JUPITER_ROCK_SLAB = REGISTRY.register("jupiter_rock_slab", () -> new JupiterRockSlabBlock());
	public static final DeferredHolder<Block, Block> SATURN_ROCK_SLAB = REGISTRY.register("saturn_rock_slab", () -> new SaturnRockSlabBlock());
	public static final DeferredHolder<Block, Block> URANUS_ROCK_SLAB = REGISTRY.register("uranus_rock_slab", () -> new UranusRockSlabBlock());
	public static final DeferredHolder<Block, Block> NEPTUNE_ROCK_SLAB = REGISTRY.register("neptune_rock_slab", () -> new NeptuneRockSlabBlock());
	public static final DeferredHolder<Block, Block> JUPITER_ROCK_STAIRS = REGISTRY.register("jupiter_rock_stairs", () -> new JupiterRockStairsBlock());
	public static final DeferredHolder<Block, Block> SATURN_ROCK_STAIRS = REGISTRY.register("saturn_rock_stairs", () -> new SaturnRockStairsBlock());
	public static final DeferredHolder<Block, Block> URANUS_ROCK_STAIRS = REGISTRY.register("uranus_rock_stairs", () -> new UranusRockStairsBlock());
	public static final DeferredHolder<Block, Block> NEPTUNE_ROCK_STAIRS = REGISTRY.register("neptune_rock_stairs", () -> new NeptuneRockStairsBlock());
	public static final DeferredHolder<Block, Block> JUPITER_ROCK_WALL = REGISTRY.register("jupiter_rock_wall", () -> new JupiterRockWallBlock());
	public static final DeferredHolder<Block, Block> SATURN_ROCK_WALL = REGISTRY.register("saturn_rock_wall", () -> new SaturnRockWallBlock());
	public static final DeferredHolder<Block, Block> URANUS_ROCK_WALL = REGISTRY.register("uranus_rock_wall", () -> new UranusRockWallBlock());
	public static final DeferredHolder<Block, Block> NEPTUNE_ROCK_WALL = REGISTRY.register("neptune_rock_wall", () -> new NeptuneRockWallBlock());
	public static final DeferredHolder<Block, Block> PHOBOS_ROCK = REGISTRY.register("phobos_rock", () -> new PhobosRockBlock());
	public static final DeferredHolder<Block, Block> DEIMOS_ROCK = REGISTRY.register("deimos_rock", () -> new DeimosRockBlock());
	public static final DeferredHolder<Block, Block> PHOBOS_ROCK_SLAB = REGISTRY.register("phobos_rock_slab", () -> new PhobosRockSlabBlock());
	public static final DeferredHolder<Block, Block> DEIMOS_ROCK_SLAB = REGISTRY.register("deimos_rock_slab", () -> new DeimosRockSlabBlock());
	public static final DeferredHolder<Block, Block> PHOBOS_ROCK_STAIRS = REGISTRY.register("phobos_rock_stairs", () -> new PhobosRockStairsBlock());
	public static final DeferredHolder<Block, Block> DEIMOS_ROCK_STAIRS = REGISTRY.register("deimos_rock_stairs", () -> new DeimosRockStairsBlock());
	public static final DeferredHolder<Block, Block> PHOBOS_ROCK_WALL = REGISTRY.register("phobos_rock_wall", () -> new PhobosRockWallBlock());
	public static final DeferredHolder<Block, Block> DEIMOS_ROCK_WALL = REGISTRY.register("deimos_rock_wall", () -> new DeimosRockWallBlock());
	public static final DeferredHolder<Block, Block> METIS_ROCK = REGISTRY.register("metis_rock", () -> new MetisRockBlock());
	public static final DeferredHolder<Block, Block> ADRASTEA_ROCK = REGISTRY.register("adrastea_rock", () -> new AdrasteaRockBlock());
	public static final DeferredHolder<Block, Block> AMALTHEA_ROCK = REGISTRY.register("amalthea_rock", () -> new AmaltheaRockBlock());
	public static final DeferredHolder<Block, Block> THEBE_ROCK = REGISTRY.register("thebe_rock", () -> new ThebeRockBlock());
	public static final DeferredHolder<Block, Block> METIS_ROCK_SLAB = REGISTRY.register("metis_rock_slab", () -> new MetisRockSlabBlock());
	public static final DeferredHolder<Block, Block> ADRASTEA_ROCK_SLAB = REGISTRY.register("adrastea_rock_slab", () -> new AdrasteaRockSlabBlock());
	public static final DeferredHolder<Block, Block> AMALTHEA_ROCK_SLAB = REGISTRY.register("amalthea_rock_slab", () -> new AmaltheaRockSlabBlock());
	public static final DeferredHolder<Block, Block> THEBE_ROCK_SLAB = REGISTRY.register("thebe_rock_slab", () -> new ThebeRockSlabBlock());
	public static final DeferredHolder<Block, Block> METIS_ROCK_STAIRS = REGISTRY.register("metis_rock_stairs", () -> new MetisRockStairsBlock());
	public static final DeferredHolder<Block, Block> ADRASTEA_ROCK_STAIRS = REGISTRY.register("adrastea_rock_stairs", () -> new AdrasteaRockStairsBlock());
	public static final DeferredHolder<Block, Block> AMALTHEA_ROCK_STAIRS = REGISTRY.register("amalthea_rock_stairs", () -> new AmaltheaRockStairsBlock());
	public static final DeferredHolder<Block, Block> THEBE_ROCK_STAIRS = REGISTRY.register("thebe_rock_stairs", () -> new ThebeRockStairsBlock());
	public static final DeferredHolder<Block, Block> METIS_ROCK_WALL = REGISTRY.register("metis_rock_wall", () -> new MetisRockWallBlock());
	public static final DeferredHolder<Block, Block> ADRASTEA_ROCK_WALL = REGISTRY.register("adrastea_rock_wall", () -> new AdrasteaRockWallBlock());
	public static final DeferredHolder<Block, Block> AMALTHEA_ROCK_WALL = REGISTRY.register("amalthea_rock_wall", () -> new AmaltheaRockWallBlock());
	public static final DeferredHolder<Block, Block> THEBE_ROCK_WALL = REGISTRY.register("thebe_rock_wall", () -> new ThebeRockWallBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
