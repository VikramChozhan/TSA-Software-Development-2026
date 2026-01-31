package Speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Audio {
	private Voice voice;
	private StringBuilder word = new StringBuilder();

	public Audio() {
		setupVoice();
		setupGlobalHook();
	}

	public void setupVoice() {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		VoiceManager vm = VoiceManager.getInstance();
		voice = vm.getVoice("kevin16");
	}

	public void setupGlobalHook() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			ex.printStackTrace();
			return;
		}
		GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
			public void nativeKeyPressed(NativeKeyEvent e) {
				int key = e.getKeyCode();
				String keyText = NativeKeyEvent.getKeyText(key);

				if (key == NativeKeyEvent.VC_SPACE || key == NativeKeyEvent.VC_PERIOD || key == NativeKeyEvent.VC_ENTER) {
					if (word.length() > 0) {
						speak(word.toString());
						word.setLength(0);
					}
				} else if (keyText.length() == 1 && Character.isLetterOrDigit(keyText.charAt(0))) {
					word.append(keyText.toLowerCase());
				}
			}

			public void nativeKeyReleased(NativeKeyEvent e) {
			}

			public void nativeKeyTyped(NativeKeyEvent e) {
			}
		});
	}

	public void speak(String text) {
		String the = text.toLowerCase();
		if (the.equals("the")) {
			text = "thee";
		}
		String to = text.toLowerCase();
		if (to.equals("to")) {
			text = "too";
		}
		String of = text.toLowerCase();
		if (of.equals("of") || of.equals("off")) {
			text = "offf";
		}
		String al = text.toLowerCase();
		if (al.equals("all")) {
			text = "alll";
		}
		String th = text.toLowerCase();
		if (th.equals("this")) {
			text = "tthis";
		}
		String in = text.toLowerCase();
		if (in.equals("in")) {
			text = "inn";
		}
		String on = text.toLowerCase();
		if (on.equals("on")) {
			text = "onn";
		}
		String ea = text.toLowerCase();
		if (ea.equals("each")) {
			text = "eeach";
		}
		String start = text.toLowerCase();
		if (start.equals("start123")) {
			text = "audible keyboard onn";
			voice.allocate();
		}
		String stop = text.toLowerCase();
		if (stop.equals("stop123")) {
			voice.deallocate();
		}
		voice.speak(text);
	}
}
