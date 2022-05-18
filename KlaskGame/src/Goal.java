public class Goal
{
	// The following instance variables define the
	// information needed to represent a Goal
	// Feel free to more instance variables if you think it will 
	// support your work... 
	
	private double xPosition;			// The X coordinate of this Goal
	private double yPosition;			// The Y coordinate of this Goal
	private double size;				// The diameter of this Goal
	private int layer;					// The layer of this Goal is on.
	private String colour;				// The colour of this Goal

										// Permissable colours are:
										// BLACK, BLUE, CYAN, DARKGREY, GREY,
										// GREEN, LIGHTGREY, MAGENTA, ORANGE,
										// PINK, RED, WHITE, YELLOW or #RRGGBB 

	/**
	 * Constructor. Creates a Goal with the given parameters.
	 * @param x The x co-ordinate of centre of the Goal (in pixels)
	 * @param y The y co-ordinate of centre of the Goal (in pixels)
	 * @param diameter The diameter of the Goal (in pixels)
	 * @param col The colour of the Goal (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 */
	public Goal(double x, double y, double diameter, String col)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.size = diameter;
		this.colour = col;
		this.layer = 0;
	}	

	/**
	 * Constructor. Creates a Goal with the given parameters.
	 * @param x The x co-ordinate of centre of the Goal (in pixels)
	 * @param y The y co-ordinate of centre of the Goal (in pixels)
	 * @param diameter The diameter of the Goal (in pixels)
	 * @param col The colour of the Goal (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 * @param layer The layer this Goal is to be drawn on. Objects with a higher layer number are always drawn on top of those with lower layer numbers.
	 */
	public Goal(double x, double y, double diameter, String col, int layer)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.size = diameter;
		this.colour = col;
		this.layer = layer;
	}	

	/**
	 * Obtains the current position of this Goal.
	 * @return the X coordinate of this Goal within the GameArena.
	 */
	public double getXPosition()
	{
		return xPosition;
	}

	/**
	 * Obtains the current position of this Goal.
	 * @return the Y coordinate of this Goal within the GameArena.
	 */
	public double getYPosition()
	{
		return yPosition;
	}

	/**
	 * Moves the current position of this Goal to the given co-ordinates
	 * @param x the new x co-ordinate of this Goal
	 */
	public void setXPosition(double x)
	{
		this.xPosition = x;
	}

	/**
	 * Moves the current position of this Goal to the given co-ordinates
	 * @param y the new y co-ordinate of this Goal
	 */
	public void setYPosition(double y)
	{
		this.yPosition = y;
	}

	/**
	 * Obtains the size of this Goal.
	 * @return the diameter of this Goal,in pixels.
	 */
	public double getSize()
	{
		return size;
	}
	
	/**
	 * Sets the diameter of this Goal to the given size.
	 * @param s the new diameter of this Goal, in pixels.
	 */
	public void setSize(double s)
	{
		size = s;
	}

	/**
	 * Obtains the layer of this Goal.
	 * @return the layer of this Goal.
	 */
	public int getLayer()
	{
		return layer;
	}

	/**
	 * Sets the layer of this Goal.
	 * @param l the new layer of this Goal. Higher layer numbers are drawn on top of low layer numbers.
	 */
	public void setLayer(int l)
	{
		layer = l;
	}

	/**
	 * Obtains the colour of this Goal.
	 * @return a textual description of the colour of this Goal.
	 */
	public String getColour()
	{
		return colour;
	}

	/**
	 * Sets the colour of this Goal.
	 * @param c the new colour of this Goal, as a String value. Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or #RRGGBB.
	 */
	public void setColour(String c)
	{
		colour = c;
	}

}