package me.wavetech.wavecarpet.core;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import me.wavetech.wavecarpet.mixins.utils.customCriteria.ObjectiveCriteriaAccessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObjectiveCriteriaRegistry {
	private static final Map<String, ObjectiveCriteria> CRITERIA = new HashMap<>();
	public static final ObjectiveCriteria SUPPRESSION_COUNT = register("suppressionCount");

	private static ObjectiveCriteria register(String name) {
		var criterion = ObjectiveCriteriaAccessor.callRegisterCustom(name);
		CRITERIA.put(name, criterion);
		return criterion;
	}

	public static Map<String, ObjectiveCriteria> getCriteria() {
		return Collections.unmodifiableMap(CRITERIA);
	}

	public static void init() {}
}
