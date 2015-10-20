package mazePossibleMoves;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

	/**
	 * An Arrow widget.
	 * @author guy
	 *
	 */
	public class Arrow extends Canvas {
		
		protected boolean state;			//red of green.
		protected Image image;				//its final image.
	
		/**
		 * Ctor
		 * @param parent - parenting Composite.
		 * @param green - the file path for the green image.
		 * @param red - the file path for the red image.
		 * @param style - SWT style.
		 */
		public Arrow(Composite parent,String green ,String red,int style) {
			super(parent, style);
			
			state = false; //by default - red.
			Canvas canvas = this;
			
			
			
	    	addPaintListener(new PaintListener() {
				
				@Override
				public void paintControl(PaintEvent e) {
					
					if (image!=null)
							image.dispose();
					
					if(state == true)		//setting the actual image.
						
						   image = new Image(getDisplay(),green);
					else
						   image = new Image(getDisplay(),red);
	
				    int imageWidth = image.getBounds().width;
				    int imageHeight = image.getBounds().height;
				    int width = canvas.getSize().x;
				    int height = canvas.getSize().y;
				    
				    //painting the arrow in its actual resized size in respect to canvas.
				    e.gc.drawImage(image,0,0,imageWidth,imageHeight,0,0,width,height); 
					  
				}
			});
	    	
		}
		
		/**
		 * Regular Get method for state.
		 * @return boolean state.
		 */
		public boolean isState() {
			return state;
		}
	
		/**
		 * Set method for state.
		 * @param state - red or green.
		 */
		public void setState(boolean state) {
			this.state = state;
			//redraws the arrow according to its new state.
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
			    	redraw();
			    }});
		}
	}
