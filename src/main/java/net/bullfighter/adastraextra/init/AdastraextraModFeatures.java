
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.bullfighter.adastraextra.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.core.registries.Registries;

import net.bullfighter.adastraextra.world.features.GasCloudFeatureFeature;
import net.bullfighter.adastraextra.AdastraextraMod;

public class AdastraextraModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registries.FEATURE, AdastraextraMod.MODID);
	public static final DeferredHolder<Feature<?>, Feature<?>> GAS_CLOUD_FEATURE = REGISTRY.register("gas_cloud_feature", GasCloudFeatureFeature::new);
}
