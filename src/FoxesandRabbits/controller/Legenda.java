package FoxesandRabbits.controller;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Legenda extends JPanel {
	
	public Legenda() {
		
		setLayout(new GridLayout(24, 1));
		JLabel labelRabbit = new JLabel("Rabbit", new ImageIcon(getClass().getResource("/files/Rabbit.png")), SwingConstants.LEFT);
		add(labelRabbit);
		JLabel labelFox = new JLabel("Fox", new ImageIcon(getClass().getResource("/files/Fox.png")), SwingConstants.LEFT);
		add(labelFox);
		JLabel labelBorg = new JLabel("Borg", new ImageIcon(getClass().getResource("/files/Borg.png")), SwingConstants.LEFT);
		add(labelBorg);
		JLabel labelHunter = new JLabel("Hunter", new ImageIcon(getClass().getResource("/files/Hunter.png")), SwingConstants.LEFT);
		add(labelHunter);
		JLabel labelGrass = new JLabel("Grass", new ImageIcon(getClass().getResource("/files/Grass.png")), SwingConstants.LEFT);
		add(labelGrass);
		JLabel labelAlpaca = new JLabel("Alpaca", new ImageIcon(getClass().getResource("/files/Alpaca.png")), SwingConstants.LEFT);
		add(labelAlpaca);
	}

}
