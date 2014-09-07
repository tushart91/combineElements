package mainpackage;

import javax.swing.JApplet;


public class CombineElements extends JApplet{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Teacher panel = new Teacher();
    
    @Override
    public void init()
    {
        add(panel);
        setSize(800,600);
        setVisible(true);
    }
}