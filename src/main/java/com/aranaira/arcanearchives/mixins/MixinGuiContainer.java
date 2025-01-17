package com.aranaira.arcanearchives.mixins;

import com.aranaira.arcanearchives.client.gui.*;
import com.aranaira.arcanearchives.client.render.LineHandler;
import com.aranaira.arcanearchives.config.ConfigHandler;
import com.aranaira.arcanearchives.config.NonModTrackingConfig;
import com.aranaira.arcanearchives.util.ColorUtils;
import com.aranaira.arcanearchives.util.ColorUtils.Color;
import com.aranaira.arcanearchives.util.ManifestTrackingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(GuiContainer.class)
@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public abstract class MixinGuiContainer {
	private static List<Class<? extends GuiContainer>> CONTAINER_IGNORE_LIST = Arrays.asList(GUIManifest.class, GUIGemCuttersTable.class, GUIUpgrades.class, GUIRadiantChest.class, GUIGemSocket.class);

	@Inject(method = "drawSlot", at = @At(value = "HEAD"))
	private void onDrawSlot (Slot slot, CallbackInfo callbackInfo) {
		if (NonModTrackingConfig.DisableMixinHighlight || NonModTrackingConfig.getContainerClasses().contains(((GuiContainer) (Object) this).getClass())) {
			return;
		}

		if (CONTAINER_IGNORE_LIST.contains(((GuiContainer) (Object) this).getClass())) {
			return;
		}

		ItemStack stack = slot.getStack();
		if (!stack.isEmpty()) {
			if (ManifestTrackingUtils.matches(stack)) {
				GlStateManager.disableDepth();
				long worldTime = ((GuiContainer) (Object) this).mc.player.world.getWorldTime();
				Color c = ColorUtils.getColorFromTime(worldTime);
				GuiContainer.drawRect(slot.xPos, slot.yPos, slot.xPos + 16, slot.yPos + 16, c.toInteger());
				GlStateManager.enableDepth();
			}
		}
	}

	@Inject(method = "onGuiClosed", at = @At(value = "RETURN"))
	private void onGuiClosed (CallbackInfo callbackInfo) {
		Minecraft _mc = ((GuiContainer) (Object) this).mc;
		if (_mc != null && _mc.player != null) {
			LineHandler.checkClear(_mc.player.dimension);
		}
	}
}