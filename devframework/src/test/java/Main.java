import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.annotations.CustomReturn;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.esfinge.virtuallab.annotations.TableReturn;
import org.esfinge.virtuallab.domain.Matematica;
import org.esfinge.virtuallab.domain.Ponto;
import org.esfinge.virtuallab.utils.JsonUtils;

public class Main
{
	public static void main(String... args) throws Exception
	{
		/*
		AnnotationReader reader = new AnnotationReader();
		ClassContainer cc = reader.readingAnnotationsTo(Tarefa.class, ClassContainer.class);
		System.out.println("Classe: " + cc.name);
		for (MethodContainer mc : cc.metodos )
		{
			System.out.println("Metodo: " + mc.name);
			
			for ( ParameterMetadata pc : mc.parameters )
			{
				System.out.println("Parametro: " + pc.getParameterName());
				System.out.println("Has Label: " + pc.isAnnotatedWithLabel());
				System.out.println("Label: " + pc.getLabel());
				System.out.println("Reflection: " + pc.getParameter().getClass());
				System.out.println("Posicao: " + pc.getIndex());
			}
			
			System.out.println("------------------------");
		}
		*/
		
		// JSON DATA
		/*
		Endereco e = new Endereco();
		e.cep = "12000-000";
		e.rua = "Minha rua";
		e.numero = 500;
		Pessoa p = new Pessoa();
		p.nome = "Meu nome";
		p.endereco = e;
		p.idade = 35;
		
		Object[] primitivos = { "Uma string qualquer", true, -70, Long.MAX_VALUE, 20.2f, -30.3, null };
		Pessoa[] pessoas = {p};
		
		System.out.println(JsonUtils.fromObjectToJsonData(p));
		System.out.println(JsonUtils.fromObjectToJsonData(primitivos));
		System.out.println(JsonUtils.fromObjectToJsonData(pessoas));

		
		for (int i = 0; i < primitivos.length; i++)
			System.out.println(JsonUtils.fromObjectToJsonData(primitivos[i]));
		
		System.out.println(new JsonPrimitive(null));
		*/
		
		// JSON SCHEMA
		int[] aInt = new int[] {};
		Pessoa[] aPessoa = new Pessoa[] {};
		List<Integer> lInt = new ArrayList<>();
		lInt.add(20);
		
//		System.out.println(JsonUtils.getJsonSchema(Pessoa.class));
//		System.out.println(JsonUtils.getJsonSchema(int.class));
//		System.out.println(JsonUtils.getJsonSchema(double.class));
//		System.out.println(JsonUtils.getJsonSchema(boolean.class));
//		System.out.println(JsonUtils.getJsonSchema(java.util.Date.class));
//		System.out.println(JsonUtils.getJsonSchema(aInt.getClass()));
//		System.out.println(JsonUtils.getJsonSchema(aPessoa.getClass()));
//		System.out.println(JsonUtils.getJsonSchema(lInt.toArray(new Integer[]{}).getClass()));
//		*/

		System.out.println(JsonUtils.getJsonSchema(aInt.getClass()));
		System.out.println(JsonUtils.getJsonSchema(Pessoa.class));
		System.out.println(JsonUtils.getJsonSchema(aPessoa.getClass()));
//		System.out.println(JsonUtils.stringifySchema(aPessoa.getClass()));
		
		
//		MetadataHelper.getInstance().getClassMetadata(Service.class);
//		MetadataHelper.getInstance().getMethodMetadata(Tarefa.class.getDeclaredMethod("getPrioridade", String.class));
		
		TestUtils.createJar("matematica.jar", Matematica.class, Ponto.class);
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
		private String estado;
		public String getCidade()
		{
			return cidade;
		}
		public void setCidade(String cidade)
		{
			this.cidade = cidade;
		}
		public String getEstado()
		{
			return estado;
		}
		public void setEstado(String estado)
		{
			this.estado = estado;
		}
	}
}
