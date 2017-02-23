package utility;

public class Pair<F, S>
{
	F firstElement;
	S secondElement;
	private String name;

	public Pair(F fe, S se)
	{
		this.firstElement = fe;
		this.secondElement = se;
		this.name = fe.toString()+"--"+se.toString();
	}

	private int rollingHash (int b, int m)
	{
		int i = 0;
		for (int j = 0;j<this.name.length();j++)
		{
			int ascii = (int)name.charAt(j);
			i+= ascii*(b^j) % m;
		}
		return i;
	}
	
	@Override
	public int hashCode()
	{
		return rollingHash(101, Integer.MAX_VALUE);
	}

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Pair))
		{
			return false;
		}

		Pair<?,?> p = (Pair<?,?>)obj;
		
		return this.name.equals(p.getName());
	}

	public F getFirstElement()
	{
		return firstElement;
	}

	public String values()
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
