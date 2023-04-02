package cn.edu.guet.main;

import cn.edu.guet.util.*;

import javax.swing.*;


public class Main {
    private JFrame jFrame;
    private JPanel jPanel;

    JMenu jMenu=new JMenu("基础信息管理");
    JMenuBar jMenuBar=new JMenuBar();



    JMenuItem item01=new JMenuItem("查看商品");

    JMenuItem item02=new JMenuItem("添加商品");

    JMenuItem item03=new JMenuItem("销售商品");

    JMenuItem item04=new JMenuItem("删除商品");

    JMenuItem item05=new JMenuItem("统计商品");





    public Main(){

        jFrame = new JFrame("主界面");

        ImageIcon imageIcon=new ImageIcon("result.jpg");
        JLabel jLabel=new JLabel(imageIcon);
        jLabel.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
        jFrame.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));



        jMenuBar.add(jMenu);
        jMenuBar.setBounds(0,0,100,30);


        jMenu.add(item01);//查看商品
        jMenu.add(item02);//添加商品
        jMenu.add(item03);//销售商品
        jMenu.add(item04);//删除商品
        jMenu.add(item05);//统计商品

        jFrame.setBounds(180,50,1200,800);
        //jFrame.setSize(700,400);
        jPanel= (JPanel) jFrame.getContentPane();
        jPanel.add(jMenuBar);
        jPanel.setLayout(null);
        jFrame.setVisible(true);//界面可视化
        jPanel.setOpaque(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //查看商品
        item01.addActionListener(e -> {
           ViewProduct viewProduct= new ViewProduct();
       });

        //销售商品
        item03.addActionListener(e -> {
            SellingGoods sellingGoods=new SellingGoods();
        });

        //统计商品利润
        item05.addActionListener(e -> {
            StatisticsProduct statisticsProduct = new StatisticsProduct();
        });

        //增加商品
        item02.addActionListener(e -> {
            AddProduct addProduct=new AddProduct();
        });

        //删除商品
        item04.addActionListener(e ->{
            DeleteProduct deleteProduct=new DeleteProduct();
        });

    }
}