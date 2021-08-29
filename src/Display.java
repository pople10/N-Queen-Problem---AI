import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import algorithms.InterfaceAlgorithm;
import factory.MethodFactoryBuilder;
import utility.Helper;
import utility.Methods;
import utility.Validator;


public class Display extends JFrame {
	private static final long serialVersionUID = 1L;
	public static HashMap<Methods,String> methodLabels = new HashMap<Methods,String>();
	public static HashMap<String,Methods> methodLabelsRev = new HashMap<String,Methods>();
	public Methods selectedMethod = null;
	public int selectedNum = 0;
	
	public void showErrorMessage(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showSuccessMessage(String msg)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/done.png"));
		} catch (IOException e) {
			this.showErrorMessage("Loading image is getting an error");
		}
		Image image = img.getScaledInstance((int)30, (int)30, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		JOptionPane.showMessageDialog(null, msg, "Succès", JOptionPane.INFORMATION_MESSAGE, 
				icon);
	}
	
	public void showWarningMessage(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "Avertissement", JOptionPane.WARNING_MESSAGE);
	}
	
	public void showSplashScreen()
	{
		JWindow window = new JWindow();
		window.getContentPane().add(
		    new JLabel(new ImageIcon("images/splashscreen.jpg")), SwingConstants.CENTER);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setSize(600,600);
	    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
	    window.setLocation(x,y);
		window.setVisible(true);
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    this.showErrorMessage("Loading image is getting an error");
		}
		window.setVisible(false);
		this.getOptionGUI();
		window.dispose();
	}
	
	public void getOptionGUI()
	{
		JFrame window = new JFrame("Commancer");
		window.setIconImage((new ImageIcon("images/logo.png")).getImage());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3,3,3,3);
        JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Génitique");
		comboBox.addItem("Escalade");
		comboBox.addItem("Escalade Version 2");
		comboBox.addItem("Par Fisceaux");
		JLabel infoMethod = new JLabel("Veuillez choisir la méthode du recherche");
		JLabel infoNumber = new JLabel("Veuillez choisir le nombre de reines");
		JTextField number = new JTextField();
		JButton button = new JButton("Selectionner");
		number.setText(8+"");
		number.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					button.doClick();
			}
        });
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/tap.png"));
		} catch (IOException e) {
			this.showErrorMessage("Loading image is getting an error");
		}
		Image image = img.getScaledInstance((int)20, (int)20, Image.SCALE_SMOOTH);
		ImageIcon finalIcon = new ImageIcon(image);
		button.setIcon(finalIcon);
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String num = number.getText();
            	String methodLabelSelectedTmp = (String) comboBox.getSelectedItem();
            	Methods method = Display.methodLabelsRev.get(methodLabelSelectedTmp);
            	if(!Validator.isInteger(num))
            	{
            		showErrorMessage("Le numéro n'est pas un entier");
            	}
            	else if(method==null)
            	{
            		showErrorMessage("Choix de méthode est erroné");
            	}
            	else if(Integer.parseInt(num)<4)
            	{
            		showErrorMessage("Veuillez choisir un entier plus de 4");
            	}
            	else
            	{
            		window.setVisible(false);
            		JWindow loader = new JWindow();
            		loader.getContentPane().add(
            			    new JLabel(new ImageIcon("images/wait.png")), SwingConstants.CENTER);
            		loader.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
            		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            		loader.pack();
            	    int x = (int) ((dimension.getWidth() - loader.getWidth()) / 2);
            	    int y = (int) ((dimension.getHeight() - loader.getHeight()) / 2);
            	    loader.setLocation(x,y);
            		loader.setVisible(true);
            		selectedMethod=method;
            		selectedNum = Integer.parseInt(num);
            		InterfaceAlgorithm methodSolving = MethodFactoryBuilder.build(selectedMethod,selectedNum);
            		methodSolving.solve();
            		int[] result = methodSolving.getStateVector();
            		loader.setVisible(false);
            		if(result!=null)
            		{
            			show(selectedNum,result,methodLabelSelectedTmp);
            			if(methodSolving.isSolutionFound())
                			showSuccessMessage("La solution a été trouvé\nTrouvé dans "+
                		Helper.secondsToString(methodSolving.getDuration()));
                		else
                			showWarningMessage("La solution n'a pas été trouvé\nTimed out");
            		}
            		else
            			showErrorMessage("Timed out");
            	}
            }
        });
		button.setVerticalTextPosition(JButton.CENTER);
		comboBox.setBackground(Color.white);
		button.setBackground(Color.decode("#337ab7"));
		button.setFocusable(false);
		window.getContentPane().add(infoMethod,gbc);
		window.getContentPane().add(comboBox, gbc);
		window.getContentPane().add(infoNumber, gbc);
		window.getContentPane().add(number, gbc);
		window.getContentPane().add(button, gbc);
		window.pack();
		window.setLocationRelativeTo(null);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
	    window.setLocation(x,y);
	    window.setResizable(false);
		window.setVisible(true);
	}
	
	public void begin()
	{
		this.showSplashScreen();
	}
	
	public void emptyComponants()
	{
		this.removeAll();
	}
	
	public void show(int n,int [] data,String method)
	{
		this.setSize(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x,y);
		this.setLayout( new GridLayout(n,n));
		for(int i = 0;i<n;i++)
		{
			for(int j = 0;j<n;j++)
			{
				JButton btn = new JButton();
				btn.setEnabled(false);
				btn.setFocusable(false);
				if(data[j]==i+1)
				{
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File("images/queen.png"));
					} catch (IOException e) {
						this.showErrorMessage("Loading image is getting an error");
					}
					Image image = img.getScaledInstance((int)600/n, (int)600/n, Image.SCALE_SMOOTH);
					ImageIcon finalIcon = new ImageIcon(image);
					btn.setIcon(finalIcon);
					btn.setDisabledIcon(finalIcon);
					btn.setHorizontalTextPosition(JButton.CENTER);
					btn.setVerticalTextPosition(JButton.CENTER);
				}
				if((i+j)%2==0)
					btn.setBackground(Color.WHITE);
				else
					btn.setBackground(Color.GRAY);
				this.add(btn);
			}
		}
		this.setIconImage((new ImageIcon("images/logo.png")).getImage());
		this.setTitle("N Queens' Problem - Algorithme "+method);
		this.setVisible(true);
	}
	
	public Display()
	{
		Display.methodLabels.put(Methods.GENETIC, "Génitique");
		Display.methodLabels.put(Methods.HILLCLIMBING, "Escalade");
		Display.methodLabels.put(Methods.HILLCLIMBINGV2, "Escalade Version 2");
		Display.methodLabels.put(Methods.LOCAL_BEAM_SEARCH, "Par Fisceaux");
		Display.methodLabelsRev.put("Génitique",Methods.GENETIC);
		Display.methodLabelsRev.put("Escalade",Methods.HILLCLIMBING);
		Display.methodLabelsRev.put("Escalade Version 2",Methods.HILLCLIMBINGV2);
		Display.methodLabelsRev.put("Par Fisceaux",Methods.LOCAL_BEAM_SEARCH);
	}
	
	public static void main(String[] f)
	{
		Display instance = new Display();
		instance.begin();
	}


}
