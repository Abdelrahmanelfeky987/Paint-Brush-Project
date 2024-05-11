import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class PaintApp extends Applet{
//intialize variable and putting default 
	int x ; 
	int y ;
	int x1 ;
	int y1 ; 
	int w ; 
	int h ; 
	boolean drawing = false;
	boolean fill = false; 
	Color color = Color.black;
	String mode = "FreeHand" ;
	List<GeoShape> shapes = new ArrayList<>();
	List<GeoShape> undoShapes = new ArrayList<>();


	
	
	public void init(){
// Creating new objects from Button class to be the source
		Button Undo = new Button("Undo");
		Button Clear = new Button("Clear");
		Button FreeHand = new Button("FreeHand");
		Button Eraser = new Button("Eraser");
		Button GREEN = new Button("Green");
		Button Line = new Button("/");
		Button Oval = new Button("O");
		Checkbox Solid = new Checkbox("Solid");
		Button Blue = new Button("Blue");
		Button RED = new Button("RED");
		Button Rect = new Button("Rectangle");
		
//Adding this buttons to the applet
		add(Solid);
		add(Rect);
		add(Oval);
		add(Line);
		add(FreeHand);
		add(RED);
		add(Blue);
		add(GREEN);
		add(Eraser);
		add(Clear);
		add(Undo);
//Using anonymous inner Classes to make listener and register this listener with source	
		Solid.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				fill = e.getStateChange() == ItemEvent.SELECTED;
			}
		});
		Undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				mode ="Undo";
				if(shapes.isEmpty()){
					shapes.addAll(undoShapes);
					repaint();
					undoShapes.clear();
				}
				else{
					shapes.remove(shapes.size()-1);
					repaint();
				}
				mode = "FreeHand";
			}
		});
		Blue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				if(mode == "Eraser"){
					color = Color.white;
				}else{
					color = Color.blue;
				}
			}
		});
		RED.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				if(mode == "Eraser"){
					color = Color.white;
				}else{
					color = Color.red;
				}
			}
		});
		GREEN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				if(mode == "Eraser"){
					color = Color.white;
				}else{
					color = Color.green;
				}
			}
		});
		Rect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				mode ="Rect";
				color = Color.black;	
			}
		});
		Eraser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				mode ="Eraser";
				color = Color.white;
			}
		});
		Clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				undoShapes.addAll(shapes);
				shapes.clear();	
				repaint();		
			}
		});
		FreeHand.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				mode ="FreeHand";
				color = Color.black;

			}
		});
		Oval.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				mode ="Oval";
				color = Color.black;
					
			}
		});
		Line.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent A){
				mode ="Line";
				color = Color.black;
					
			}
		});
//Here the source is the applet itself
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				x = e.getX();
				y = e.getY();	
			}
			public void mouseReleased(MouseEvent e){
				if(x==x1 | y == y1){
					drawing = false;
				}else{
					if(drawing){
						if(mode == "Rect" | mode == "Oval"){
							if(x < x1 & y < y1 ){
								
								w = x1 - x;
								h = y1 - y;
							
							}
							else if(x > x1 & y < y1){
								w = x - x1;
								h = y1 - y;
								x = x1;
							}
							else if(x > x1 & y > y1){
								w = x - x1;
								h = y - y1;
								x = x1;
								y = y1;
								
							}
							else if(x <x1 & y > y1){
								w = x1 - x;
								h = y - y1 ;
								y = y1 ; 
							}
						}	
					addToList();
					drawing = false;
					repaint();
					}
				}
			}
		});

		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				x1 = e.getX() ;
				y1 = e.getY() ;
				drawing = true ;
				if(mode == "Eraser" | mode=="FreeHand"){
					x = x1;
					y = y1;
					repaint();
					addToList();
				}
				if(drawing){
					if(x < x1 & y < y1 ){
						w = x1 - x;
						h = y1 - y;							
					}
					else if(x > x1 & y < y1){
						w = x - x1;
						h = y1 - y;						
					}
					else if(x > x1 & y > y1){
						w = x - x1;
						h = y - y1;						
					}
					else if(x <x1 & y > y1){
						w = x1 - x;
						h = y - y1 ;	 
					}						
				repaint();						
				}	
			}
		});
	}

	
	public void paint(Graphics g){
		g.setColor(color);
		if(drawing){
			switch(mode){
				case "Rect":
					if(fill == false){
						if(x < x1 & y < y1 ){
							
							g.drawRect( x , y ,w , h);
						}
						else if(x > x1 & y < y1){
							g.drawRect( x1 , y ,w , h);
						}
						else if(x > x1 & y > y1){
							g.drawRect( x1 , y1 ,w , h);
							
						}
						else if (x <x1 & y > y1){
							g.drawRect( x , y1 ,w , h);
						}
					}
					else if(fill == true){
						if(x < x1 & y < y1 ){
							
							g.fillRect( x , y ,w , h);
						}
						else if(x > x1 & y < y1){
							g.fillRect( x1 , y ,w , h);
						}
						else if(x > x1 & y > y1){
							g.fillRect( x1 , y1 ,w , h);
							
						}
						else if (x <x1 & y > y1){
							g.fillRect( x , y1 ,w , h);
						}
					}
				break;
				case "Oval":
					if(fill == false){
						if(x < x1 & y < y1 ){
							
							g.drawOval( x , y ,w , h);
						}
						else if(x > x1 & y < y1){
							g.drawOval( x1 , y ,w , h);
						}
						else if(x > x1 & y > y1){
							g.drawOval( x1 , y1 ,w , h);
							
						}
						else if (x <x1 & y > y1){
							g.drawOval( x , y1 ,w , h);
						}
					}
					else if(fill == true){
						if(x < x1 & y < y1 ){
							
							g.fillOval( x , y ,w , h);
						}
						else if(x > x1 & y < y1){
							g.fillOval( x1 , y ,w , h);
						}
						else if(x > x1 & y > y1){
							g.fillOval( x1 , y1 ,w , h);
							
						}
						else if (x <x1 & y > y1){
							g.fillOval( x , y1 ,w , h);
						}
					}
				break;
				case"Line":
					g.drawLine(x , y , x1 , y1);
				break;
				case"Eraser":
					if(fill == true | fill == false){
					g.fillRect(x1 , y1 ,7 , 7);
					}
				break;
				case"FreeHand":
					if(fill == true | fill == false){
					g.fillRect(x , y , 5 ,5 );
					}
				break;
			}
		}

		for(GeoShape object : shapes){
			g.setColor(object.color);
			switch(object.mode){
				case "Rect":
					if(object.solid == false){
					g.drawRect( object.x , object.y , object.w , object.h);
					}
					if(object.solid == true){
						g.fillRect( object.x , object.y ,object.w , object.h);
					}
				break;
				case"Oval":
					if(object.solid == false){
					g.drawOval( object.x ,object.y ,object.w , object.h);
					}
					if(object.solid == true){
						g.fillOval( object.x , object.y , object.w , object.h);
					}
				break;
				case"Line":
					g.drawLine(object.x , object.y , object.x1 , object.y1);
				break;
				case"Eraser":
					g.fillRect(object.x , object.y ,20 , 20);
				break;
				case"FreeHand":
					g.fillRect(object.x , object.y , 5 ,5 );
				break;
				}				
			}
		}
		
		
//Create method to create new objects and save it in the ArrayList
	public void addToList(){
		switch (mode){
			case"Rect":
			shapes.add(new Rect(x,y,w,h,color,mode,fill));
			break;
			case"Oval":
			shapes.add(new Oval(x,y,w,h,color,mode,fill));
			break;
			case"Line":
			shapes.add(new Line(x,y,x1,y1,color,mode));
			break;
			case"Eraser":
			shapes.add(new Rect(x,y,w,h,color,mode,fill));
			break;
			case"FreeHand":
			shapes.add(new Rect(x,y,x1,y1,color,mode,fill));
			break;			
		}

	}
//Parent Class
	public abstract class GeoShape{
			protected int x ;
			protected  int y ;
			protected int w ;
			protected int h ;
			protected int x1 ;
			protected int y1;
			protected Color color ;
			protected String mode;
			protected boolean solid;
//Setters & getters		
			public int getX(){
				return x;
			}
			public void setX(int x){
					this.x = x;
			}	
			public int getY(){
				return y;
			}
			public void setY(int y){
					this.y = y;
			}
			public int getX1(){
				return x1;
			}
			public void setX1(int x1){
					this.x1 = x1;
			}		
			public int getY1(){
				return y1;
			}
			public void setY1(int y1){
					this.y1 = y1;
			}		
			public Color getColor(){
				return color;
			}
			public void setColor(Color color){
					this.color = color;
			}		
			public String getShape(){
				return mode;
			}
			public void setShape(String mode){
				this.mode = mode;
		}		
		
//Constructors
			public GeoShape(int x , int y , int x1 , int y1 ,Color color ,String mode){
			this.x = x;
			this.y = y ;
			this.x1 = x1 ;
			this.y1 = y1 ;
			this.color = color;
			this.mode = mode;
		}
		
			public GeoShape(int x , int y , int w , int h ,Color color ,String mode,boolean solid){
			this.x = x ; 
			this.y = y ;
			this.w = w ;
			this.h = h ;
			this.color = color;
			this.mode = mode;
			this.solid = solid;
		}
	}



//Childern Classes
	class Rect extends GeoShape{
		public Rect(int x , int y , int w , int h ,Color color ,String mode,boolean solid){
			super(x,y,w,h,color,mode,solid);
			}
		}


	class Oval extends GeoShape{
		public Oval(int x , int y , int w , int h ,Color color ,String mode,boolean solid){
			super(x,y,w,h,color,mode,solid);
			}
		}


	class Line extends GeoShape{
		public Line(int x , int y , int x1 , int y1 ,Color color ,String mode){
			super(x,y,x1,y1,color,mode);
			}
		}

}