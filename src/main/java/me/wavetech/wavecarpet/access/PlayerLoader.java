package me.wavetech.wavecarpet.access;

public interface PlayerLoader {
	default boolean getLoadItems$wavecarpet() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default void setLoadItems$wavecarpet(boolean state) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}
}
