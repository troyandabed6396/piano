import org.junit.jupiter.api.*;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains a set of unit tests for the Piano class.
 */
class PianoTester {
	private TestReceiver _receiver;
	private Piano _piano;
	private PianoMouseListener _mouseListener;

	private MouseEvent makeMouseEvent (int x, int y) {
		return new MouseEvent(_piano, 0, 0, 0, x, y, 0, false);
	}

	@BeforeEach
	void setup () {
		// A new TestReceiver will be created before running *each*
		// test. Hence, the "turn on" and "turn off" counts will be
		// reset to 0 before *each* test.
		_receiver = new TestReceiver();
		_piano = new Piano(_receiver);
		_mouseListener = _piano.getMouseListener();
	}

	@Test
	void testClickUpperLeftMostPixel () {
		// Pressing the mouse should cause the key to turn on.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	@Test
	void testDragWithinKey () {
		// Test that pressing and dragging the mouse *within* the same key
		// should cause the key to be turned on only once, not multiple times.
		// Use makeMouseEvent and TestReceiver.getKeyOnCount.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mouseDragged(makeMouseEvent(10, 10));
		assertEquals(1, _receiver.getKeyOnCount(Piano.START_PITCH));
	}

	@Test
	void testMouseReleaseTurnsOffKey() {
		// Test: Releasing a pressed key should turn it off.
		// Expected: The key with pitch START_PITCH should be "off" after release.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mouseReleased(makeMouseEvent(0, 0));
		assertFalse(_receiver.isKeyOn(Piano.START_PITCH));
	}

	@Test
	void testBlackKeyPressTurnsOnKey() {
		// Test: Pressing on a black key location should turn on that key.
		// Expected: The key with pitch START_PITCH + 1 should be "on".
		int blackKeyPitch = Piano.START_PITCH + 1;
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertTrue(_receiver.isKeyOn(blackKeyPitch));
	}

	@Test
	void testPressingAndDraggingAcrossDifferentKeys() {
		// Test: Dragging from one key to another should turn off the first key and turn on the second key.
		// Expected: The initial key should be "off," and the second key should be "on".
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mouseDragged(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertFalse(_receiver.isKeyOn(Piano.START_PITCH));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH + 1));
	}

	@Test
	void testPolygonBoundaryDetectionForWhiteKey() {
		// Test: Clicking on the boundary of a white key should still turn it on.
		// Expected: The key with pitch START_PITCH should be "on" when clicked near its edge.
		int pitch = Piano.START_PITCH;
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(pitch));
	}

	@Test
	void testPolygonBoundaryDetectionForBlackKey() {
		// Test: Clicking within the boundary of a black key should turn it on.
		// Expected: The key with pitch START_PITCH + 1 should be "on" when clicked within bounds.
		int blackKeyPitch = Piano.START_PITCH + 1;
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH - Piano.BLACK_KEY_WIDTH / 2, 0));
		assertTrue(_receiver.isKeyOn(blackKeyPitch));
	}

	@Test
	void testMultiplePressesDoNotIncrementKeyOnCount() {
		// Test: Pressing the same key multiple times should not increment the "on" count.
		// Expected: The key-on count for START_PITCH should be 1, even after multiple presses.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertEquals(1, _receiver.getKeyOnCount(Piano.START_PITCH));
	}
}