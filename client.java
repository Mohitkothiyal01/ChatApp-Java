import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.util.*; 
import java.text.*;
import java.net.*;
import java.io.*;

public class client implements ActionListener {

    JTextField text;
    static JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();

    static DataOutputStream dout;

    client() {

        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 250, 50);
        p1.setLayout(null);
        f.add(p1);

        p2 = new JPanel();

        p2.setBounds(0, 53, 248, 315);
        p2.setLayout(null);
        f.add(p2);

        text = new JTextField();
        text.setBounds(0, 370, 180, 30);
        text.setFont(new Font("SAN_SARRIF", Font.PLAIN, 15));

        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(180, 370, 70, 30);
        send.setBackground(new Color(7, 94, 82));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);

        ImageIcon bkbtn = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image bkbtn2 = bkbtn.getImage().getScaledInstance(22, 20,
                Image.SCALE_DEFAULT);
        ImageIcon bkbtn3 = new ImageIcon(bkbtn2);
        JLabel back = new JLabel(bkbtn3);
        back.setBounds(5, 15, 15, 20);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                f.setVisible(false);
            }
        });

        ImageIcon dp = new ImageIcon(ClassLoader.getSystemResource("icons/Ram.png"));
        Image dp1 = dp.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon dp2 = new ImageIcon(dp1);
        JLabel profile = new JLabel(dp2);
        profile.setBounds(35, 12, 30, 30);
        p1.add(profile);

        JLabel name = new JLabel("Shiva");
        name.setBounds(75, 12, 150, 25);
        name.setForeground(Color.WHITE);
        p1.add(name);

        JLabel Active = new JLabel("Active now");
        Active.setBounds(73, 30, 80, 9);
        Active.setForeground(Color.white);
        p1.add(Active);

        ImageIcon cl = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image cl1 = cl.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon cl2 = new ImageIcon(cl1);
        JLabel call = new JLabel(cl2);
        call.setBounds(180, 15, 25, 20);
        p1.add(call);

        ImageIcon vcl = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image vcl1 = vcl.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon vcl2 = new ImageIcon(vcl1);
        JLabel videocall = new JLabel(vcl2);
        videocall.setBounds(145, 15, 25, 25);
        p1.add(videocall);

        ImageIcon op = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image op1 = op.getImage().getScaledInstance(8, 20, Image.SCALE_DEFAULT);
        ImageIcon op2 = new ImageIcon(op1);
        JLabel option = new JLabel(op2);
        option.setBounds(210, 14, 25, 25);
        p1.add(option);

        f.setSize(250, 400);
        f.setLocation(500, 80);
        f.getContentPane().setBackground(Color.white);
        f.setUndecorated(true);
        f.setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            // System.out.println(out);
            // JLabel output = new JLabel(out);

            JPanel p3 = formatLabel(out);
            // p3.add(output);

            p2.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            p2.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style = \"width:120px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(12, 12, 12, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {

                p2.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                p2.add(vertical, BorderLayout.PAGE_START);

                f.validate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
