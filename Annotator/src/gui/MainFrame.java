package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.ApplicationManager;

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
			String[] possibleValues = { "OpenNlp", "RightMatch" };
			String annotator = JOptionPane.showInputDialog(null, "Choose an annotator", "Annotator", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]).toString();
			ApplicationManager am = new ApplicationManager();
			am.execute(annotator);
		} catch (NullPointerException e)
		{
			System.exit(0);
		}

	}
}
