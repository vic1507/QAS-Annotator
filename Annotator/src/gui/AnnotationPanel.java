package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import core.ApplicationManager;
import utility.Pair;

public class AnnotationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private DemoFrame mf;
	private JTextArea jta;

	public AnnotationPanel(DemoFrame mf)
	{
		this.mf = mf;
		this.setPreferredSize(new Dimension(1280, 800));
		this.setLayout(null);
		jta = new JTextArea();
		jta.setFont(new Font("Italic-20", Font.ITALIC, 20));
		jta.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		jta.setBounds(100, 100, 600, 300);
		jta.setLineWrap(true);
		JButton clear = new JButton("clear");
		clear.setBounds(100, 420, 150, 70);
		clear.addActionListener(e -> clear());
		JButton submit = new JButton("submit");
		submit.setBounds(300, 420, 150, 70);
		submit.addActionListener(e ->
		{
			submit(jta.getText());

		});
		this.add(jta);
		this.add(clear);
		this.add(submit);
		this.setVisible(true);
	}

	private void clear()
	{
		jta.setText("");
	}

	@SuppressWarnings("unchecked")
	private void submit(String s)
	{
		DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
		List<Pair<Integer, Integer>> cr = (List<Pair<Integer, Integer>>) ApplicationManager.getInstance().execute3(s);
		for (Pair<Integer, Integer> p : cr)
		{
			try
			{
				jta.getHighlighter().addHighlight(p.getFirstElement(),p.getSecondElement()+1,highlightPainter);
			} catch (BadLocationException e)
			{
				e.printStackTrace();
			}
		}


	}

	

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(ImageProvider.getAnnotationPanel(), 0, 0, 1280, 800, null);
	}

	public DemoFrame getMf()
	{
		return mf;
	}

	public JTextArea getJta()
	{
		return jta;
	}

}
