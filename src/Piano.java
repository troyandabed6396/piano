import javax.sound.midi.Receiver;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	// DO NOT MODIFY THESE CONSTANTS
	public static int START_PITCH = 48;
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
		
	private java.util.List<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;

	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	// DO NOT MODIFY THIS METHOD.
	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff; don't worry too much about it.
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}

	// TODO: implement this method. You should create and use several helper methods to do so.
	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */
	private void makeKeys () {
		for (int i = 0; i < NUM_OCTAVES; i++) {
			for (int j = 0; j < NUM_WHITE_KEYS_PER_OCTAVE; j++) {
				int x = i * NUM_WHITE_KEYS_PER_OCTAVE * WHITE_KEY_WIDTH + j * WHITE_KEY_WIDTH;

				Polygon polygon = new Polygon(new int[] { x, x + WHITE_KEY_WIDTH, x + WHITE_KEY_WIDTH, x },
											  new int[] { 0, 0, WHITE_KEY_HEIGHT, WHITE_KEY_HEIGHT },
											  4);

				int pitch = START_PITCH + (2 * i * (NUM_WHITE_KEYS_PER_OCTAVE - 1)) + 2 * j - (j > 2 ? 1 : 0);
				Key key = new Key(polygon, pitch, false, this);
				_keys.add(key);
			}

			for (int j = 0; j < NUM_WHITE_KEYS_PER_OCTAVE - 1; j++) {
				if (j == 2) {
					continue;
				}

				int x = i * NUM_WHITE_KEYS_PER_OCTAVE * WHITE_KEY_WIDTH + j * WHITE_KEY_WIDTH;

				Polygon polygon = new Polygon(new int[] { x + (WHITE_KEY_WIDTH - (BLACK_KEY_WIDTH / 2)), x + (WHITE_KEY_WIDTH - (BLACK_KEY_WIDTH / 2)), x + (WHITE_KEY_WIDTH - (BLACK_KEY_WIDTH / 2)) + (BLACK_KEY_WIDTH), x + (WHITE_KEY_WIDTH - (BLACK_KEY_WIDTH / 2)) + (BLACK_KEY_WIDTH) },
											  new int[] { 0, BLACK_KEY_HEIGHT, BLACK_KEY_HEIGHT, 0 },
									  4);
				int pitch = START_PITCH + (2 * i * (NUM_WHITE_KEYS_PER_OCTAVE - 1)) + 2 * j + (j > 2 ? 0 : 1);
				Key key = new Key(polygon, pitch, true, this);

				_keys.add(key);
			}
		}
	}

	// DO NOT MODIFY THIS METHOD.
	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
		}
	}
}
