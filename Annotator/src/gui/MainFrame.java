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
			String[] pV = { "Annotator", "ExtendsCorpora", "Test", "TrainModel", "Generate Test Case", "Cross" };
			ApplicationManager.getInstance().execuete2(JOptionPane.showInputDialog(null, "Choose an operation", "Operation", JOptionPane.INFORMATION_MESSAGE, null, pV, pV[0]).toString());
		} catch (NullPointerException e)
		{
			System.exit(0);
		}

	}
}
