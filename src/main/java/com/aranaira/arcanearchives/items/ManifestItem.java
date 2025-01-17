package com.aranaira.arcanearchives.items;

import com.aranaira.arcanearchives.AAGuiHandler;
import com.aranaira.arcanearchives.ArcaneArchives;
import com.aranaira.arcanearchives.client.Keybinds;
import com.aranaira.arcanearchives.data.ClientNetwork;
import com.aranaira.arcanearchives.data.DataHelper;
import com.aranaira.arcanearchives.items.templates.ItemTemplate;
import com.aranaira.arcanearchives.util.ManifestTrackingUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ManifestItem extends ItemTemplate {
	public static final String NAME = "manifest";

	public ManifestItem () {
		super(NAME);
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick (World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}

		openManifest(worldIn, playerIn);

		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	public static void openManifest (World worldIn, EntityPlayer playerIn) {
		if (playerIn.isSneaking()) {
			ManifestTrackingUtils.clear();
		} else {
			ClientNetwork network = DataHelper.getClientNetwork(playerIn.getUniqueID());
			network.manifestItems.clear();
			network.synchroniseManifest();

			playerIn.openGui(ArcaneArchives.instance, AAGuiHandler.MANIFEST, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + I18n.format("arcanearchives.tooltip.item.manifest"));
		String additional = "";
		if (Keybinds.manifestKey.getKeyCode() != 0) {
			additional = " or " + Keybinds.manifestKey.getDisplayName();
		}
		tooltip.add(TextFormatting.GOLD + "" + TextFormatting.BOLD + "Right-Click" + additional + TextFormatting.RESET + TextFormatting.GOLD + " to open the manifest.");
		if (Keybinds.manifestKey.getKeyCode() != 0) {
			additional = " or Sneak-" + Keybinds.manifestKey.getDisplayName();
		}
		tooltip.add(TextFormatting.GOLD + "" + TextFormatting.BOLD + "Sneak-Right-Click" + additional + TextFormatting.RESET + TextFormatting.GOLD + " to clear inventory tracking.");
		if (Keybinds.manifestKey.getKeyCode() == 0) {
		}
	}
}
