package utility;

public class FMeasure
{
	public static double calculatePrecision(double tp, double fp)
	{
		return tp / (tp + fp);
	}

	public static double calculateRecall(double tp, double fn)
	{
		return tp / (tp + fn);
	}

	public static double calculateF1Score(double precision, double recall, double metric)
	{
		return 2 * ((precision * recall) / (precision + recall));
	}
}
