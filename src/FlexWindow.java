import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextArea;
import javax.swing.JMenuBar;
import java.awt.GridLayout;
import java.awt.Color;

public class FlexWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// this is the program output display area
	private TextArea textArea;

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
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 424);

		// This menu bar contains the read-only labels for the variables shown
		// in the Task lines
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(220, 220, 220));
		menuBar.setForeground(new Color(0, 0, 0));
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		textArea = new TextArea();
		textArea.setEditable(false);
		contentPane.add(textArea);

	}

	public TextArea getTextArea() {
		return textArea;
	}

}