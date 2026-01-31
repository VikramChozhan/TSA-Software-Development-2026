package Speech;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;

public class SoundAlert {
	private JPanel panel;
	private JLabel volumeLabel;
	private JSlider thresholdSlider;
	private volatile boolean running = false;

	public SoundAlert() {

		panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		volumeLabel = new JLabel("Volume: 0 dB", SwingConstants.CENTER);
		volumeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

		thresholdSlider = new JSlider(0, 100, 60);
		thresholdSlider.setPaintLabels(true);
		thresholdSlider.setMajorTickSpacing(20);
		thresholdSlider.setMinorTickSpacing(5);

		JLabel sliderTitle = new JLabel("Decibel Alert", SwingConstants.CENTER);

		panel.add(volumeLabel, BorderLayout.NORTH);
		panel.add(sliderTitle, BorderLayout.CENTER);
		panel.add(thresholdSlider, BorderLayout.SOUTH);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void startListening() throws Exception {
		if (running)
			return;
		running = true;

		AudioFormat format = new AudioFormat(44100, 16, 1, true, true);

		TargetDataLine mic = AudioSystem.getTargetDataLine(format);
		mic.open(format);
		mic.start();

		byte[] buffer = new byte[2048];

		while (running) {

			mic.read(buffer, 0, buffer.length);

			double deci = calcDecibel(buffer);
			double displayDB = deci + 90;

			SwingUtilities.invokeLater(() -> {

				volumeLabel.setText(String.format("Volume: %.1f dB", displayDB));

				if (displayDB > thresholdSlider.getValue()) {
					panel.setBackground(Color.RED);
				} else {
					panel.setBackground(Color.WHITE);
				}
			});
		}

		mic.stop();
		mic.close();
	}

	public double calcDecibel(byte[] buffer) {

		long sum = 0;
		int count = buffer.length / 2;

		for (int i = 0; i < buffer.length; i += 2) {
			int hi = buffer[i];
			int lo = buffer[i + 1] & 0xFF;
			int sample = (hi << 8) | lo;
			sum += (long) sample * sample;
		}

		double rms = Math.sqrt(sum / (double) count);

		return 20.0 * Math.log10(rms / 32768.0 + 1e-10);
	}
}
