package org.esfinge.virtuallab.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

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
			this.properties.load(this.getClass().getClassLoader().getResourceAsStream("virtuallab.properties"));

			// cria um diretorio de upload no diretorio temporario do sistema
			// para caso a chave "upload.dir" do arquivo de propriedades seja invalida
			Paths.get(FileUtils.getTempDirectoryPath(), "upload").toAbsolutePath().toFile().mkdirs();

			// TODO: debug..
			System.out.println(">> DIRETORIO DE UPLOAD: " + this.getUploadDir());
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
	 * Retorna se a String eh nula ou vazia.
	 */
	public static boolean isNullOrEmpty(String value)
	{
		return (value == null || value.trim().length() == 0);
	}
	
	/**
	 * Retorna se a colecao eh nula ou vazia.
	 */
	public static boolean isNullOrEmpty(Collection<?> collection)
	{
		return (collection == null || collection.size() == 0);
	}
}