import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class FlexWindow extends JFrame implements KeyListener {

	private String[] inputString = new String[1];
	private JPanel contentPane;
	private TextArea textArea;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlexWindow frame = new FlexWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FlexWindow() {
		setTitle("Flex Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		textField.addKeyListener(this);
	
	
		
	}

	public TextArea getTextArea(){
		return textArea;
	}

	public JTextField getTextField(){
		return this.textField;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	
		int inputKeyCode = e.getKeyCode();
		if(inputKeyCode == KeyEvent.VK_ENTER){
			
		
			String tempInputString = new String("");
			tempInputString = this.getTextField().getText();
			this.setInputString(tempInputString);
			
			
			this.getTextField().setText("");
			this.getTextArea().append(tempInputString + "\n");
				
			
		}
					
	}
		
	

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public void setInputString(String tempString){
		this.inputString[0] = tempString;
	}
	
	public String getInputString(){
		return this.inputString[0];
	}





}