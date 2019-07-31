import java.util.List;

import org.esfinge.virtuallab.domain.TarefaInvalida;
import org.esfinge.virtuallab.metadata.MetadataHelper;
import org.esfinge.virtuallab.metadata.ParameterMetadata;
import org.esfinge.virtuallab.metadata.processor.ProcessParameters;

import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ProcessMethods;
import net.sf.esfinge.metadata.container.ContainerTarget;

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
		
		MetadataHelper.getInstance().getClassMetadata(TarefaInvalida.class);
//		MetadataHelper.getInstance().getMethodMetadata(Tarefa.class.getDeclaredMethod("getPrioridade", String.class));
	}
	
	@ContainerFor(ContainerTarget.TYPE)
	public static class ClassContainer
	{
		@ElementName
		String name;
		
		@ProcessMethods
		List<MethodContainer> metodos;
		
		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public List<MethodContainer> getMetodos()
		{
			return metodos;
		}

		public void setMetodos(List<MethodContainer> metodos)
		{
			this.metodos = metodos;
		}
	}
	
	@ContainerFor(ContainerTarget.METHODS)
	public static class MethodContainer
	{
		@ElementName
		String name;
		
		@ProcessParameters
		List<ParameterMetadata> parameters;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public List<ParameterMetadata> getParameters()
		{
			return parameters;
		}

		public void setParameters(List<ParameterMetadata> parameters)
		{
			this.parameters = parameters;
		}
	}
}
