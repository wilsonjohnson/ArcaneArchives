package com.aranaira.arcanearchives.blocks.templates;

import com.aranaira.arcanearchives.tileentities.AATileEntity;
import com.aranaira.arcanearchives.util.WorldUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDirectionalTemplate extends BlockTemplate {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	/**
	 * Creates a new directional block and adds it to the BlockRegistry, and if createItemBlock is true,
	 * a matching ItemBlock to the ItemRegistry. Functionally just calls {@link BlockTemplate#BlockTemplate(String, Material)}
	 *
	 * @param name       The name of the block, used for translation key and registry name
	 * @param materialIn The material of the block
	 */
	protected BlockDirectionalTemplate (String name, Material materialIn) {
		super(name, materialIn);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta (int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
	}

	@Override
	public int getMetaFromState (IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateForPlacement (World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, EnumFacing.fromAngle(placer.rotationYaw - 90));
	}

	@Override
	protected BlockStateContainer createBlockState () {
		/*if (hasTileEntity(getDefaultState())) {
			return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{Properties.AnimationProperty});
		}*/

		return new BlockStateContainer(this, FACING);
		//ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{});
	}

	@Override
	public boolean rotateBlock (World world, BlockPos pos, EnumFacing axis) {
		AATileEntity tile = WorldUtil.getTileEntity(AATileEntity.class, world, pos);
		boolean result = super.rotateBlock(world, pos, axis);
		if (tile != null) {
			tile.validate();
			world.setTileEntity(pos, tile);
		}
		return result;
	}


}
