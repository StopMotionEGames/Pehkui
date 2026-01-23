package virtuoel.pehkui.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.command.argument.ScaleEasingArgumentType;
import virtuoel.pehkui.command.argument.ScaleModifierArgumentType;
import virtuoel.pehkui.command.argument.ScaleOperationArgumentType;
import virtuoel.pehkui.command.argument.ScaleTypeArgumentType;
import virtuoel.pehkui.server.command.DebugCommand;
import virtuoel.pehkui.server.command.ScaleCommand;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CommandUtils {
	public static void registerCommands() {
		if (ModLoaderUtils.isModLoaded("fabric-command-api-v2")) {
			CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) ->
			{
				registerCommands(dispatcher);
			});
		} else if (ModLoaderUtils.isModLoaded("fabric-command-api-v1")) {
			registerV1ApiCommands();
		}
	}

	public static void registerArgumentTypes() {
		if (ModLoaderUtils.isModLoaded("fabric-command-api-v2") && ModLoaderUtils.isModLoaded("fabric-registry-sync-v0")) {
			CommandUtils.registerArgumentTypes(new ArgumentTypeConsumer() {
				@Override
				public <T extends ArgumentType<?>> void register(ResourceLocation id, Class<T> argClass, Supplier<T> supplier) {
					ArgumentTypeRegistry.registerArgumentType(id, argClass, SingletonArgumentInfo.contextFree(supplier));
				}
			});
		}
	}

	public static void registerCommands(final CommandDispatcher<CommandSourceStack> dispatcher) {
		ScaleCommand.register(dispatcher);
		DebugCommand.register(dispatcher);
	}

	private static void registerV1ApiCommands() {
		try {
			final MethodHandles.Lookup lookup = MethodHandles.lookup();

			final Method staticRegister = CommandUtils.class.getDeclaredMethod("registerV1ApiCommands", CommandDispatcher.class, boolean.class);
			final MethodHandle staticRegisterHandle = lookup.unreflect(staticRegister);
			final MethodType staticRegisterType = staticRegisterHandle.type();

			final Class<?> callbackClass = Class.forName("net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback");

			@SuppressWarnings("unchecked") final Event<Object> registerEvent = (Event<Object>) callbackClass.getField("EVENT").get(null);

			final Method register = callbackClass.getDeclaredMethod("register", CommandDispatcher.class, boolean.class);
			final MethodType registerType = MethodType.methodType(register.getReturnType(), register.getParameterTypes());

			final MethodType factoryMethodType = MethodType.methodType(callbackClass);

			final CallSite lambdaFactory = LambdaMetafactory.metafactory(lookup, "register", factoryMethodType, registerType, staticRegisterHandle, staticRegisterType);
			final MethodHandle factoryInvoker = lambdaFactory.getTarget();

			final Object eventLambda = factoryInvoker.asType(factoryMethodType).invokeWithArguments(Collections.emptyList());

			registerEvent.register(eventLambda);
		} catch (Throwable e) {
			Pehkui.LOGGER.catching(e);
		}
	}

	protected static void registerV1ApiCommands(final CommandDispatcher<CommandSourceStack> dispatcher, final boolean dedicated) {
		registerCommands(dispatcher);
	}

	public static void registerArgumentTypes(ArgumentTypeConsumer consumer) {
		consumer.register(Pehkui.id("scale_type"), ScaleTypeArgumentType.class, ScaleTypeArgumentType::scaleType);
		consumer.register(Pehkui.id("scale_modifier"), ScaleModifierArgumentType.class, ScaleModifierArgumentType::scaleModifier);
		consumer.register(Pehkui.id("scale_operation"), ScaleOperationArgumentType.class, ScaleOperationArgumentType::operation);
		consumer.register(Pehkui.id("scale_easing"), ScaleEasingArgumentType.class, ScaleEasingArgumentType::scaleEasing);
	}

	@FunctionalInterface
	public interface ArgumentTypeConsumer {
		<T extends ArgumentType<?>> void register(ResourceLocation id, Class<T> argClass, Supplier<T> supplier);
	}

	public static void sendFeedback(CommandSourceStack source, Supplier<Component> text, boolean broadcastToOps) {
		source.sendSuccess(text, broadcastToOps);
	}

	public static CompletableFuture<Suggestions> suggestIdentifiersIgnoringNamespace(String namespace, Iterable<ResourceLocation> candidates, SuggestionsBuilder builder) {
		forEachMatchingIgnoringNamespace(
			namespace,
			candidates,
			builder.getRemaining().toLowerCase(Locale.ROOT),
			Function.identity(),
			id -> builder.suggest(String.valueOf(id))
		);

		return builder.buildFuture();
	}

	public static <T> void forEachMatchingIgnoringNamespace(String namespace, Iterable<T> candidates, String string, Function<T, ResourceLocation> idFunc, Consumer<T> action) {
		final boolean hasColon = string.indexOf(':') > -1;

		ResourceLocation id;
		for (final T object : candidates) {
			id = idFunc.apply(object);
			if (hasColon) {
				if (wordStartsWith(string, id.toString(), '_')) {
					action.accept(object);
				}
			} else if (
				wordStartsWith(string, id.getNamespace(), '_') ||
					id.getNamespace().equals(namespace) &&
						wordStartsWith(string, id.getPath(), '_')
			) {
				action.accept(object);
			}
		}
	}

	public static boolean wordStartsWith(String string, String substring, char wordSeparator) {
		for (int i = 0; !substring.startsWith(string, i); i++) {
			i = substring.indexOf(wordSeparator, i);
			if (i < 0) {
				return false;
			}
		}

		return true;
	}
}
