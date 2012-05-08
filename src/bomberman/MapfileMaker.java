package bomberman;

import java.io.*;

public class MapfileMaker
{
	private static String fileName = "level.dat";
	
	private static short lg_orig_A = 15, lg_orig_B = 15;
	private static short sd_orig[][] =
	{
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }
	};
	
	private static short lg_maze_A = 15, lg_maze_B = 15;
	private static short sd_maze[][] =
	{
		{2,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
		{1,1,1,1,1,1,2,1,1,1,1,2,1,2,1},
		{1,1,2,1,2,1,2,2,2,2,1,2,1,2,1},
		{1,2,2,1,2,1,1,1,1,2,1,2,1,1,1},
		{1,1,2,1,2,1,2,2,2,2,1,1,1,2,1},
		{1,2,2,2,2,2,2,1,2,1,1,2,1,2,1},
		{1,1,1,2,1,1,1,1,2,2,1,2,1,2,1},
		{1,2,1,2,1,2,2,1,1,1,1,2,1,2,1},
		{1,2,1,2,1,2,2,2,1,2,1,2,2,2,1},
		{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
		{1,2,2,2,2,2,1,2,2,2,2,2,1,2,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,2,1},
		{1,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
		{1,2,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	
	public static void write(String levelName)
	{
		try
		{
			DataOutputStream out = new DataOutputStream(
									new BufferedOutputStream(
											new FileOutputStream(fileName)));
			
			if(levelName.equals("maze"))
			{
				out.writeByte(lg_maze_A);
				out.writeByte(lg_maze_B);
				for(int i=0; i<lg_maze_A; i++)
					for(int j=0; j<lg_maze_B; j++)
						out.writeByte(sd_maze[i][j]);
			}
			else if(levelName.equals("orig"))
			{
				out.writeByte(lg_orig_A);
				out.writeByte(lg_orig_B);
				for(int i=0; i<lg_orig_A; i++)
					for(int j=0; j<lg_orig_B; j++)
						out.writeByte(sd_orig[i][j]);	
			}
			else
			{
				System.out.println("Mapname does not exist");
			}
			
			out.close();
		}
		catch(IOException iox)
		{
			System.out.println("Error while writing File "+fileName);
		}
	}
	
	public static void main(String[] args)
	{
		if(args.length != 0)
			write(args[0]);
		else
			write("orig");
	}
}
