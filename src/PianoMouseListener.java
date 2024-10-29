import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Handles mouse press, release, and drag events on the Piano.
 */
public class PianoMouseListener extends MouseAdapter {
	// You are free to add more instance variables if you wish.
	private List<Key> _keys;
	private Key _lastPlayedKey = null;

	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged(MouseEvent e) {
		Key currentKey = getKeyAtPosition(e.getX(), e.getY());

		if (currentKey != null && currentKey != _lastPlayedKey) {
			if (_lastPlayedKey != null) {
				_lastPlayedKey.play(false);
			}
			if (!currentKey.isPlaying()) {
				currentKey.play(true);
			}
			_lastPlayedKey = currentKey;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Key currentKey = getKeyAtPosition(e.getX(), e.getY());

		if (currentKey != null) {
			if (_lastPlayedKey != null && _lastPlayedKey != currentKey) {
				_lastPlayedKey.play(false);
			}
			if (!currentKey.isPlaying()) {
				currentKey.play(true);
			}
			_lastPlayedKey = currentKey;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (_lastPlayedKey != null) {
			_lastPlayedKey.play(false);
			_lastPlayedKey = null;
		}
	}

	/**
	 * Helper method to find the key at the given x and y coordinates.
	 * Black keys are prioritized over white keys if both are at the same position.
	 */
	private Key getKeyAtPosition(int x, int y) {
		for (Key key : _keys) {
			if (key.isBlack() && key.getPolygon().contains(x, y)) {
				return key;
			}
		}

		for (Key key : _keys) {
			if (!key.isBlack() && key.getPolygon().contains(x, y)) {
				return key;
			}
		}
		return null;
	}
}