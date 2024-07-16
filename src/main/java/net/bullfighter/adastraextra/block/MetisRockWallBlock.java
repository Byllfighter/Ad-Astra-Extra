
package net.bullfighter.adastraextra.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.SoundType;

public class MetisRockWallBlock extends WallBlock {
	public MetisRockWallBlock() {
		super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(1.2f, 5f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn());
	}
}
