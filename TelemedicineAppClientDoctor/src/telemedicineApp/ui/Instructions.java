package telemedicineApp.ui;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Instructions extends JFrame {

	/**
	 * Create the frame.
	 */
	public Instructions(JFrame appDisplay) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Patient");
		appDisplay.setEnabled(false);
		setBounds(100, 100, 639, 386);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 623, 347);
		getContentPane().add(scrollPane);

		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		

		File file = new File("files\\doctor_instructions.txt");
		String instructions = "";
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = "";
			while ((line = reader.readLine()) != null) {
				instructions += line + "\n";
			}
			reader.close();
		} catch (IOException e) {

		}
		textPane.setText(instructions);

		// CLOSING CONNECTION WHEN EXIT
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				appDisplay.setEnabled(true);
			}
		};
		this.addWindowListener(exitListener);

	}
}
