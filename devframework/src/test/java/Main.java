import java.util.ArrayList;
import java.util.List;

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
	}
	
	public static class Pessoa
	{
		private String nome;
		private String endereco;
		private int idade;
	}
}
