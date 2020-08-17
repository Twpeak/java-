package wuziqi;
//�����£�����֮���message�ĸ��ģ���ť�ĸ��ǣ���������Ч��
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

//�ɹ�������Ҫ�Ż�

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements MouseListener,Runnable{
    int x=0;//��Ա��������x��y������ֵ
    int y=0;
    int[][] allchess=new int[19][19];		//�ö�ά���鱣����������//���ݣ�0��ʾ����  1��ʾ����  2��ʾ����
    static Boolean isblack=true;					//�źţ�Ĭ��Ϊ����
    static String message="�ڷ�����";
    Boolean canplay=true;
    Thread t=new Thread(this);
    String input;
    Integer maxtime=0;//(��)
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
        win=Applet.newAudioClip(new File("youxi/src/ʤ��.wav").toURI().toURL());

        if(new Random().nextInt(100)%2==0) {
            message="�׷�����";
            isblack=false;
        }
        new MyFrame();
        bgm.loop();
    }

    @SuppressWarnings("deprecation")
    public MyFrame()
    {
        //���ô�������
        this.setBounds(1000,300,970,931);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("��Ϸ����");
        this.setDefaultCloseOperation(3);

        Image m=(new ImageIcon("youxi/src/ͼƬ1.png")).getImage();
        this.setIconImage(m);

        this.setVisible(true);
        //Ϊ���ڼ������
        this.addMouseListener(this);
        t.start();
        t.suspend();


    }
    public void paint(Graphics g)
    {
        //��Ҫ������
        //˫���弼����ֹ��Ļ��˸
        //1.����ͼƬ���������ڴ���
        BufferedImage bi=new BufferedImage(970,931,BufferedImage.TYPE_INT_ARGB);
        //2.�����µĻ��ʣ�ֱ�����ڴ��е�ͼƬ�������Ҫ���������̻���
        Graphics g2=bi.createGraphics();
        g2.setColor(Color.BLACK);
        //���Ʊ���
        BufferedImage m=null;
        try {
            m=ImageIO.read(new File("youxi/src/456.jpg"));

        }
        catch(IOException e) {System.out.println("�Ҳ����ļ�");}

        //���������Ϣ
        g2.drawImage(m,10,10,this);
        g2.setFont(new Font("����",Font.BOLD,50));
        g2.drawString("��Ϸ��Ϣ:"+message,250,90);
        g2.setFont(new Font("����",Font.BOLD,40));
        //���ʱ����Ϣ
        g2.drawString("�ڷ�ʱ�� "+blackmessage,75,890);
        g2.drawString("�׷�ʱ�� "+whitemessage,510,890);

        //��������
//		Graphics2D g5 = (Graphics2D)g2;			//ǿ��ת��g���͸�Graphics2D���͵Ķ����Ա���÷���
//		g5.setStroke(new BasicStroke(2.0f));	//�������ʴ�ϸ


        //��һ
//		for( int i=108;i<=812;i+=39)
//			g2.drawLine(29,i,733,i);
//		for(int i=29;i<=734;i+=39)
//			g2.drawLine(i,108,i,812);
        //����
        for(int i=0;i<19;i++)
        {
            g2.drawLine(31, 108+39*i, 733, 108+39*i);
            g2.drawLine(31+39*i,108,31+39*i,810);
        }

        //���Ʊ��
        g2.fillOval(142, 219, 12, 12);//���Ͻ�
        g2.fillOval(610, 219, 12, 12);//���Ͻ�
        g2.fillOval(142, 687, 12, 12);//���½�
        g2.fillOval(610, 687, 12, 12);//���½�
        g2.fillOval(376, 453, 12, 12);//����

        g2.fillOval(376, 219, 12, 12);
        g2.fillOval(142, 453, 12, 12);
        g2.fillOval(610, 453, 12, 12);
        g2.fillOval(376, 687, 12, 12);

        //��������
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
                    g3.drawOval(tempx-10, tempy-10,22, 22);//�����Ӽӱ߿�
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
            again=JOptionPane.showConfirmDialog(this,"�Ƿ�ȷ�����¿�ʼ��Ϸ��");
        if(again==0)
        {
            //���¿�ʼ�Ĳ�����1����������գ�allchess����ȫ������
            for(int i=0;i<19;i++)
                for(int j=0;j<19;j++)
                    allchess[i][j]=0;
//									��2������Ϸ��Ϣ����ʾ�Ļص���ʼλ��
//										(3)��һ����Ϊ���
            r=new Random().nextInt();
            if(r%2==0)
            {
                message="�׷�����";
                isblack=false;
            }else {
                message="�ڷ�����";
                isblack=true;
            }
            blacktime=maxtime;
            whitetime=maxtime;
            blackmessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
            whitemessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
            t.resume();
            //���»�����
            this.repaint();
            //��canplay��Ϊtrue
            canplay=true;
            //����Ч
            bgm.play();
        }
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=209&&e.getY()<=265)
        {

            input=JOptionPane.showInputDialog("��������Ϸ���ʱ��(����)��");
            if(input!=null) {
                try {
                    maxtime=Integer.valueOf(input)*60;
                    if(maxtime<0)
                        JOptionPane.showMessageDialog(this, "��������ȷ��Ϣ�����������븺��");
                    if(maxtime==0)
                    {
                        int result=JOptionPane.showConfirmDialog(this, "�����á�ʱ�䣬�Ƿ����¿�ʼ��Ϸ");
                        if(result==0)
                        {
                            canplay=true;
                            again=0;
                            //���¿�ʼ�Ĳ�����1����������գ�allchess����ȫ������
                            for(int i=0;i<19;i++)
                                for(int j=0;j<19;j++)
                                    allchess[i][j]=0;

//										��2������Ϸ��Ϣ����ʾ�Ļص���ʼλ��
//											(3)��һ����Ϊ���
                            r=new Random().nextInt();
                            if(r%2==0)
                            {
                                message="�׷�����";
                                isblack=false;
                            }else {
                                message="�ڷ�����";
                                isblack=true;
                            }
//						blacktime=maxtime;
//						whitetime=maxtime;
                            blackmessage="������";
                            whitemessage="������";
                            //���»�����
                            this.repaint();
                        }


                    }
                    if(maxtime>0)
                    {
                        int result=JOptionPane.showConfirmDialog(this, "�����ú����ʱ�䣬�Ƿ����¿�ʼ��Ϸ");
                        if(result==0)
                        {
                            canplay=true;
                            again=0;
                            //���¿�ʼ�Ĳ�����1����������գ�allchess����ȫ������
                            for(int i=0;i<19;i++)
                                for(int j=0;j<19;j++)
                                    allchess[i][j]=0;

//										��2������Ϸ��Ϣ����ʾ�Ļص���ʼλ��
//											(3)��һ����Ϊ���
                            r=new Random().nextInt();
                            if(r%2==0)
                            {
                                message="�׷�����";
                                isblack=false;
                            }else {
                                message="�ڷ�����";
                                isblack=true;
                            }
                            blacktime=maxtime;
                            whitetime=maxtime;
                            blackmessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
                            whitemessage=maxtime/3600+":"+(maxtime/60-maxtime/3600*60)+":"+(maxtime-maxtime/60*60);
                            t.resume();
                            bgm.loop();
                            //���»�����
                            this.repaint();
                        }
                    }

                }catch (Exception e2)
                {
                    JOptionPane.showMessageDialog(this,"��������ȷ��ʱ����Ϣ");
                }
            }else {}


        }
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=305&&e.getY()<=363)
            new Explain();
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=503&&e.getY()<=561) {
            lose =JOptionPane.showConfirmDialog(this, "�Ƿ�ȷ�����䣿");
            t.suspend();
        }
        if(lose ==0)
        {
            if(isblack)
            {
                JOptionPane.showMessageDialog(this, "�ڷ��Ѿ����䣬��Ϸ������");
                bgm.stop();
                win.play();
                message="�׷�ʤ����";
            }else {
                JOptionPane.showMessageDialog(this, "�׷��Ѿ����䣬��Ϸ������");
                bgm.stop();
                win.play();
                message="�ڷ�ʤ����";
            }
            canplay=false;


        }
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=600&&e.getY()<=657)
            JOptionPane.showMessageDialog(this,"����һ����������Ϸ\n\n�з��ˣ�������\n���֣�1.��Ӱ�续\n             2.�����С�������\n��л����һ���������ѧ����\n(���ò����ûѧ��= =)");
        if(e.getX()>=794&&e.getX()<=929&&e.getY()>=698&&e.getY()<=753)
            System.exit(0);
        if(x>=31 && x<=733 && y>=108 && y<=810)
        {
            if(canplay) {
                //�����������Ľ���㣬����������
                x=(x-15)/39;
                y=(y-88)/39;

                //Ϊ�˱������Ӹ��ǣ�Ҫ�ж����ӵ������Ƿ�Ϊ��
                if(allchess[x][y]==0)
                {
                    if(isblack==true)		//�ж��Ƿ�Ϊ����
                    {
                        allchess[x][y]=1;		//�º���
                        isblack=false;			//������Ӹð���
                        message="�ֵ��׷�";
                        s1.play();
                    }else{
                        allchess[x][y]=2;	//�°���
                        isblack=true;		//������Ӹú���
                        message="�ֵ��ڷ�";
                        s2.play();
                    }
                    //repaint������ʾҪ����ִ��һ��paint����
                    this.repaint();

                    //�ж�ʤ����Ϊ�˴���ÿ������жϷ���д����
                    if(	this.Chesswin()==true)
                    {
                        t.suspend();
                        canplay=false;
                        bgm.stop();
                        win.play();
                        JOptionPane.showMessageDialog(this	, "��Ϸ������"+(allchess[x][y]==1?"�ڷ�":"�׷�")+"ʤ��");
                        if(allchess[x][y]==1)message="�ڷ�ʤ��";
                        else message ="�׷�ʤ��";
                        this.repaint();
                    }

                }else {
                    JOptionPane.showMessageDialog(this,"��ǰλ���������ӣ�����������");
                }
            }
        }
    }

    public boolean Chesswin()
    {
        boolean flag=false;
        //������ٸ���������
        int num=1;
        //ȡ����ǰ������ɫ
        int color =allchess[x][y];
        //Ϊ�˹�ܱ߽������������⣬ר�ŶԱ߽���������ж�
        //��߽�
        if(x==0)
        {
            //���Ͻ�
            if(y==0)
            {
                //���жϺ���(����)
                for(int i=1;allchess[x+i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //����
                for(int i=1;allchess[x][y+i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //�жϷ���( \���·���)
                for(int i=1,j=1;allchess[x+i][y+j]==color;i++,j++,num++); if(num>=5)flag=true;	num=1;
            }
            //���½�
            else if(y==18)
            {
                //���жϺ���(����)
                for(int i=1;allchess[x+i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //�ж�����(����)
                for(int i=1;allchess[x][y-i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //�ж�б��( /���Ϸ���)
                for(int i=1;allchess[x+i][y-i]==color;i++,num++); if(num>=5)flag=true;	num=1;
            }
            //��߽�
            else
            {
                //����
                for(int i=1;allchess[x+i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //����
                for(int i=1;allchess[x][y-i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-i==0)break;
                }
                //����
                for(int i=1;allchess[x][y+i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+i==18)break;
                }
                num=1;
                //�жϷ���( \���·���)
                for(int i=1,j=1;allchess[x+i][y+j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+j==18)break;
                }num=1;
                //�ж�б��( /���Ϸ���)
                for(int i=1,j=1;allchess[x+i][y-j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-j==0)break;
                }num=1;
            }
        }
        //�ұ߽�
        if(x==18)
        {
            //���Ͻ�
            if(y==0)
            {
                //����
                for(int i=1;allchess[x][y+i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //����
                for(int i=1;allchess[x-i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //�ж�б��( /���·���)
                for(int i=1;allchess[x-i][y+i]==color;i++,num++); if(num>=5)flag=true;	num=1;
            }
            //���½�
            else if(y==18)
            {
                //�ж�����(����)
                for(int i=1;allchess[x][y-i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //����
                for(int i=1;allchess[x-i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //�жϷ���( \���Ϸ���)
                for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++,num++); if(num>=5)flag=true;	num=1;
            }
            //�ұ߽�
            else
            {
                //����
                for(int i=1;allchess[x-i][y]==color;i++,num++);	if(num>=5)flag=true;	num=1;
                //����
                for(int i=1;allchess[x][y-i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-i==0)break;
                }
                //����
                for(int i=1;allchess[x][y+i]==color;i++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+i==18)break;
                }
                num=1;
                //�ж�б��( /���·���)
                for(int i=1,j=1;allchess[x-i][y+j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y+j==18)break;
                }num=1;
                //�жϷ���( \���Ϸ���)
                for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++)
                {
                    num++;
                    if(num>=5)flag=true;
                    if(y-j==0)break;
                }num=1;
            }
        }
        //�ϱ߽�
        if(y==0&&x!=0&&x!=18)
        {
            //����
            for(int i=1;allchess[x][y+i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
            //����
            for(int i=1;allchess[x-i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x-i==0)break;
            }num=1;
            //����
            for(int i=1;allchess[x+i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x+i==18)break;
            }num=1;
        }
        //�±߽�
        if(y==18&&x!=0&&x!=18)
        {
            //����
            for(int i=1;allchess[x][y-i]==color;i++,num++);	if(num>=5)flag=true;	num=1;
            //����
            for(int i=1;allchess[x-i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x-i==0)break;
            }num=1;
            //����
            for(int i=1;allchess[x+i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x+i==18)break;
            }num=1;
            //�жϷ���( \���Ϸ���)
            for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x-i==0)break;
            }num=1;
            //�ж�б��( /���Ϸ���)
            for(int i=1,j=1;allchess[x+i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x+i==18)break;
            }num=1;
        }
        //һ������
        if(x>0&&x<18&&y>0&&y<18) {
            //ͨ��ѭ���ж���������
            //���жϺ���(����)
            for(int i=1;allchess[x+i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x+i==18)break;
            }
            //����
            for(int i=1;allchess[x-i][y]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(x-i==0)break;
            }num=1;

            //����
            for(int i=1;allchess[x][y-i]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-i==0)break;
            }
            //����
            for(int i=1;allchess[x][y+i]==color;i++)
            {
                num++;
                if(num>=5)flag=true;
                if(y+i==18)break;
            }
            num=1;
            //�жϷ���( \���·���)
            for(int i=1,j=1;allchess[x+i][y+j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y+i==18||x+i==18)break;
            }
            //�жϷ���( \���Ϸ���)
            for(int i=1,j=1;allchess[x-i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x-i==0)break;
            }num=1;
            //�ж�б��( /���Ϸ���)
            for(int i=1,j=1;allchess[x+i][y-j]==color;i++,j++)
            {
                num++;
                if(num>=5)flag=true;
                if(y-j==0||x+i==18)break;
            }
            //�ж�б��( /���·���)
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
                        JOptionPane.showInternalMessageDialog(this, "�ڷ���ʱ����Ϸ����");
                    }
                }
                else{whitetime--;
                    if(whitetime==0) {
                        JOptionPane.showInternalMessageDialog(this, "�׷���ʱ����Ϸ����");
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
