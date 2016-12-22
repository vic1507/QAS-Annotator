package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataPanel extends JPanel
{
	
	private static final long serialVersionUID = 1L;

	private MainFrame mf;

	public void addText()
	{
		JTextField jtf = new JTextField("prova");
		jtf.setBounds(new Rectangle(200, 200,300, 100));
		this.add(jtf);
	}
	
	public DataPanel (MainFrame mf)
	{
		this.mf = mf;
		this.setSize(new Dimension (800,600));
		this.setBackground(Color.black);
		this.setLayout(null);
		
		this.setVisible(true);
	}

	public MainFrame getMainframe()
	{
		return mf;
	}

	
}
