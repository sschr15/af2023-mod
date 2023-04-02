package sschr15.mods.af2023.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.voting.rules.BooleanRule;
import net.minecraft.voting.rules.RandomNumberRule;
import net.minecraft.voting.rules.Rule;
import net.minecraft.voting.rules.Rules;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(Rules.class)
public abstract class RulesMixin {
	@Shadow @NotNull @SuppressWarnings("DataFlowIssue")
	private static <R extends Rule> R register(String string, int i, R rule) {
		return null;
	}

	@Unique
	private static final BooleanRule ONLY_TRAILS = register("only_trails", 500, new BooleanRule(Component.literal("Trails (no tails)")));
	@Unique
	private static final BooleanRule ONLY_TAILS = register("only_tails", 500, new BooleanRule(Component.literal("(no trails) Tails")));
	@Unique
	private static final BooleanRule ANY_SLEEP = register("any_sleep", 500, new BooleanRule(Component.literal("Sleep anywhere, anytime")));
	@Unique
	private static final RandomNumberRule.RandomFloat PLAYER_TRANSPARENCY = register("player_transparency", 500,
		new RandomNumberRule.RandomFloat(1.0F, UniformFloat.of(0.0F, 1.0F)) {
			@Override
			protected Component valueDescription(Float number) {
				return Component.literal("Player transparency: ").append(Math.round(number * 100) + "%");
			}
		}
	);
	@Unique
	private static final BooleanRule CONSTANT_JEB = register("constant_jeb", 500, new BooleanRule(Component.literal("Constant Jeb!")));
	@Unique
	private static final BooleanRule EVERYTHING_GLOWS = register("everything_glows", 500, new BooleanRule(Component.literal("Everything glows")));
	@Unique
	private static final BooleanRule FREE_TRADE = register("free_trade", 500, new BooleanRule(Component.literal("Trading, minus the trade")));

	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static <R extends Rule> void register(String string, int i, R rule, CallbackInfoReturnable<R> cir) {
		switch (string) {
			case "transparent_players" -> cir.setReturnValue(rule); // don't register this as i have replacement
			default -> Af2023Mod.RULES.put(string, rule);
		}


	}
}
