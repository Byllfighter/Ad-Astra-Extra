package net.bullfighter.adastraextra.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.bullfighter.adastraextra.init.AdastraextraModBlocks;

public class GasCloudOverlayDisplayOverlayIngameProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		return (world.getBlockState(BlockPos.containing(x, y + (entity.isShiftKeyDown() ? 1.4 : 1.6), z))).getBlock() == AdastraextraModBlocks.GAS_CLOUD.get();
	}
}
