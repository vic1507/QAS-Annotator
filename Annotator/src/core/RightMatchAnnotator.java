package core;

import java.util.HashMap;
import java.util.List;

import utility.Pair;

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
	public HashMap<String, List<Pair<Integer, Integer>>> annotatorStrategy(Object o, String question)
	{
		Interpreter i = (Interpreter) o;
		return i.execute(data, mappedElements, question);
	}
	
}
