import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.CustomReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.domain.MatematicaService;
import org.esfinge.virtuallab.domain.Ponto;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaService;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessorHelper;
import org.esfinge.virtuallab.web.json.JsonData;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;


public class Main
{
	public static void main(String... args) throws Exception
	{
		TestUtils.createJar("tarefa.jar", TarefaService.class, Tarefa.class);
		TestUtils.createJar("matematica.jar", MatematicaService.class, Ponto.class);
//		TestUtils.createJar("temperatura.jar", TemperaturaService.class, Temperatura.class);
		
		/*
		File jarFile = Paths.get(TestUtils.TEST_DIR, "temperatura.jar").toFile();
		System.out.println("Exists: " + jarFile.exists() + " - " + jarFile.getAbsolutePath());
		addJarToClasspath(jarFile);
		
		System.out.println(Class.forName("org.esfinge.virtuallab.domain.Temperatura"));
		
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(ClassLoader.getSystemClassLoader());
		String pattern = "classpath*:" + "org/esfinge/virtuallab/domain" +  ";
//				ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/
		System.out.println("Pattern: " + pattern);
		Resource[] resources = resourcePatternResolver.getResources(pattern);
		
		for (Resource resource : resources)
		{
			System.out.println(resource.getFilename());
		}
		*/
		
		
		Method m = MethodUtils.getMethodsWithAnnotation(Main.class, BarChartReturn.class)[0];
		MethodReturnProcessor<?> processor = MethodReturnProcessorHelper.getInstance().findProcessor(m);
		JsonData data = processor.process(getData());
		System.out.println(data);

//		ValidServiceDAOValidador v = new ValidServiceDAOValidador();
//		v.validate(null, RepoTest.class);
	}	
	
	public static void addJarToClasspath(File jar) throws Exception
	{
		// Get the ClassLoader class
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class<?> clazz = cl.getClass();

		// Get the protected addURL method from the parent URLClassLoader class
		Method method = clazz.getSuperclass().getDeclaredMethod("addURL", new Class[] { URL.class });

		// Run projected addURL method to add JAR to classpath
		method.setAccessible(true);
		method.invoke(cl, new Object[] { jar.toURI().toURL() });
	}
	
	@BarChartReturn(labels = {"Sul", "Sudeste", "Nordeste", "Norte", "Centro-Oeste"},
			colors = {"rgba(255, 99, 132, 0.2)", "rgba(255, 206, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(153, 102, 255, 0.2)", "rgba(255, 159, 64, 0.2)"},
			legend = "Number of Votes",
			title = "Brazil Election Votes",
			titleFontSize = 40,
			xAxisLabel = "Regions",
			yAxisLabel = "Votes",
			xAxisGridLines = false,
			yAxisGridLines = false,			
			axisFontSize = 30,
			horizontal = false) 
	public static List<Number> getData()
	{
		List<Number> list = new ArrayList<>();
		list.add(12);
		list.add(19);
		list.add(3);
		list.add(7);
		list.add(10);
		
		return list;
	}
	
	/**
	 * Retorna uma String com as informacoes de bytecode de uma classe.
	 * 
	 * @param classBytecode os bytecodes da classe 
	 * @return as informacoes de bytecode da classe informada
	 */
	public static String inspectToString(byte[] classBytecode)
	{
		ClassReader classReader = new ClassReader(classBytecode);
		StringWriter buffer = new StringWriter();
		TraceClassVisitor tracer = new TraceClassVisitor(new PrintWriter(buffer));
		classReader.accept(tracer, 0);
		
		return ( buffer.toString() );
	}
	
	@ServiceClass
	public static class Service
	{
		@ServiceMethod
		@TableReturn(fields={"endereco", "idade", "nome"})
		public List<Pessoa> getPessoas()
		{
			return new ArrayList<Pessoa>();
		}
		
		@ServiceMethod
		@TableReturn
		@CustomReturn
		public List<String> doThing()
		{
			return new ArrayList<>();
		}
		
//		@ServiceMethod
		protected int getThing()
		{
			return 1;
		}
	}
	
	public static class Pessoa
	{
		private String nome;
		private int idade;
		private Date nascimento;
		private Endereco endereco;
		public String getNome()
		{
			return nome;
		}
		public void setNome(String nome)
		{
			this.nome = nome;
		}
		public int getIdade()
		{
			return idade;
		}
		public void setIdade(int idade)
		{
			this.idade = idade;
		}
		public Endereco getEndereco()
		{
			return endereco;
		}
		public void setEndereco(Endereco endereco)
		{
			this.endereco = endereco;
		}
		
		public void setNascimento(Date data)
		{
			this.nascimento = data;
		}
		
		public Date getNascimento()
		{
			return nascimento;
		}
	}
	
	public static class Endereco
	{
		private String cep;
		private String rua;
		private int numero;
		private Regiao regiao;		
		public String getCep()
		{
			return cep;
		}
		public void setCep(String cep)
		{
			this.cep = cep;
		}
		public String getRua()
		{
			return rua;
		}
		public void setRua(String rua)
		{
			this.rua = rua;
		}
		public int getNumero()
		{
			return numero;
		}
		public void setNumero(int numero)
		{
			this.numero = numero;
		}
		public Regiao getRegiao()
		{
			return regiao;
		}		
		public void setRegiao(Regiao regiao)
		{
			this.regiao  = regiao;
		}
	}
	
	public static class Regiao
	{
		private String cidade;
		protected String estado;
		
		public String getCidade()
		{
			return cidade;
		}
		public void setCidade(String cidade)
		{
			this.cidade = cidade;
		}
		/*
		public String getEstado()
		{
			return estado;
		}
		public void setEstado(String estado)
		{
			this.estado = estado;
		}
		*/
	}
	
	public static class RegiaoExtension extends Regiao
	{
		private boolean rural;

		/*
		public boolean isRural()
		{
			return rural;
		}

		public void setRural(boolean rural)
		{
			this.rural = rural;
		}
		*/
	}
}
