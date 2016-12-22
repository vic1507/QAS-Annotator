package core;

import java.util.HashMap;
import java.util.List;

public class RightMatchAnnotator extends AnnotationStrategy
{
	
	List<String>data;
	HashMap<String,String> mappedElements;

	public RightMatchAnnotator(List<String> data, HashMap<String, String> mappedElements)
	{
		super();
		this.data = data;
		this.mappedElements = mappedElements;
	}

	@Override
	public void annotatorStrategy(Object o)
	{
		Interpreter i = (Interpreter) o;
		i.execute(data, mappedElements);
	}

}
