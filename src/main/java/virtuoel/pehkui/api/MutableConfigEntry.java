package virtuoel.pehkui.api;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MutableConfigEntry<T> extends Supplier<T>, Consumer<T> {
	T getValue();
	void setValue(T value);

	@Override
	default T get() {
		return getValue();
	}

	@Override
	default void accept(T t) {
		setValue(t);
	}
}
