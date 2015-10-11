import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextArea;
import javax.swing.JMenuBar;
import java.awt.Label;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Color;

public class FlexWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// this is the program output display area
	private TextArea textArea;
	private Button button;

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
		setBackground(new Color(255, 235, 205));
		setTitle("Flex Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 424);
		
		// This menu bar contains the read-only labels for the variables shown in the Task lines
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(220, 220, 220));
		menuBar.setForeground(new Color(0, 0, 0));
		setJMenuBar(menuBar);
		
		Label label = new Label("Date,");
		label.setForeground(new Color(255, 0, 0));
		menuBar.add(label);
		
		Label label_1 = new Label("Start,");
		label_1.setForeground(new Color(255, 165, 0));
		menuBar.add(label_1);
		
		Label label_2 = new Label("End,");
		label_2.setForeground(new Color(255, 255, 0));
		menuBar.add(label_2);
		
		Label label_3 = new Label("Title,");
		label_3.setForeground(new Color(34, 139, 34));
		menuBar.add(label_3);
		
		Label label_4 = new Label("Description,");
		label_4.setForeground(new Color(0, 0, 205));
		menuBar.add(label_4);
		
		Label label_5 = new Label("Priority,");
		label_5.setForeground(new Color(65, 105, 225));
		menuBar.add(label_5);
		
		Label label_6 = new Label("Category");
		label_6.setForeground(new Color(148, 0, 211));
		menuBar.add(label_6);
		

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea = new TextArea();
		textArea.setEditable(false);
		contentPane.add(textArea);
		
		
	
		
	}

	public TextArea getTextArea(){
		return textArea;
	}

}