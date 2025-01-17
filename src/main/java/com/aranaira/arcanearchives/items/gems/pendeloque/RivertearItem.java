package com.aranaira.arcanearchives.items.gems.pendeloque;

import com.aranaira.arcanearchives.items.gems.ArcaneGemItem;
import com.aranaira.arcanearchives.items.gems.GemUtil;
import com.aranaira.arcanearchives.items.gems.GemUtil.AvailableGemsHandler;
import com.aranaira.arcanearchives.network.Networking;
import com.aranaira.arcanearchives.network.PacketArcaneGems.GemParticle;
import com.aranaira.arcanearchives.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.tiles.crafting.TileCrucible;
import vazkii.botania.api.item.IPetalApothecary;

import java.util.List;

public class RivertearItem extends ArcaneGemItem {
	public static final String NAME = "rivertear";

	public RivertearItem () {
		super(NAME, GemCut.PENDELOQUE, GemColor.BLUE, 25, 100);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("arcanearchives.tooltip.gemcharge") + ": " + getTooltipData(stack));
		tooltip.add(TextFormatting.GOLD + I18n.format("arcanearchives.tooltip.gem.rivertear"));
		tooltip.add(TextFormatting.GOLD + I18n.format("arcanearchives.tooltip.gem.recharge.rivertear"));
	}

	@Override
	public boolean doesSneakBypassUse (ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return true;
	}

    /*@Override
    public EnumActionResult onItemUse (EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if(GemUtil.getCharge(player.getHeldItem(hand)) > 0) {
                world.setBlockState(pos.offset(facing), Blocks.WATER.getDefaultState(), 11);
                GemUtil.consumeCharge(player.getHeldItemMainhand(), 1);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }*/

	@Override
	public boolean onEntityItemUpdate (EntityItem entityItem) {
		World world = entityItem.world;

		if (!world.isRemote && entityItem.isInWater()) {
			if (GemUtil.getCharge(entityItem) < GemUtil.getMaxCharge(entityItem)) {
				GemUtil.restoreCharge(entityItem, -1);
				world.playSound(entityItem.posX, entityItem.posY, entityItem.posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 0.5f, false);
				return true;
			}
		}
		return super.onEntityItemUpdate(entityItem);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick (World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			AvailableGemsHandler handler = GemUtil.getHeldGem(player, hand);
			if (GemUtil.getCharge(handler.getHeld()) > 0) {
				Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
				Vec3d dir = player.getLookVec();
				Vec3d rayTarget = new Vec3d(start.x + dir.x * 40, start.y + dir.y * 40, start.z + dir.z * 40);

				RayTraceResult ray = world.rayTraceBlocks(start, rayTarget, false, true, false);

				if (ray != null) {
					BlockPos pos = ray.getBlockPos();
					boolean isPetalApothecary = false;
					boolean isCrucible = false;
					if(Loader.isModLoaded("botania")) {
						IPetalApothecary ipa = WorldUtil.getTileEntity(IPetalApothecary.class, player.dimension, pos);
						if(ipa != null)
							isPetalApothecary = true;
					}
					if(Loader.isModLoaded("thaumcraft")) {
						TileCrucible tc = WorldUtil.getTileEntity(TileCrucible.class, player.dimension, pos);
						if(tc != null)
							isCrucible = true;
					}
					if (!isPetalApothecary && !isCrucible) { //Cancel normal use if the target block is a petal apothecary
						EnumFacing facing = ray.sideHit;

						Vec3d end = new Vec3d(pos.offset(facing).getX(), pos.offset(facing).getY(), pos.offset(facing).getZ());

						IBlockState water = Blocks.WATER.getDefaultState();
						world.setBlockState(pos.offset(facing), water);
						Blocks.WATER.neighborChanged(water, world, pos.offset(facing), Blocks.WATER, null);

						if (!player.capabilities.isCreativeMode) {
							GemUtil.consumeCharge(handler.getHeld(), 1);
						}

						GemParticle packet = new GemParticle(cut, color, start, end);
						NetworkRegistry.TargetPoint tp = new NetworkRegistry.TargetPoint(player.dimension, start.x, start.y, start.z, 160);
						Networking.CHANNEL.sendToAllTracking(packet, tp);
					}
				}
			}
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
