package com.nt.bytebuddy;

import java.util.Comparator;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class HelloBuddy {
	static ClassLoader classLoader = HelloBuddy.class.getClassLoader();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Class<?> dynamicType = new ByteBuddy().subclass(Object.class).method(named("toString"))
				.intercept(FixedValue.value("Hello World!")).make()
				.load(classLoader, ClassLoadingStrategy.Default.WRAPPER).getLoaded();
		System.out.println(dynamicType.newInstance());

		@SuppressWarnings("rawtypes")
		Class<? extends Comparator> dynamicType2 = new ByteBuddy().subclass(Comparator.class).method(named("compare")).
		intercept(MethodDelegation.to(new ComparisonInterceptor())).make()
		.load(classLoader, ClassLoadingStrategy.Default.WRAPPER).getLoaded();
		System.out.println(dynamicType2.newInstance().compare(42, 20));
	}

}
