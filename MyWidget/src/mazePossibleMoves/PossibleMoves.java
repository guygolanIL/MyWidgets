package mazePossibleMoves;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import abstracts.MazeDisplayer;

/**
 * The Widget represents the actual moves the player can take.
 * @author Guy Golan && Amit Sandak.
 *
 */
public class PossibleMoves extends MazeDisplayer {

	/**
	 * Regular SWT Ctor.
	 * @param parent - parenting widget.
	 * @param style - SWT style.
	 */
	public PossibleMoves(Composite parent, int style) {
		super(parent, style);
		
		Image image = new Image(this.getDisplay(), "resources/bckrnd.jpeg");
		//main draw if- if no data exist will not draw a thing.
		setBackgroundImage(image);
		setBackgroundMode(SWT.INHERIT_FORCE);
		
		setLayout(new GridLayout(3,false));
		
		Label title = new Label(this, SWT.TITLE);			//widget title.
		title.setText("Possible moves controller");
		title.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 2));
		
		//level up arrow widget.
		Arrow lvlUp = new Arrow(this,"resources/upGreen.png","resources/upRed.png", SWT.FILL);
		lvlUp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		//up arrow widget.
		Arrow up= new Arrow(this,"resources/upGreen.png","resources/upRed.png", SWT.FILL);
		up.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 2, 1));
		
		//level down arrow widget.
		Arrow lvlDown= new Arrow(this,"resources/downGreen.png","resources/downRed.png", SWT.FILL);
		lvlDown.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 2));

		//left arrow widget.
		Arrow left= new Arrow(this,"resources/leftGreen.png","resources/leftRed.png", SWT.FILL);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		//right arrow widget.
		Arrow right= new Arrow(this,"resources/rightGreen.png","resources/rightRed.png", SWT.FILL);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		//down arrow widget.
		Arrow down= new Arrow(this,"resources/downGreen.png","resources/downRed.png", SWT.FILL);
		down.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		
		
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				
				ArrayList<String> moves = new ArrayList<String>();
				
				if ((charPosition!=null)&&(mazeData!=null))
				{
					String[] possibleMoves = mazeData.getPossibleMoves(charPosition);
				
					for (String string : possibleMoves) {
						moves.add(string);
					}
																//updating the Arrow Widgets of its color according to the actual possible moves.
					if (moves.contains("UP"))
						lvlUp.setState(true);
					else
						lvlUp.setState(false);
	
					if (moves.contains("FORWARD"))
						down.setState(true);
					else
						down.setState(false);
	
					if (moves.contains("DOWN"))
						lvlDown.setState(true);
					else
						lvlDown.setState(false);
					
	
					if (moves.contains("LEFT"))
						right.setState(true);
					else
						right.setState(false);
					
	
					if (moves.contains("RIGHT"))
						left.setState(true); 
					else
						left.setState(false);
					
					if (moves.contains("BACKWARD"))
						up.setState(true);
					else
						up.setState(false);
					
					
				}
			}
		});
		
	}

}




