package net.bullfighter.adastraextra.procedures;

import org.checkerframework.checker.units.qual.h;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class ThebeRenderProcedure {
	private static RenderLevelStageEvent _provider = null;
	private static Runnable _fogRenderer = null;
	private static boolean _usingBuffers = false;
	private static VertexBuffer _abyssBuffer = null;
	private static VertexBuffer _deepskyBuffer = null;
	private static VertexBuffer _moonBuffer = null;
	private static VertexBuffer _polygonBuffer = null;
	private static VertexBuffer _shapeBuffer = null;
	private static VertexBuffer _skyboxBuffer = null;
	private static VertexBuffer _starBuffer = null;
	private static VertexBuffer _sunBuffer = null;
	private static VertexBuffer _sunlightBuffer = null;
	private static VertexBuffer _textureBuffer = null;
	private static BufferBuilder _bufferBuilder = null;

	private static void begin(int type) {
		if (_bufferBuilder == null) {
			_bufferBuilder = Tesselator.getInstance().getBuilder();
			switch (type) {
				case 0 :
					_bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
					break;
				case 1 :
					_bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
					break;
				case 2 :
					_bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
					break;
				case 3 :
					_bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
					break;
				case 4 :
					_bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
					break;
				case 5 :
					_bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
					break;
				case 6 :
					_bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
					break;
			}
		}
	}

	private static void vertex(float yaw, float pitch, float radius, int color) {
		vertex(yaw, pitch, radius, 0.0F, 0.0F, color);
	}

	private static void vertex(float yaw, float pitch, float radius, float u, float v, int color) {
		if (_bufferBuilder != null && _bufferBuilder.building()) {
			if (_bufferBuilder.currentElement().getUsage() == VertexFormatElement.Usage.POSITION) {
				float y = yaw * Mth.DEG_TO_RAD;
				float p = pitch * Mth.DEG_TO_RAD;
				float i = -Mth.sin(y) * Mth.cos(p) * radius;
				float j = -Mth.sin(p) * radius;
				float k = Mth.cos(y) * Mth.cos(p) * radius;
				_bufferBuilder.vertex(i, j, k);
			}
			if (_bufferBuilder.currentElement().getUsage() == VertexFormatElement.Usage.UV) {
				_bufferBuilder.uv(u, v);
			}
			if (_bufferBuilder.currentElement().getUsage() == VertexFormatElement.Usage.COLOR) {
				_bufferBuilder.color(color);
			}
			_bufferBuilder.endVertex();
		}
	}

	private static void end() {
		if (_bufferBuilder != null && _bufferBuilder.building()) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			if (_shapeBuffer != null)
				_shapeBuffer.close();
			_shapeBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
			_shapeBuffer.bind();
			_shapeBuffer.upload(_bufferBuilder.end());
			if (_usingBuffers) {
				VertexBuffer.unbind();
			} else {
				ShaderInstance shaderInstance = null;
				if (_shapeBuffer.getFormat() == DefaultVertexFormat.POSITION_COLOR) {
					RenderSystem.setShader(GameRenderer::getPositionColorShader);
					shaderInstance = GameRenderer.getPositionColorShader();
				} else if (_shapeBuffer.getFormat() == DefaultVertexFormat.POSITION_TEX_COLOR) {
					RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
					shaderInstance = GameRenderer.getPositionTexColorShader();
				}
				_shapeBuffer.drawWithShader(_provider.getPoseStack().last().pose(), _provider.getProjectionMatrix(), shaderInstance);
				_shapeBuffer.close();
			}
			_bufferBuilder = null;
		}
	}

	private static void renderAbyss(int color, boolean constant) {
		Minecraft minecraft = Minecraft.getInstance();
		double h = minecraft.player.getEyePosition(_provider.getPartialTick()).y() - minecraft.level.getLevelData().getHorizonHeight(minecraft.level);
		if (constant || h < 0.0D) {
			PoseStack poseStack = _provider.getPoseStack();
			poseStack.pushPose();
			poseStack.translate(0.0F, 12.0F, 0.0F);
			RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
			RenderSystem.setShader(GameRenderer::getPositionShader);
			BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
			bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
			bufferBuilder.vertex(0.0F, -16.0F, 0.0F).color(color).endVertex();
			for (int i = 0; i <= 8; ++i) {
				bufferBuilder.vertex(-512.0F * Mth.cos(i * 45.0F * Mth.DEG_TO_RAD), -16.0F, 512.0F * Mth.sin(i * 45.0F * Mth.DEG_TO_RAD)).endVertex();
			}
			if (_abyssBuffer != null)
				_abyssBuffer.close();
			_abyssBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
			_abyssBuffer.bind();
			_abyssBuffer.upload(bufferBuilder.end());
			if (_usingBuffers) {
				VertexBuffer.unbind();
			} else {
				_abyssBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionShader());
				_abyssBuffer.close();
			}
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			poseStack.popPose();
		}
	}

	private static void renderBuffer(VertexBuffer vertexBuffer, double x, double y, double z, float yaw, float pitch, float roll, int color) {
		if (_usingBuffers && vertexBuffer != null) {
			PoseStack poseStack = _provider.getPoseStack();
			poseStack.pushPose();
			poseStack.translate(x, y, z);
			poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
			poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
			poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
			RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
			vertexBuffer.bind();
			ShaderInstance shaderInstance = GameRenderer.getPositionShader();
			if (vertexBuffer.getFormat() == DefaultVertexFormat.POSITION_COLOR) {
				shaderInstance = GameRenderer.getPositionColorShader();
			} else if (vertexBuffer.getFormat() == DefaultVertexFormat.POSITION_TEX_COLOR) {
				shaderInstance = GameRenderer.getPositionTexColorShader();
			}
			vertexBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), shaderInstance);
			VertexBuffer.unbind();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			poseStack.popPose();
		}
	}

	private static void renderCorepolygon(int vertex, float size, float yaw, float pitch, float roll, int color, boolean constant) {
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
		float deg = 360.0F / vertex;
		bufferBuilder.vertex(0.0F, 0.0F, 100.0F).color(255, 255, 255, 255).endVertex();
		for (int i = 0; i <= vertex; ++i) {
			bufferBuilder.vertex(-size * Mth.cos((90.0F + i * deg) * Mth.DEG_TO_RAD), size * Mth.sin((90.0F + i * deg) * Mth.DEG_TO_RAD), 100.0F).color(255, 255, 255, 0).endVertex();
		}
		if (_polygonBuffer != null)
			_polygonBuffer.close();
		_polygonBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_polygonBuffer.bind();
		_polygonBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_polygonBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionColorShader());
			_polygonBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderCrustpolygon(int vertex, float size, float yaw, float pitch, float roll, int color, boolean constant) {
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
		float deg = 360.0F / vertex;
		bufferBuilder.vertex(0.0F, 0.0F, 100.0F).color(255, 255, 255, 0).endVertex();
		for (int i = 0; i <= vertex; ++i) {
			bufferBuilder.vertex(-size * Mth.cos((90.0F + i * deg) * Mth.DEG_TO_RAD), size * Mth.sin((90.0F + i * deg) * Mth.DEG_TO_RAD), 100.0F).color(255, 255, 255, 255).endVertex();
		}
		if (_polygonBuffer != null)
			_polygonBuffer.close();
		_polygonBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_polygonBuffer.bind();
		_polygonBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_polygonBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionColorShader());
			_polygonBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderDeepsky(int color) {
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
		RenderSystem.setShader(GameRenderer::getPositionShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
		bufferBuilder.vertex(0.0F, 16.0F, 0.0F).color(color).endVertex();
		for (int i = 0; i <= 8; ++i) {
			bufferBuilder.vertex(512.0F * Mth.cos(45.0F * i * Mth.DEG_TO_RAD), 16.0F, 512.0F * Mth.sin(45.0F * i * Mth.DEG_TO_RAD)).endVertex();
		}
		if (_deepskyBuffer != null)
			_deepskyBuffer.close();
		_deepskyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_deepskyBuffer.bind();
		_deepskyBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_deepskyBuffer.drawWithShader(_provider.getPoseStack().last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionShader());
			_deepskyBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private static void renderEndsky() {
		RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft:textures/environment/end_sky.png"));
		RenderSystem.defaultBlendFunc();
		renderEndsky(0.0F, 0.0F, 0.0F, -14145496);
	}

	private static void renderEndsky(float yaw, float pitch, float roll, int color) {
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		for (int i = 0; i < 6; ++i) {
			switch (i) {
				case 0 :
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 1 :
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(0.0F, .0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(0.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(16.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(16.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 2 :
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(0.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(16.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(16.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 3 :
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(0.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(16.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(16.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 4 :
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(0.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 5 :
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(16.0F, 16.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(16.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
			}
		}
		if (_skyboxBuffer != null)
			_skyboxBuffer.close();
		_skyboxBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_skyboxBuffer.bind();
		_skyboxBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_skyboxBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionTexColorShader());
			_skyboxBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderFlatpolygon(int vertex, float size, float yaw, float pitch, float roll, int color, boolean constant) {
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
		float deg = 360.0F / vertex;
		bufferBuilder.vertex(0.0F, 0.0F, 100.0F).color(255, 255, 255, 255).endVertex();
		for (int i = 0; i <= vertex; ++i) {
			bufferBuilder.vertex(-size * Mth.cos((90.0F + i * deg) * Mth.DEG_TO_RAD), size * Mth.sin((90.0F + i * deg) * Mth.DEG_TO_RAD), 100.0F).color(255, 255, 255, 255).endVertex();
		}
		if (_polygonBuffer != null)
			_polygonBuffer.close();
		_polygonBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_polygonBuffer.bind();
		_polygonBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_polygonBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionColorShader());
			_polygonBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderLinepolygon(int vertex, float size, float yaw, float pitch, float roll, int color, boolean constant) {
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
		float deg = 360.0F / vertex;
		for (int i = 0; i <= vertex; ++i) {
			bufferBuilder.vertex(-size * Mth.cos((90.0F + i * deg) * Mth.DEG_TO_RAD), size * Mth.sin((90.0F + i * deg) * Mth.DEG_TO_RAD), 100.0F).color(255, 255, 255, 255).endVertex();
		}
		if (_polygonBuffer != null)
			_polygonBuffer.close();
		_polygonBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_polygonBuffer.bind();
		_polygonBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_polygonBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionColorShader());
			_polygonBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderMoon(float size, float angle, int color, boolean phase, boolean constant) {
		float r = size / 2.0F;
		float u0 = 0.0F;
		float v0 = 0.0F;
		float u1 = 1.0F;
		float v1 = 1.0F;
		if (phase) {
			int i0 = Minecraft.getInstance().level.getMoonPhase();
			int i1 = i0 % 4;
			int i2 = i0 / 4 % 2;
			u0 = i1 / 4.0F;
			v0 = i2 / 2.0F;
			u1 = (i1 + 1) / 4.0F;
			v1 = (i2 + 1) / 2.0F;
		}
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(angle));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferBuilder.vertex(-r, -100.0F, -r).uv(u1, v1).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(-r, -100.0F, r).uv(u0, v1).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(r, -100.0F, r).uv(u0, v0).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(r, -100.0F, -r).uv(u1, v0).color(255, 255, 255, 255).endVertex();
		if (_moonBuffer != null)
			_moonBuffer.close();
		_moonBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_moonBuffer.bind();
		_moonBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_moonBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionTexColorShader());
			_moonBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderOnefacesky(float yaw, float pitch, float roll, int color) {
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		for (int i = 0; i < 6; ++i) {
			switch (i) {
				case 0 :
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 1 :
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 2 :
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 3 :
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 4 :
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 5 :
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
			}
		}
		if (_skyboxBuffer != null)
			_skyboxBuffer.close();
		_skyboxBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_skyboxBuffer.bind();
		_skyboxBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_skyboxBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionTexColorShader());
			_skyboxBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderPolygon(int mode, int vertex, float size, float yaw, float pitch, float roll, int color, boolean constant) {
		if (vertex >= 2) {
			switch (mode) {
				case 0 :
					renderCorepolygon(vertex, size, yaw, pitch, roll, color, constant);
					break;
				case 1 :
					renderCrustpolygon(vertex, size, yaw, pitch, roll, color, constant);
					break;
				case 2 :
					renderFlatpolygon(vertex, size, yaw, pitch, roll, color, constant);
					break;
				case 3 :
					renderLinepolygon(vertex, size, yaw, pitch, roll, color, constant);
					break;
			}
		}
	}

	private static void renderSixfacesky(float yaw, float pitch, float roll, int color) {
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		for (int i = 0; i < 6; ++i) {
			switch (i) {
				case 0 :
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(0.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(1.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(1.0F / 3.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 1 :
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(1.0F / 3.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(1.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(2.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(2.0F / 3.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 2 :
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(2.0F / 3.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(2.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(1.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
					break;
				case 3 :
					bufferBuilder.vertex(-100.0F, 100.0F, 100.0F).uv(0.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(1.0F / 3.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(1.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					break;
				case 4 :
					bufferBuilder.vertex(-100.0F, 100.0F, -100.0F).uv(1.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(-100.0F, -100.0F, -100.0F).uv(1.0F / 3.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(2.0F / 3.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(2.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					break;
				case 5 :
					bufferBuilder.vertex(100.0F, 100.0F, -100.0F).uv(2.0F / 3.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, -100.0F).uv(2.0F / 3.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
					bufferBuilder.vertex(100.0F, 100.0F, 100.0F).uv(1.0F, 0.5F).color(255, 255, 255, 255).endVertex();
					break;
			}
		}
		if (_skyboxBuffer != null)
			_skyboxBuffer.close();
		_skyboxBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_skyboxBuffer.bind();
		_skyboxBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_skyboxBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionTexColorShader());
			_skyboxBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderSky(boolean abyss, boolean deepsky, boolean moon, boolean stars, boolean sun, boolean sunlights) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		float partialTick = _provider.getPartialTick();
		if (deepsky) {
			Vec3 color = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), partialTick);
			RenderSystem.defaultBlendFunc();
			renderDeepsky(255 << 24 | (int) (color.x() * 255.0D) << 16 | (int) (color.y() * 255.0D) << 8 | (int) (color.z() * 255.0D));
		}
		if (sunlights) {
			float[] color = level.effects().getSunriseColor(level.getTimeOfDay(partialTick), partialTick);
			if (color != null) {
				RenderSystem.defaultBlendFunc();
				renderSunlights((int) (color[3] * 255.0F) << 24 | (int) (color[0] * 255.0F) << 16 | (int) (color[1] * 255.0F) << 8 | (int) (color[2] * 255.0F));
			}
		}
		if (sun) {
			RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft:textures/environment/sun.png"));
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			renderSun(60.0F, level.getTimeOfDay(partialTick) * 360.0F, -1, false);
		}
		if (moon) {
			RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft:textures/environment/moon_phases.png"));
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			renderMoon(40.0F, level.getTimeOfDay(partialTick) * 360.0F, -1, true, false);
		}
		if (stars) {
			int color = (int) (level.getStarBrightness(partialTick) * 255.0F);
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			renderStars(1500, 10842, 90.0F, level.getTimeOfDay(partialTick) * 360.0F, 0.0F, color << 24 | color << 16 | color << 8 | color, false);
		}
		if (abyss) {
			RenderSystem.defaultBlendFunc();
			renderAbyss(-16777216, false);
		}
		RenderSystem.defaultBlendFunc();
	}

	private static void renderSkybox(int mode, float yaw, float pitch, float roll, int color, boolean constant) {
		Vec3 camPos = _provider.getCamera().getPosition();
		boolean visible = constant || !(Minecraft.getInstance().level.effects().isFoggyAt(Mth.floor(camPos.x()), Mth.floor(camPos.y())) || Minecraft.getInstance().gui.getBossOverlay().shouldCreateWorldFog());
		if (visible) {
			switch (mode) {
				case 0 :
					renderEndsky(yaw, pitch, roll, color);
					break;
				case 1 :
					renderOnefacesky(yaw, pitch, roll, color);
					break;
				case 2 :
					renderSixfacesky(yaw, pitch, roll, color);
					break;
			}
		}
	}

	private static void renderStars(int amount, int seed, float yaw, float pitch, float roll, int color, boolean constant) {
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		RandomSource randomsource = RandomSource.create((long) seed);
		for (int i = 0; i < 1500; ++i) {
			float f0 = randomsource.nextFloat() * 2.0F - 1.0F;
			float f1 = randomsource.nextFloat() * 2.0F - 1.0F;
			float f2 = randomsource.nextFloat() * 2.0F - 1.0F;
			float f3 = 0.15F + 0.1F * randomsource.nextFloat();
			float f4 = f0 * f0 + f1 * f1 + f2 * f2;
			if (f4 < 1.0F && f4 > 0.01F) {
				f4 = 1.0F / Mth.sqrt(f4);
				f0 *= f4;
				f1 *= f4;
				f2 *= f4;
				float f5 = f0 * 100.0F;
				float f6 = f1 * 100.0F;
				float f7 = f2 * 100.0F;
				float f8 = (float) Math.atan2(f0, f2);
				float f9 = Mth.sin(f8);
				float f10 = Mth.cos(f8);
				float f11 = (float) Math.atan2(Mth.sqrt(f0 * f0 + f2 * f2), f1);
				float f12 = Mth.sin(f11);
				float f13 = Mth.cos(f11);
				float f14 = (float) randomsource.nextDouble() * Mth.PI * 2.0F;
				float f15 = Mth.sin(f14);
				float f16 = Mth.cos(f14);
				for (int j = 0; j < 4; ++j) {
					float f17 = ((j & 2) - 1) * f3;
					float f18 = ((j + 1 & 2) - 1) * f3;
					float f20 = f17 * f16 - f18 * f15;
					float f21 = f18 * f16 + f17 * f15;
					float f22 = -f20 * f13;
					float f23 = f22 * f9 - f21 * f10;
					float f24 = f20 * f12;
					float f25 = f21 * f9 + f22 * f10;
					bufferBuilder.vertex(f5 + f23, f6 + f24, f7 + f25).color(255, 255, 255, 255).endVertex();
				}
			}
		}
		if (_starBuffer != null)
			_starBuffer.close();
		_starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_starBuffer.bind();
		_starBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_starBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionColorShader());
			_starBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderSun(float size, float angle, int color, boolean constant) {
		float r = size / 2.0F;
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(angle));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferBuilder.vertex(r, 100.0F, -r).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(r, 100.0F, r).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(-r, 100.0F, r).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(-r, 100.0F, -r).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
		if (_sunBuffer != null)
			_sunBuffer.close();
		_sunBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_sunBuffer.bind();
		_sunBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_sunBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionTexColorShader());
			_sunBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	private static void renderSunlights(int color) {
		ClientLevel level = Minecraft.getInstance().level;
		float partialTick = _provider.getPartialTick();
		float[] base = level.effects().getSunriseColor(level.getTimeOfDay(partialTick), partialTick);
		if (base != null) {
			RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
			bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
			boolean flag = Mth.sin(level.getSunAngle(partialTick)) < 0.0F;
			if (flag || _usingBuffers) {
				bufferBuilder.vertex(100.0F, 0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			} else {
				bufferBuilder.vertex(-100.0F, 0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			}
			for (int i = 0; i <= 16; ++i) {
				float deg = i * Mth.TWO_PI / 16.0F;
				float sin = Mth.sin(deg);
				float cos = Mth.cos(deg);
				if (flag || _usingBuffers) {
					bufferBuilder.vertex(cos * 120.0F, cos * 40.0F * base[3], -sin * 120.0F).color(255, 255, 255, 0).endVertex();
				} else {
					bufferBuilder.vertex(-cos * 120.0F, cos * 40.0F * base[3], sin * 120.0F).color(255, 255, 255, 0).endVertex();
				}
			}
			if (_sunlightBuffer != null)
				_sunlightBuffer.close();
			_sunlightBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
			_sunlightBuffer.bind();
			_sunlightBuffer.upload(bufferBuilder.end());
			if (_usingBuffers) {
				VertexBuffer.unbind();
			} else {
				_sunlightBuffer.drawWithShader(_provider.getPoseStack().last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionColorShader());
				_sunlightBuffer.close();
			}
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	private static void renderTexture(float size, float yaw, float pitch, float roll, int color, boolean constant) {
		float r = size / 2.0F;
		float alpha = (color >>> 24) / 255.0F;
		if (!constant)
			alpha *= (1.0F - Minecraft.getInstance().level.getRainLevel(_provider.getPartialTick()));
		PoseStack poseStack = _provider.getPoseStack();
		poseStack.pushPose();
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, alpha);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferBuilder.vertex(r, r, 100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(r, -r, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(-r, -r, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();
		bufferBuilder.vertex(-r, r, 100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
		if (_textureBuffer != null)
			_textureBuffer.close();
		_textureBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		_textureBuffer.bind();
		_textureBuffer.upload(bufferBuilder.end());
		if (_usingBuffers) {
			VertexBuffer.unbind();
		} else {
			_textureBuffer.drawWithShader(poseStack.last().pose(), _provider.getProjectionMatrix(), GameRenderer.getPositionTexColorShader());
			_textureBuffer.close();
		}
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	@SubscribeEvent
	public static void renderSky(RenderLevelStageEvent event) {
		_provider = event;
		if (_provider.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
			Minecraft minecraft = Minecraft.getInstance();
			ClientLevel level = minecraft.level;
			Entity entity = _provider.getCamera().getEntity();
			Vec3 camPos = _provider.getCamera().getPosition();
			Vec3 entPos = entity.getPosition(_provider.getPartialTick());
			boolean isFoggy = level.effects().isFoggyAt(Mth.floor(camPos.x()), Mth.floor(camPos.y())) || minecraft.gui.getBossOverlay().shouldCreateWorldFog();
			_fogRenderer = () -> {
				FogRenderer.setupFog(_provider.getCamera(), FogRenderer.FogMode.FOG_SKY, minecraft.gameRenderer.getRenderDistance(), isFoggy, _provider.getPartialTick());
			};
			_fogRenderer.run();
			FogRenderer.levelFogColor();
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			execute(_provider, level, level.dimension());
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.colorMask(true, true, true, true);
			RenderSystem.enableCull();
			RenderSystem.enableDepthTest();
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			_fogRenderer.run();
		}
	}

	public static void execute(LevelAccessor world, ResourceKey<Level> dimension) {
		execute(null, world, dimension);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, ResourceKey<Level> dimension) {
		if (dimension == null)
			return;
		if (dimension == (ResourceKey.create(Registries.DIMENSION, new ResourceLocation("adastraextra:thebe")))) {
			RenderSystem.clear(16640, Minecraft.ON_OSX);
			renderStars(3000, 10842, 90, (float) (world.getTimeOfDay(Minecraft.getInstance().getPartialTick()) * 360.0F), 0, (int) (255 << 24 | 255 << 16 | 255 << 8 | 255), false);
			{
				ResourceLocation _texturelocation = new ResourceLocation(("adastraextra" + ":textures/" + "sky/sun" + ".png"));
				RenderSystem.setShaderTexture(0, _texturelocation);
				Minecraft.getInstance().getTextureManager().bindForSetup(_texturelocation);
			}
			renderSun(30, (float) (world.getTimeOfDay(Minecraft.getInstance().getPartialTick()) * 360.0F), (int) (255 << 24 | 255 << 16 | 255 << 8 | 255), false);
			{
				ResourceLocation _texturelocation = new ResourceLocation(("ad_astra" + ":textures/" + "sky/jupiter" + ".png"));
				RenderSystem.setShaderTexture(0, _texturelocation);
				Minecraft.getInstance().getTextureManager().bindForSetup(_texturelocation);
			}
			renderTexture(15, 0, -60, 0, (int) (255 << 24 | 255 << 16 | 255 << 8 | 255), false);
		}
	}
}
