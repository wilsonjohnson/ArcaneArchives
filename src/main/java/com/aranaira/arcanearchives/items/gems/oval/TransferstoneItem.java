package com.aranaira.arcanearchives.items.gems.oval;

import com.aranaira.arcanearchives.ArcaneArchives;
import com.aranaira.arcanearchives.init.BlockRegistry;
import com.aranaira.arcanearchives.items.gems.ArcaneGemItem;
import com.aranaira.arcanearchives.tileentities.RadiantTroveTileEntity;
import com.aranaira.arcanearchives.tileentities.RadiantTroveTileEntity.TroveItemHandler;
import com.aranaira.arcanearchives.util.ItemUtils;
import com.aranaira.arcanearchives.util.WorldUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class TransferstoneItem extends ArcaneGemItem {
	public static final String NAME = "transferstone";

	public TransferstoneItem () {
		super(NAME, GemCut.OVAL, GemColor.BLUE, 1, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		NBTTagCompound nbt = ItemUtils.getOrCreateTagCompound(stack);
		tooltip.add(I18n.format("arcanearchives.tooltip.gemcharge") + ": " + getTooltipData(stack));
		tooltip.add(TextFormatting.GOLD + I18n.format("arcanearchives.tooltip.gem.transferstone"));
		tooltip.add(TextFormatting.GOLD + I18n.format("arcanearchives.tooltip.gem.recharge.transferstone"));
		if (nbt.hasKey("troveLocation")) {
			BlockPos bp = BlockPos.fromLong(nbt.getLong("troveLocation"));
			tooltip.add("Linked to " + bp.getX() + "/" + bp.getY() + "/" + bp.getZ() + " in \"" + DimensionType.getById(nbt.getInteger("dimID")).getName() + "\"");
		}
	}

	@Override
	public boolean doesSneakBypassUse (ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return false;
	}

	@Override
	public boolean hasToggleMode () {
		return true;
	}

	@Override
	public EnumActionResult onItemUse (EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				//ArcaneArchives.logger.info("sneaking");
				if (world.getBlockState(pos).getBlock() == BlockRegistry.RADIANT_TROVE) {
					//ArcaneArchives.logger.info("trove detected");
					setLinkedTrove(player.getHeldItemMainhand(), pos, player.dimension);
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	public static void setLinkedTrove (ItemStack stack, BlockPos pos, int dimension) {
		NBTTagCompound nbt = ItemUtils.getOrCreateTagCompound(stack);

		nbt.setLong("troveLocation", pos.toLong());
		nbt.setInteger("troveDimID", dimension);

		stack.setTagCompound(nbt);
	}

	public static ItemStack extractLinkedItem (ItemStack stack, boolean doFullStack) {
		NBTTagCompound nbt = ItemUtils.getOrCreateTagCompound(stack);
		ItemStack output = null;

		if (nbt.hasKey("troveLocation")) {
			BlockPos pos = BlockPos.fromLong(nbt.getLong("troveLocation"));
			int dimID = nbt.getInteger("troveDimID");
			if (WorldUtil.isChunkLoaded(DimensionManager.getWorld(dimID), pos)) {
				RadiantTroveTileEntity te = WorldUtil.getTileEntity(RadiantTroveTileEntity.class, dimID, pos);
				if (te != null) {
					if (doFullStack)
					//TODO: Check item's actual stack size
					{
						output = te.getInventory().extractItem(0, 64, false);
					} else {
						output = te.getInventory().extractItem(0, 1, false);
					}
				}
			}
		}

		return output;
	}

	public static ItemStack insertLinkedItem (ItemStack stack) {
		NBTTagCompound nbt = ItemUtils.getOrCreateTagCompound(stack);
		ItemStack output = null;

		if (nbt.hasKey("troveLocation")) {
			BlockPos pos = BlockPos.fromLong(nbt.getLong("troveLocation"));
			int dimID = nbt.getInteger("troveDimID");
			if (WorldUtil.isChunkLoaded(DimensionManager.getWorld(dimID), pos)) {
				RadiantTroveTileEntity te = WorldUtil.getTileEntity(RadiantTroveTileEntity.class, dimID, pos);
				if (te != null) {
					TroveItemHandler handler = te.getInventory();
					handler.insertItem(1, stack, false);
				}
			}
		}

		return output;
	}

	public static Item getLinkedItem (ItemStack stack) {
		NBTTagCompound nbt = ItemUtils.getOrCreateTagCompound(stack);
		Item output = null;

		if (nbt.hasKey("troveLocation")) {
			BlockPos pos = BlockPos.fromLong(nbt.getLong("troveLocation"));
			int dimID = nbt.getInteger("troveDimID");
			if (WorldUtil.isChunkLoaded(DimensionManager.getWorld(dimID), pos)) {
				RadiantTroveTileEntity te = WorldUtil.getTileEntity(RadiantTroveTileEntity.class, dimID, pos);
				if (te != null) {
					output = te.getInventory().extractItem(0, 1, true).getItem();
				}
			}
		}

		return output;
	}
}
