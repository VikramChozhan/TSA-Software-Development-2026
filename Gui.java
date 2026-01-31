package Speech;

import java.awt.*;
import javax.swing.*;

public class Gui {
	private JFrame frame;
	private JButton button1;
	private JButton button2;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Gui gui1 = new Gui();
				gui1.Water();
			}
		});
	}

	public void Water() {
		frame = new JFrame("Awarely");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(2000, 300);
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(160,160,160));
		button1 = new JButton("App Description");
		button1.setPreferredSize(new Dimension(200, 200));
		button2 = new JButton("Live Decibel");
		button2.setPreferredSize(new Dimension(200, 200));
		JLabel title = new JLabel("Awarely Assistant");
		title.setFont(new Font("Segoe UI", Font.BOLD, 30));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(title, BorderLayout.NORTH);
		JLabel direct = new JLabel("                        Type 'start123' and Enter to Start Audible Keyboard");
		direct.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		direct.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(direct, BorderLayout.WEST);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(Box.createHorizontalGlue());
		panel.add(button1);
		panel.add(Box.createRigidArea(new Dimension(150, 0)));
		panel.add(button2);
		panel.add(Box.createHorizontalGlue());
		panel.setBackground(new Color(160,160,160));
		frame.add(panel, BorderLayout.CENTER);
		Audio audio = new Audio();
		SoundAlert alert = new SoundAlert();
		button2.addActionListener(e -> {
			new Thread(() -> {
				try {
					alert.startListening();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();
		});
		button1.addActionListener(e -> {
		    JFrame frame1 = new JFrame("App Description");
		    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame1.setSize(600, 300);

		    String text1 = "Awarely is a program that improves accessibility for hearing or vision disabilities, straight from your device. "
		    		+ "The Audible keyboard allows you to hear every word you type from your speakers after each space key, period key, or enter key. "
		    		+ "The live decibel provides an interactive sound meter with a slider you can adjust for alerts on specific volumes.";

		    JTextArea t1 = new JTextArea(text1);
		    t1.setLineWrap(true);
		    t1.setWrapStyleWord(true);
		    t1.setEditable(false);
		    t1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		    t1.setFont(new Font("Segoe UI", Font.ITALIC, 20));

		    frame1.add(t1, BorderLayout.CENTER);
		    frame1.setVisible(true);
		});

		frame.setVisible(true);
		alert.getPanel().setBackground(new Color(160,160,160));
		frame.add(alert.getPanel(), BorderLayout.SOUTH);

	}
}
