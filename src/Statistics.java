/******************************************************************************
* Statistics class
*
*	Stores the number of ponds and islands that have been created.  Provides
*setters, getters for both properties.
* ******************************************************************************/
public class Statistics 
{
	private int ponds = 0; //number of ponds created
	private int islands = 0; //number of islands created
	
	
	/**
	 * All setters and getters for Statistics class
	 */
	public void addPond()
	{
		ponds++;
	}
	
	public void addIsland()
	{
		islands++;
	}
	
	public int getPonds()
	{
		return ponds;
	}
	
	public int getIslands()
	{
		return islands;
	}
}
