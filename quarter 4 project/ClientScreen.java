import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class ClientScreen extends JPanel implements ActionListener, KeyListener, MouseListener{
    private String message;
    private JTextField messageInput;
    private JButton sendButton;
    private PrintWriter out;
    private Socket socket;
    private DLList<String> messages;

	public ClientScreen(){
	    this.setLayout(null);
        messageInput = new JTextField();
        messageInput.setFont(new Font("Arial", Font.PLAIN, 20));
        messageInput.setHorizontalAlignment(SwingConstants.LEFT);
        messageInput.setBounds(40, 663, 800, 30);
        messageInput.setText("");
        this.add(messageInput);

        sendButton = new JButton();
        sendButton.setFont(new Font("Arial", Font.BOLD, 20));
        sendButton.setHorizontalAlignment(SwingConstants.CENTER);
        sendButton.setBounds(902, 663, 200, 30);
        sendButton.setText("Send");
        this.add(sendButton);
        sendButton.addActionListener(this);

		addMouseListener(this);
		addKeyListener(this);
        messages = new DLList<String>();
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}
    public void connect() throws IOException{
		String hostName = "localhost"; 
		int portNumber = 1024;
	
		try {
            socket = new Socket(hostName, portNumber);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
                System.out.println("Waiting for message");
                message = in.readLine();
                if(message==null){
                    break;
                }
                else if(message!=null && !message.equals("null")){
                    messages.add(message);
                    System.out.println(message);
                }
				repaint();
			}
            in.close();
            socket.close();
		} catch (UnknownHostException e){
			System.err.println("Host unknown: " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

	@Override
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("Arial",Font.PLAIN,20));
        g.setColor(Color.BLACK);
        int x= 50;
        int y = 50;
        for(int i=0; i<messages.size(); i++){
			if(messages.get(i)!=null && !messages.get(i).equals("null")){
				g.drawString("Message: " + messages.get(i),x,y);
            	y+=20;
			}
        }
	}
	public void actionPerformed(ActionEvent e){
        if(e.getSource()==sendButton){
            String message = messageInput.getText();
            messageInput.setText("");

            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            } catch (IOException ex) {
               
            }
        }
        repaint();
    }
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

}