package mazeCube;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import abstracts.MazeDisplayer;

/**
 * A type of MazeDisplayer that draws the Maze graphically in 3D.
 * @author Guy Golan && Amit Sandak
 *
 */
public class MazeCube extends MazeDisplayer {

	protected int canvasWidth;		//current canvas Width.
	protected int canvasHeight;		//current canvas height.
	protected int playerAbsoluteX;
	protected int playerAbsoluteY;	
	protected int playerAbsoluteZ;
	protected double ratioPlayerX;
	protected double ratioPlayerY;
	protected double ratioPlayerZ;
	protected int realPlayerX;
	protected int realPlayerY;
	protected int realPlayerZ;
	protected int xAxis;			//the Maze's x.
	protected int yAxis;			//the Maze's y.
	protected int zAxis;			//the Maze's z.
	protected double ratioxAxis;	//rationed x in respect to the canvas size.
	protected double ratioyAxis;	//rationed y in respect to the canvas size.
	protected double ratiozAxis;	//rationed z in respect to the canvas size.
	protected double mainAngle;		//angle for y rotation.
	protected double viewAngle;		//real cal angle.
	protected int[] canvasCenter;	//the x and y of the middle of the canvas.
	protected double[] shapeCenter;	//the x and y of the middle of the shape.
	protected double[] deflection;	//deflection in x and y.
	protected int[] upperShapeVertices;	//the upper shape vertices.
	protected int[] lowerShapeVertices;	//the lower shape vertices.
	
	
	/**
	 * Regular SWT Ctor.
	 * @param parent - parenting Widget.
	 * @param style - SWT style.
	 */
	public MazeCube(Composite parent, int style) {
		super(parent, style);
		
		MazeDisplayer canvas = this;
		canvasWidth = this.getSize().x ;
		canvasHeight = this.getSize().y;
		
		
		mainAngle = 120 ; //default
		viewAngle = (180 - mainAngle)/2;
		xAxis = 15;//default
		yAxis = 15;//default
		zAxis = 15;//default
		playerAbsoluteX = 10;	//default player starting position.
		playerAbsoluteY = 10;	//
		playerAbsoluteZ = 10;	//
		
		canvasCenter = new int[2];
		canvasCenter[0] = 50;
		canvasCenter[1] = 50;
		shapeCenter = new double[2];
		deflection= new double[2];
		upperShapeVertices = new int[8];
		lowerShapeVertices = new int[8];
		
		setBackground(new Color(null, 255, 255, 255));

		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent event) {
				if(mazeData!= null)
				{
					
					
					xAxis = mazeData.getxAxis();		
					yAxis = mazeData.getyAxis();		
					zAxis = mazeData.getzAxis();
				
					playerAbsoluteX = charPosition.getX();
					playerAbsoluteY = charPosition.getY();
					playerAbsoluteZ = charPosition.getZ();
				
					int sumPos = playerAbsoluteX + playerAbsoluteY + playerAbsoluteZ;
					double ratioPosMultiplier = 100 / sumPos;
					int sumAxis = xAxis + yAxis + zAxis;		//summing the total of the dimension.
					double  ratioMultiplier = 100 / sumAxis;	//getting the ration multiplier.
					
					ratioPlayerX = ratioPosMultiplier * playerAbsoluteX;
					ratioPlayerY = ratioPosMultiplier * playerAbsoluteY;
					ratioPlayerZ = ratioPosMultiplier * playerAbsoluteZ;
					
					ratioxAxis = ratioMultiplier * xAxis;		//each ratio axis receives its 'share' by its original size.
					ratioyAxis = ratioMultiplier * yAxis;
					ratiozAxis = ratioMultiplier * zAxis;
					
	
					canvasWidth = canvas.getSize().x;
					canvasHeight = canvas.getSize().y;
					
					double[] pointA = new double[2];
					double[] pointB = new double[2];
					double[] pointC = new double[2];
					double[] pointD = new double[2];
					double[] pointE = new double[2];
					double[] pointF = new double[2];
					double[] pointG = new double[2];
					double[] pointH = new double[2];
					
					pointA[0] = canvasCenter[0];
					pointA[1] = canvasCenter[1];
					pointB[0] = canvasCenter[0] + ratioyAxis*Math.sin(viewAngle);
					pointB[1] = canvasCenter[1] - ratioyAxis*Math.cos(viewAngle);
					pointC[0] = pointB[0] - ratiozAxis*Math.sin(viewAngle);
					pointC[1] = pointB[1] - ratiozAxis*Math.cos(viewAngle);
					pointD[0] = canvasCenter[0] - ratiozAxis*Math.sin(viewAngle);
					pointD[1] = canvasCenter[1] - ratiozAxis*Math.cos(viewAngle);
					pointG[0] = pointC[0];
					pointG[1] = pointC[1] + ratioxAxis;
					pointE[0] = pointA[0];
					pointE[1] = pointA[1] + ratioxAxis;
					pointF[0] = pointB[0];
					pointF[1] = pointB[1] + ratioxAxis; 
					pointH[0] =	pointD[0];
					pointH[1] = pointD[1] + ratioxAxis;
					
					shapeCenter[1] = pointC[1]	+ (pointE[1] - pointC[1])/2;
					shapeCenter[0] = pointD[0] + (pointB[0] - pointD[0])/2;
					deflection[0] = shapeCenter[0] - canvasCenter[0];
					deflection[1] = shapeCenter[1] - canvasCenter[1];
					
					pointA[0] = pointA[0] - deflection[0];			//deflecting each point according to the deflection.
					pointA[1] = pointA[1] - deflection[1];
					pointB[0] = pointB[0] - deflection[0];
					pointB[1] = pointB[1] - deflection[1];
					pointC[0] = pointC[0] - deflection[0];
					pointC[1] = pointC[1] - deflection[1];
					pointD[0] = pointD[0] - deflection[0];
					pointD[1] = pointD[1] - deflection[1];
					pointE[0] = pointE[0] - deflection[0];
					pointE[1] = pointE[1] - deflection[1];
					pointF[0] = pointF[0] - deflection[0];
					pointF[1] = pointF[1] - deflection[1];
					pointG[0] = pointG[0] - deflection[0];
					pointG[1] = pointG[1] - deflection[1];
					pointH[0] = pointH[0] - deflection[0];
					pointH[1] = pointH[1] - deflection[1];
					
					upperShapeVertices[0] = (int) (canvasWidth*(pointA[0]/100));
					upperShapeVertices[1] = (int) (canvasHeight*(pointA[1]/100));
					upperShapeVertices[2] = (int) (canvasWidth*(pointB[0]/100));
					upperShapeVertices[3] = (int) (canvasHeight*(pointB[1]/100));
					upperShapeVertices[4] = (int) (canvasWidth*(pointC[0]/100));
					upperShapeVertices[5] = (int) (canvasHeight*(pointC[1]/100));
					upperShapeVertices[6] = (int) (canvasWidth*(pointD[0]/100));
					upperShapeVertices[7] = (int) (canvasHeight*(pointD[1]/100));
					
					lowerShapeVertices[0] = (int) (canvasWidth*(pointE[0]/100));
					lowerShapeVertices[1] = (int) (canvasHeight*(pointE[1]/100));
					lowerShapeVertices[2] = (int) (canvasWidth*(pointF[0]/100));
					lowerShapeVertices[3] = (int) (canvasHeight*(pointF[1]/100));
					lowerShapeVertices[4] = (int) (canvasWidth*(pointG[0]/100));
					lowerShapeVertices[5] = (int) (canvasHeight*(pointG[1]/100));
					lowerShapeVertices[6] = (int) (canvasWidth*(pointH[0]/100));
					lowerShapeVertices[7] = (int) (canvasHeight*(pointH[1]/100));
					
					System.out.println("F x:" + lowerShapeVertices[2]+" y: "+lowerShapeVertices[3]);
					System.out.println("G x:" + lowerShapeVertices[4]+" y: "+ lowerShapeVertices[5]);
					System.out.println("B x:" + upperShapeVertices[2]+" y: "+upperShapeVertices[3]);
					
					event.gc.setForeground(new Color(getDisplay(),255,0,0));
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointE[0]/100)-2.5), (int) (canvasHeight*(pointE[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointH[0]/100)-2.5), (int) (canvasHeight*(pointH[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointG[0]/100)-2.5), (int) (canvasHeight*(pointG[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointF[0]/100)-2.5), (int) (canvasHeight*(pointF[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.setForeground(new Color(getDisplay(),0,0,0));
					
					event.gc.drawPolygon(upperShapeVertices);	//drawing the upper Vertices and their edges.
					event.gc.drawPolygon(lowerShapeVertices);	//drawing the lower Vertices and their edges.
					//drawing the lines connecting the upper and lower vertices.
					event.gc.drawLine((int)(canvasWidth*(pointA[0]/100)), (int)(canvasHeight*(pointA[1]/100)),(int)(canvasWidth*(pointE[0]/100)) ,(int)(canvasHeight*(pointE[1]/100)));
					event.gc.drawLine((int)(canvasWidth*(pointB[0]/100)), (int)(canvasHeight*(pointB[1]/100)),(int)(canvasWidth*(pointF[0]/100)) ,(int)(canvasHeight*(pointF[1]/100)));
					event.gc.drawLine((int)(canvasWidth*(pointC[0]/100)), (int)(canvasHeight*(pointC[1]/100)),(int)(canvasWidth*(pointG[0]/100)) ,(int)(canvasHeight*(pointG[1]/100)));
					event.gc.drawLine((int)(canvasWidth*(pointD[0]/100)), (int)(canvasHeight*(pointD[1]/100)),(int)(canvasWidth*(pointH[0]/100)) ,(int)(canvasHeight*(pointH[1]/100)));
	
					
					double k = Math.sqrt(Math.pow((lowerShapeVertices[4]-lowerShapeVertices[2]),2) + Math.pow((lowerShapeVertices[5]-lowerShapeVertices[3]),2));
					
					
					Image image = new Image(getDisplay(),"resources/locationIcon.png");		
					int imageWidth = image.getBounds().width;
					int imageHeight = image.getBounds().height;
					int width = (int) Math.round(getSize().x*0.02);						
					int height = (int) Math.round(getSize().y*0.02);
					event.gc.drawImage(image,0,0,imageWidth,imageHeight,(int)(lowerShapeVertices[2]+((lowerShapeVertices[6]-upperShapeVertices[2])*(playerAbsoluteY/(double)yAxis*100))/100),(int)(lowerShapeVertices[3]-((lowerShapeVertices[3]-upperShapeVertices[3])*(playerAbsoluteX/(double)xAxis*100))/100),width,height);
					
				}
			}
		});
	}
	
}
