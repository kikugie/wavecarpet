package wavetech.me.wavecarpet.core;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import wavetech.me.wavecarpet.mixins.ObjectiveCriteria_customCriteriaAccessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObjectiveCriteriaRegistry {
	private static final Map<String, ObjectiveCriteria> CRITERIA = new HashMap<>();
	public static final ObjectiveCriteria SUPPRESSION_COUNT = register("suppressionCount");

	private static ObjectiveCriteria register(String name) {
		var criterion = ObjectiveCriteria_customCriteriaAccessor.callRegisterCustom(name);
		CRITERIA.put(name, criterion);
		return criterion;
	}

	public static Map<String, ObjectiveCriteria> getCriteria() {
		return Collections.unmodifiableMap(CRITERIA);
	}

	public static void init() {}
}
