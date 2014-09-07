package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Teacher extends JPanel implements Runnable{
	
	/*
	 * @author tushart12
	 */
	
	private static final long serialVersionUID = 1L;
	Translate trs = new Translate();
	private JButton prevButton = new JButton(trs.TranslateString("PREVIOUS"));
	private JButton pButton = new JButton(trs.TranslateString("PAUSE"));
	private JButton nextButton = new JButton(trs.TranslateString("NEXT"));
	Thread t = null;
	boolean threadSuspended;
	boolean pause;
	int stage;
	int time;
	int fontSize;
	int leftTitle;
	String elementSymbols[] = {"Ca","Cl"};
	String signString[] = {"+","-"};
	int sign[] = {0,1};
	int charge[] = {2,1};
	
	public Teacher(){
			
		
		setSize(800,600);
		setLayout(null); 
		System.out.println("init(): begin");
		setBackground( Color.black );
		setPreferredSize(new Dimension(800, 600));
		setMaximumSize(this.getPreferredSize());
		setMinimumSize(this.getPreferredSize());
		
		/* Set Background of Components */
		prevButton.setBackground(Color.LIGHT_GRAY);
		pButton.setBackground(Color.LIGHT_GRAY);
		nextButton.setBackground(Color.LIGHT_GRAY);
		
		/* Set Bounds of all Components */
		prevButton.setBounds(220, 2, 120, 30);
		pButton.setBounds(340, 2, 120, 30);
		nextButton.setBounds(460, 2, 120, 30);
		
		
		/* Set visibility of components */
		
		/* Add all components */
		add(prevButton);
		add(pButton);
		add(nextButton);
		
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(stage==1){
					stage=2;
					fontSize=36;
					time=1;
				}
				if(stage==2){
					stage=3;
					time=1;
				}
				
				repaint();
				
			}
		});
		
		prevButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(stage==2){
					stage=1;
					leftTitle = 430;
					fontSize = 1;
				}
				else if(stage==3){
					stage=2;
					time=1;	
				}

				repaint();
				
			}
		});
		
		pButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!pause){
					pause = true;
					pButton.setText(trs.TranslateString("PLAY"));
				}
				else{
					pause = false;
					pButton.setText(trs.TranslateString("PAUSE"));
				}
			}
		});
		
		/*
		 * Initialize Variables
		 */
		
		time = 1;
		stage = 3;
		leftTitle = 430;
		fontSize = 1;
		pause = false;
		
		System.out.println("init(): end");
        
        System.out.println("start(): begin");
        if ( t == null ) {
            System.out.println("start(): creating thread");
            t = new Thread( this );
            System.out.println("start(): starting thread");
            threadSuspended = false;
            t.start();
        }
        else {
            if ( threadSuspended ) {
                threadSuspended = false;
                System.out.println("start(): notifying thread");
                synchronized( this ) {
                notify();
                }
            }
        }
        System.out.println("start(): end");
			
	}
	
	@Override
	public void run(){
		
		System.out.println("run(): begin");
        try {
            while (true) {
            	
            	if(!pause){
            		if(stage==1 && fontSize<=36)
            		{
            			pButton.setEnabled(true);
            			prevButton.setEnabled(false);
            			fontSize++;
            			time++;
            			leftTitle-=7;
            			repaint();
            		}
            		else if(stage==2&&time<=250)
                    {
            			pButton.setEnabled(true);
                        if(time==50||time==100||time==150||time==200||time==250)
                            repaint();
                        time++;
                    }
            		else if(stage==3&&time<=250){
            			if(time==50)
            				repaint();
            			else
            				time++;
            		}
            		else
            		{
            			pButton.setEnabled(false);
            		}
            	}
            	// Now the thread checks to see if it should suspend itself
            	if ( threadSuspended ) {
            		synchronized( this ) {
            			while ( threadSuspended ) {
            				System.out.println("run(): waiting");
            				wait();
            			}
            		}
            	}
                System.out.println(time);
                System.out.println("run(): sleeping");
                Thread.sleep( 41 );  // interval given in milliseconds
            }
        }
        catch (InterruptedException e) { }
        System.out.println("run(): end");
		
	}
	
	@Override
	public void paint(Graphics g){
		System.out.println("paint()");
        g.clearRect(0, 0, 800, 600);
        switch(stage)
        {
            case 1:
                stageOne(g);
                break;
            case 2:
            	stageTwo(g);
            	break;
            case 3:
            	stageThree(g);
            	break;
        }
		
	}
	
	public void drawTranslatableString( Graphics g, String str , int x, int y)
    {
		String write = trs.TranslateString(str);
        g.drawString(write,x,y);
    }
	
	public void drawTranslatableDigit( Graphics g, Integer str , int x, int y)
    {
		String write = trs.TranslateDigit(str);
        g.drawString(write,x,y);
    }
	
	public void stageOne( Graphics g ){
		
		super.paint(g);
        g.setColor( Color.green );
        setBackground(Color.BLACK);
        g.setFont(new Font("Verdana", Font.PLAIN, fontSize));
        drawTranslatableString(g,"Combination of Atoms", leftTitle, 300);
        
	}
	
	public void drawValenceShell( Graphics g ){
		
		
		
	}

	
	public void stageTwo( Graphics g ){
		
		setBackground(new Color(238,238,238));
		super.paint(g);
		g.setColor( Color.BLACK );
        g.setFont(new Font("Verdana", Font.PLAIN, 24));
        drawTranslatableString(g,"How Atoms Combine(Theory)", 225, 80);
        g.setFont(new Font("Verdana", Font.BOLD, 14));  
        drawTranslatableString(g,"Here we shall demonstrate how atoms combine to form compounds.", 70, 140);
        drawTranslatableString(g,"Note: Only basic compounds will be considered throughout the tutorial.",70, 160);
        
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        drawTranslatableString(g,"Lets take water for example. It is formed by the combination of", 70, 200);
        drawTranslatableString(g,"two H atoms and one O atom", 70, 220);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        
        if(time>=100)
        {
            g.setColor(Color.RED);
            g.fillOval(140,310,30,30);
            g.setColor(Color.BLACK);
            g.fillOval(175,310,10,10);
            g.drawOval(175,330,10,10);
            
            g.setColor(Color.BLUE);
            g.fillOval(220,300,50,50);
            g.setColor(Color.BLACK);
            g.fillOval(230,285,10,10);
            g.fillOval(250,285,10,10);
            g.fillOval(275,310,10,10);
            g.fillOval(275,330,10,10);
            g.fillOval(250,355,10,10);
            g.drawOval(230,355,10,10);
            g.fillOval(205,330,10,10);
            g.drawOval(205,310,10,10);
            
            g.setColor(Color.RED);
            g.fillOval(230,400,30,30);
            g.setColor(Color.BLACK);
            g.fillOval(230,385,10,10);
            g.drawOval(250,385,10,10);
            
            g.drawString("H", 150, 330);
            g.drawString("H", 240, 420);
            g.drawString("O", 240, 330);
        }
        if(time>=150)
        {
            g.drawLine(176, 340, 150, 375);
            drawTranslatableString(g,"Electron Hole",100,390);
        }
        if(time>=200)
        {
            g.fillPolygon(new int[]{320,420,410,450,410,420,320}, 
                    new int[]{320,320,300,325,350,330,330}, 7);
        }
        if(time>=250)
        {           
            g.setColor(Color.RED);
            g.fillOval(470,310,30,30);
            g.setColor(Color.BLACK);
            g.fillOval(505,310,10,10);            
            
            g.setColor(Color.BLUE);
            g.fillOval(520,300,50,50);
            g.setColor(Color.BLACK);
            g.fillOval(530,285,10,10);
            g.fillOval(550,285,10,10);
            g.fillOval(575,310,10,10);
            g.fillOval(575,330,10,10);
            g.fillOval(550,355,10,10);
            g.fillOval(505,330,10,10);
            
            g.setColor(Color.RED);
            g.fillOval(530,370,30,30);
            g.setColor(Color.BLACK);
            g.fillOval(530,355,10,10);
            
            g.drawString("H", 480, 330);
            g.drawString("H", 540, 390);
            g.drawString("O", 540, 330);
        }
        
	}
	
	public void drawIon( Graphics g , String elementSymbol, int sign, int charge, int x, int y){
		
		g.drawString(elementSymbol, x, y);
		g.setFont(new Font("Verdana", Font.BOLD, 10));
        drawTranslatableDigit(g, charge, x+19, y-10);
        g.drawString(signString[sign], x+25, y-10);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
		
	}
	
	public void stageThree( Graphics g ){
		setBackground(new Color(238,238,238));
		super.paint(g);
		g.setColor( Color.BLACK );
        g.setFont(new Font("Verdana", Font.PLAIN, 24));
        drawTranslatableString(g,"Demo Mode", 325, 80);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        
        /*
         *  String elementSymbols[] = {"Ca","Cl"};
		 *	String signString[] = {"+","-"};
	     *	int sign[] = {0,1};
		 *	int charge[] = {2,1};
         */
        
        if(time>=50){
	        drawTranslatableString(g, "How To Predict Formulaes of Compunds Using Elements", 170, 130);
			drawIon(g, elementSymbols[0], sign[0], charge[0], 300, 200);
			drawIon(g, elementSymbols[1], sign[1], charge[1], 480, 200);
        }
		
	}
	
}





