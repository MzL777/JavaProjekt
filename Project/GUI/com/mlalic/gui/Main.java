package com.mlalic.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.mlalic.bl.PersonelHandler;

public class Main {

	private static final PersonelHandler PERSONEL_HANDLER = new PersonelHandler();

	private JFrame frame;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private String getPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter a password:");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "Cancel", "OK" };
		int option = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 1) // pressing OK button
		{
			return new String(pass.getPassword());
		}
		return null;
	}

	private int authorizePersonel() {
		boolean loggedIn = false;
		int idPersonel = -1;

		do {
			String username;
			String password;

			do {
				username = JOptionPane.showInputDialog(null, "Username:", "Login", 1);
				if (username == null) {
					System.exit(0);
				} else if (username.isEmpty()) {
					JOptionPane.showMessageDialog(null, (Object) ("Username is required!"), "Error", 0);
				} else
					break;
			} while (true);

			do {
				password = getPassword();
				if (password == null || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, (Object) ("Password is required!"), "Error", 0);
				} else
					break;
			} while (true);

			int authRes = 0;
			try {
				authRes = PERSONEL_HANDLER.authorizePersonel(username, password);

				if (authRes == 1) {
					idPersonel = PERSONEL_HANDLER.getPersonelIDByUsernamePassword(username, password);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, (Object) ("Check your connection and try again!"
						+ System.lineSeparator() + "Program will exit now!"), "Error", 0);
				System.exit(0);
			}
			if (authRes == 1) {
				loggedIn = true;
			} else {
				JOptionPane
						.showMessageDialog(null,
								(Object) ("Unable to login!" + System.lineSeparator()
										+ (authRes == 2 ? "Username does not exist!"
												: authRes == 3 ? "Password not valid!" : "Well, this is weird!")),
								"Error", 0);
			}
		} while (!loggedIn);

		return idPersonel;
	}

	private void initialize() {
		int loginId = authorizePersonel();

		frame = new JFrame();
		frame.setBounds(new Rectangle(0, 0, 1060, 700));
		frame.setPreferredSize(new Dimension(1060, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// todo, todo, todo todo todo todo todooooo, todo todo
		try {
			int loginTypeId = (PERSONEL_HANDLER.getPersonel(loginId)).getPersonelTypeID();

			// logged in as reception executive
			if (loginTypeId == 1) {
				frame.getContentPane().add(new ReceptionExecutiveFrame(), BorderLayout.CENTER);
				frame.setTitle("Logged in as reception executive!");
			}
			// logged in as doctor/physician
			else if (loginTypeId == 2) {
				frame.getContentPane().add(new DoctorFrame(loginId), BorderLayout.CENTER);
				frame.setTitle("Logged in as Doctor/physician!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, (Object) ("Check your connection and try again!"
					+ System.lineSeparator() + "Program will exit now!"), "Error", 0);
			System.exit(0);
		}

	}
}
