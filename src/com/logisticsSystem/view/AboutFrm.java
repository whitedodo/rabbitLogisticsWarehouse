package com.logisticsSystem.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutFrm extends JFrame {

	private JPanel contentPane;
	private int MemberID;

	public int getMemberID() {
		return MemberID;
	}

	public void setMemberID(int memberID) {
		MemberID = memberID;
	}

	/**
	 * Create the frame.
	 */
	public AboutFrm(int memberID) {
		
		setMemberID(memberID);
		
		setTitle("Á¤º¸(About)");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton("´Ý±â(Close)");
		btnClose.addActionListener(btnClosed());
		btnClose.setFont(new Font("µ¸¿ò", Font.PLAIN, 12));
		btnClose.setBounds(298, 238, 134, 23);
		contentPane.add(btnClose);
		setVisible(true);
	}
	
	private ActionListener btnClosed(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				
				MgtFrm mgtFrm = new MgtFrm();
				mgtFrm.setMemberID(getMemberID());
				mgtFrm.setVisible(true);
			}
		};
		
		return actionListener;
	}

}
