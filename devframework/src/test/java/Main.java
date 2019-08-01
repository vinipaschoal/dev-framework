import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.annotations.JsonReturn;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;
import org.esfinge.virtuallab.annotations.TableReturn;
import org.esfinge.virtuallab.metadata.MetadataHelper;

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
		MetadataHelper.getInstance().getClassMetadata(Service.class);
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
		@JsonReturn
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
