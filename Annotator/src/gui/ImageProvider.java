package gui;

import java.awt.Image;

import javax.imageio.ImageIO;

public class ImageProvider
{

	private static Image background; 
	private static Image generateModel;
	private static Image annotationPanel;
	
	static {
		try{
			background = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/persistenzaDellaMemoria.jpg")).getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
			annotationPanel = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/affrescoDali.jpg")).getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
			generateModel = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/generateModel.png")).getScaledInstance(170, 70, Image.SCALE_SMOOTH);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Image getBackground()
	{
		return background;
	}
	
	public static Image getAnnotationPanel()
	{
		return annotationPanel;
	}


	public static Image getGenerateModel()
	{
		return generateModel;
	}
	
	
}
