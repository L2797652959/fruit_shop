package cn.edu.guet.util;

import cn.edu.guet.bean.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private JFrame jFrame;
    private JPanel jPanel;

    JMenu jMenu=new JMenu("基础信息管理");
    JMenuBar jMenuBar=new JMenuBar();



    JMenuItem item01=new JMenuItem("查看商品");
    private String columnNames[] = {"id", "商品名称", "单价", "类型"};
    private Object[][] rowData = null;
    JTable table;
    JScrollPane jscrollpane = new JScrollPane();

    JMenuItem item02=new JMenuItem("添加商品");





    public Main(){
        jMenu.add(item01);//查看商品
        jMenu.add(item02);//添加商品
        jMenuBar.add(jMenu);
        jMenuBar.setBounds(0,0,100,30);
        jFrame = new JFrame("主界面");
        jFrame.setSize(700,400);
        jPanel= (JPanel) jFrame.getContentPane();
        jPanel.add(jMenuBar);
        jPanel.setLayout(null);
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        item01.addActionListener(e -> {
           ViewProduct viewProduct= new ViewProduct();
           rowData=viewProduct.ViewProduct();
           table = new JTable(rowData, columnNames);
           jscrollpane.setBounds(0, 31, 700, 370);
           jscrollpane.setViewportView(table);
           table.setRowHeight(35);
           /**
            * 字居中显示设置
            */
           DefaultTableCellRenderer r = new DefaultTableCellRenderer();
           r.setHorizontalAlignment(JLabel.CENTER);
           table.setDefaultRenderer(Object.class, r);
           jPanel.add(jscrollpane);
       });
       }
}