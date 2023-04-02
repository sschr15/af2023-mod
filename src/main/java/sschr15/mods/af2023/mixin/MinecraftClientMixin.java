package sschr15.mods.af2023.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.voting.rules.BooleanRule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import sschr15.mods.af2023.Af2023Mod;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@Shadow
	@Final
	public Options options;

	/**
	 * @reason april fools
	 * @author sschr15
	 */
	@Overwrite
	public boolean shouldEntityAppearGlowing(Entity entity) {
		return entity.isCurrentlyGlowing()
			|| (player != null && player.isSpectator() && options.keySpectatorOutlines.isDown() && entity.getType() == EntityType.PLAYER)
			|| ((BooleanRule) Af2023Mod.RULES.get("everything_glows")).get();
	}
}
