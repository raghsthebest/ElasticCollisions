import javax.swing.*;

import java.awt.event.*;

public class CTMain2 extends JFrame implements KeyListener{	
	
	int width = 500, height = 500;
	CTPP2 p;
	
	public static void main(String args[]){
		new CTMain2();
	}
	
	public CTMain2(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setTitle("Elastic Collisions");
        
        p = new CTPP2(width, height);
        add(p);

        setResizable(true);
        setVisible(true);
        
        Action updateCursorAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	p.forever();
            	p.repaint();
            }
        };
        
        this.addKeyListener(this);
        p.addKeyListener(this);

        new Timer(10, updateCursorAction).start();
	}

	public void keyPressed(KeyEvent ke) {
		p.keyDown(ke.getKeyCode());
	}

	public void keyReleased(KeyEvent ke) {
		
	}

	public void keyTyped(KeyEvent ke) {
		
	}

}
