package org.esfinge.virtuallab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Classe com metodos utilitarios para testes.
 */
public abstract class TestUtils {
	// diretorio de teste
	public static final String TEST_DIR;
	public static final File TEST_DIR_FILE;

	static {
		String testDir = Paths.get(File.separator + "tmp", "test-dir").toAbsolutePath().toString();
		try {
			// tenta criar o diretorio de teste em "target/test-dir"
			if (!new File(testDir).exists()) {
				FileUtils.forceMkdir(new File(testDir));
			}

		} catch (IOException ioe) {
			testDir = Paths.get(FileUtils.getTempDirectoryPath(), "test-dir").toAbsolutePath().toString();

			// tenta criar em "diretorio temporario/test-dir"
			new File(testDir).mkdirs();
		} finally {
			TEST_DIR = testDir;
			TEST_DIR_FILE = new File(TEST_DIR);
		}
	}

	/**
	 * Cria um arquivo jar no diretorio de teste com as classes especificadas.
	 */
	public static boolean createJar(String name, Class<?>... classes) {
		try {
			// cria o manifest do arquivo jar de teste
			Manifest manifest = new Manifest();
			manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

			// arquivo jar
			JarOutputStream jarFile = new JarOutputStream(new FileOutputStream(TEST_DIR + "/" + name), manifest);

			// adiciona as classes ao jar
			for (Class<?> clazz : classes) {
				String classPath = clazz.getName().replace(".", "/") + ".class";
				jarFile.putNextEntry(new JarEntry(classPath));
				jarFile.write(IOUtils.resourceToByteArray(classPath, clazz.getClassLoader()));
				jarFile.closeEntry();
			}

			// fecha o arquivo jar
			jarFile.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Apaga o conteudo do diretorio de teste.
	 */
	public static boolean cleanTestDir() {
		boolean deleted = false;
		while (!deleted) {
			try {
				System.gc();
				FileUtils.cleanDirectory(TEST_DIR_FILE);
				TEST_DIR_FILE.mkdirs();
				deleted= true;
			} catch (IOException ioe) {
				
			}
		}
		return true;
	}

	/**
	 * Copia as classes especificadas para o diretorio de teste.
	 */
	public static boolean copyToTestDir(Class<?>... classes) {
		try {
			for (Class<?> clazz : classes) {
				String classPath = clazz.getName().replace(".", "/") + ".class";
				FileUtils.copyFileToDirectory(new File(clazz.getClassLoader().getResource(classPath).getFile()),
						TEST_DIR_FILE);
			}
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Obtem o caminho para o recurso a partir do diretorio de teste.
	 */
	public static String pathFromTestDir(String resourceName) {
		// retorna o caminho a partir do diretorio de teste
		return TEST_DIR + File.separator + resourceName;
	}

	/**
	 * Obtem o stream para o recurso a partir do diretorio de teste.
	 */
	public static InputStream streamFromTestDir(String resourceName) throws IOException {
		// retorna um stream a partir do diretorio de teste
		return FileUtils.openInputStream(new File(pathFromTestDir(resourceName)));
	}

}
