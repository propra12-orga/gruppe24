package bomberman;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener
{
	private final int blocksize = 32;
	private int player_lastx, player_lasty;
	private Dimension boardsize;
	
	private Image img_wall, img_air, img_player;
	private String img_wall_name = "../images/wall.png",
					img_air_name = "../images/air.png";
	
	private short screendata[][] =
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
	
	/*private short screendata[][] =
		{
			{2,2,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,2,1,1,2,1,2,1,2,2,2,2,1,2,2},
			{1,2,1,2,2,2,2,1,1,1,1,2,1,2,1},
			{1,2,1,1,1,1,2,2,2,2,2,2,1,2,1},
			{1,2,1,2,2,2,2,1,1,1,1,2,1,2,1},
			{1,2,1,1,1,1,2,1,2,2,2,2,1,2,1},
			{1,2,2,2,1,2,2,1,2,2,1,1,1,2,1},
			{1,2,1,2,1,2,1,1,1,2,2,2,1,2,1},
			{1,2,1,2,1,2,2,2,1,1,1,2,1,2,1},
			{1,2,1,2,2,2,1,2,1,2,2,2,1,2,1},
			{1,2,1,1,1,1,1,1,1,1,1,2,1,2,1},
			{1,2,2,2,2,1,2,2,2,2,2,2,1,2,1},
			{1,2,1,1,1,1,1,1,1,2,1,1,1,2,1},
			{1,2,2,2,1,2,2,2,2,2,2,2,2,2,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};*/

	private Player player;
	
	private Timer timer;
	
	public Board()
	{
		addKeyListener(new TAdapter());
		setFocusable(true);
		getImages();
		boardsize = new Dimension(screendata.length*blocksize,
								screendata.length*blocksize);
		setDoubleBuffered(true);
		
		timer = new Timer(15, this);
		timer.start();
	}
	
	public void addNotify()
	{
		super.addNotify();		
		GameInit();
	}
	
	public void drawBoard(Graphics2D g2d)
	{
		for(int i=0; i<screendata.length; i++)
			for(int j=0; j<screendata.length; j++)
			{
				if((screendata[i][j] & 1) != 0)
					g2d.drawImage(img_wall, j*blocksize, i*blocksize, this);
				else if((screendata[i][j] & 2) != 0)
					g2d.drawImage(img_air, j*blocksize, i*blocksize, this);
			}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		drawBoard(g2d);
		
		player.move();
		checkCollisions();
		
		g2d.drawImage(img_player, player_lastx, player_lasty, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void checkCollisions()
	{
		if(collisionOccured())
		{
			player.setX(player_lastx);
			player.setY(player_lasty);
		}
		else
		{
			player_lastx = player.getX();
			player_lasty = player.getY();
		}
	}
	
	public boolean collisionOccured()
	{
		int x = player.getX();
		int y = player.getY();
		int playersize = player.getSize();
		
		int corner[][] = new int[4][2];
		
		// top left corner of player
		corner[0][0] = x/blocksize;
		corner[0][1] = y/blocksize;
		
		// top right corner of player
		corner[1][0] = (x+playersize-1)/blocksize;
		corner[1][1] = y/blocksize;
		
		// bottom left corner of player
		corner[2][0] = x/blocksize;
		corner[2][1] = (y+playersize-1)/blocksize;
		
		// bottom right corner of player
		corner[3][0] = (x+playersize-1)/blocksize;
		corner[3][1] = (y+playersize-1)/blocksize;
		
		if(x<0 || y<0)
			return true;
		else if(x+playersize>boardsize.width || y+playersize>boardsize.height)
			return true;
		for(int i=0; i<corner.length; i++)
			if((screendata[corner[i][1]][corner[i][0]] & 1) != 0)
				return true;
		return false;
	}
	
	public void GameInit()
	{
		player = new Player();
		img_player = new ImageIcon(this.getClass().getResource(player.getImageString())).getImage();
	}
	
	public void getImages()
	{
		img_wall = new ImageIcon(this.getClass().getResource(img_wall_name)).getImage();
		img_air = new ImageIcon(this.getClass().getResource(img_air_name)).getImage();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
	
	private class TAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			player.keyPressed(e);
		}
		public void keyReleased(KeyEvent e)
		{
			player.keyReleased(e);
		}
	}
}

