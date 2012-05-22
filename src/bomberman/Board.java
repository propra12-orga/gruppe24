package bomberman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3162328337047992623L;
	private final int blocksize = 32;
	private int player_lastx, player_lasty;
	private Dimension boardsize;

	private Image img_wall, img_air, img_player;
	private String img_wall_name = "../images/wall.png",
			img_air_name = "../images/air.png";

	private int screendata[][];
	private int sdlengthA, sdlengthB;

	private Player player;

	private Timer timer;

	public Board() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		getImages();

		setDoubleBuffered(true);

		timer = new Timer(15, this);
	}

	public void addNotify() {
		super.addNotify();
		GameInit();
	}

	public boolean loadLevel(String fileName) {
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					new FileInputStream(fileName)));

			try {
				sdlengthA = in.readUnsignedByte();
				sdlengthB = in.readUnsignedByte();
				screendata = new int[sdlengthA][sdlengthB];

				for (int i = 0; i < sdlengthA; i++)
					for (int j = 0; j < sdlengthB; j++)
						screendata[i][j] = in.readUnsignedByte();

				boardsize = new Dimension(sdlengthA * blocksize, sdlengthB
						* blocksize);
			} catch (EOFException eof) {
				return false;
			}

			in.close();
		} catch (IOException iox) {
			return false;
		}

		return true;
	}

	public void drawBoard(Graphics2D g2d) {
		for (int i = 0; i < sdlengthA; i++)
			for (int j = 0; j < sdlengthB; j++) {
				if ((screendata[i][j] & 1) != 0)
					g2d.drawImage(img_wall, i * blocksize, j * blocksize, this);
				else if ((screendata[i][j] & 2) != 0)
					g2d.drawImage(img_air, i * blocksize, j * blocksize, this);
			}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		drawBoard(g2d);

		player.move();
		checkCollisions();

		g2d.drawImage(img_player, player_lastx, player_lasty, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void checkCollisions() {
		if (collisionOccured()) {
			player.setX(player_lastx);
			player.setY(player_lasty);
		} else {
			player_lastx = player.getX();
			player_lasty = player.getY();
		}
	}

	public boolean collisionOccured() {
		int x = player.getX();
		int y = player.getY();
		int playersize = player.getSize();

		int corner[][] = new int[4][2];

		// top left corner of player
		corner[0][0] = x / blocksize;
		corner[0][1] = y / blocksize;

		// top right corner of player
		corner[1][0] = (x + playersize - 1) / blocksize;
		corner[1][1] = y / blocksize;

		// bottom left corner of player
		corner[2][0] = x / blocksize;
		corner[2][1] = (y + playersize - 1) / blocksize;

		// bottom right corner of player
		corner[3][0] = (x + playersize - 1) / blocksize;
		corner[3][1] = (y + playersize - 1) / blocksize;

		if (x < 0 || y < 0)
			return true;
		else if (x + playersize > boardsize.width
				|| y + playersize > boardsize.height)
			return true;
		for (int i = 0; i < corner.length; i++)
			if ((screendata[corner[i][0]][corner[i][1]] & 1) != 0)
				return true;
		return false;
	}

	public void GameInit() {
		if (!loadLevel("level.dat"))
			System.out.println("NO LEVEL.DAT FOUND");
		player = new Player();
		img_player = new ImageIcon(this.getClass().getResource(
				player.getImageString())).getImage();
		timer.start();
	}

	public void getImages() {
		img_wall = new ImageIcon(this.getClass().getResource(img_wall_name))
				.getImage();
		img_air = new ImageIcon(this.getClass().getResource(img_air_name))
				.getImage();
	}

	public void actionPerformed(ActionEvent e) {

		repaint();

	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
	}
}
