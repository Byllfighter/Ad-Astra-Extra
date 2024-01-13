
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.bullfighter.adastraextra.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.feature.Feature;

import net.bullfighter.adastraextra.world.features.GasCloudFeatureFeature;
import net.bullfighter.adastraextra.AdastraextraMod;

@Mod.EventBusSubscriber
public class AdastraextraModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, AdastraextraMod.MODID);
	public static final RegistryObject<Feature<?>> GAS_CLOUD_FEATURE = REGISTRY.register("gas_cloud_feature", GasCloudFeatureFeature::new);
}
