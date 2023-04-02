package sschr15.mods.af2023.mixin;

import net.minecraft.voting.rules.BooleanRule;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(MerchantContainer.class)
public class MerchantContainerMixin {
	@Redirect(method = "updateSellItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
	private boolean sschr15af$isEmpty(ItemStack stack) {
		return stack.isEmpty() && !((BooleanRule) Af2023Mod.RULES.get("free_trade")).get();
	}
}
