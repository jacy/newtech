package com.nt.bytebuddy.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.bytebuddy.ByteBuddy;

public class BuddyExample {
	// create a subclass of an existing class
	public static void createClass() {
		new ByteBuddy().subclass(Object.class).name("Dynamic").make();
	}

	// alteration of an existing class,either by adding fields and methods or by
	// replacing existing method implementations.Preexisting method
	// implementations are however lost if they are replaced by another
	// implementation
	public static void redefineClass() {
		new ByteBuddy().redefine(Foo.class);
	}

	// Instead of discarding overridden methods like when performing a type
	// redefinition, Byte Buddy copies all such method implementations into
	// renamed private methods with compatible signatures
	public static void rebaseClass() {
		new ByteBuddy().rebase(Foo.class);
	}

	// store a class in a given folder
	public static void storeClass() throws Exception {
		File classDir = new File("/Users/jacy/Desktop/test");
		String className = "Dynamic";
		File classFile = new File(classDir, className + ".class");
		classFile.deleteOnExit();
		new ByteBuddy().subclass(Object.class).name(className).make().saveIn(classDir);

		// test same class load by diff classloader
		FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDir.getAbsolutePath());
		FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDir.getAbsolutePath());
		Object obj1 = fscl1.loadClass(className).newInstance();
		Object obj2 = fscl2.loadClass(className).newInstance();
		System.out.println(obj1.getClass().getCanonicalName());
		System.out.println(obj2.getClass().getCanonicalName());
	}

	// injected to existing jar file
	public static void injectClassToJar() throws IOException {
		File classFile = new File("/Users/jacy/Desktop/test/test.jar");
		new ByteBuddy().subclass(Object.class).name("Dynamic").make().inject(classFile);
	}

	public static void main(String[] args) throws Exception {
		storeClass();
	}
}

class Foo {
	String bar() {
		return "bar";
	}
}

class FileSystemClassLoader extends ClassLoader {

	private String rootDir;

	public FileSystemClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);
		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		String path = classNameToPath(className);
		try {
			InputStream ins = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			while ((bytesNumRead = ins.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesNumRead);
			}
			ins.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String className) {
		return rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
	}
}