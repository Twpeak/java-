package wuziqi;

import javax.swing.*;
import java.awt.*;
@SuppressWarnings("serial")
public class Explain extends JFrame{
    JLabel l1;
    JLabel l2;
    public static void main(String[] args) {
        new Explain();
    }
    public Explain() {
//		super(MyFrame,"",true);
        this.setSize(700,800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(2);

        l1=new JLabel();
        Icon c=new ImageIcon("youxi/src/1.png");
        l1.setBounds(90,0,580,488);
        l1.setIcon(c);
        this.add(l1);

        l2=new JLabel();
        l2.setFont(new Font("΢���ź�",Font.BOLD,25));

//		��Ҫ�������ڱ�ǩ��\n�޷����У���Ҫ�ڱ�ǩ��ͷǰ��<html><body>�����沢��<br>��Ϊ���з�������

        l2.setText("<html><body>��ӭ������������Ϸ,"+"<br>����һ�������������С��Ϸ�����ַ��������������"+"<br>һ�������ⷽ�����ɲ���ϵ����Ӽ���ʤ\n"+"<br>����ϷĬ��Ϊ����ʱ�䣬��Ϸ�޶�ʱ�������������ġ�"+"<br>����0Ϊ������ʱ��"+"<br>ף���������");
        l2.setBounds(20,450,700,300);
        this.add(l2);
        l2.setVisible(true);
        this.setVisible(true);
    }

}
