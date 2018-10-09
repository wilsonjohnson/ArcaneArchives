package com.aranaira.arcanearchives.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.aranaira.arcanearchives.util.NetworkHelper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Int;

/** @author SoggyMustache's GUI Creator (http://tools.soggymustache.net)*/
public class GUIBook extends GuiScreen {

	Minecraft mc = Minecraft.getMinecraft();
	private final int ImageHeight = 256, ImageWidth = 256, ImageScale = 256;
	private static final ResourceLocation GUITextures = new ResourceLocation("arcanearchives:textures/gui/tex_hud_lectern_items.png");

	private GenericButton Tab1;
	private GenericButton Tab2;
	private GenericButton Tab3;
	
	private GenericButton TopButton1;
	private GenericButton TopButton2;
	private GenericButton TopButton3;
	private GenericButton TopButton4;
	
	private GenericButton ClearCrafting;
	
	private Slot slot;

	public GUIBook()
	{
		
	}

	@Override
	public void initGui() 
	{
		buttonList.clear();
		int offLeft = (width - ImageWidth) / 2 - 3;
		int offTop = -16;

		buttonList.add(TopButton1 = new GenericButton(offLeft + (285 - 140) + (14 / 3), 44 + offTop, 14, 12, ""));
		buttonList.add(TopButton2 = new GenericButton(offLeft + (303 - 140) + (14 / 3), 44 + offTop, 14, 12, ""));
		buttonList.add(TopButton3 = new GenericButton(offLeft + (321 - 140) + (14 / 3), 44 + offTop, 14, 12, ""));
		buttonList.add(TopButton4 = new GenericButton(offLeft + (339 - 140) + (14 / 3), 44 + offTop, 14, 12, ""));

		buttonList.add(Tab3 = new GenericButton(offLeft + (375 - 140) + (15 / 3), 124 + offTop, 15, 40, ""));
		buttonList.add(Tab2 = new GenericButton(offLeft + (374 - 140) + (20 / 3), 40 + offTop, 20, 40, ""));
		buttonList.add(Tab1 = new GenericButton(offLeft + (375 - 140) + (15 / 3), 82 + offTop, 15, 40, ""));
		

		buttonList.add(ClearCrafting = new GenericButton(offLeft + (259 - 140) + (10 / 3), 116 + offTop, 10, 10, ""));
	}

	@Override
	public void updateScreen() 
	{
		for (GuiButton button : buttonList)
		{
			button.visible = true;
		}
	}

	@Override
	public void drawScreen(int parWidth, int parHeight, float particle) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableColorMaterial();
		this.mc.getTextureManager().bindTexture(GUITextures);

		//Adjust these values to move locations of elements without individual adjustment
		int offLeft = (int) ((width - ImageWidth) / 2.0F);
		int offTop = 10;
		int topOffset = 3;

		drawModalRectWithCustomSizedTexture(offLeft, offTop, 0, 0, ImageScale,ImageScale,ImageScale,ImageScale);
		
		offLeft += 1;

		GlStateManager.disableLighting();
		
		

		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (NetworkHelper.getArcaneArchivesNetwork(Minecraft.getMinecraft().player.getUniqueID()).GetAllItemsOnNetwork().size() > (x + y * 9))
				{
					ItemStack s = NetworkHelper.getArcaneArchivesNetwork(Minecraft.getMinecraft().player.getUniqueID()).GetAllItemsOnNetwork().get(x + y * 9);
					this.itemRender.renderItemAndEffectIntoGUI(s, offLeft + 45 + (20 * x), 40 + (18 * y) + topOffset);
					this.itemRender.renderItemOverlayIntoGUI(fontRenderer, s, offLeft + 45 + (20 * x), 40 + (18 * y) + topOffset, null);
				}
				else
					this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(0)), offLeft + 45 + (20 * x), 40 + (18 * y) + topOffset);
				
			}
		}
		/*
		//Top Matrix Inventory
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (205 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (222 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (239 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (258 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (294 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (312 - 140), (170 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (330 - 140), (170 - 130) + topOffset);
		//Second Bottom Matrix Inventory
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (203 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (221 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (239 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (257 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (294 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (312 - 140), (206 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (330 - 140), (206 - 130) + topOffset);
		//Bottom Matrix Inventory
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (203 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (221 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (239 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (257 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (293 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (312 - 140), (188 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (330 - 140), (188 - 130) + topOffset);
		*/
		//Crafting Area
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (203 - 140), (228 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (221 - 140), (228 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (239 - 140), (228 - 130) + topOffset);

		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (221 - 140), (246 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (203 - 140), (246 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (239 - 140), (246 - 130) + topOffset);

		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (203 - 140), (264 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (221 - 140), (264 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (238 - 140), (264 - 130) + topOffset);
		//Crafting Area Result
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (311 - 140), (246 - 130) + topOffset);
		//Third Lowest
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (203 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (221 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (239 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (257 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (294 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (312 - 140), (297 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (329 - 140), (297 - 130) + topOffset);
		//Second Lowest
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (203 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (221 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (239 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (257 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (293 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (311 - 140), (315 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (329 - 140), (315 - 130) + topOffset);
		//Bottom Inventory
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (203 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (221 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (238 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (257 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (293 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (311 - 140), (332 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (329 - 140), (332 - 130) + topOffset);
		//Hot Bar
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(1)), offLeft + (185 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(2)), offLeft + (203 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(3)), offLeft + (221 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(4)), offLeft + (239 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(5)), offLeft + (257 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(6)), offLeft + (275 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(7)), offLeft + (293 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(8)), offLeft + (311 - 140), (355 - 130) + topOffset);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Block.getBlockById(9)), offLeft + (329 - 140), (355 - 130) + topOffset);

		//this.itemRender.renderItemOverlayIntoGUI(fontRenderer, new ItemStack(Block.getBlockById(8)), offLeft + (329 - 140), (355 - 130), "");
		
		fontRenderer.drawString("Search", offLeft + 186 - 140, (154 - 130) + topOffset + 4, 0x000000);
		
		GlStateManager.enableLighting();
		
		super.drawScreen(parWidth, parHeight, particle);
	}
	@Override
	protected void keyTyped(char typedChar, int keyCode){
		if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
			Minecraft.getMinecraft().player.closeScreen();
	}
	@Override
	protected void mouseClickMove(int parMouseX, int parMouseY, int parLastButtonClicked, long parTimeSinceMouseClick) 
	{ 
		
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		//Within the if statements write your button actions (if you have buttons)
		if (button == Tab1)
		{
			
		}
		if (button == Tab2)
		{
			
		}
		if (button == Tab3)
		{
			
		}
		if (button == ClearCrafting)
		{
			
		}
		if (button == TopButton1)
		{
			
		}
		if (button == TopButton2)
		{
			
		}
		if (button == TopButton3)
		{
			
		}
		if (button == TopButton4)
		{
			
		}
	}

	@Override
	public void onGuiClosed() 
	{ 
		
	}

	@Override
	public boolean doesGuiPauseGame() 
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
   	static class GenericButton extends GuiButton
   	{
		public GenericButton(int x, int y, int width, int height, String text) {
			super(1, x, y, width, height, text);
		}
	}
}
