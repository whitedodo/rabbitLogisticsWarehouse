/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: Function.java
 * Created Date: 2019-07-27
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */
package com.logisticsSystem.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.BadLocationException;

public class Function {

	public KeyAdapter checkKeyAdapter(){
		
		KeyAdapter keyAdapter = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
					if (!((Character.isDigit(c) || 
							(c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
						e.consume();
				}
			}
		};
		
		return keyAdapter;
	}
}

