package net.bullfighter.adastraextra.procedures;

import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EntityTickProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double lightningx = 0;
		double lightningy = 0;
		double lightningz = 0;
		if (entity.getPersistentData().getDouble("adastraextraGassed") >= 20) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("adastraextra:gassed")))), 1);
		}
		if (entity.getPersistentData().getDouble("adastraextraGassed") > 0) {
			entity.getPersistentData().putDouble("adastraextraGassed", (entity.getPersistentData().getDouble("adastraextraGassed") - 0.25));
		}
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, new ResourceLocation("adastraextra:jupiter"))) {
			if (Math.random() < 0.01) {
				lightningx = x + Mth.nextInt(RandomSource.create(), -60, 60);
				lightningz = z + Mth.nextInt(RandomSource.create(), -60, 60);
				lightningy = y;
				if (world.canSeeSkyFromBelowWater(BlockPos.containing(lightningx, lightningy, lightningz))) {
					while (world.canSeeSkyFromBelowWater(BlockPos.containing(lightningx, lightningy, lightningz))) {
						lightningy = lightningy - 1;
					}
				} else {
					while (!world.canSeeSkyFromBelowWater(BlockPos.containing(lightningx, lightningy, lightningz))) {
						lightningy = lightningy + 1;
					}
				}
				if (world instanceof ServerLevel _level) {
					LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
					entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(lightningx, lightningy, lightningz)));;
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
	}
}
