package dev.quantumfusion.hyphen;

public enum Options {
	/**
	 * Disables the {@link HyphenSerializer#put(Object, Object)} Method. Usage will result in a {@link UnsupportedOperationException}
	 */
	DISABLE_PUT(false),
	/**
	 * Disables the {@link HyphenSerializer#get(Object)} Method. Usage will result in a {@link UnsupportedOperationException}
	 */
	DISABLE_GET(false),
	/**
	 * Disables the {@link HyphenSerializer#measure(Object)} Method. Usage will result in a {@link UnsupportedOperationException}
	 */
	DISABLE_MEASURE(false),

	/**
	 * Compacts 8 booleans into a single byte
	 */
	COMPACT_BOOLEANS(true),

	/**
	 * Uses short but cryptic method names. Most method will be named "_"
	 */
	SHORT_METHOD_NAMES(true),
	/**
	 * Uses short but cryptic variable names. Most variables will be named "_"
	 */
	SHORT_VARIABLE_NAMES(true);

	public final boolean defaultValue;

	Options(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
}