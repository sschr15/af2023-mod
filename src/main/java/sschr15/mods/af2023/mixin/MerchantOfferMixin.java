package sschr15.mods.af2023.mixin;

import net.minecraft.voting.rules.BooleanRule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {
	@Shadow
	@Final @Mutable
	private ItemStack baseCostA;

	@Shadow
	@Final @Mutable
	private ItemStack costB;

	@Inject(method = "<init>*", at = @At("RETURN"))
	private void sschr15af$init(CallbackInfo ci) {
		if (((BooleanRule) Af2023Mod.RULES.get("free_trade")).get()) {
			baseCostA = ItemStack.EMPTY;
			costB = ItemStack.EMPTY;
		}
	}

	@Inject(method = "satisfiedBy", at = @At("HEAD"), cancellable = true)
	private void sschr15af$satisfiedBy(ItemStack playerOfferA, ItemStack playerOfferB, CallbackInfoReturnable<Boolean> cir) {
		if (((BooleanRule) Af2023Mod.RULES.get("free_trade")).get()) {
			cir.setReturnValue(playerOfferA.isEmpty() && playerOfferB.isEmpty());
		}
	}
}
