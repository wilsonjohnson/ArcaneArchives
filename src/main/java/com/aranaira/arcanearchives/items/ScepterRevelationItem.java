package com.aranaira.arcanearchives.items;

import com.aranaira.arcanearchives.items.templates.ItemTemplate;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ScepterRevelationItem extends ItemTemplate
{
	public static final String NAME = "item_scepterrevelation";

	public ScepterRevelationItem()
	{
		super(NAME);
	}
}
