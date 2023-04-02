package sschr15.mods.af2023.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.voting.rules.BooleanRule;
import net.minecraft.voting.rules.Rules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

	@Shadow
	public abstract void startSleeping(BlockPos pos);

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/voting/rules/BooleanRule;get()Z"))
	private boolean sschr15af$getTrails(BooleanRule rule) {
		if (rule != Rules.TRAILS_AND_TAILS) return rule.get(); // if it's not the trails and tails rule, just return the value
		return rule.get() || ((BooleanRule) Af2023Mod.RULES.get("only_trails")).get(); // if it is, return the value of the trails rule or the only_trails rule
	}

	@Inject(method = "startSleepInBed", at = @At("HEAD"), cancellable = true)
	private void sschr15af$cancelSleep(BlockPos pos, CallbackInfoReturnable<Either<BedSleepingProblem, Unit>> cir) {
		if (((BooleanRule) Af2023Mod.RULES.get("any_sleep")).get()) {
			cir.setReturnValue(super.startSleepInBed(pos));
		}
	}

	private ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
		super(level, blockPos, f, gameProfile);
	}
}
