package org.sense.util;

import java.util.Random;

public class RandomCity<E extends Enum<E>> {

	private static final Random RND = new Random();
	private final E[] values;

	public RandomCity(Class<E> token) {
		values = token.getEnumConstants();
	}

	public E random() {
		return values[RND.nextInt(values.length)];
	}
}
