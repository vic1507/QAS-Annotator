package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DataPanel extends JPanel
{
	
	private static final long serialVersionUID = 1L;

	private MainFrame mf;

	public DataPanel (MainFrame mf)
	{
		this.mf = mf;
		this.setSize(new Dimension (800,600));
		this.setBackground(Color.black);
		this.setLayout(null);
		JButton model = new JButton("Generate a model");
		this.add(model);
		this.setVisible(true);
	}

	public MainFrame getMainframe()
	{
		return mf;
	}

	
}
