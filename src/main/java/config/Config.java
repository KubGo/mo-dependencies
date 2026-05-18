package config;

import java.util.List;

public final class Config {
	public static boolean DEBUG = false;

	private Config() {
	}

	private final static List<String> availableExtensions = List.of("json");

	public static String getAvailableExtensionsList() {
		return String.join(" ,", availableExtensions.stream().map(it -> "\"" + it + "\"").toList());
	}
}
