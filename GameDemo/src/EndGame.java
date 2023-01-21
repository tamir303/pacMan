import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import javax.imageio.ImageIO;

public class EndGame extends GameState {
	boolean active;
	JFrame frame;
	
	public void enter(Object memento) {
		active = true;
	}
	
	public void processKeyReleased(int aKeyCode) {
		if (aKeyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
		
		active = false;
	}
	
	public boolean isActive() { return active; }
	
	public String next() {
		return "WelcomeState";
	}
	
	/*public void render(GameFrameBuffer aGameFrameBuffer) {

		/*Graphics g = aGameFrameBuffer.graphics();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("pac-man-game-over.jpg"));
			g.drawImage(image, 0, 0, aGameFrameBuffer.getWidth(), aGameFrameBuffer.getHeight(), null);
		} catch (IOException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, aGameFrameBuffer.getWidth(), aGameFrameBuffer.getHeight());*
		}
	}   

}*/
	public void createAndShowGUI() {
        JButton button = new JButton("Text");
        button.setSize(button.getPreferredSize());

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(snapshot(button)));

        JPanel contentPane = new JPanel();
        contentPane.add(label);

        JFrame frame = new JFrame();
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private BufferedImage snapshot(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        component.paint(image.createGraphics());
        return image;
    }
}
