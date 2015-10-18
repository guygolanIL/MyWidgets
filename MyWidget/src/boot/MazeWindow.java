package boot;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import mazeCube.MazeCube;

public class MazeWindow extends BasicWindow{
	protected Maze3d maze;
	protected Position charPosition;
	
	
	

	public MazeWindow( String title, int width, int height) {
		super(title, width, height);
		
	}



	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(1, true));
	
		// cube widget
		MazeCube mazeCube = new MazeCube(shell, SWT.BORDER);
		mazeCube.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
	}
	
	protected void exitRequest() {
		shell.dispose();

	}
	
	
	public void setPositionData(Position charPosition) {
		this.charPosition = charPosition;
		
		
	}
	
	public void setMazeData(Maze3d maze){
		this.maze = maze;
		
	
	}
	
	
}
