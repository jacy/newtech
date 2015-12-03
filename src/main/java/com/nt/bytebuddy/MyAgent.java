package com.nt.bytebuddy;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class MyAgent {
	public static void premain(String arguments, Instrumentation inst) {
		System.out.println("==============================================");
		System.out.println("==============================================");
		System.out.println("==============================================");
		System.out.println("==============================================");
		System.out.println("==============================================");
		new AgentBuilder.Default().withListener(new AgentBuilder.Listener() {
			@Override
			public void onTransformation(TypeDescription typeDescription, DynamicType dynamicType) {
				System.out.println("Transformed - " + typeDescription + "," + dynamicType);

			}

			@Override
			public void onIgnored(TypeDescription typeDescription) {
				//System.out.println("Ignored - " + typeDescription);

			}

			@Override
			public void onError(String typeName, Throwable throwable) {
				System.err.println("Error - " + typeName);
				throwable.printStackTrace();
			}

			@Override
			public void onComplete(String typeName) {
				//System.out.println("Completed - " + typeName);
			}

		}).type(ElementMatchers.nameStartsWith("com.nt")).transform(new Transformer() {
			
			@Override
			public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription) {
				return builder.method(ElementMatchers.isDeclaredBy(typeDescription)).intercept(MethodDelegation.to(MyInterceptor.class));
			}
		}).installOn(inst);
	}
}