package net.bullfighter.adastraextra.procedures;

import org.lwjgl.opengl.GL11;

import org.joml.Matrix4f;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.CloudStatus;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class JupiterCloudsRenderProcedure {
	private static RenderLevelStageEvent _provider = null;
	private static VertexBuffer _cloudBuffer = null;
	private static int _scaleX = 12;
	private static int _scaleY = 4;
	private static int _scaleZ = 12;

	private static void buildClouds(int mode, double altitude, double x, double y, double z, int color) {
		Minecraft minecraft = Minecraft.getInstance();
		float du = 1.0F / GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
		float dv = 1.0F / GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
		float dx = Mth.floor(x) * du;
		float dz = Mth.floor(z) * dv;
		float cloudY = (float) Math.floor(y / _scaleY) * _scaleY;
		float r0 = (color >> 16 & 255) / 255.0F;
		float g0 = (color >> 8 & 255) / 255.0F;
		float b0 = (color & 255) / 255.0F;
		float r1 = r0 * 0.9F;
		float g1 = g0 * 0.9F;
		float b1 = b0 * 0.9F;
		float r2 = r0 * 0.8F;
		float g2 = g0 * 0.8F;
		float b2 = b0 * 0.8F;
		float r3 = r0 * 0.7F;
		float g3 = g0 * 0.7F;
		float b3 = b0 * 0.7F;
		float alpha = (color >>> 24) / 255.0F;
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
		if (mode == 1 || (mode == 0 && minecraft.options.getCloudsType() == CloudStatus.FANCY)) {
			for (int i = -3; i <= 4; ++i) {
				for (int j = -3; j <= 4; ++j) {
					float cloudX = i * 8;
					float cloudZ = j * 8;
					if (cloudY > -_scaleY - 1) {
						bufferBuilder.vertex(cloudX, cloudY, cloudZ + 8.0F).uv(cloudX * du + dx, (cloudZ + 8.0F) * dv + dz).color(r3, g3, b3, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
						bufferBuilder.vertex(cloudX + 8.0F, cloudY, cloudZ + 8.0F).uv((cloudX + 8.0F) * du + dx, (cloudZ + 8.0F) * dv + dz).color(r3, g3, b3, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
						bufferBuilder.vertex(cloudX + 8.0F, cloudY, cloudZ).uv((cloudX + 8.0F) * du + dx, cloudZ * dv + dz).color(r3, g3, b3, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
						bufferBuilder.vertex(cloudX, cloudY, cloudZ).uv(cloudX * du + dx, cloudZ * dv + dz).color(r3, g3, b3, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
					}
					if (cloudY <= _scaleY + 1) {
						bufferBuilder.vertex(cloudX, cloudY + _scaleY - 9.765625E-4F, cloudZ + 8.0F).uv(cloudX * du + dx, (cloudZ + 8.0F) * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
						bufferBuilder.vertex(cloudX + 8.0F, cloudY + _scaleY - 9.765625E-4F, cloudZ + 8.0F).uv((cloudX + 8.0F) * du + dx, (cloudZ + 8.0F) * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
						bufferBuilder.vertex(cloudX + 8.0F, cloudY + _scaleY - 9.765625E-4F, cloudZ).uv((cloudX + 8.0F) * du + dx, cloudZ * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
						bufferBuilder.vertex(cloudX, cloudY + _scaleY - 9.765625E-4F, cloudZ).uv(cloudX * du + dx, cloudZ * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
					}
					if (i > -1) {
						for (int k = 0; k < 8; ++k) {
							bufferBuilder.vertex(cloudX + k, cloudY, cloudZ + 8.0F).uv((cloudX + k + 0.5F) * du + dx, (cloudZ + 8.0F) * dv + dz).color(r1, g1, b1, alpha).normal(-1.0F, 0.0F, 0.0F).endVertex();
							bufferBuilder.vertex(cloudX + k, cloudY + _scaleY, cloudZ + 8.0F).uv((cloudX + k + 0.5F) * du + dx, (cloudZ + 8.0F) * dv + dz).color(r1, g1, b1, alpha).normal(-1.0F, 0.0F, 0.0F).endVertex();
							bufferBuilder.vertex(cloudX + k, cloudY + _scaleY, cloudZ).uv((cloudX + k + 0.5F) * du + dx, cloudZ * dv + dz).color(r1, g1, b1, alpha).normal(-1.0F, 0.0F, 0.0F).endVertex();
							bufferBuilder.vertex(cloudX + k, cloudY, cloudZ).uv((cloudX + k + 0.5F) * du + dx, cloudZ * dv + dz).color(r1, g1, b1, alpha).normal(-1.0F, 0.0F, 0.0F).endVertex();
						}
					}
					if (i <= 1) {
						for (int k = 0; k < 8; ++k) {
							bufferBuilder.vertex(cloudX + k + 1.0F - 9.765625E-4F, cloudY, cloudZ + 8.0F).uv((cloudX + k + 0.5F) * du + dx, (cloudZ + 8.0F) * dv + dz).color(r1, g1, b1, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
							bufferBuilder.vertex(cloudX + k + 1.0F - 9.765625E-4F, cloudY + _scaleY, cloudZ + 8.0F).uv((cloudX + k + 0.5F) * du + dx, (cloudZ + 8.0F) * dv + dz).color(r1, g1, b1, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
							bufferBuilder.vertex(cloudX + k + 1.0F - 9.765625E-4F, cloudY + _scaleY, cloudZ).uv((cloudX + k + 0.5F) * du + dx, cloudZ * dv + dz).color(r1, g1, b1, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
							bufferBuilder.vertex(cloudX + k + 1.0F - 9.765625E-4F, cloudY, cloudZ).uv((cloudX + k + 0.5F) * du + dx, cloudZ * dv + dz).color(r1, g1, b1, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
						}
					}
					if (j > -1) {
						for (int k = 0; k < 8; ++k) {
							bufferBuilder.vertex(cloudX, cloudY + _scaleY, cloudZ + k).uv(cloudX * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, -1.0F).endVertex();
							bufferBuilder.vertex(cloudX + 8.0F, cloudY + _scaleY, cloudZ + k).uv((cloudX + 8.0F) * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, -1.0F).endVertex();
							bufferBuilder.vertex(cloudX + 8.0F, cloudY, cloudZ + k).uv((cloudX + 8.0F) * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, -1.0F).endVertex();
							bufferBuilder.vertex(cloudX, cloudY, cloudZ + k).uv(cloudX * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, -1.0F).endVertex();
						}
					}
					if (j <= 1) {
						for (int k = 0; k < 8; ++k) {
							bufferBuilder.vertex(cloudX, cloudY + _scaleY, cloudZ + k + 1.0F - 9.765625E-4F).uv(cloudX * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
							bufferBuilder.vertex(cloudX + 8.0F, cloudY + _scaleY, cloudZ + k + 1.0F - 9.765625E-4F).uv((cloudX + 8.0F) * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
							bufferBuilder.vertex(cloudX + 8.0F, cloudY, cloudZ + k + 1.0F - 9.765625E-4F).uv((cloudX + 8.0F) * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
							bufferBuilder.vertex(cloudX, cloudY, cloudZ + k + 1.0F - 9.765625E-4F).uv(cloudX * du + dx, (cloudZ + k + 0.5F) * dv + dz).color(r2, g2, b2, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
						}
					}
				}
			}
		} else if (mode == 2 || minecraft.options.getCloudsType() == CloudStatus.FAST) {
			for (int i = -32; i < 32; i += 32) {
				for (int j = -32; j < 32; j += 32) {
					bufferBuilder.vertex(i, cloudY, j + 32).uv(i * du + dx, (j + 32) * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
					bufferBuilder.vertex(i + 32, cloudY, j + 32).uv((i + 32) * du + dx, (j + 32) * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
					bufferBuilder.vertex(i + 32, cloudY, j).uv((i + 32) * du + dx, j * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
					bufferBuilder.vertex(i, cloudY, j).uv(i * du + dx, j * dv + dz).color(r0, g0, b0, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
				}
			}
		}
		_cloudBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_cloudBuffer.bind();
		_cloudBuffer.upload(bufferBuilder.end());
		VertexBuffer.unbind();
	}

	private static void renderClouds(int mode, double altitude, double vx, double vz, int color) {
		Minecraft minecraft = Minecraft.getInstance();
		if (mode == 0 && minecraft.options.getCloudsType() == CloudStatus.OFF)
			return;
		Vec3 camPos = _provider.getCamera().getPosition();
		float partialTick = _provider.getPartialTick();
		double factor = (_provider.getRenderTick() + partialTick) * 0.03D;
		double x = (camPos.x() + factor * -vx) / _scaleX;
		double y = altitude + 0.33D - camPos.y();
		double z = (camPos.z() + factor * -vz) / _scaleZ + 0.33D;
		int width = 8 * GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
		int height = 8 * GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
		if (width == 0 || height == 0)
			return;
		x -= Mth.floor(x / width) * width;
		z -= Mth.floor(z / height) * height;
		float dx = (float) (x - Mth.floor(x));
		float dy = (float) (y / _scaleY - Mth.floor(y / _scaleY)) * _scaleY;
		float dz = (float) (z - Mth.floor(z));
		buildClouds(mode, altitude, x, y, z, color);
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.scale(_scaleX, 1.0F, _scaleZ);
		poseStack.translate(-dx, dy, -dz);
		Matrix4f matrix4f = poseStack.last().pose();
		ShaderInstance shaderInstance = GameRenderer.getPositionTexColorNormalShader();
		_cloudBuffer.bind();
		if (mode == 1 || (mode == 0 && minecraft.options.getCloudsType() == CloudStatus.FANCY)) {
			RenderSystem.colorMask(false, false, false, false);
			_cloudBuffer.drawWithShader(matrix4f, _provider.getProjectionMatrix(), shaderInstance);
			RenderSystem.colorMask(true, true, true, true);
			_cloudBuffer.drawWithShader(matrix4f, _provider.getProjectionMatrix(), shaderInstance);
		} else if (mode == 2 || (mode == 0 && minecraft.options.getCloudsType() == CloudStatus.FAST)) {
			RenderSystem.colorMask(true, true, true, true);
			_cloudBuffer.drawWithShader(matrix4f, _provider.getProjectionMatrix(), shaderInstance);
		}
		VertexBuffer.unbind();
		_cloudBuffer.close();
		_cloudBuffer = null;
		poseStack.popPose();
	}

	private static void setScale(int scaleX, int scaleY, int scaleZ) {
		_scaleX = scaleX;
		_scaleY = scaleY;
		_scaleZ = scaleZ;
	}

	@SubscribeEvent
	public static void renderClouds(RenderLevelStageEvent event) {
		_provider = event;
		if (_provider.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
			ClientLevel level = Minecraft.getInstance().level;
			Entity entity = _provider.getCamera().getEntity();
			Vec3 entPos = entity.getPosition(_provider.getPartialTick());
			RenderSystem.depthMask(true);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableCull();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			execute(_provider, level.dimension());
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.colorMask(true, true, true, true);
			RenderSystem.enableCull();
			RenderSystem.enableDepthTest();
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
		}
	}

	public static void execute(ResourceKey<Level> dimension) {
		execute(null, dimension);
	}

	private static void execute(@Nullable Event event, ResourceKey<Level> dimension) {
		if (dimension == null)
			return;
		if (dimension == (ResourceKey.create(Registries.DIMENSION, new ResourceLocation("adastraextra:jupiter")))) {
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			setScale(12, 4, 12);
			{
				ResourceLocation _texturelocation = new ResourceLocation(("minecraft" + ":textures/" + "environment/clouds" + ".png"));
				RenderSystem.setShaderTexture(0, _texturelocation);
				Minecraft.getInstance().getTextureManager().bindForSetup(_texturelocation);
			}
			renderClouds(0, 192, 0, 0, (int) (255 << 24 | 255 << 16 | 198 << 8 | 113));
		}
	}
}
