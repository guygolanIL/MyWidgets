package mainMaze;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import abstracts.MazeDisplayer;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

/**
 * Maze3D is a type of MazeDisplayer and represents a display of 2 small Maze2D widgets 
 * and the ability to change between which plain {x,y,z} we can view.
 * @author Guy Golan && Amit Sandak.
 *
 */
public class Maze3D extends MazeDisplayer {

	
	protected Maze2D leftDisplay;			//left maze display
	protected Maze2D rightDisplay;			//right maze display

	/**
	 * Regular SWT CTOR that initializing MazeDisplayer which, in its turn , initializing Canvas.
	 * @param parent - parenting Widget.
	 * @param style - SWT style.
	 */
	public Maze3D(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, true));				//setting layout for 2 Maze2D displayers.

		Label title = new Label(this, SWT.TITLE);
		title.setText("3D maze display by cross sections");
		title.setLayoutData(new GridData(SWT.TOP, SWT.TOP, false, false, 2, 1));
		
		CrossCombo leftDisplayOptions = new CrossCombo(this, SWT.DROP_DOWN, leftDisplay);		//a box of options to control the displayed plain in the Maze2D widget.
		leftDisplayOptions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		CrossCombo rightDisplayOptions = new CrossCombo(this, SWT.DROP_DOWN, rightDisplay);
		rightDisplayOptions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));


		leftDisplay = new Maze2D(this, SWT.NULL);					//creating left Maze2D display.
		leftDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		leftDisplay.setCrossSection('x');			//default plain display.
		
		
		rightDisplay = new Maze2D(this, SWT.NULL);					//creating right Maze2D display.
		rightDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		rightDisplay.setCrossSection('y');			//default plain display.
		
		
		leftDisplayOptions.setSlave(leftDisplay);	//setting which crossCombo will control which maze2D.
		rightDisplayOptions.setSlave(rightDisplay);
		
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

			}

		});

	}

	@Override
	public void setMazeData(Maze3d maze) {
		this.mazeData = maze;
		leftDisplay.setMazeData(maze);				//updates the MazeData of each individual Maze2D widget.
		rightDisplay.setMazeData(maze);
		widgetsRefresh();							//refreshing the widgets.

	}

	@Override
	public void addKeyListener(KeyListener listener) {
		super.addKeyListener(listener);
		leftDisplay.addKeyListener(listener);		//updating the keyListener for every sub- Widget.
		rightDisplay.addKeyListener(listener);
	}

	@Override
	public void setCharPosition(Position position) {
		this.charPosition = position;
		leftDisplay.setCharPosition(position);		//updates the charPosition and refreshing the widgets.
		rightDisplay.setCharPosition(position);
		widgetsRefresh();

	}

	@Override
	public void setSolution(ArrayList<Position> solution) {
		this.solution = solution;
		leftDisplay.setSolution(solution);			//updates the solution and refreshing the widgets.
		rightDisplay.setSolution(solution);
		widgetsRefresh();

	}

	/**
	 * if we have data in mazeData and charPosition the method redraws the widgets.
	 */
	public void widgetsRefresh() {
		if ((mazeData != null) && (charPosition != null))
			Display.getDefault().syncExec(new Runnable() {
				public void run() {

					redraw();
				}
			});

	}
}
