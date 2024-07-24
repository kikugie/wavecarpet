package me.wavetech.wavecarpet.mixins.command.player.loadItems;

import me.wavetech.wavecarpet.access.PlayerLoader;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements PlayerLoader {
	@Unique
	private boolean loadItems = false;

	@Override
	public boolean getLoadItems$wavecarpet() {
		return loadItems;
	}

	@Override
	public void setLoadItems$wavecarpet(boolean state) {
		loadItems = state;
	}
}
