package bomberman;

import java.awt.event.KeyEvent;

public class Player
{
	private final String img_player_name = "../images/playersmall.png";
	private final int playersize = 25;
	
	private int x=0,y=0,dx=0,dy=0, v=1;
	
	public String getImageString()
	{
		return img_player_name;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSize() { return playersize; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	public void move()
	{
		x+=dx;
		y+=dy;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W)
		{
			dx=0;
			dy=-v;
		}
		else if(key == KeyEvent.VK_D)
		{
			dx=v;
			dy=0;
		}
		else if(key == KeyEvent.VK_S)
		{
			dx=0;
			dy=v;
		}
		else if(key == KeyEvent.VK_A)
		{
			dx=-v;
			dy=0;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W)
		{
			dy=0;
		}
		else if(key == KeyEvent.VK_D)
		{
			dx=0;
		}
		else if(key == KeyEvent.VK_S)
		{
			dy=0;
		}
		else if(key == KeyEvent.VK_A)
		{
			dx=0;
		}
	}
}
