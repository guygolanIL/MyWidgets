package mainMaze;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CrossCombo extends Canvas{
	
	protected Maze2D slave;
	protected Combo combo;
	
	public CrossCombo(Composite parent, int style, Maze2D aslave) {
		super(parent, SWT.NULL);
		this.slave = aslave;
		Canvas canvas =this;
		
		setLayout(new GridLayout(1, true));
		combo = new Combo(canvas, style);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		combo.setItems(new String[] { "x", "y", "z" });
		
		combo.setBackground(new Color(getDisplay(), 255, 255, 255));
		combo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(slave !=null)
				switch(combo.getText())
				{
				case "x":
					slave.setCrossSection('x');
					break;
				case "y":
					slave.setCrossSection('y');
					break;
				case "z":
					slave.setCrossSection('z');
					break;
				default:
					slave.setCrossSection('x');	
				}
				
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
				    	redraw();
				    }
			    });
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public Maze2D getSlave() {
		return slave;
	}
	public void setSlave(Maze2D slave) {
		this.slave = slave;
		updateText();
	}
	public void updateText()
	{
		if(slave!=null)
		{
			switch(slave.getCrossSection())
			{
			case 'x':
				combo.select(0);
				break;
			case 'y':
				combo.select(1);
				break;
			case 'z':
				combo.select(3);
				break;
			default:
				combo.select(0);	
			}
		}
	}
}
