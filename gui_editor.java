import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

public class gui_editor extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_editor frame = new gui_editor();
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
	public gui_editor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 694, 376);
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel);
		
		JLabel label = new JLabel("USERNAME");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 11, 183, 36);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Role");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(0, 58, 193, 36);
		panel.add(label_1);
		
		JButton button = new JButton("Log Out");
		button.setBounds(40, 323, 122, 36);
		panel.add(button);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 105, 193, 190);
		panel.add(panel_1);
		
		JButton btnResponse = new JButton("Response");
		btnResponse.setBounds(40, 32, 122, 36);
		panel_1.add(btnResponse);
		
		JButton button_2 = new JButton("Select article");
		button_2.setBounds(40, 104, 122, 36);
		panel_1.add(button_2);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.DARK_GRAY);
		layeredPane.setBounds(192, 0, 512, 376);
		panel.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel_initial = new JPanel();
		panel_initial.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_initial, "name_1689996478926200");
		panel_initial.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Summary");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 47, 96, 32);
		panel_initial.add(lblNewLabel_1);
		
		JLabel lblTypographicalError = new JLabel(" Typographical error");
		lblTypographicalError.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypographicalError.setBounds(0, 150, 132, 24);
		panel_initial.add(lblTypographicalError);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 78, 502, 70);
		panel_initial.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 174, 502, 70);
		panel_initial.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnNewButton = new JButton("Errors");
		btnNewButton.setBounds(0, 255, 122, 32);
		panel_initial.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Strong Accept", "Weak Accept", "Weak Reject", " Strong Reject"}));
		comboBox.setBounds(0, 333, 243, 32);
		panel_initial.add(comboBox);
		
		JLabel lblJudgement = new JLabel("Verdict");
		lblJudgement.setHorizontalAlignment(SwingConstants.CENTER);
		lblJudgement.setBounds(0, 298, 96, 32);
		panel_initial.add(lblJudgement);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(336, 330, 132, 38);
		panel_initial.add(btnSubmit);
		
		JLabel lblNewLabel = new JLabel("INITIAL REVIEW");
		lblNewLabel.setBounds(200, 0, 100, 36);
		panel_initial.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_final = new JPanel();
		layeredPane.add(panel_final, "name_1692316316589000");
		panel_final.setLayout(null);
		panel_final.setBackground(Color.LIGHT_GRAY);
		
		JLabel label_2 = new JLabel("Summary");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(0, 47, 96, 32);
		panel_final.add(label_2);
		
		JLabel label_3 = new JLabel(" Typographical error");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(0, 150, 132, 24);
		panel_final.add(label_3);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 78, 502, 70);
		panel_final.add(scrollPane_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(0, 174, 502, 70);
		panel_final.add(scrollPane_3);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(106, 255, 243, 32);
		panel_final.add(comboBox_1);
		
		JLabel lblVerdict = new JLabel("Verdict");
		lblVerdict.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerdict.setBounds(0, 255, 96, 32);
		panel_final.add(lblVerdict);
		
		JButton button_3 = new JButton("Submit");
		button_3.setBounds(168, 327, 132, 38);
		panel_final.add(button_3);
		
		JLabel lblFinalReview = new JLabel("FINAL REVIEW");
		lblFinalReview.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalReview.setBounds(200, 0, 100, 36);
		panel_final.add(lblFinalReview);
		
		JPanel panel_select3 = new JPanel();
		panel_select3.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_select3, "name_1690330076326300");
	}
}
