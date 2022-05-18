/**
 * Models a simple solid sphere. 
 * This class represents a Player object. When combined with the GameArena class,
 * instances of the Player class can be displayed on the screen.
 */
public class Player 
{
	// The following instance variables define the
	// information needed to represent a Player
	// Feel free to more instance variables if you think it will 
	// support your work... 
	
	private double xPosition;			// The X coordinate of this Player
	private double yPosition;			// The Y coordinate of this Player
	private double xPPosition; 		
	private double yPPosition; 			
	private double size;				// The diameter of this Player
	private double width;				// The width of this Player
	private double height;				// The width of this Player
	private int layer;					// The layer of this Player is on.
	private String colour;				// The colour of this Player

										// Permissable colours are:
										// BLACK, BLUE, CYAN, DARKGREY, GREY,
										// GREEN, LIGHTGREY, MAGENTA, ORANGE,
										// PINK, RED, WHITE, YELLOW or #RRGGBB 

	/**
	 * Constructor. Creates a Player with the given parameters.
	 * @param x The x co-ordinate of centre of the Player (in pixels)
	 * @param y The y co-ordinate of centre of the Player (in pixels)
	 * @param diameter The diameter of the Player (in pixels)
	 * @param w The width of the Rectangle (in pixels)
	 * @param h The height of the Rectangle (in pixels)
	 * @param col The colour of the Player (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 */
	public Player(double x, double y, double xp, double yp, double diameter, double w, double h, String col)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.xPPosition = xp;
		this.yPPosition = yp;
		this.size = diameter;
		this.width = w;
		this.height = h;
		this.colour = col;
		this.layer = 0;
	}	

	/**
	 * Constructor. Creates a Player with the given parameters.
	 * @param x The x co-ordinate of centre of the Player (in pixels)
	 * @param y The y co-ordinate of centre of the Player (in pixels)
	 * @param diameter The diameter of the Player (in pixels)
	 * @param w The width of the Rectangle (in pixels)
	 * @param h The height of the Rectangle (in pixels)
	 * @param col The colour of the Player (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 * @param layer The layer this Player is to be drawn on. Objects with a higher layer number are always drawn on top of those with lower layer numbers.
	 */
	public Player(double x, double y,  double xp, double yp, double diameter, double w, double h, String col, int layer)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.xPPosition = xp;
		this.yPPosition = yp;
		this.size = diameter;
		this.width = w;
		this.height = h;
		this.colour = col;
		this.layer = layer;
	}	

	/**
	 * Obtains the current position of this Player.
	 * @return the X coordinate of this Player within the GameArena.
	 */
	public double getXPosition()
	{
		return xPosition;
	}

	/**
	 * Obtains the current position of this Player.
	 * @return the Y coordinate of this Player within the GameArena.
	 */
	public double getYPosition()
	{
		return yPosition;
	}

	/**
	 * Moves the current position of this Player to the given co-ordinates
	 * @param x the new x co-ordinate of this Player
	 */
	public void setXPosition(double x)
	{
		this.xPosition = x;
	}

	/**
	 * Moves the current position of this Player to the given co-ordinates
	 * @param y the new y co-ordinate of this Player
	 */
	public void setYPosition(double y)
	{
		this.yPosition = y;
	}
	
	/**
	 * Obtains the current position of this Player.
	 * @return the X coordinate of this Player within the GameArena.
	 */
	public double getXPPosition()
	{
		return xPPosition;
	}

	/**
	 * Obtains the current position of this Player.
	 * @return the Y coordinate of this Player within the GameArena.
	 */
	public double getYPPosition()
	{
		return yPPosition;
	}

	/**
	 * Moves the current position of this Player to the given co-ordinates
	 * @param x the new x co-ordinate of this Player
	 */
	public void setXPPosition(double x)
	{
		this.xPPosition = x;
	}

	/**
	 * Moves the current position of this Player to the given co-ordinates
	 * @param y the new y co-ordinate of this Player
	 */
	public void setYPPosition(double y)
	{
		this.yPPosition = y;
	}


	/**
	 * Obtains the size of this Player.
	 * @return the diameter of this Player,in pixels.
	 */
	public double getSize()
	{
		return size;
	}
	
	/**
	 * Sets the diameter of this Player to the given size.
	 * @param s the new diameter of this Player, in pixels.
	 */
	public void setSize(double s)
	{
		size = s;
	}
	
	/**
	 * Obtains the width of this Rectangle.
	 * @return the width of this Rectangle,in pixels.
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Sets the width of this Rectangle to the given value
	 * @param w the new width of this Rectangle, in pixels.
	 */
	public void setWidth(double w)
	{
		width = w;
	}

	/**
	 * Obtains the height of this Rectangle.
	 * @return the height of this Rectangle,in pixels.
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * Sets the height of this Rectangle to the given value
	 * @param h the new height of this Rectangle, in pixels.
	 */
	public void setHeight(double h)
	{
		height = h;
	}

	/**
	 * Obtains the layer of this Player.
	 * @return the layer of this Player.
	 */
	public int getLayer()
	{
		return layer;
	}

	/**
	 * Sets the layer of this Player.
	 * @param l the new layer of this Player. Higher layer numbers are drawn on top of low layer numbers.
	 */
	public void setLayer(int l)
	{
		layer = l;
	}

	/**
	 * Obtains the colour of this Player.
	 * @return a textual description of the colour of this Player.
	 */
	public String getColour()
	{
		return colour;
	}

	/**
	 * Sets the colour of this Player.
	 * @param c the new colour of this Player, as a String value. Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or #RRGGBB.
	 */
	public void setColour(String c)
	{
		colour = c;
	}

	/**
	 * Moves this Player by the given amount.
	 * 
	 * @param dx the distance to move on the x axis (in pixels)
	 * @param dy the distance to move on the y axis (in pixels)
	 */
	public void move(double dx, double dy)
	{
		xPosition += dx;
		yPosition += dy;
		yPPosition += dy;
		xPPosition += dx;
	}

	/**
	 * Determines if this Player is overlapping the given Player.
	 * 
	 * @param b the Player to test for collision
	 * @return true of this Player is overlapping the Player b, false otherwise.
	 */
	public boolean collides(Player p)
	{
		double dx = p.xPosition - xPosition;
		double dy = p.yPosition - yPosition;
		double distance = Math.sqrt(dx*dx+dy*dy);

		return distance < size/2 + p.size/2;
	}
}