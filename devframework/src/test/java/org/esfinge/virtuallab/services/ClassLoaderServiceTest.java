package org.esfinge.virtuallab.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.utils.Utils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;

/**
 * Testes unitarios para a classe ClassLoaderService.
 */
public class ClassLoaderServiceTest
{
	private void createClass(String name, boolean valid) throws IOException
	{
		// cria uma classe com a anotacao @ServiceClass
		IClassWriter mock = ClassMock.of(name);
		mock.annotation(ServiceClass.class);
		
		// adiciona um metodo valido/invalido com a anotacao @ServiceMethod
		mock.method("method").returnType(valid ? int.class : void.class).annotation(ServiceMethod.class);
		
		// salva o arquivo .CLASS da classe no diretorio de testes
		TestUtils.saveClassToTestDir((ClassMock) mock);
	}
	
	@Before
	public void doBefore()
	{
		// apaga o diretorio de testes antes de cada teste
		TestUtils.cleanTestDir();
	}

	@AfterClass
	public static void doAfterClass()
	{
		// apaga o diretorio de testes ao fim dos testes
		TestUtils.cleanTestDir();
	}

	@Test
	public void testLoadClassFromPath() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, true);
		String classPath = TestUtils.pathFromTestDir(className + ".class");

		// verifica se a classe foi carregada
		Class<?> clazz = ClassLoaderService.getInstance().loadClass(classPath);
		Assert.assertNotNull(clazz);
		Assert.assertEquals(className, clazz.getCanonicalName());
	}

	@Test
	public void testLoadClassFromStream() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, true);
		InputStream classStream = TestUtils.streamFromTestDir(className + ".class");

		// verifica se a classe foi carregada
		Class<?> clazz = ClassLoaderService.getInstance().loadClass(classStream, className);
		Assert.assertNotNull(clazz);
		Assert.assertEquals(className, clazz.getCanonicalName());
	}

	@Test
	public void testLoadClassFromJarPath() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String className1 = TestUtils.createMockClassName();
		String className2 = TestUtils.createMockClassName();
		String className3 = TestUtils.createMockClassName();
		
		this.createClass(className1, true);
		this.createClass(className2, true);
		this.createClass(className3, false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("someJar.jar", className1, className2, className3));
		String jarPath = TestUtils.pathFromTestDir("someJar.jar");
		
		// assegura que as classes nao estao carregadas
		TestUtils.assertClassNotLoaded(className1);
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		// verifica se as classes foram carregadas
		Class<?> clazz = ClassLoaderService.getInstance().loadClassFromJar(jarPath, className1);
		Assert.assertEquals(className1, clazz.getCanonicalName());
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		clazz = ClassLoaderService.getInstance().loadClassFromJar(jarPath, className2);
		Assert.assertEquals(className2, clazz.getCanonicalName());
		TestUtils.assertClassNotLoaded(className3);
		
		clazz = ClassLoaderService.getInstance().loadClassFromJar(jarPath, className3);
		Assert.assertEquals(className3, clazz.getCanonicalName());
	}

	@Test
	public void testLoadClassFromJarStream() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String className1 = TestUtils.createMockClassName();
		String className2 = TestUtils.createMockClassName();
		String className3 = TestUtils.createMockClassName();
		
		this.createClass(className1, true);
		this.createClass(className2, true);
		this.createClass(className3, false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("someJar.jar", className1, className2, className3));
		InputStream jarStream = TestUtils.streamFromTestDir("someJar.jar");
		
		// assegura que as classes nao estao carregadas
		TestUtils.assertClassNotLoaded(className1);
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		// verifica se as classes foram carregadas
		Class<?> clazz = ClassLoaderService.getInstance().loadClassFromJar(jarStream, className1);
		Assert.assertEquals(className1, clazz.getCanonicalName());
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		jarStream = TestUtils.streamFromTestDir("someJar.jar");
		clazz = ClassLoaderService.getInstance().loadClassFromJar(jarStream, className2);
		Assert.assertEquals(className2, clazz.getCanonicalName());
		TestUtils.assertClassNotLoaded(className3);
		
		jarStream = TestUtils.streamFromTestDir("someJar.jar");
		clazz = ClassLoaderService.getInstance().loadClassFromJar(jarStream, className3);
		Assert.assertEquals(className3, clazz.getCanonicalName());
	}

	@Test
	public void testLoadJarFromPath() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String className1 = TestUtils.createMockClassName();
		String className2 = TestUtils.createMockClassName();
		String className3 = TestUtils.createMockClassName();
		
		this.createClass(className1, true);
		this.createClass(className2, true);
		this.createClass(className3, false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("someJar.jar", className1, className2, className3));
		String jarPath = TestUtils.pathFromTestDir("someJar.jar");
		
		// assegura que as classes nao estao carregadas
		TestUtils.assertClassNotLoaded(className1);
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		// verifica se as classes foram carregadas
		List<Class<?>> classList = ClassLoaderService.getInstance().loadJar(jarPath);
		Assert.assertEquals(3, classList.size());
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(className1)));
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(className2)));
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(className3)));
	}

	@Test
	public void testLoadJarFromStream() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String className1 = TestUtils.createMockClassName();
		String className2 = TestUtils.createMockClassName();
		String className3 = TestUtils.createMockClassName();
		
		this.createClass(className1, true);
		this.createClass(className2, true);
		this.createClass(className3, false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("someJar.jar", className1, className2, className3));
		InputStream jarStream = TestUtils.streamFromTestDir("someJar.jar");
		
		// assegura que as classes nao estao carregadas
		TestUtils.assertClassNotLoaded(className1);
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		// verifica se as classes foram carregadas
		List<Class<?>> classList = ClassLoaderService.getInstance().loadJar(jarStream);
		Assert.assertEquals(3, classList.size());
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(className1)));
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(className2)));
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(className3)));
	}
}
