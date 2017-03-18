	package gui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DemoFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private int width = 1280;
	private int height = 800;

	public DemoFrame()
	{
		this.setPreferredSize(new Dimension (width,height));
		this.setTitle("Annotatore per Question Answering System");
		this.setIconImage(ImageProvider.getUrloLego());
		this.setLocation(500, 200);
		this.setBounds(500, 200, width, height);
		this.setContentPane(new AnnotationPanel(this));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void goToMaxEnt()
	{
		SwingUtilities.invokeLater(() -> goToThePanel(new AnnotationPanel(this)));
	}
	
	public void goToPattMatch()
	{
		SwingUtilities.invokeLater(() -> goToThePanel(new AnnotationPanel(this)));
	}
	
	public void goToStart()
	{
		SwingUtilities.invokeLater(()->goToThePanel(new PrincipalPanel(this)));
	}
	
	private void goToThePanel(JComponent p)
	{
		setContentPane(p);
		this.revalidate();
		p.requestFocus();
		p.updateUI();
	}

	public static void main (String [] args) 
	{
		new DemoFrame();
	}

	int getMyWidth()
	{
		return width;
	}

	void setWidth(int width)
	{
		this.width = width;
	}

	int getMyHeight()
	{
		return height;
	}

	void setHeight(int height)
	{
		this.height = height;
	}
	
	
}
