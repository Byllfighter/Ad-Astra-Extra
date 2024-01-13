package net.bullfighter.adastraextra.procedures;

import net.minecraft.world.entity.Entity;

public class GasCloudEntityCollidesInTheBlockProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getPersistentData().getDouble("adastraextraGassed") < 100) {
			entity.getPersistentData().putDouble("adastraextraGassed", (entity.getPersistentData().getDouble("adastraextraGassed") + 1));
		}
	}
}
