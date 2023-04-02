package sschr15.mods.af2023.mixin;

import net.minecraft.client.renderer.entity.layers.TailLayer;
import net.minecraft.voting.rules.BooleanRule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(TailLayer.class)
public class TailLayerMixin {
	@Redirect(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/voting/rules/BooleanRule;get()Z"))
	private boolean sschr15af$getTails(BooleanRule rule) {
		return rule.get() || ((BooleanRule) Af2023Mod.RULES.get("only_tails")).get();
	}
}
