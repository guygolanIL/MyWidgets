package abstracts;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

/**
 * Abstract type for concrete maze widgets to inherit. Every maze displaying
 * widget inherits this class.
 * 
 * @author Guy Golan && Amit Sandak
 *
 */
public abstract class MazeDisplayer extends Canvas {

	protected Maze3d mazeData; // current displayed maze
	protected Position charPosition; // current character position
	protected ArrayList<Position> solution; // available solution

	/**
	 * Ctor.
	 * 
	 * @param parent
	 *            - widget.
	 * @param style
	 *            - SWT style.
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		charPosition = new Position(1, 1, 1);
		solution = new ArrayList<Position>();
	}

	/**
	 * Changes the mazeData of the current displayed maze and redraws the
	 * widget.
	 * 
	 * @param mazeData
	 *            - new Maze3d maze data to display.
	 */
	public void setMazeData(Maze3d mazeData) {
		this.mazeData = mazeData;

		Display.getDefault().syncExec(new Runnable() {
			public void run() { // redraws the MazeDisplyer.
				redraw();
			}
		});
	}

	/**
	 * Recieving new updated character Position, redraws the widget, and
	 * removing that Position from the available Solution<Position>.
	 * 
	 * @param position
	 *            - new Position to update the current displayed character
	 *            position.
	 */
	public void setCharPosition(Position position) {
		this.charPosition = position;

		// if the user have reached a position in the solution it will remove that position from the solution.
		if ((solution != null) && (solution.contains(position))) 
			solution.remove(position);

		Display.getDefault().syncExec(new Runnable() {
			public void run() { // redraws the MazeDisplyer.
				redraw();
			}
		});
	}

	/**
	 * If a request to present a solution was received will redraw the widget
	 * with the updated solution.
	 * 
	 * @param solution
	 *            - ArrayList of Position.
	 */
	public void setSolution(ArrayList<Position> solution) {
		this.solution = solution;

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				redraw(); // redraws the widget with updated solution.
			}
		});
	}

}