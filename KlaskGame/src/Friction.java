
public class Friction {

	
	public static double[] addFriction(double x, double y, double frictionValue)
	{
		double friction[] = new double[2];
		
		if (x > 0)
		{
			x = x - frictionValue;
		}
		else if (x < 0)
		{
			x = x + frictionValue;
		}
		if (y > 0)
		{
			y = y - frictionValue;
		}
		else if (y < 0)
		{
			y = y + frictionValue;
		}
		
		
		friction[0] = x;
		friction[1] = y;
		
		return friction;
	}
}
