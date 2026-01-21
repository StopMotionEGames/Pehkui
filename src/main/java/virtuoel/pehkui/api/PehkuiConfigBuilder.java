package virtuoel.pehkui.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import virtuoel.pehkui.util.ConfigSyncUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PehkuiConfigBuilder {
	private final Path configPath;
	private JsonObject configData = new JsonObject();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public PehkuiConfigBuilder(String modId, Path path) {
		this.configPath = path;
		load();
	}

	public <T> MutableConfigEntry<T> createConfigEntry(String name, T defaultValue, Supplier<T> supplier, Consumer<T> consumer) {
		// Implementação padrão caso não seja sobrescrito (opcional)
		return null;
	}

	public void load() {
		try {
			if (Files.exists(configPath)) {
				String content = new String(Files.readAllBytes(configPath));
				configData = JsonParser.parseString(content).getAsJsonObject();
			}
		} catch (Exception e) {
			configData = new JsonObject();
		}
	}

	public void save() {
		try {
			Files.createDirectories(configPath.getParent());
			Files.write(configPath, GSON.toJson(configData).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MutableConfigEntry<Double> doubleConfig(String name, double defaultValue) {
		Supplier<Double> supplier = () -> {
			if (configData.has(name)) return configData.get(name).getAsDouble();
			return defaultValue;
		};
		Consumer<Double> consumer = (val) -> {
			configData.addProperty(name, val);
			save();
		};

		return createConfigEntry(name, defaultValue, supplier, consumer);
	}

	public <T> MutableConfigEntry<T> booleanConfig(String name, T defaultValue) {
		Supplier<T> supplier = () -> {
			if (configData.has(name)) {
				return (T) (Boolean) configData.get(name).getAsBoolean();
			}
			return defaultValue;
		};
		Consumer<T> consumer = (val) -> {
			configData.addProperty(name, (Boolean) val);
			save();
		};
		return createConfigEntry(name, defaultValue, supplier, consumer);
	}

	public MutableConfigEntry<List<String>> stringListConfig(String name) {
		Supplier<List<String>> supplier = () -> {
			List<String> list = new ArrayList<>();
			if (configData.has(name) && configData.get(name).isJsonArray()) {
				configData.getAsJsonArray(name).forEach(element -> {
					list.add(element.getAsString());
				});
			}
			return list;
		};

		Consumer<List<String>> consumer = (val) -> {
			com.google.gson.JsonArray array = new com.google.gson.JsonArray();
			val.forEach(array::add);
			configData.add(name, array);
			save();
		};

		return createConfigEntry(name, Collections.emptyList(), supplier, consumer);
	}
}
