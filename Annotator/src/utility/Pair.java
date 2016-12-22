package utility;

public class Pair<F,S>
{
	F firstElement;
	S secondElement;
	
	public Pair (F fe, S se)
	{
		this.firstElement = fe;
		this.secondElement = se;
	}

	public F getFirstElement()
	{
		return firstElement;
	}

	public String values ()
	{
		return this.getFirstElement() + " " + this.getSecondElement();
	}

	public void setFirstElement(F firstElement)
	{
		this.firstElement = firstElement;
	}

	public S getSecondElement()
	{
		return secondElement;
	}

	public void setSecondElement(S secondElement)
	{
		this.secondElement = secondElement;
	}
	
	
}
