package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class DemoFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private int width = 1280;
	private int height = 800;

	public DemoFrame()
	{
		this.setPreferredSize(new Dimension (width,height));
		this.setLocation(500, 200);
		this.setBounds(500, 200, width, height);
		this.setContentPane(new AnnotationPanel(this));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
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
