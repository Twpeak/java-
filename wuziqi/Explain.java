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
        l2.setFont(new Font("微软雅黑",Font.BOLD,25));

//		重要！！！在标签里\n无法换行，需要在标签开头前加<html><body>，后面并以<br>作为换行符！！！

        l2.setText("<html><body>欢迎来到五子棋游戏,"+"<br>这是一个经典的五子棋小游戏，开局方由随机函数定义"+"<br>一方在任意方向连成不间断的五子即获胜\n"+"<br>该游戏默认为无限时间，游戏限定时间可在设置里更改。"+"<br>输入0为即无限时间"+"<br>祝大家玩的愉快");
        l2.setBounds(20,450,700,300);
        this.add(l2);
        l2.setVisible(true);
        this.setVisible(true);
    }

}
