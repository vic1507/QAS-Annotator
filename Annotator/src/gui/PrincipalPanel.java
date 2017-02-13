package gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PrincipalPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private DemoFrame mainFrame;
	
	public PrincipalPanel(DemoFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		this.setSize(new Dimension (mainFrame.getMyWidth(),mainFrame.getMyHeight()));
		this.setLayout(null);
		JButton model = new JButton("MaxEntAnnotator");
		model.setIcon(new ImageIcon(ImageProvider.getGenerateModel()));
		model.setContentAreaFilled(false);
		model.setBorderPainted(false);
		model.setBounds(100, 100, 170, 70);
		this.add(model);
		this.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(ImageProvider.getBackground(), 0, 0, mainFrame.getMyWidth(), mainFrame.getMyHeight(), null);
	}

	public DemoFrame getMainFrame()
	{
		return mainFrame;
	}
	
	 
}
