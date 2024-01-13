
package net.bullfighter.adastraextra.world.features;

import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.WorldGenLevel;

import net.bullfighter.adastraextra.procedures.GasCloudFeatureAdditionalGenerationConditionProcedure;

public class GasCloudFeatureFeature extends OreFeature {
	public GasCloudFeatureFeature() {
		super(OreConfiguration.CODEC);
	}

	public boolean place(FeaturePlaceContext<OreConfiguration> context) {
		WorldGenLevel world = context.level();
		int x = context.origin().getX();
		int y = context.origin().getY();
		int z = context.origin().getZ();
		if (!GasCloudFeatureAdditionalGenerationConditionProcedure.execute())
			return false;
		return super.place(context);
	}
}
