package virtuoel.pehkui.api;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.util.ClampingScaleModifier;
import virtuoel.pehkui.util.ConfigSyncUtils;
import virtuoel.pehkui.util.ScaleUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PehkuiConfig
{
	@ApiStatus.Internal
	public static final PehkuiConfigBuilder BUILDER = new PehkuiConfigBuilder(
		Pehkui.MOD_ID,
		FabricLoader.getInstance().getConfigDir().resolve(Pehkui.MOD_ID).resolve("config.json").normalize()
	)
	{
		@Override
		public <T> MutableConfigEntry<T> createConfigEntry(final String name, final T defaultValue, final Supplier<T> supplier, final Consumer<T> consumer)
		{
			return ConfigSyncUtils.createConfigEntry(name, defaultValue, supplier, consumer);
		}
	};

	public static final Client CLIENT = new Client(BUILDER);
	public static final Common COMMON = new Common(BUILDER);
	public static final Server SERVER = new Server(BUILDER);

	public static final class Common
	{
		public final MutableConfigEntry<Boolean> keepAllScalesOnRespawn;
		public final MutableConfigEntry<List<String>> scalesKeptOnRespawn;

		public final MutableConfigEntry<Boolean> accurateNetherPortals;

		public final MutableConfigEntry<Boolean> enableCommands;
		public final MutableConfigEntry<Boolean> enableDebugCommands;

		public final MutableConfigEntry<Boolean> scaledFallDamage;
		public final MutableConfigEntry<Boolean> scaledMotion;
		public final MutableConfigEntry<Boolean> scaledReach;
		public final MutableConfigEntry<Boolean> scaledAttack;
		public final MutableConfigEntry<Boolean> scaledDefense;
		public final MutableConfigEntry<Boolean> scaledHealth;
		public final MutableConfigEntry<Boolean> scaledItemDrops;
		public final MutableConfigEntry<Boolean> scaledProjectiles;
		public final MutableConfigEntry<Boolean> scaledExplosions;

		private Common(final PehkuiConfigBuilder builder)
		{
			this.keepAllScalesOnRespawn = builder.booleanConfig(synced("keepAllScalesOnRespawn", "boolean"), false);
			this.scalesKeptOnRespawn = builder.stringListConfig(synced("scalesKeptOnRespawn", "string_list"));

			this.accurateNetherPortals = builder.booleanConfig(synced("accurateNetherPortals", "boolean"), true);

			this.enableCommands = builder.booleanConfig("enableCommands", true);
			this.enableDebugCommands = builder.booleanConfig("enableDebugCommands", false);

			this.scaledFallDamage = builder.booleanConfig(synced("scaledFallDamage", "boolean"), true);
			this.scaledMotion = builder.booleanConfig(synced("scaledMotion", "boolean"), true);
			this.scaledReach = builder.booleanConfig(synced("scaledReach", "boolean"), true);
			this.scaledAttack = builder.booleanConfig(synced("scaledAttack", "boolean"), true);
			this.scaledDefense = builder.booleanConfig(synced("scaledDefense", "boolean"), true);
			this.scaledHealth = builder.booleanConfig(synced("scaledHealth", "boolean"), true);
			this.scaledItemDrops = builder.booleanConfig(synced("scaledItemDrops", "boolean"), true);
			this.scaledProjectiles = builder.booleanConfig(synced("scaledProjectiles", "boolean"), true);
			this.scaledExplosions = builder.booleanConfig(synced("scaledExplosions", "boolean"), true);
			
			Identifier id;
			String namespace, path;
			ScaleType type;
			Supplier<Double> min, max;
			for (final Map.Entry<Identifier, ScaleType> entry : ScaleRegistries.SCALE_TYPES.entrySet())
			{
				id = entry.getKey();
				namespace = id.getNamespace();

				if (namespace.equals(Pehkui.MOD_ID))
				{
					type = entry.getValue();

					if (type == ScaleTypes.INVALID)
					{
						continue;
					}

					path = id.getPath();

					min = builder.doubleConfig(synced(path + ".minimum", "double"), type.getAffectsDimensions() ? ScaleUtils.DEFAULT_MINIMUM_POSITIVE_SCALE : Float.MIN_VALUE);
					max = builder.doubleConfig(synced(path + ".maximum", "double"), Float.MAX_VALUE);

					type.getDefaultBaseValueModifiers().add(
						ScaleRegistries.register(
							ScaleRegistries.SCALE_MODIFIERS,
							Pehkui.id("clamping", path),
							new ClampingScaleModifier(min, max, 0.0F)
						)
					);
				}
			}
		}
	}

	public static final class Client
	{
		public final Supplier<Double> minimumCameraDepth;

		private Client(final PehkuiConfigBuilder builder)
		{
			this.minimumCameraDepth = builder.doubleConfig("minimumCameraDepth", 1.0D / 32767.0D);
		}
	}

	public static final class Server
	{
		private Server(final PehkuiConfigBuilder builder)
		{

		}
	}

	private PehkuiConfig()
	{

	}

	private static String synced(final String name, final String codecKey)
	{
		ConfigSyncUtils.setupSyncableConfig(name, codecKey);
		return name;
	}
}
