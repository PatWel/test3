package rpggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class MessagePanel extends JPanel {

	int messageWidth =300;
	int messageHeight = 300;
	public JTextArea  message;
	
	Font messageFont = 	new Font("Minecraft Seven",Font.BOLD,20);
	
	public MessagePanel()
	{
		this.setPreferredSize(new Dimension(messageWidth, messageHeight));
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(400, 200, messageWidth, messageHeight);
		
		message = new JTextArea();
		message.setPreferredSize(new Dimension(messageWidth, messageHeight));
		message.setBackground(Color.darkGray);
		message.setForeground(Color.WHITE);
		message.setFont(messageFont);
		message.setEditable(false);
		message.disable();
		//message.se
		this.add(message);
		message.setText(">> ");
	}
	
	public void SetMessageText(String message) {
		this.message.setText(message);
	}

	public void AddMessageText(String message, boolean suppressPrompt) {
		
		//String tmp = this.message.getText();
		//tmp = message + "\r\n" + tmp + ">> ";
		
		if (suppressPrompt) {
			this.message.append(message);
		}
		else this.message.append(message + "\r\n" + ">> ");
		
		int count = this.message.getLineCount();
		//System.out.println("Count: " + count);
		//this.message.
		
		while (count > 6)
		{
			int end = 0;
			try {
				end = this.message.getLineEndOffset(0);
				//System.out.println("end = " + end);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.message.replaceRange("", 0, end);
			count = this.message.getLineCount();
		}
		//this.message.scrol 
//		if (this.message.getLineCount()>0)
//		{
//		int end = 0;
//		try {
//			end = this.message.getLineEndOffset(0);
//			System.out.print("end = " + end);
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.message.replaceRange("", 0, end);
//		}
		//this.message.setText(tmp);
	}
}
