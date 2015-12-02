package com.nt.bytebuddy;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Callable;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

public class MyAgent {
	public static void premain(String arguments, Instrumentation inst) {
		new AgentBuilder.Default().type(ElementMatchers.any()).transform((builder, desc) -> {
			return builder.method(ElementMatchers.any()).intercept(MethodDelegation.to(MyServiceInterceptor.class));
		}).installOn(inst);
	}
}

class MyServiceInterceptor {
	@RuntimeType
	public static Object intercept(@SuperCall Callable<?> zuper) throws Exception {
		return zuper.call();
	}
}