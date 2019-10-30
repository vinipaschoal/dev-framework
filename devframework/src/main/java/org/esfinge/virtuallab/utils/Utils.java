package org.esfinge.virtuallab.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ObjectUtils;

/**
 * Classe com metodos utilitarios para a aplicacao.
 *
 */
public class Utils
{
	// instancia unica da classe
	private static Utils _instance;

	// arquivo de propriedades
	private Properties properties;

	/**
	 * Singleton.
	 */
	public static Utils getInstance()
	{
		if (_instance == null)
			_instance = new Utils();

		return _instance;
	}

	/**
	 * Construtor interno.
	 */
	private Utils()
	{
		try
		{
			// carrega o arquivo de propriedades
			this.properties = new Properties();
			this.properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));

			// cria um diretorio de upload no diretorio temporario do sistema
			// para caso a chave "upload.dir" do arquivo de propriedades seja invalida
			Paths.get(FileUtils.getTempDirectoryPath(), "upload").toAbsolutePath().toFile().mkdirs();

			// TODO: debug..
			System.err.println("\n>> DIRETORIO DE UPLOAD: " + this.getUploadDir() + "\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o valor da chave especificada no arquivo de propriedades, ou o valor
	 * default especificado caso o a chave nao seja encontrada.
	 */
	public String getProperty(String prop, String defaultValue)
	{
		return this.properties.getProperty(prop, defaultValue);
	}

	/**
	 * Retorna o valor da chave especificada no arquivo de propriedades, ou o valor
	 * default especificado caso o a chave nao seja encontrada.
	 */
	public int getPropertyAsInt(String prop, int defaultValue)
	{
		try
		{
			return Integer.parseInt(this.properties.getProperty(prop));
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * Atribui o valor a uma propriedade.
	 */
	public void setProperty(String prop, String value)
	{
		this.properties.setProperty(prop, value);
	}

	/**
	 * Retorna o diretorio de upload.
	 */
	public String getUploadDir()
	{
		// verifica se o diretorio de upload existe e pode ser escrito
		File uploadDir = Paths.get(this.properties.getProperty("upload.dir")).toAbsolutePath().toFile();
		if (uploadDir.isDirectory() && uploadDir.canWrite())
			return uploadDir.getAbsolutePath();

		// retorna o diretorio de upload criado no diretorio temporario do sistema
		return Paths.get(FileUtils.getTempDirectoryPath(), "upload").toAbsolutePath().toString();
	}
	
	/**
	 * Retorna o elemento da colecao que corresponda ao filtro informado, ou null se
	 * nao encontrado.
	 */
	public static <T> T getFromCollection(Collection<T> collection, Predicate<T> filter)
	{
		return (collection.stream().filter(filter).findFirst().orElse(null));
	}

	/**
	 * Retorna os elementos da colecao que correspondam ao filtro informado, ou uma
	 * lista vazia se nao encontrado.
	 */
	public static <T> List<T> filterFromCollection(Collection<T> collection, Predicate<T> filter)
	{
		return (collection.stream().filter(filter).collect(Collectors.toList()));
	}
	
	/**
	 * Retorna se o objeto eh null, ou se a colecao, mapa, array ou string eh vazia.
	 */
	public static boolean isNullOrEmpty(Object value)
	{
		return ObjectUtils.isEmpty(value);
	}

	/**
	 * Lanca a excecao caso o objeto seja nulo.
	 */
	public static <E extends Throwable> void throwIfNull(Object obj, Class<E> exception, String msg) throws E
	{
		if ( isNullOrEmpty(obj) )
		{
			try
			{
				throw exception.getDeclaredConstructor(String.class).newInstance(msg);
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Cria um arquivo jar no diretorio de destino com os arquivos das classes especificadas.
	 */
	public static boolean createJar(String jarName, String destinationPath, String... classesPaths) 
	{
		try
		{
			// cria o manifest do arquivo jar de teste
			Manifest manifest = new Manifest();
			manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
			
			// verifica se o nome do arquivo jar contem a extensao .jar
			String jarPath = Paths.get(destinationPath, 
					FilenameUtils.isExtension(jarName, new String[] {"jar", "JAR"}) ? 
					jarName : jarName.concat(".jar")).toAbsolutePath().toString();

			// arquivo jar
			JarOutputStream jarFile = new JarOutputStream(new FileOutputStream(jarPath), manifest);

			// adiciona as classes ao jar
			for (String classPath : classesPaths)
			{
				// obtem o nome qualificado da classe
				JavaClass jc = new ClassParser(classPath).parse();
				jarFile.putNextEntry(new JarEntry(jc.getClassName()+ ".class"));
				
				// adiciona a classe ao jar
				jarFile.write(FileUtils.readFileToByteArray(new File(classPath)));
				jarFile.closeEntry();
			}

			// fecha o arquivo jar
			jarFile.close();

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Salva o stream no diretorio temporario.
	 */
	public static File saveToTempDir(InputStream inputStream, String fileName) throws IOException
	{
		// o arquivo de destino
		File tmpFile = Paths.get(FileUtils.getTempDirectoryPath(), fileName).toAbsolutePath().toFile();
		
		// copia o stream para o arquivo
		FileUtils.copyInputStreamToFile(inputStream, tmpFile);
		
		//
		return tmpFile;
	}
}