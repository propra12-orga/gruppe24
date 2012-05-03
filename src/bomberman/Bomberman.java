package bomberman;

import javax.swing.JFrame;

public class Bomberman extends JFrame
{
	  public Bomberman()
	  {
	    add(new Board());
	    setTitle("Bomberman");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(480, 480);
	    setLocationRelativeTo(null);
	    setVisible(true);
	  }

	  public static void main(String[] args) {
	      new Bomberman();
	  }
}
