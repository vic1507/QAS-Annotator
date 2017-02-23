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
		JButton pattern = new JButton ("PatternMatching");
		pattern.setBounds(650, 130, 470, 180);
		pattern.setIcon(new ImageIcon(ImageProvider.getPattMatch()));
		pattern.setRolloverEnabled(true);
		pattern.setRolloverIcon(new ImageIcon (ImageProvider.getPattMatchMod()));
		pattern.addActionListener(e -> mainFrame.goToPattMatch());
		pattern.setBorderPainted(false);
		pattern.setContentAreaFilled(false);
		JButton model = new JButton("MaxEntAnnotator");
		model.setIcon(new ImageIcon(ImageProvider.getMaxEnt()));
		model.setContentAreaFilled(false);
		model.setBorderPainted(false);
		model.setBounds(100, 100, 470, 180);
		model.setRolloverIcon(new ImageIcon(ImageProvider.getMaxEntMod()));
		model.setRolloverEnabled(true);
		model.addActionListener(e -> mainFrame.goToMaxEnt());
		this.add(model);
		this.add(pattern);
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
