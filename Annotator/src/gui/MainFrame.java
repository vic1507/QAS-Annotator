package gui;

import dataFromWiki.DataFromSite;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.ApplicationManager;
import core.OpenNlpAnnotator;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public MainFrame()
	{
		this.setSize(new Dimension(800, 600));
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		try
		{
			String[] pV = { "Annotator", "ExtendsCorpora", "Test", "TrainModel" };
			String gg = JOptionPane.showInputDialog(null, "Choose an operation", "Operation", JOptionPane.INFORMATION_MESSAGE, null, pV, pV[0]).toString();
			if (gg.equals("Annotator"))
			{
				String[] possibleValues = { "OpenNlp", "RightMatch" };
				String annotator = JOptionPane.showInputDialog(null, "Choose an annotator", "Annotator", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]).toString();
				ApplicationManager am = new ApplicationManager();
				am.execute(annotator);
			} else if (gg.equals("ExtendsCorpora"))
			{
				@SuppressWarnings("unused")
				DataFromSite p = new DataFromSite();
			}
			else if (gg.equals("Test"))
			{
				//test
			}
			else if (gg.equals("TrainModel"))
			{
				OpenNlpAnnotator.trainModel("src/models/it-ner-art.bin", "src/models/model.txt");
			}
		} catch (NullPointerException e)
		{
			System.exit(0);
		}

	}
}
