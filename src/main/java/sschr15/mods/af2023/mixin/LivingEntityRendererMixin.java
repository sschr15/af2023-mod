package sschr15.mods.af2023.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.voting.rules.BooleanRule;
import net.minecraft.voting.rules.RandomNumberRule;
import net.minecraft.voting.rules.Rules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
	private static final String RENDER = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V";
	@Redirect(method = RENDER, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"))
	private void sschr15af$renderToBuffer(EntityModel<?> model, PoseStack stack, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
		model.renderToBuffer(stack, consumer, light, overlay, red, green, blue, ((RandomNumberRule.RandomFloat) Af2023Mod.RULES.get("player_transparency")).get());
	}

	@Redirect(method = RENDER, at = @At(value = "INVOKE", target = "Lnet/minecraft/voting/rules/BooleanRule;get()Z"))
	private boolean sschr15af$changeTransparencyCheck(BooleanRule rule) {
		if (rule == Rules.TRANSPARENT_PLAYERS) {
			return ((RandomNumberRule.RandomFloat) Af2023Mod.RULES.get("player_transparency")).get() != 1.0F;
		} else if (rule == Rules.UNIVERSAL_JEB) {
			return rule.get() || ((BooleanRule) Af2023Mod.RULES.get("constant_jeb")).get();
		}
		return rule.get();
	}

	// two checks to force true if constant_jeb
	@Redirect(method = RENDER, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasCustomName()Z"))
	private boolean sschr15af$changeNameCheck(net.minecraft.world.entity.LivingEntity entity) {
		return entity.hasCustomName() || ((BooleanRule) Af2023Mod.RULES.get("constant_jeb")).get();
	}

	@Redirect(method = RENDER, at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
	private boolean sschr15af$changeNameCheck(String string, Object object) {
		return "jeb_".equals(object) || ((BooleanRule) Af2023Mod.RULES.get("constant_jeb")).get();
	}
}
