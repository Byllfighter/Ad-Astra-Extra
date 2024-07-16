
package net.bullfighter.adastraextra.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.SoundType;

public class SaturnRockWallBlock extends WallBlock {
	public SaturnRockWallBlock() {
		super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(1f, 4f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn());
	}
}
