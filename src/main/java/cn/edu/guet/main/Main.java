package cn.edu.guet.main;

import cn.edu.guet.util.ViewProduct;

import javax.swing.*;


public class Main {
    private JFrame jFrame;
    private JPanel jPanel;

    JMenu jMenu=new JMenu("基础信息管理");
    JMenuBar jMenuBar=new JMenuBar();



    JMenuItem item01=new JMenuItem("查看商品");


    JMenuItem item02=new JMenuItem("添加商品");





    public Main(){
        jMenu.add(item01);//查看商品
        jMenu.add(item02);//添加商品
        jMenuBar.add(jMenu);
        jMenuBar.setBounds(0,0,100,30);
        jFrame = new JFrame("主界面");
        jFrame.setBounds(180,50,1200,800);
        //jFrame.setSize(700,400);
        jPanel= (JPanel) jFrame.getContentPane();
        jPanel.add(jMenuBar);
        jPanel.setLayout(null);
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //查看商品
        item01.addActionListener(e -> {
           ViewProduct viewProduct= new ViewProduct();
       });



    }
}