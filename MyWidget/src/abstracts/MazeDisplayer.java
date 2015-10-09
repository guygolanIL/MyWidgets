package abstracts;


import java.util.ArrayList;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;



public abstract class MazeDisplayer extends Canvas{
	
	protected Maze3d mazeData;
	protected Position charPosition;
	protected ArrayList<Position> solution;
	
//	// stub
//	int[][] mazeData={
//			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
//			{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
//			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
//			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
//			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
//			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
//			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
//			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
//			{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
//		};

	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		charPosition = new Position(1,1,1);
		solution = new ArrayList<Position>();
	}

	public void setMazeData(Maze3d mazeData){
		this.mazeData=mazeData;
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	redraw();
		    	//update();
		    }
		});
	}
	
	public void setCharPosition(Position position){
		this.charPosition=position;
		if ((solution!=null)&&(solution.contains(position)))
			solution.remove(position);
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	redraw();
		    	//update();
		    }
		});
	}
	
	public void setSolution(ArrayList<Position> solution2){
		this.solution=solution2;
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	redraw();
		    	//update();
		    }
		});
	}

}