package com.it;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
        int[][] data = new int[4][4];
        //记录空白方块所在二维数组的位置
        int x=0;
        int y=0;
        //判断胜利：定位一个正确的二维数组
    int[][] win={
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}
        };
    int step=0;
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");

    public GameJFrame() {
        initJFrame();


        //初始化菜单
        //创建整个菜单对象
        initJMenuBar();

        //初始化数据（打乱
        initData();

        //初始化图片
        initImage();

        this.setVisible(true);
    }


    private void initData() {
        //定位一个1维数组
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //打乱数组里的顺序
        //翻牌算法
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            //获得随机索引
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;

        }
        //给二维数组添加数据
        //遍历--添加
        for (int i = 0; i < tempArr.length; i++) {
            if(tempArr[i] == 0){
                x=i/4;
                y=i%4;
            }

            data[i / 4][i % 4] = tempArr[i];


        }
    }

    //初始化图片
    //添加图片的时候，按照二维数组里管理的数据添加图片

    private void initImage() {
        //清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        if(victory()){
            //显示胜利图标
        JLabel winJlabel = new JLabel(new ImageIcon("src/com/it/images/WIN.png"));
        winJlabel.setBounds(150,200,300,300);
        this.getContentPane().add(winJlabel);

        }
        JLabel stepCount=new JLabel("步数"+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);




        //外循环
        for (int i = 0; i < 4; i++) {
            //内循环，一行加4图片
            for (int j = 0; j < 4; j++) {
                int num=data[i][j];
                JLabel Jlable = new JLabel(new ImageIcon("src/com/it/images/切片_" + num + ".gif"));
                
                //指定图片位置
                Jlable.setBounds(105 * j+83, 105 * i+134, 105, 105);
    //           给图片添加边框
                Jlable.setBorder(new BevelBorder(1));
                //把管理容器添加到界面中 
                this.getContentPane().add(Jlable);

            }
        }
        //添加背景图片
        ImageIcon bg=new ImageIcon("src/com/it/images/background.png");
        JLabel backgound=new JLabel(bg);
        backgound.setBounds(40,38,510,570);
        //  背景图片添加到界面中
        this.getContentPane().add(backgound);


        //刷新界面
        this.getContentPane().repaint();

    }


    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单选项对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutMenu = new JMenu("关于我们");
        //创建选项下面的条目对象



        //将选每一个选项下面条目添加到选项中
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);

        //将菜单里的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutMenu);


        aboutMenu.add(accountItem);

        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }


    private void initJFrame() {
        this.setSize(603, 688);
        //标题
        this.setTitle("拼图单机版v0.9");
        //设置界面置顶
//        this.setAlwaysOnTop(true);

        //设置界面居中
        this.setLocationRelativeTo(null);

        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消图片默认居中放置，才可以控制xy
        this.setLayout(null);
        //给整个界面添加监听事件
        this.addKeyListener( this);
    }
    public void keyTyped(KeyEvent e) {

    }
    //按下不松时调用这个方法
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==65){
            //把界面中所有图片del
            this.getContentPane().removeAll();
            //加载第一张完整图片
            JLabel all=new JLabel(new ImageIcon("src/com/it/images/ALL.jpeg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //加载背景图片
            ImageIcon bg=new ImageIcon("src/com/it/images/background.png");
            JLabel backgound=new JLabel(bg);
            backgound.setBounds(40,38,510,570);
            this.getContentPane().add(backgound);
            //刷新界面
            this.getContentPane().repaint();


        }


    }
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利，如果胜利，此方法要直接结束，不能执行移动代码
        if (victory()){

            //结束犯法
            return;
        }
        //左37 上38 右39 下40
        int code=e.getKeyCode();
        if(code == 37){
            if (y == 3) {
                return;

            }
                //向左移动
            data[x][y] = data[x][y + 1];
            data[x][y+1]=0;
            y++;
            step++;
            //调用方法，按照最新的数字加载图片
            initImage();

        }else if(code == 38){
            if(x==3){
                return;
            }
            //向上移动、
            //xy表示空白方块
            //x+1，y表示空白方块下方的块的数字
            //将空白方块下方的数字赋值给空白方块实现移动
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            step++;
            //调用方法，按照最新的数字加载图片
            initImage();

        }else if(code == 39){
            if(y==0){
                return;
            }
            //
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            step++;
            //调用方法，按照最新的数字加载图片
            initImage();

        }
        else if(code == 40){
            if(x==0){
                return;
            }
            //向下移动
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            step++;
            //调用方法，按照最新的数字加载图片
            initImage();

        }else if (code==65){
            initImage();
        }else if(code==87){
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0},
            };
            initImage();

        }


    }

//判读data数组中的数据是否跟win数组中相同
    //if全部相同就返回true，否则返回false

    public boolean victory(){
        for(int i=0;i<data.length;i++){

        //遍历
        //i:依次表示二维数组data里面的索引
        //data[i]:依次表示每一个一维数组
        for (int j = 0; j < data[i].length; j++) {
            if(data[i][j] !=win[i][j]){
                //有一个数据不一样返回f
                return false;
                }
            }

        }
        //循环结束返回，一样的话返回t
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            //获取当前被点击的对象
        Object obj=e.getSource();
        if(obj==replayItem){
            //计数器清零
            step=0;
            //重新游戏
            //再次打乱二维数组的数据
            initData();
            //重新加载图片
            initImage();



        }else if(obj==reloginItem){
            System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        }
        else if(obj==closeItem){
            System.out.println("关闭游戏");
            //关闭虚拟机

            System.exit(0);

        }
        else if(obj==accountItem){
            System.out.println("公众号");
            //创建一个弹框对象
            JDialog jDialog=new JDialog();
            JLabel jLabel=new JLabel(new ImageIcon("src/com/it/images/微信图片_20250513171036.jpg"));
            //设置位置和匡高
            jLabel.setBounds(0,0,258,258);
            //图片加进弹框
            jDialog.getContentPane().add(jLabel);
            //弹框大小
            jDialog.setSize(344,344);

            //弹框置顶
            jDialog.setAlwaysOnTop(true);
            //弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭无法操作界面
            jDialog.setModal(true);
            //让弹框显示
            jDialog.setVisible(true);
        }
    }
}
