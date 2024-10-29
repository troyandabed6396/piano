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
	public void mouseDragged (MouseEvent e) {
		boolean blackKeyDragged = false;

		for (Key key : _keys) {
			if (key.isBlack() && key.getPolygon().contains(e.getX(), e.getY())) {
				if (_lastPlayedKey != null && _lastPlayedKey != key) {
					_lastPlayedKey.play(false);
				}
				key.play(true);
				_lastPlayedKey = key;
				blackKeyDragged = true;
				break;
			}
		}

		if (!blackKeyDragged) {
			for (Key key : _keys) {
				if (!key.isBlack() && key.getPolygon().contains(e.getX(), e.getY())) {
					if (_lastPlayedKey != null && _lastPlayedKey != key) {
						_lastPlayedKey.play(false);
					}
					key.play(true);
					_lastPlayedKey = key;
					break;
				}
			}
		}
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mousePressed (MouseEvent e) {
		// To test whether a certain key received the mouse event, you could write something like:
		//	if (key.getPolygon().contains(e.getX(), e.getY())) {
		// To turn a key "on", you could then write:
		//      key.play(true);  // Note that the key should eventually be turned off!

		boolean blackKeyPressed = false;
		for (Key key : _keys) {
			if (key.getPolygon().contains(e.getX(), e.getY())) {
				if (key.isBlack()) {
					key.play(true);
					blackKeyPressed = true;
				}
			}
		}

		if (!blackKeyPressed) {
			for (Key key : _keys) {
				if (key.getPolygon().contains(e.getX(), e.getY())) {
					key.play(true);
				}
			}
		}
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		for (Key key : _keys) {
			if (key.getPolygon().contains(e.getX(), e.getY())) {
				key.play(false);
			}
		}
		if (_lastPlayedKey != null) {
			_lastPlayedKey.play(false);
			_lastPlayedKey = null;
		}
	}
}
