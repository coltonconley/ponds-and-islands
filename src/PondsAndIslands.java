import java.util.Scanner;
import java.io.*;

/****************************************************************************** *
 * Name: Colton Conley
 * Block: A
 * Date: 11/16/14
 *
 * Ponds and Islands Description:
 * 	This program takes input from a text file and converts it into a grid containing
 * ponds and islands. Ponds are identified by numbers, and ponds are connected only 
 * when they share a side with another body of water. Islands are identified as 
 * lower case letters, and are connected as long as they either share a side or
 * corner with another land mass. The program displays the final grid of ponds and
 * islands, as well as the total number created.
 * ******************************************************************************/
public class PondsAndIslands 
{
	public static final String FILE_NAME = "map.txt";

	public static void main(String[] args) 
	{
		
		char[][] grid = getInput();
		Statistics stats = new Statistics();
		
		//fill with all ponds and islands
		char[][] fixedGrid = fillGrid(grid, stats);
		
		//print everything
		printWorld(fixedGrid);
		printStats(stats);

	}
	
	/**
	 * Prints a 2d array of chars
	 * 
	 * @param world 	2d array to be printed
	 */
	private static void printWorld(char[][] world)
	{
		for(int row = 0; row < world.length; row++)
		{
			System.out.println("");
			for(int column = 0; column < world[0].length;  column ++)
			{
				System.out.print(world[row][column]);
			}
		}
	}


	/**
	 * converts the input from a text file into a 2d array of chars
	 * 
	 * @return	2d array of chars derived from input
	 */
	public static char[][] getInput()
	{
		Scanner file = openTheFile();

		char[][] grid = new char[file.nextInt()][file.nextInt()];
		file.nextLine();

		//inserts an array of chars into each row of the 2d array
		for(int index = 0; index < grid.length; index++)
		{
			grid[index] = chopUpString(file.nextLine());
		}

		return grid;

	}

	/**
	 * Converts a string into an array of chars
	 * 
	 * @param line		string to be converted
	 * @return			array of chars matching the 
	 * 					string entered
	 */
	public static char[] chopUpString(String line)
	{
		char[] convertedString = new char[line.length()];
		for(int index = 0; index < line.length(); index++)
		{
			convertedString[index] = line.charAt(index);
		}

		return convertedString;
	}

	/**
	 * creates a scanner object that reads from a text file
	 * 
	 * @return		Scanner object which reads from the 
	 * 				input text file
	 */
	private static Scanner openTheFile()
	{
		Scanner file = null;

		try
		{
			file = new Scanner(new File(FILE_NAME));
		}
		catch (IOException e)
		{
			System.out.println("File not found");
		}
		return file;
	}


	/**
	 * Takes a 2d array of chars and fills it with ponds and islands,
	 * counting the number of each as it goes
	 * 
	 * @param grid		2d array of chars to be divided up into ponds
	 * 					and islands
	 * @param stats		Statistics object capable of counting the number
	 * 					of ponds and islands created
	 * @return			returns a grid comprised of Ponds and Islands
	 */
	private static char[][] fillGrid (char[][] grid, Statistics stats)
	{
		char[][] newGrid = floodFillLand(grid, stats);
		newGrid = floodFillWater(newGrid, stats);
		return newGrid;			
	}

	/**
	 * Converts all X's into land masses, grouping them as appropriate
	 * using letters
	 * 
	 * @param grid		2d array of chars containing potential land
	 * 					masses
	 * @param stats		Statistics object to count the number of islands
	 * 					created
	 * @return			returns the original grid with all X's converted
	 * 					to islands
	 */
	private static char[][] floodFillLand(char[][] grid, Statistics stats)
	{
		char label = 'a';
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[0].length; column++)
			{
				floodFillLand(grid, row, column, label);
				if(grid[row][column] == label)
				{
					label++;
					stats.addIsland();
				}
			}
		}
		return grid;
	}

	/**
	 * Fills an entire island with a designated label, starting at a 
	 * specified point
	 * 
	 * @param grid		2d array of chars containing a possible land mass
	 * @param row		Row in 2d array where the starting point is located
	 * @param col		column in 2d array where the starting point is located
	 * @param label		character to assign all points in the island
	 */
	private static void floodFillLand(char[][] grid, int row, int col, char label)
	{

		if (row >= 0 &&
				col >= 0 &&
				row < grid.length &&
				col < grid[0].length)
		{
			if(grid[row][col] == 'X')
			{
				grid[row][col] = label;
				
				//recursively call for all surrounding cells
				floodFillLand(grid, row + 1, col + 1, label);
				floodFillLand(grid, row - 1, col + 1, label);
				floodFillLand(grid, row, col + 1, label);
				floodFillLand(grid, row + 1, col - 1, label);
				floodFillLand(grid, row - 1, col - 1, label);
				floodFillLand(grid, row, col - 1, label);
				floodFillLand(grid, row + 1, col, label);
				floodFillLand(grid, row - 1, col, label);
			} 
		}

	}

	/**
	 * Converts all .'s into ponds, labeling each with a number
	 * 
	 * @param grid		2d array of chars containing potential ponds
	 * @param stats		Statistics object to count the number of ponds
	 * 					created
	 * @return			returns the original grid with all .'s converted
	 * 					to ponds
	 */
	private static char[][] floodFillWater(char[][] grid, Statistics stats)
	{
		char label = '1';
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[0].length; column++)
			{
				floodFillPond(grid, row, column, label);
				if(grid[row][column] == label)
				{
					label++;
					stats.addPond();
				}
			}
		}
		return grid;
	}

	/**
	 *  Fills an entire pond with a designated label, starting at a 
	 * specified point
	 * 
	 * @param grid		2d array of chars containing a possible pond
	 * @param row		Row in 2d array where the starting point is located
	 * @param col		column in 2d array where the starting point is located
	 * @param label		character to assign to all points in the pond
	 */
	private static void floodFillPond(char[][] grid, int row, int col, char label)
	{
		if (row >= 0 &&
				col >= 0 &&
				row < grid.length &&
				col < grid[0].length)
		{
			if(grid[row][col] == '.')
			{
				grid[row][col] = label;
				
				//recursively call adjacent cells
				floodFillPond(grid, row, col + 1, label);
				floodFillPond(grid, row, col - 1, label);
				floodFillPond(grid, row + 1, col, label);
				floodFillPond(grid, row - 1, col, label);
			} 
		}

	}
	
	/**
	 * Prints the number of ponds and islands created
	 * 
	 * @param stats		Statistics object holding the number
	 * 					of ponds and islands created
	 */
	private static void printStats(Statistics stats)
	{
		System.out.println("");
		System.out.println("");
		System.out.println("There are " + stats.getIslands() + 
				" islands and " + stats.getPonds() + " ponds.");
	}

}





