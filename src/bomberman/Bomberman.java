package bomberman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Bomberman extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2949892020454126752L;

	public Bomberman() {
		/**
		 * add(new Board()); setTitle("Bomberman");
		 * setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(600, 550);
		 * setLocationRelativeTo(null); setVisible(true);
		 **/

		JFrame f = new JFrame("Bomberman");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(50, 50, 500, 545);

		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("Datei");
		mb.add(m1);
		JMenuItem start = new JMenuItem("Neues Spiel");
		m1.add(start);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				new Bomberman(); // Bugfix nötig

			}
		});
		JMenuItem end = new JMenuItem("Beenden");
		m1.add(end);

		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(1);
			}
		});

		f.setJMenuBar(mb);
		f.setLocationRelativeTo(null);
		f.getContentPane().add(new Board());
		f.setVisible(true);
	}

	public static void main(String[] args) {
		new Bomberman();
	}
}
