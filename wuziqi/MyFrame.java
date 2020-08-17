package wuziqi;
//待更新，认输之后的message的更改，按钮的覆盖，四联的音效。
import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

//成功代码需要优化

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements MouseListener,Runnable{
    int x=0;//成员变量保存x，y的坐标值
    int y=0;
    int[][] allchess=new int[19][19];		//用二维数组保存所有棋子//数据：0表示无子  1表示黑子  2表示白子
    static Boolean isblack=true;					//信号，默认为黑子
    static String message="黑方先行";
    Boolean canplay=true;
    Thread t=new Thread(this);
    String input;
    Integer maxtime=0;//(秒)
    static AudioClip s1;
    static AudioClip s2;
    static AudioClip bgm;
    static AudioClip win;
    int blacktime=0;
    int whitetime=0;
    String blackmessage="00:00";
    String whitemessage="00:00";

    public static void main(String[] args) throws MalformedURLException {

        s1=Applet.newAudioClip(new File("youxi/src/b.wav").toURI().toURL());
        s2=Applet.newAudioClip(new File("youxi/src/d.wav").toURI().toURL());
        bgm=Applet.newAudioClip(new File("youxi/src/bgm.wav").toURI().toURL());
        win=Applet.newAudioClip(new File("youxi/src/胜利.wav").toURI().toURL());

        if(new Random().nextInt(100)%2==0) {
            message="白方先行";
            isblack=false;
        }
        new MyFrame();
        bgm.loop();
    }

    @SuppressWarnings("deprecation")
    public MyFrame()
    {
        //设置窗口属性
        this.setBounds(1000,300,970,931);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("游戏窗口");
        this.setDefaultCloseOperation(3);

        Image m=(new ImageIcon("youxi/src/图片1.png")).getImage();
        this.setIconImage(m);

        this.setVisible(true);
        //为窗口加入监听
        this.addMouseListener(this);
        t.start();
        t.suspend();


    }
    public void paint(Graphics g)
    {
        //重要！！！
        //双缓冲技术防止屏幕闪烁
        //1.创建图片缓冲区在内存里
        BufferedImage bi=new BufferedImage(970,931,BufferedImage.TYPE_INT_ARGB);
        //2.创建新的画笔，直接在内存中的图片区里，将需要的棋子棋盘画好
        Graphics g2=bi.createGraphics();
        g2.setColor(Color.BLACK);
        //绘制背景
        BufferedImage m=null;
        try {
            m=ImageIO.read(new File("youxi/src/456.jpg"));

        }
        catch(IOException e) {System.out.println("找不到文件");}

        //输出标题信息
        g2.drawImage(m,10,10,this);
        g2.setFont(new Font("楷体",Font.BOLD,50));
        g2.drawString("游戏信息:"+message,250,90);
        g2.setFont(new Font("宋体",Font.BOLD,40));
        //输出时间信息
        g2.drawString("黑方时间 "+blackmessage,75,890);
        g2.drawString("白方时间 "+whitemessage,510,890);

        //绘制棋盘
//		Graphics2D g5 = (Graphics2D)g2;			//强制转换g类型给Graphics2D类型的对象，以便调用方法
//		g5.setStroke(new BasicStroke(2.0f));	//调整画笔粗细


        //法一
//		for( int i=108;i<=812;i+=39)
//			g2.drawLine(29,i,733,i);
//		for(int i=29;i<=734;i+=39)
//			g2.drawLine(i,108,i,812);
        //法二
        for(int i=0;i<19;i++)
        {
            g2.drawLine(31, 108+39*i, 733, 108+39*i);
            g2.drawLine(31+39*i,108,31+39*i,810);
        }

        //绘制标点
        g2.fillOval(142, 219, 12, 12);//左上角
        g2.fillOval(610, 219, 12, 12);//右上角
        g2.fillOval(142, 687, 12, 12);//左下角
        g2.fillOval(610, 687, 12, 12);//右下角
        g2.fillOval(376, 453, 12, 12);//中心

        g2.fillOval(376, 219, 12, 12);
        g2.fillOval(142, 453, 12, 12);
        g2.fillOval(610, 453, 12, 12);
        g2.fillOval(376, 687, 12, 12);

        //绘制棋子
        for(int i=0;i<19;i++) {
            for(int j=0;j<19;j++)
            {
                if(allchess[i][j]==1)
                {
                    int tempx=i*39+31;
                    int tempy=j*39+108;
                    g2.fillOval(tempx-10, tempy-10,22,22);
                }
                if(allchess[i][j]==2)
                {
                    int tempx=i*39+31;
                    int tempy=j*39+108;
                    g2.setColor(Color.white);
                    g2.fillOval(tempx-10, tempy-10,20,20);
                    g2.setColor(Color.BLACK);
                    Graphics2D g3=(Graphics2D)g2;
                    g3.setStroke(new BasicStroke(1.5f));
                    g3.drawOval(tempx-10, tempy-10,22, 22);//给白子加边框
                }
            }
        }
        g.drawImage(bi,0,0,this);
//		g2.fillOval(x-10, y-10,20,20);
//		g2.setColor(Color.white);
//		g2.setStroke(new BasicStroke(1.0f));
//		g2.drawOval(x-10, y-10, 20, 20);

    }






    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @SuppressWarnings("deprecation")
    public void mousePressed(MouseEvent e) {

        int lose=100;
        int again=100;
        int r;
//		System.out.println("X="+e.getX());
//		System.out.println("Y="+e.getY());
        x=e.getX();
        y=e.getY();
        System.out.println("x= "+x+"   y= "+y);
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=112&&e.getY()<=167)
            again=JOptionPane.showConfirmDialog(this,"是否确认重新开始游戏？");
        if(again==0)
        {
            //重新开始的操作（1）把棋盘清空，allchess数组全部归零
            for(int i=0;i<19;i++)
                for(int j=0;j<19;j++)
                    allchess[i][j]=0;
//									（2）将游戏信息的显示改回到开始位置
//										(3)下一步改为随机
            r=new Random().nextInt();
            if(r%2==0)
            {
                message="白方先行";
                isblack=false;
            }else {
                message="黑方先行";
                isblack=true;
            }
            blacktime=maxtime;
            whitetime=maxtime;
            blackmessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
            whitemessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
            t.resume();
            //重新画棋盘
            this.repaint();
            //将canplay改为true
            canplay=true;
            //打开音效
            bgm.play();
        }
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=209&&e.getY()<=265)
        {

            input=JOptionPane.showInputDialog("请输入游戏最大时间(分钟)：");
            if(input!=null) {
                try {
                    maxtime=Integer.valueOf(input)*60;
                    if(maxtime<0)
                        JOptionPane.showMessageDialog(this, "请输入正确信息，不允许输入负数");
                    if(maxtime==0)
                    {
                        int result=JOptionPane.showConfirmDialog(this, "已设置∞时间，是否重新开始游戏");
                        if(result==0)
                        {
                            canplay=true;
                            again=0;
                            //重新开始的操作（1）把棋盘清空，allchess数组全部归零
                            for(int i=0;i<19;i++)
                                for(int j=0;j<19;j++)
                                    allchess[i][j]=0;

//										（2）将游戏信息的显示改回到开始位置
//											(3)下一步改为随机
                            r=new Random().nextInt();
                            if(r%2==0)
                            {
                                message="白方先行";
                                isblack=false;
                            }else {
                                message="黑方先行";
                                isblack=true;
                            }
//						blacktime=maxtime;
//						whitetime=maxtime;
                            blackmessage="无限制";
                            whitemessage="无限制";
                            //重新画棋盘
                            this.repaint();
                        }


                    }
                    if(maxtime>0)
                    {
                        int result=JOptionPane.showConfirmDialog(this, "已设置好最大时间，是否重新开始游戏");
                        if(result==0)
                        {
                            canplay=true;
                            again=0;
                            //重新开始的操作（1）把棋盘清空，allchess数组全部归零
                            for(int i=0;i<19;i++)
                                for(int j=0;j<19;j++)
                                    allchess[i][j]=0;

//										（2）将游戏信息的显示改回到开始位置
//											(3)下一步改为随机
                            r=new Random().nextInt();
                            if(r%2==0)
                            {
                                message="白方先行";
                                isblack=false;
                            }else {
                                message="黑方先行";
                                isblack=true;
                            }
                            blacktime=maxtime;
                            whitetime=maxtime;
                            blackmessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
                            whitemessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
                            t.resume();
                            bgm.loop();
                            //重新画棋盘
                            this.repaint();
                        }
                    }

                }catch (Exception e2)
                {
                    JOptionPane.showMessageDialog(this,"请输入正确的时间信息");
                }
            }else {}


        }
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=305&&e.getY()<=363)
            new Explain();
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=503&&e.getY()<=561) {
            lose =JOptionPane.showConfirmDialog(this, "是否确认认输？");
            t.suspend();
        }
        if(lose ==0)
        {
            if(isblack)
            {
                JOptionPane.showMessageDialog(this, "黑方已经认输，游戏结束！");
                bgm.stop();
                win.play();
                message="白方胜利！";
            }else {
                JOptionPane.showMessageDialog(this, "白方已经认输，游戏结束！");
                bgm.stop();
                win.play();
                message="黑方胜利！";
            }
            canplay=false;


        }
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=600&&e.getY()<=657)
            JOptionPane.showMessageDialog(this,"这是一个五子棋游戏\n\n研发人：唐文鹏\n音乐：1.烟影如画\n             2.盗将行【笛曲】\n感谢陪我一起解决问题的学长们\n(这次貌似我没学姐= =)");
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=698&&e.getY()<=753)
            System.exit(0);
        if(x>=31 && x<=733 && y>=108 && y<=810)
        {
            if(canplay) {
                //计算距离最近的交叉点，来设置坐标
                x=(x-15)/39;
                y=(y-88)/39;

                //为了避免棋子覆盖，要判断落子的坐标是否为空
                if(allchess[x][y]==0)
                {
                    if(isblack==true)		//判断是否为黑子
                    {
                        allchess[x][y]=1;		//下黑子
                        isblack=false;			//下完黑子该白子
                        message="轮到白方";
                        s1.play();
                    }else{
                        allchess[x][y]=2;	//下白子
                        isblack=true;		//下完白子该黑子
                        message="轮到黑方";
                        s2.play();
                    }
                    //repaint方法表示要重新执行一次paint方法
                    this.repaint();

                    //判断胜负，为了代码好看，把判断方法写出来
                    if(	this.Chesswin()==true)
                    {
                        t.suspend();
                        canplay=false;
                        bgm.stop();
                        win.play();
                        JOptionPane.showMessageDialog(this	, "游戏结束！"+(allchess[x][y]==1?"黑方":"白方")+"胜利");
                        if(allchess[x][y]==1)message="黑方胜利";
                        else message ="白方胜利";
                        this.repaint();
                    }

                }else {
                    JOptionPane.showMessageDialog(this,"当前位置已有棋子，请重新落子");
                }
            }
        }
    }

    public boolean Chesswin()
    {
        boolean flag=false;
        //保存多少个棋子相连
        int num=1;
        //取出当前棋子颜色
        int color =allchess[x][y];
        //为了规避边界的数组溢出问题，专门对边界情况做出判断
        //左边界
        if(x==0)
        {
            //左上角
            if(y==0)
            {
                //先判断横向(向右)
                for(int i=1;allchess[x+i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //向下
                for(int i=1;allchess[x][y+i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //判断方向( \向下方向)
                for(int i=1,j=1;allchess[x+i][y+j]==color;i++,j++,num++); if(num>=5)flag=true;	num=1;
            }
            //左下角
            else if(y==18)
            {
                //先判断横向(向右)
                for(int i=1;allchess[x+i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //判断纵向(向上)
                for(int i=1;allchess[x][y-i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //判断斜向( /向上方向)
                for(int i=1;allchess[x+i][y-i]==color;i++,num++); if(num>=5)flag=true;	num=1;
            }
            //左边界
            else
            {
                //向右
                for(int i=1;allchess[x+i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //向上
                for(int i=1;allchess[x][y-i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-i==0)break;
                }
                //向下
                for(int i=1;allchess[x][y+i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+i==18)break;
                }
                num=1;
                //判断方向( \向下方向)
                for(int i=1,j=1;allchess[x+i][y+j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+j==18)break;
                }num=1;
                //判断斜向( /向上方向)
                for(int i=1,j=1;allchess[x+i][y-j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-j==0)break;
                }num=1;
            }
        }
        //右边界
        if(x==18)
        {
            //右上角
            if(y==0)
            {
                //向下
                for(int i=1;allchess[x][y+i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //向左
                for(int i=1;allchess[x-i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //判断斜向( /向下方向)
                for(int i=1;allchess[x-i][y+i]==color;i++,num++); if(num>=5)flag=true;	num=1;
            }
            //右下角
            else if(y==18)
            {
                //判断纵向(向上)
                for(int i=1;allchess[x][y-i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //向左
                for(int i=1;allchess[x-i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //判断方向( \向上方向)
                for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++,num++); if(num>=5)flag=true;	num=1;
            }
            //右边界
            else
            {
                //向左
                for(int i=1;allchess[x-i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //向上
                for(int i=1;allchess[x][y-i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-i==0)break;
                }
                //向下
                for(int i=1;allchess[x][y+i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+i==18)break;
                }
                num=1;
                //判断斜向( /向下方向)
                for(int i=1,j=1;allchess[x-i][y+j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+j==18)break;
                }num=1;
                //判断方向( \向上方向)
                for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-j==0)break;
                }num=1;
            }
        }
        //上边界
        if(y==0&&x!=0&&x!=18)
        {
            //向下
            for(int i=1;allchess[x][y+i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
            //向左
            for(int i=1;allchess[x-i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x-i==0)break;
            }num=1;
            //向右
            for(int i=1;allchess[x+i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x+i==18)break;
            }num=1;
        }
        //下边界
        if(y==18&&x!=0&&x!=18)
        {
            //向上
            for(int i=1;allchess[x][y-i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
            //向左
            for(int i=1;allchess[x-i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x-i==0)break;
            }num=1;
            //向右
            for(int i=1;allchess[x+i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x+i==18)break;
            }num=1;
            //判断方向( \向上方向)
            for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x-i==0)break;
            }num=1;
            //判断斜向( /向上方向)
            for(int i=1,j=1;allchess[x+i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x+i==18)break;
            }num=1;
        }
        //一般评判
        if(x>0&&x<18&&y>0&&y<18) {
            //通过循环判断棋子连续
            //先判断横向(向右)
            for(int i=1;allchess[x+i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x+i==18)break;
            }
            //向左
            for(int i=1;allchess[x-i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x-i==0)break;
            }num=1;

            //向上
            for(int i=1;allchess[x][y-i]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-i==0)break;
            }
            //向下
            for(int i=1;allchess[x][y+i]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(y+i==18)break;
            }
            num=1;
            //判断方向( \向下方向)
            for(int i=1,j=1;allchess[x+i][y+j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y+i==18||x+i==18)break;
            }
            //判断方向( \向上方向)
            for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x-i==0)break;
            }num=1;
            //判断斜向( /向上方向)
            for(int i=1,j=1;allchess[x+i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x+i==18)break;
            }
            //判断斜向( /向下方向)
            for(int i=1,j=1;allchess[x-i][y+j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y+j==18||x-i==0)break;
            }num=1;
        }
        return flag;
    }


    public void run()
    {
//		maxtime=Integer.valueOf(input);
//		System.out.println(maxtime+"    1");
        if(maxtime>0)
        {
//			System.out.println(maxtime+"     2");
//			System.out.println(maxtime);
            while(true)
            {
//				System.out.println(1);

                if(isblack)
                {blacktime--;
                    if(blacktime==0) {
                        JOptionPane.showInternalMessageDialog(this, "黑方超时，游戏结束");
                    }
                }
                else{whitetime--;
                    if(whitetime==0) {
                        JOptionPane.showInternalMessageDialog(this, "白方超时，游戏结束");
                    }
                }
                blackmessage=blacktime/3600+":"+(blacktime/60-blacktime/3600*60)+":"+(blacktime-blacktime/60*60);
                whitemessage=whitetime/3600+":"+(whitetime/60-whitetime/3600*60)+":"+(whitetime-whitetime/60*60);
                this.repaint();
                try {Thread.sleep(1000);}
                catch (Exception e) {}

            }
        }
    }



    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
