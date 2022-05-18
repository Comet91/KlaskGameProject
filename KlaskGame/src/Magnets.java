/**
 * Models a simple solid sphere. 
 * This class represents a Magnets object. When combined with the GameArena class,
 * instances of the Magnets class can be displayed on the screen.
 */
public class Magnets 
{
	// The following instance variables define the
	// information needed to represent a Magnets
	// Feel free to more instance variables if you think it will 
	// support your work... 
	
	private double xPosition;			// The X coordinate of this Magnets
	private double yPosition;			// The Y coordinate of this Magnets
	private double size;				// The diameter of this Magnets
	private int layer;					// The layer of this Magnets is on.
	private String colour;				// The colour of this Magnets

										// Permissable colours are:
										// BLACK, BLUE, CYAN, DARKGREY, GREY,
										// GREEN, LIGHTGREY, MAGENTA, ORANGE,
										// PINK, RED, WHITE, YELLOW or #RRGGBB 

	/**
	 * Constructor. Creates a Magnets with the given parameters.
	 * @param x The x co-ordinate of centre of the Magnets (in pixels)
	 * @param y The y co-ordinate of centre of the Magnets (in pixels)
	 * @param diameter The diameter of the Magnets (in pixels)
	 * @param col The colour of the Magnets (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 */
	public Magnets(double x, double y, double diameter, String col)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.size = diameter;
		this.colour = col;
		this.layer = 0;
	}	

	/**
	 * Constructor. Creates a Magnets with the given parameters.
	 * @param x The x co-ordinate of centre of the Magnets (in pixels)
	 * @param y The y co-ordinate of centre of the Magnets (in pixels)
	 * @param diameter The diameter of the Magnets (in pixels)
	 * @param col The colour of the Magnets (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 * @param layer The layer this Magnets is to be drawn on. Objects with a higher layer number are always drawn on top of those with lower layer numbers.
	 */
	public Magnets(double x, double y, double diameter, String col, int layer)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.size = diameter;
		this.colour = col;
		this.layer = layer;
	}	

	/**
	 * Obtains the current position of this Magnets.
	 * @return the X coordinate of this Magnets within the GameArena.
	 */
	public double getXPosition()
	{
		return xPosition;
	}

	/**
	 * Obtains the current position of this Magnets.
	 * @return the Y coordinate of this Magnets within the GameArena.
	 */
	public double getYPosition()
	{
		return yPosition;
	}

	/**
	 * Moves the current position of this Magnets to the given co-ordinates
	 * @param x the new x co-ordinate of this Magnets
	 */
	public void setXPosition(double x)
	{
		this.xPosition = x;
	}

	/**
	 * Moves the current position of this Magnets to the given co-ordinates
	 * @param y the new y co-ordinate of this Magnets
	 */
	public void setYPosition(double y)
	{
		this.yPosition = y;
	}

	/**
	 * Obtains the size of this Magnets.
	 * @return the diameter of this Magnets,in pixels.
	 */
	public double getSize()
	{
		return size;
	}
	
	/**
	 * Sets the diameter of this Magnets to the given size.
	 * @param s the new diameter of this Magnets, in pixels.
	 */
	public void setSize(double s)
	{
		size = s;
	}

	/**
	 * Obtains the layer of this Magnets.
	 * @return the layer of this Magnets.
	 */
	public int getLayer()
	{
		return layer;
	}

	/**
	 * Sets the layer of this Magnets.
	 * @param l the new layer of this Magnets. Higher layer numbers are drawn on top of low layer numbers.
	 */
	public void setLayer(int l)
	{
		layer = l;
	}

	/**
	 * Obtains the colour of this Magnets.
	 * @return a textual description of the colour of this Magnets.
	 */
	public String getColour()
	{
		return colour;
	}

	/**
	 * Sets the colour of this Magnets.
	 * @param c the new colour of this Magnets, as a String value. Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or #RRGGBB.
	 */
	public void setColour(String c)
	{
		colour = c;
	}

	/**
	 * Moves this Magnets by the given amount.
	 * 
	 * @param dx the distance to move on the x axis (in pixels)
	 * @param dy the distance to move on the y axis (in pixels)
	 */
	public void move(double dx, double dy)
	{
		xPosition += dx;
		yPosition += dy;
	}

	/**
	 * Determines if this Magnets is overlapping the given Magnets.
	 * 
	 * @param b the Magnets to test for collision
	 * @return true of this Magnets is overlapping the Magnets b, false otherwise.
	 */
	public boolean collides(Magnets m)
	{
		double dx = m.xPosition - xPosition;
		double dy = m.yPosition - yPosition;
		double distance = Math.sqrt(dx*dx+dy*dy);

		return distance < size/2 + m.size/2;
	}
}