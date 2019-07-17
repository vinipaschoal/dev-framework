package devframework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FileUtils;

/**
 * Classe com metodos utilitarios para a aplicacao.
 *
 */
public class Utils {
	// instancia unica da classe
	private static Utils _instance;

	// arquivo de propriedades
	private Properties properties;

	/**
	 * Singleton.
	 */
	public static Utils getInstance() {
		if (_instance == null)
			_instance = new Utils();

		return _instance;
	}

	/**
	 * Construtor interno.
	 */
	private Utils() {
		try {
			// carrega o arquivo de propriedades
			this.properties = new Properties();
			this.properties.load(this.getClass().getClassLoader().getResourceAsStream("devframework.properties"));

			// cria um diretorio de upload no diretorio temporario do sistema
			// para caso a chave "upload.dir" do arquivo de propriedades seja invalida
			Paths.get(FileUtils.getTempDirectoryPath(), "upload").toAbsolutePath().toFile().mkdirs();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o valor da chave especificada no arquivo de propriedades, ou o valor
	 * default especificado caso o a chave nao seja encontrada.
	 */
	public String getProperty(String prop, String defaultValue) {
		return this.properties.getProperty(prop, defaultValue);
	}

	/**
	 * Retorna o valor da chave especificada no arquivo de propriedades, ou o valor
	 * default especificado caso o a chave nao seja encontrada.
	 */
	public int getPropertyAsInt(String prop, int defaultValue) {
		try {
			return Integer.parseInt(this.properties.getProperty(prop));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Atribui o valor a uma propriedade.
	 */
	public void setProperty(String prop, String value) {
		this.properties.setProperty(prop, value);
	}

	/**
	 * Retorna o diretorio de upload.
	 */
	public String getUploadDir() {
		return this.properties.getProperty("upload.dir",
				Paths.get(FileUtils.getTempDirectoryPath(), "upload").toAbsolutePath().toString());
	}

	/**
	 * Retorna os campos da classe.
	 */
	public static List<Field> getFields(Class<?> clazz, boolean includeInherited) {
		List<Field> fields = new ArrayList<Field>();

		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

		if (includeInherited)
			while ((clazz = clazz.getSuperclass()) != null)
				fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

		return (fields);
	}

	/**
	 * Retorna os campos da classe que possuem a anotacao informada pelo parametro.
	 */
	public static List<Field> getAnnotadedFields(Class<?> clazz, Class<? extends Annotation> annotationClazz,
			boolean includeInherited) {
		List<Field> fields = new ArrayList<Field>();

		fields.addAll(getFields(clazz, includeInherited).stream().filter(f -> f.isAnnotationPresent(annotationClazz))
				.collect(Collectors.toList()));

		return (fields);
	}

	/**
	 * Retorna o campo da classe com o nome informado pelo parametro, ou null se nao
	 * encontrado.
	 */
	public static Field getField(Class<?> clazz, String name, boolean includeInherited) {
		return (getFields(clazz, includeInherited).stream().filter(f -> f.getName().equals(name)).findFirst()
				.orElse(null));
	}

	/**
	 * Retorna o elemento da colecao que corresponda ao filtro informado, ou null se
	 * nao encontrado.
	 */
	public static <T> T getFromCollection(Collection<T> collection, Predicate<T> filter) {
		return (collection.stream().filter(filter).findFirst().orElse(null));
	}

	/**
	 * Retorna os elementos da colecao que correspondam ao filtro informado, ou uma
	 * lista vazia se nao encontrado.
	 */
	public static <T> List<T> filterFromCollection(Collection<T> collection, Predicate<T> filter) {
		return (collection.stream().filter(filter).collect(Collectors.toList()));
	}

	public JavaClass getClassInfo(String classPath) throws ClassFormatException, IOException {
		return new ClassParser(classPath).parse();
	}

	public JavaClass getClassInfo(InputStream inputStream, String fileName) throws ClassFormatException, IOException {
		return new ClassParser(inputStream, fileName).parse();
	}
}