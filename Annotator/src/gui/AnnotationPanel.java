package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import core.ApplicationManager;
import utility.Pair;

public class AnnotationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private DemoFrame mf;
	private JTextArea dialogWindow;

	public AnnotationPanel(DemoFrame mf, String type)
	{
		this.mf = mf;
		this.setPreferredSize(new Dimension(1280, 800));
		this.setLayout(null);

		dialogWindow = new JTextArea();
		dialogWindow.setFont(new Font("Italic", Font.ITALIC, 25));

		dialogWindow.setForeground(new Color(70, 130, 180));
		dialogWindow.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		dialogWindow.setBounds(100, 100, 500, 300);
		dialogWindow.setLineWrap(true);
		dialogWindow.setBackground(new Color(255, 223, 173));
		JButton back = new JButton("back");
		back.setBounds(1050, 560, 150, 100);
		back.setIcon(new ImageIcon(ImageProvider.getBack()));
		back.setRolloverEnabled(true);
		back.setRolloverIcon(new ImageIcon(ImageProvider.getBackMod()));
		back.addActionListener(e -> mf.goToStart());
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		JButton clear = new JButton("clear");
		clear.setBounds(100, 420, 150, 70);
		clear.addActionListener(e -> clear());
		clear.setBorderPainted(false);
		clear.setContentAreaFilled(false);
		clear.setIcon(new ImageIcon(ImageProvider.getClearButton()));
		clear.setRolloverEnabled(true);
		clear.setRolloverIcon(new ImageIcon(ImageProvider.getClearButtonMod()));
		JButton submit = new JButton("submit");
		submit.setBorderPainted(false);
		submit.setContentAreaFilled(false);
		submit.setIcon(new ImageIcon(ImageProvider.getSubmitButton()));
		submit.setBounds(300, 420, 150, 70);
		submit.addActionListener(e -> submit(dialogWindow.getText(), type));
		submit.setRolloverEnabled(true);
		submit.setRolloverIcon(new ImageIcon(ImageProvider.getSubmitButtonMod()));
		JScrollPane scroll = new JScrollPane(dialogWindow);
		scroll.setBounds(100, 100, 500, 300);
		this.add(scroll);
		this.add(clear);
		this.add(submit);
		this.add(back);
		this.setVisible(true);
	}

	private void clear()
	{
		dialogWindow.setText("");
		dialogWindow.removeAll();
	}

	@SuppressWarnings("unchecked")
	private void submit(String s, String type)
	{
		DefaultHighlighter.DefaultHighlightPainter opereHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
		DefaultHighlighter.DefaultHighlightPainter artistHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		HashMap<Pair<Integer, Integer>, String> cr = new HashMap<Pair<Integer, Integer>, String>();
		if (type.equals("ME"))
			cr = (HashMap<Pair<Integer, Integer>, String>) ApplicationManager.getInstance().execute3(s);
		else if (type.equals("PM"))
		{
			HashMap<String, List<Pair<Integer, Integer>>> res = (HashMap<String, List<Pair<Integer, Integer>>>) ApplicationManager.getInstance().executePatternMatching(s);
			for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : res.entrySet())
			{
				try
				{
					if (ApplicationManager.getInstance().getMappedTypes().get(entry.getKey()).equals("opere"))
					{
						for (Pair<Integer, Integer> pair : entry.getValue())
							dialogWindow.getHighlighter().addHighlight(pair.getFirstElement(), pair.getSecondElement() +1, opereHighlightPainter);
					} else
					{
						for (Pair<Integer, Integer> pair : entry.getValue())
							dialogWindow.getHighlighter().addHighlight(pair.getFirstElement(), pair.getSecondElement()+1, artistHighlightPainter);
					}
				} catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
		}
		for (Map.Entry<Pair<Integer, Integer>, String> p : cr.entrySet())
		{
			try
			{
				if (p.getValue().equals("opera"))
					dialogWindow.getHighlighter().addHighlight(p.getKey().getFirstElement(), p.getKey().getSecondElement() + 1, opereHighlightPainter);
				else
					dialogWindow.getHighlighter().addHighlight(p.getKey().getFirstElement(), p.getKey().getSecondElement() + 1, artistHighlightPainter);
			} catch (BadLocationException e)
			{
				JOptionPane.showMessageDialog(null, "Errore durante la segnalazione dei risultati");
			}
		}

	}

	public DemoFrame getMf()
	{
		return mf;
	}

	public JTextArea getDialogWindow()
	{
		return dialogWindow;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(ImageProvider.getAnnBackground(), 0, 0, 1280, 800, null);
		g.drawImage(ImageProvider.getLegend(), 680, 100, 250, 130, null);
	}

}
