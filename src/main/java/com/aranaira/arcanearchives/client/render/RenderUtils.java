package com.aranaira.arcanearchives.client.render;

import com.aranaira.arcanearchives.util.ColorUtils;
import com.aranaira.arcanearchives.util.ColorUtils.Color;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Set;

public class RenderUtils {
	@SideOnly(Side.CLIENT)
	public static void drawRays (long worldTime, Vec3d player_pos, Set<Vec3d> target_pos) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.translate(-player_pos.x, -player_pos.y, -player_pos.z);

		Color c = ColorUtils.getColorFromTime(worldTime);//new Color(0.601f, 0.164f, 0.734f, 1f);
		GlStateManager.color(c.red, c.green, c.blue, c.alpha);
		GlStateManager.depthMask(false);
		for (Vec3d vec : target_pos) {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			GlStateManager.glLineWidth((1.0f - getLineWidthFromDistance(player_pos, vec, 10, 70)) * 10.0F);
			bufferBuilder.pos(player_pos.x, player_pos.y + 1, player_pos.z).color(c.red, c.green, c.blue, c.alpha).endVertex();
			bufferBuilder.pos(vec.x + 0.5, vec.y + 0.5, vec.z + 0.5).color(c.red, c.green, c.blue, c.alpha).endVertex();
			tessellator.draw();
		}

		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
		GlStateManager.disableBlend();
		//GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
	}

	@SideOnly(Side.CLIENT)
	public static void drawSegmentedLine (long worldTime, java.awt.Color color, float width, Vec3d player_pos, ArrayList<Vec3d> verts) {
		//GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		//ArcaneArchives.logger.info("starting line render");
		GlStateManager.pushMatrix();
		//ArcaneArchives.logger.info("pushed matrix");
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.translate(-player_pos.x, -player_pos.y, -player_pos.z);

		GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		GlStateManager.depthMask(false);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

		bufferBuilder.pos(player_pos.x, player_pos.y + 1, player_pos.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
		for (Vec3d vert : verts) {
			GlStateManager.glLineWidth(getLineWidthFromDistance(player_pos, vert, 10, 70));
			bufferBuilder.pos(vert.x, vert.y, vert.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
			//ArcaneArchives.logger.info("added line vert at "+vert);
		}
		tessellator.draw();

		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
	}

	private static float getLineWidthFromDistance (Vec3d first, Vec3d second, float minDistanceClamp, float maxDistanceClamp) {
		float dist = (float) first.distanceTo(second);
		float normalized = MathHelper.clamp((dist - minDistanceClamp) / (maxDistanceClamp - minDistanceClamp), 0.0f, 1.0f);
		float width = normalized * 0.7f + 0.3f;
		return width;
	}

	public static void renderFullbrightBlockModel (World world, BlockPos pos, IBlockState state, boolean translateToOrigin) {
		GlStateManager.pushMatrix();
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		if (translateToOrigin) {
			buffer.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
		}
		Minecraft mc = Minecraft.getMinecraft();
		BlockRendererDispatcher dispatcher = mc.getBlockRendererDispatcher();
		BlockModelShapes shapes = dispatcher.getBlockModelShapes();
		IBakedModel thisBlock = shapes.getModelForState(state);

		final IBlockAccess wrapper = world;
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		long rand = MathHelper.getPositionRandom(pos);

		for (BlockRenderLayer layer : BlockRenderLayer.values()) {
			if (state.getBlock().canRenderInLayer(state, layer)) {
				ForgeHooksClient.setRenderLayer(layer);
				dispatcher.getBlockModelRenderer().renderModel(wrapper, thisBlock, state, pos, buffer, false);
			}
		}

		ForgeHooksClient.setRenderLayer(null);
		if (translateToOrigin) {
			buffer.setTranslation(0, 0, 0);
		}
		int colour = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, world, pos, 0);
		float[] argb = ColorUtils.getARGB(colour);
		float bright = 2f;
		//buffer.putColorRGB_F4(255, 255, 255);
		//argb[1] * bright, argb[2] * bright, argb[3] * bright);
		//buffer.putBrightness4(0, 255, 255, 0);
		buffer.putBrightness4(255, 255, 255, 255);
		Tessellator.getInstance().draw();
	}

}
