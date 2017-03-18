package gui;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageProvider
{
	private static Image background;
	private static Image meButton;
	private static Image pmButton;
	private static Image generateModel;
	private static Image annotationPanel;
	private static Image maxEnt;
	private static Image maxEntMod;
	private static Image back;
	private static Image backMod;
	private static Image guernica;
	private static Image urloLego;
	private static Image clearButton;
	private static Image submitButton;
	private static Image clearButtonMod;
	private static Image submitButtonMod;
	private static Image legend;
	private static Image annBackground;
	private static Image pattMatch;
	private static Image pattMatchMod;

	static
	{
		try
		{
			pmButton = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/pmbutton.png")).getScaledInstance(160, 80, Image.SCALE_SMOOTH);
			meButton = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/mebutton.png")).getScaledInstance(160, 80, Image.SCALE_SMOOTH);
			annBackground = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/annotatorBackground.png")).getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
			legend = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/legend.png")).getScaledInstance(250, 130, Image.SCALE_SMOOTH);
			submitButtonMod = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/submitButtonMod.png")).getScaledInstance(160, 80, Image.SCALE_SMOOTH);
			clearButtonMod = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/clearButtonMod.png")).getScaledInstance(160, 80, Image.SCALE_SMOOTH);
			submitButton = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/submitButton.png")).getScaledInstance(160, 80, Image.SCALE_SMOOTH);
			clearButton = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/clearButton.png")).getScaledInstance(160, 80, Image.SCALE_SMOOTH);
			urloLego = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/urloLego.jpg")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			guernica = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/Guernica.jpg")).getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
			back = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/backLeft.png")).getScaledInstance(150, 100, Image.SCALE_SMOOTH);
			backMod = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/backLeftMod.png")).getScaledInstance(150, 100, Image.SCALE_SMOOTH);
			background = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/ultimaCenaLego.png")).getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
			annotationPanel = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/affrescoDali.jpg")).getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
			generateModel = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/generateModel.png")).getScaledInstance(170, 70, Image.SCALE_SMOOTH);
			maxEnt = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/MaxEnt.png")).getScaledInstance(470, 180, Image.SCALE_SMOOTH);
			maxEntMod = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/MaxEntMod.png")).getScaledInstance(470, 180, Image.SCALE_SMOOTH);
			pattMatch = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/pattMatch.png")).getScaledInstance(470, 180, Image.SCALE_SMOOTH);
			pattMatchMod = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("img/pattMatchMod.png")).getScaledInstance(470, 180, Image.SCALE_SMOOTH);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Errore nel caricamente delle immagini");
		}
	}

	public static Image getUrloLego()
	{
		return urloLego;
	}
	
	

	public static Image getMeButton()
	{
		return meButton;
	}


	public static Image getPmButton()
	{
		return pmButton;
	}



	public static Image getBack()
	{
		return back;
	}

	public static Image getBackMod()
	{
		return backMod;
	}

	public static Image getMaxEntMod()
	{
		return maxEntMod;
	}

	public static Image getMaxEnt()
	{
		return maxEnt;
	}

	public static Image getBackground()
	{
		return background;
	}

	public static Image getAnnotationPanel()
	{
		return annotationPanel;
	}

	public static Image getClearButton()
	{
		return clearButton;
	}

	public static Image getSubmitButton()
	{
		return submitButton;
	}

	public static Image getGenerateModel()
	{
		return generateModel;
	}

	
	public static Image getClearButtonMod()
	{
		return clearButtonMod;
	}

	public static Image getSubmitButtonMod()
	{
		return submitButtonMod;
	}

	public static Image getGuernica()
	{
		return guernica;
	}

	public static Image getLegend()
	{
		return legend;
	}

	public static Image getAnnBackground()
	{
		return annBackground;
	}

	public static Image getPattMatch()
	{
		return pattMatch;
	}

	public static Image getPattMatchMod()
	{
		return pattMatchMod;
	}
	

}
