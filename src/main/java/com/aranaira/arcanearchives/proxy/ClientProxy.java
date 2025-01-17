package com.aranaira.arcanearchives.proxy;

import com.aranaira.arcanearchives.ArcaneArchives;
import com.aranaira.arcanearchives.client.Keybinds;
import com.aranaira.arcanearchives.client.render.*;
import com.aranaira.arcanearchives.client.render.entity.RenderWeight;
import com.aranaira.arcanearchives.data.DataHelper;
import com.aranaira.arcanearchives.entity.EntityWeight;
import com.aranaira.arcanearchives.init.BlockRegistry;
import com.aranaira.arcanearchives.integration.guidebook.GBookInit;
import com.aranaira.arcanearchives.items.itemblocks.RadiantTankItem;
import com.aranaira.arcanearchives.tileentities.BrazierTileEntity;
import com.aranaira.arcanearchives.tileentities.RadiantChestTileEntity;
import com.aranaira.arcanearchives.tileentities.RadiantTankTileEntity;
import com.aranaira.enderio.core.client.render.IconUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = ArcaneArchives.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@SideOnly(Side.CLIENT)
	public static RadiantTankTESR tankTESR;
	@SideOnly(Side.CLIENT)
	public static RadiantTankTEISR itemTESR;
	@SideOnly(Side.CLIENT)
	public static RadiantChestTESR chestTESR;
	@SideOnly(Side.CLIENT)
	public static RadiantTroveTESR troveTESR;
	@SideOnly(Side.CLIENT)
	public static BrazierTESR brazierTESR;

	@SubscribeEvent
	public static void playerLoggedIn (PlayerEvent.PlayerLoggedInEvent event) {
		DataHelper.clearClientCache();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void modelRegister (ModelRegistryEvent event) {
		tankTESR = new RadiantTankTESR();
		ClientRegistry.bindTileEntitySpecialRenderer(RadiantTankTileEntity.class, tankTESR);
		//
		RadiantTankItem item = (RadiantTankItem) BlockRegistry.RADIANT_TANK.getItemBlock();
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(ArcaneArchives.MODID, "dummy_builtin_blocktransforms"), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
		itemTESR = new RadiantTankTEISR(tankTESR);
		item.setTileEntityItemStackRenderer(itemTESR);
		//
		chestTESR = new RadiantChestTESR();
		ClientRegistry.bindTileEntitySpecialRenderer(RadiantChestTileEntity.class, chestTESR);
		//
		//troveTESR = new RadiantTroveTESR();
		//ClientRegistry.bindTileEntitySpecialRenderer(RadiantTroveTileEntity.class, troveTESR);
		//
		brazierTESR = new BrazierTESR();
		ClientRegistry.bindTileEntitySpecialRenderer(BrazierTileEntity.class, brazierTESR);
	}

	@Override
	public void preInit (FMLPreInitializationEvent event) {
		super.preInit(event);
		OBJLoader.INSTANCE.addDomain(ArcaneArchives.MODID);
		IconUtil.instance.init();

		Keybinds.initKeybinds();
		RenderingRegistry.registerEntityRenderingHandler(EntityWeight.class, RenderWeight::new);
	}

	@Override
	public void init (FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void loadComplete (FMLLoadCompleteEvent event) {
		if (Loader.isModLoaded("gbook_snapshot")) {
			GBookInit.init();
		}
	}

	@Override
	public void registerItemRenderer (@Nonnull Item item, int meta, String id) {
	}
}
