import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.CustomReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.domain.ChartService;
import org.esfinge.virtuallab.domain.MatematicaInvokerProxy;
import org.esfinge.virtuallab.domain.MatematicaService;
import org.esfinge.virtuallab.domain.Ponto;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaService;
import org.esfinge.virtuallab.domain.Temperatura;
import org.esfinge.virtuallab.domain.TemperaturaServiceProxy;
import org.esfinge.virtuallab.domain.TemperaturaService;
import org.esfinge.virtuallab.domain.Topic;
import org.esfinge.virtuallab.domain.TopicService;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;


public class Main
{
	public static void main(String... args) throws Exception
	{
		TestUtils.createJar("tarefa.jar", TarefaService.class, Tarefa.class);
		TestUtils.createJar("matematica.jar", MatematicaService.class, MatematicaInvokerProxy.class, Ponto.class);
		TestUtils.createJar("chart.jar", ChartService.class, Temperatura.class);
		TestUtils.createJar("temperaturaDAO.jar", TemperaturaService.class, TemperaturaServiceProxy.class, Temperatura.class);
		TestUtils.createJar("topicDAO.jar", TopicService.class, Topic.class);
		
		/*
		Enhancer proxy = new Enhancer();
		proxy.setSuperclass(MatematicaService.class);
		proxy.setCallback(InvokerService.getInstance());
		
		MatematicaService matSvc = (MatematicaService) proxy.create();
		System.out.println(matSvc.calcularDistancia(new Ponto(1,2), new Ponto(4,6)));
		*/
		
		/*
		for ( Method m : MethodUtils.getMethodsWithAnnotation(Main.class, BarChartReturn.class) )
		{
			MethodReturnProcessor<?> processor = MethodReturnProcessorHelper.getInstance().findProcessor(m);
			JsonData data = processor.process(m.invoke(null, null));
			System.out.println(data);
			System.out.println();
		}
		*/
	}
	
	
	@BarChartReturn(dataLabels = {"Sul", "Sudeste", "Nordeste", "Norte", "Centro-Oeste"},
			dataColors = {"rgba(255, 99, 132, 0.2)", "rgba(255, 206, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(153, 102, 255, 0.2)", "rgba(255, 159, 64, 0.2)"},
			legend = "Number of Votes",
			title = "Brazil Election Votes",
			titleFontSize = 40,
			xAxisLabel = "Regions",
			yAxisLabel = "Votes",
			xAxisShowGridlines = false,
			yAxisShowGridlines = false,			
			axisFontSize = 30,
			horizontal = false) 
	public static List<Number> getDataList()
	{
		List<Number> list = new ArrayList<>();
		list.add(12);
		list.add(19);
		list.add(3);
		list.add(7);
		list.add(10);
		
		return list;
	}
	
	@BarChartReturn
	public static Map<String,Number> getDataMap()
	{
		Map<String, Number> map = new HashMap<>();
		
		map.put("Sul", 12);
		map.put("Sudeste", 19);
		map.put("Nordeste", 3);
		map.put("Norte", 7);
		map.put("Centro-Oeste", 10);
		
		return map;
	}

	@BarChartReturn(dataLabelsField = "mes",
			dataValuesField = "maxima",
			legend = "Temperatura Máxima",
			title = "Temperatura Máxima Anual",
			titleFontSize = 40,
			xAxisLabel = "Meses",
			yAxisLabel = "Temperatura",
			xAxisShowGridlines = false,
			yAxisShowGridlines = true,			
			axisFontSize = 30,
			horizontal = false) 
	public static List<Temperatura> getTemperaturasByMaxima()
	{
		List<Temperatura> list = new ArrayList<>();
		list.add(new Temperatura(2l, "-23.5475","-46.63611111", 28.2, 19.3, "janeiro"));
		list.add(new Temperatura(3l, "-23.5475","-46.63611111", 28.8, 19.5, "fevereiro"));
		list.add(new Temperatura(4l, "-23.5475","-46.63611111", 28.0, 18.8, "marco"));
		list.add(new Temperatura(5l, "-23.5475","-46.63611111", 26.2, 17.4, "abril"));
		list.add(new Temperatura(6l, "-23.5475","-46.63611111", 23.3, 14.5, "maio"));
		list.add(new Temperatura(7l, "-23.5475","-46.63611111", 22.6, 13.0, "junho"));
		
		return list;
	}

	@BarChartReturn(dataValuesField = "minima",
			legend = "Temperatura Mínma",
			title = "Temperatura Mínima Anual",
			titleFontSize = 40,
			xAxisLabel = "Temperatura",
			yAxisLabel = "Meses",
			xAxisShowGridlines = true,
			yAxisShowGridlines = false,			
			axisFontSize = 30,
			horizontal = true) 
	public static Map<String,Temperatura> getTemperaturasByMinima()
	{
		Map<String, Temperatura> map = new HashMap<>();
		map.put("JANEIRO", new Temperatura(2l, "-23.5475","-46.63611111", 28.2, 19.3, "janeiro"));
		map.put("FEVEREIRO", new Temperatura(3l, "-23.5475","-46.63611111", 28.8, 19.5, "fevereiro"));
		map.put("MARCO", new Temperatura(4l, "-23.5475","-46.63611111", 28.0, 18.8, "marco"));
		map.put("ABRIL", new Temperatura(5l, "-23.5475","-46.63611111", 26.2, 17.4, "abril"));
		map.put("MAIO", new Temperatura(6l, "-23.5475","-46.63611111", 23.3, 14.5, "maio"));
		map.put("JUNHO", new Temperatura(7l, "-23.5475","-46.63611111", 22.6, 13.0, "junho"));
		
		return map;
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
