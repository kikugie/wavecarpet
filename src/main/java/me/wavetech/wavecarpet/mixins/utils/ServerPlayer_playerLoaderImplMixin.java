package me.wavetech.wavecarpet.mixins.utils;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import me.wavetech.wavecarpet.access.PlayerLoader;

@Mixin(ServerPlayer.class)
public class ServerPlayer_playerLoaderImplMixin implements PlayerLoader {
	@Unique
	boolean loadItems = false;

	@Override
	public boolean wavecarpet$getLoadItems() {
		return loadItems;
	}

	@Override
	public void wavecarpet$setLoadItems(boolean state) {
		loadItems = state;
	}
}