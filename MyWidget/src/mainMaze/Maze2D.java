package mainMaze;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import abstracts.MazeDisplayer;
import algorithms.mazeGenerators.Position;

/**
 * Maze2D widget draws and displays a 2d plain of the maze.
 * @author Guy Golan && Amit Sandak.
 *
 */
public class Maze2D extends MazeDisplayer {
	char crossSection;				//a char for the represented dimension.
	int zoomFactor;					//a zoom factor controled by the mouse wheel (not yet fully implemented).
	protected int state;			//current state of the character : pacman mouth open or closed.
	protected int[] goal2d;			//contains the width and height of the goal position dynamically updated related to the crossSection variable.
	protected int[] position2d;		//contains the width and height of the character position dynamically updated related to the crossSection variable.
	int width;						//canvas width.
	int height;						//canvas height.
	int cursorX;					//contains the mouse current x.
	int cursorY;					//contains the mouse current y.
	int cubeWidth;					//each maze tile's width.
	int cubeHeight;					//each maze tile's height.
	
	
	/**
	 * Regular SWT Ctor.
	 * @param parent - parenting Widget.
	 * @param style - SWT style.
	 */
	public Maze2D(Composite parent, int style) {
		super(parent, style);
		state = 1;			
		zoomFactor = 0;
		crossSection = 'x'; // default
		
		
		addMouseWheelListener(new MouseWheelListener() {
			
			
			@Override
			public void mouseScrolled(MouseEvent arg0) {
				if(zoomFactor + arg0.count >= 0 && zoomFactor + arg0.count <=500)		//limit for the zoom factor.
				{
					zoomFactor = zoomFactor+arg0.count;		
					cursorX = arg0.x;
					cursorY = arg0.y;
					redraw();					//redraws the widget.
				}
				
				
			}
		});
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				
				e.gc.setForeground(new Color(null, 10, 36, 106));
				e.gc.setBackground(new Color(null, 10, 36, 106));
				width = getSize().x +zoomFactor*4;					//canvas width = real canvas width + the amount of zoom applied.
				height = getSize().y +zoomFactor*4;					//canvas height = real canvas height + the amount of zoom applied.
				
				if ((mazeData != null) && (charPosition != null)) {		//main draw if- if no data exist will not draw a thing.
					Image image;

					int imageWidth;
					int imageHeight;
					int resizeWidth = getSize().x;
					int resizeHeight = getSize().y;
					
					if (charPosition.equals(mazeData.getExit())) {				//if the player reached the end of the maze.
						image = new Image(getDisplay(), "resources/winner.jpg");
						imageWidth = image.getBounds().width;
						imageHeight = image.getBounds().height;
						e.gc.drawImage(image, 0, 0, imageWidth, imageHeight, 0, 0, resizeWidth, resizeHeight);

					} 
					else 					//the player still hasnt reached the end of the maze - draws the maze.
					{
						setBackground(new Color(null, 0, 0, 0));	//draws the background black.

						int[][] maze2d = null;
					
						int [] lastPosition = position2d;
						switch (crossSection) {
							case 'x':						//if x plain needed to be painted.
								maze2d = mazeData.getCrossSectionByX(charPosition.getX());			//Retrieves the according plain in the index of the players right position.
								position2d = new int[] { charPosition.getZ(), charPosition.getY() };	 //updates the player's position.			
								goal2d = new int[] { mazeData.getExit().getZ(), mazeData.getExit().getY() };	 //updates the goal position.	 
								break;																				
							case 'y':																				
								maze2d = mazeData.getCrossSectionByY(charPosition.getY());			//Retrieves the according plain in the index of the players right position.		
								position2d = new int[] { charPosition.getZ(),mazeData.getxAxis()-1- charPosition.getX() };		//updates the player's position.			
								goal2d = new int[] { mazeData.getExit().getZ(),mazeData.getxAxis()-1- mazeData.getExit().getX() };		//updates the goal position.	
								break;																				
							case 'z':																				
								maze2d = mazeData.getCrossSectionByZ(charPosition.getZ());			//Retrieves the according plain in the index of the players right position.	
								position2d = new int[] { charPosition.getY(),mazeData.getxAxis()-1- charPosition.getX() };		//updates the player's position.	
								goal2d = new int[] { mazeData.getExit().getY(),mazeData.getxAxis()-1- mazeData.getExit().getX() };		//updates the goal position.	
								break;																				
							default:
								// TODO
						}

						cubeWidth = width / maze2d[0].length;		 //calculation for current cubeWidth in respect to canvas width.
						cubeHeight = height / maze2d.length;		//calculation for current cubeHeight in respect to canvas height.

						for (int i = 0; i < maze2d.length; i++) {			//main maze draw loop.
							for (int j = 0; j < maze2d[i].length; j++) {
								int x = j * cubeWidth;
								int y = i * cubeHeight;
								if (maze2d[i][j] != 0)			//if the is a wall to be drawn.
									e.gc.fillRectangle(x, y, cubeWidth, cubeHeight);
								
								else if (solution != null) {	//if there's exists a solution needed to be drawn.
									int flag = 0;

									switch (crossSection) {			//checks whether the solution is visible and needed to be drawn in the current crossSection.
										case 'x':
											if (solution.contains(new Position(charPosition.getX(), i, j))) {
												flag = 1;
											}
											break;
										case 'y':
											if (solution.contains(new Position(i, charPosition.getY(), j))) {
												flag = 1;
											}
											break;
										case 'z':
											if (solution.contains(new Position(i, j, charPosition.getZ()))) {
												flag = 1;
											}
											break;
									}
									if (flag == 1) {					//prints the solution.
										System.out.println("solution print");
										e.gc.setBackground(new Color(null, 255, 255, 255));
										e.gc.fillOval(x + cubeWidth / 2, y + cubeHeight / 2, cubeWidth / 4, cubeHeight / 4);	//draws a small circle in the middle of the tile.
										e.gc.setBackground(new Color(null, 10, 36, 106));
									}
								}

							}
						}		//end of main draw loop.
						
						boolean shouldShowExit = false;
						resizeWidth = cubeWidth;
						resizeHeight = cubeHeight;
						switch (crossSection) {			//check if the player stands in the same plain as the goal in order to display it.
							case 'x':
								if (mazeData.getExit().getX() == charPosition.getX())
									shouldShowExit = true;
								break;
							case 'y':
								if (mazeData.getExit().getY() == charPosition.getY())
									shouldShowExit = true;
								break;
							case 'z':
								if (mazeData.getExit().getZ() == charPosition.getZ())
									shouldShowExit = true;
								break;
						}
						if (shouldShowExit == true) {
							image = new Image(getDisplay(), "resources/pacmanwoman.png");
							imageWidth = image.getBounds().width;
							imageHeight = image.getBounds().height;
							e.gc.drawImage(image, 0, 0, imageWidth, imageHeight, goal2d[0] * cubeWidth, goal2d[1] * cubeHeight,resizeWidth, resizeHeight);

						}

						if (state == 1) {					//draws the pacman according to its 'state'.
							image = new Image(getDisplay(), "resources/pacman.png");
							state = 0;
						} else {
							image = new Image(getDisplay(), "resources/closedpacman.png");
							state = 1;
						}
						
						imageWidth = image.getBounds().width;
						imageHeight = image.getBounds().height;
						if (lastPosition!=null)
						{
							if(lastPosition[0]>position2d[0])
							{
								Image vertical = rotateImage(rotateImage(image));
							    imageWidth = vertical.getBounds().width;
								imageHeight = vertical.getBounds().height;
							    e.gc.drawImage(vertical, 0, 0, imageWidth, imageHeight, position2d[0] * cubeWidth, position2d[1] * cubeHeight,resizeWidth, resizeHeight);
							}
							else if(lastPosition[1]>position2d[1])
							{
								Image vertical = rotateImage(image);
							    imageWidth = vertical.getBounds().width;
								imageHeight = vertical.getBounds().height;
							    e.gc.drawImage(vertical, 0, 0, imageWidth, imageHeight, position2d[0] * cubeWidth, position2d[1] * cubeHeight,resizeWidth, resizeHeight);
							}
							else if(lastPosition[1]<position2d[1])
							{
								Image vertical = rotateImage(rotateImage(rotateImage(image)));
							    imageWidth = vertical.getBounds().width;
								imageHeight = vertical.getBounds().height;
							    e.gc.drawImage(vertical, 0, 0, imageWidth, imageHeight, position2d[0] * cubeWidth, position2d[1] * cubeHeight,resizeWidth, resizeHeight);
							}
							else
								e.gc.drawImage(image, 0, 0, imageWidth, imageHeight, position2d[0] * cubeWidth, position2d[1] * cubeHeight,resizeWidth, resizeHeight);
						
						}
						else
							e.gc.drawImage(image, 0, 0, imageWidth, imageHeight, position2d[0] * cubeWidth, position2d[1] * cubeHeight,resizeWidth, resizeHeight);
							
					}
					
				}
				
			}
		});
	}

	/**
	 * Used to determine which plain will be displayed.
	 * @param cross - char representing the current displayed plain.
	 */
	public void setCrossSection(char cross) {
		if ((cross == 'x') || (cross == 'X') || (cross == 'y') || (cross == 'Y') || (cross == 'z') || (cross == 'Z')) {
			this.crossSection = cross;
			
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					redraw();
				}
			});
		}
		// TODO add exception.

	}

	/**
	 * Get method for the member crossSection.
	 * @return char - crossSection.
	 */
	public char getCrossSection() {
		return crossSection;
	}
	
	/**
	 * A rotation transformation on an image.
	 * @param image - Image to rotate.
	 * @return - Image. The new rotated image.
	 */
	protected Image rotateImage(Image image)
	{
		 ImageData sd = image.getImageData();
	
	     ImageData dd = new ImageData(sd.height, sd.width, sd.depth, sd.palette);
	
	     int style = SWT.UP;
	
	     boolean up = (style & SWT.UP) == SWT.UP;
	
	     // Run through the horizontal pixels
	     for (int sx = 0; sx < sd.width; sx++) {
	       // Run through the vertical pixels
	       for (int sy = 0; sy < sd.height; sy++) {
	         // Determine where to move pixel to in destination image data
	         int dx = up ? sy : sd.height - sy - 1;
	         int dy = up ? sd.width - sx - 1 : sx;
	         // Swap the x, y source data to y, x in the destination
	         dd.setPixel(dx, dy, sd.getPixel(sx, sy));
	       }
	     }
	
	     // Create the vertical image
	     return new Image(getDisplay(), dd);
	
	}
}

