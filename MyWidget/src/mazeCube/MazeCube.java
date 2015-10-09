package mazeCube;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import abstracts.MazeDisplayer;

public class MazeCube extends MazeDisplayer {

	protected int canvasWidth;
	protected int canvasHeight;
	protected int xAxis;
	protected int yAxis;
	protected int zAxis;
	protected double ratioxAxis;
	protected double ratioyAxis;
	protected double ratiozAxis;
	protected double mainAngle;
	protected double viewAngle;
	protected int[] canvasCenter;
	protected double[] shapeCenter;
	protected double[] deflection;
	protected int[] upperShapeVertices;
	protected int[] lowerShapeVertices;
	
	public MazeCube(Composite parent, int style) {
		super(parent, style);
		MazeDisplayer canvas= this;
		canvasWidth = this.getSize().x ;
		canvasHeight = this.getSize().y;
		
		
		mainAngle = 120 ; //default
		viewAngle = (180 - mainAngle)/2;
		xAxis = 10;//default
		yAxis = 10;//default
		zAxis = 10;//default
		
		canvasCenter = new int[2];
		canvasCenter[0] = 50 ;
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
					//System.out.println("MazeCube paintControl");
					
					xAxis = mazeData.getxAxis();	
					yAxis = mazeData.getyAxis();
					zAxis = mazeData.getzAxis();
					int sumAxis = xAxis + yAxis + zAxis;
					double  ratioMultiplier = 100 / sumAxis;
					
					ratioxAxis = ratioMultiplier * xAxis;
					ratioyAxis = ratioMultiplier * yAxis;
					ratiozAxis = ratioMultiplier * zAxis;
					
	//				xAxis = 21;		//STUB
	//				yAxis = 51;
	//				zAxis = 13;
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
					
					pointA[0] = pointA[0] - deflection[0];
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
					
					
					event.gc.setForeground(new Color(getDisplay(),255,0,0));
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointE[0]/100)-2.5), (int) (canvasHeight*(pointE[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointH[0]/100)-2.5), (int) (canvasHeight*(pointH[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointG[0]/100)-2.5), (int) (canvasHeight*(pointG[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.drawRoundRectangle((int) (canvasWidth*(pointF[0]/100)-2.5), (int) (canvasHeight*(pointF[1]/100)-2.5), 5, 5, 5, 5);
					event.gc.setForeground(new Color(getDisplay(),0,0,0));
					
					event.gc.drawPolygon(upperShapeVertices);
					event.gc.drawPolygon(lowerShapeVertices);
					event.gc.drawLine((int)(canvasWidth*(pointA[0]/100)), (int)(canvasHeight*(pointA[1]/100)),(int)(canvasWidth*(pointE[0]/100)) ,(int)(canvasHeight*(pointE[1]/100)));
					event.gc.drawLine((int)(canvasWidth*(pointB[0]/100)), (int)(canvasHeight*(pointB[1]/100)),(int)(canvasWidth*(pointF[0]/100)) ,(int)(canvasHeight*(pointF[1]/100)));
					event.gc.drawLine((int)(canvasWidth*(pointC[0]/100)), (int)(canvasHeight*(pointC[1]/100)),(int)(canvasWidth*(pointG[0]/100)) ,(int)(canvasHeight*(pointG[1]/100)));
					event.gc.drawLine((int)(canvasWidth*(pointD[0]/100)), (int)(canvasHeight*(pointD[1]/100)),(int)(canvasWidth*(pointH[0]/100)) ,(int)(canvasHeight*(pointH[1]/100)));
	
					Image image = new Image(getDisplay(),"resources/locationIcon.png");
					int imageWidth = image.getBounds().width;
					int imageHeight = image.getBounds().height;
					int width = (int) Math.round(getSize().x*0.05);
					int height = (int) Math.round(getSize().y*0.05);
					event.gc.drawImage(image,0,0,imageWidth,imageHeight,0,0,width,height);
				}
			}
		});
	}
}
