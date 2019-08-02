import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.annotations.CustomReturn;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.esfinge.virtuallab.annotations.TableReturn;
import org.esfinge.virtuallab.utils.JsonPrimitive;
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
		
		Pessoa p = new Pessoa();
		p.nome = "Meu nome";
		p.endereco = "Meu endereco";
		p.idade = 35;
		
		Object[] primitivos = { "Uma string qualquer", true, -70, Long.MAX_VALUE, 20.2f, -30.3, null };
		
		System.out.println(JsonUtils.fromObjectToJsonData(p));
		System.out.println(JsonUtils.fromObjectToJsonData(primitivos));
		
		for (int i = 0; i < primitivos.length; i++)
			System.out.println(JsonUtils.fromObjectToJsonData(primitivos[i]));
		
		System.out.println(new JsonPrimitive(null));
		
		
		
//		MetadataHelper.getInstance().getClassMetadata(Service.class);
//		MetadataHelper.getInstance().getMethodMetadata(Tarefa.class.getDeclaredMethod("getPrioridade", String.class));
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
		private String endereco;
		private int idade;
		public String getNome()
		{
			return nome;
		}
		public void setNome(String nome)
		{
			this.nome = nome;
		}
		public String getEndereco()
		{
			return endereco;
		}
		public void setEndereco(String endereco)
		{
			this.endereco = endereco;
		}
		public int getIdade()
		{
			return idade;
		}
		public void setIdade(int idade)
		{
			this.idade = idade;
		}
	}
}
