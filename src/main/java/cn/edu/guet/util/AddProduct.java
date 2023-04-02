package cn.edu.guet.util;

import cn.edu.guet.bean.Fruit;
import cn.edu.guet.main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AddProduct
 * @Description TODO
 * @Author 1475
 * @Date 2023/3/30 20:07
 * @Version 1.0
 */

public class AddProduct {
    private JPanel jPanel;
    private JFrame jFrame;
    private JTextField numid;
    private JTextField f_name;
    private JTextField f_price1;
    private JTextField f_price2;
    private JTextField f_amount;
    //private JTextField f_link;
    private JTextArea title;
    private JTextArea tishi;
    private JButton add;
    private JButton update;
    private JButton leave;
    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"id", "商品名称", "售价/斤", "进价/斤","数量/斤"};
    private Object[][] rowData = null;

    public AddProduct() {

        jFrame = new JFrame("添加商品");
        jFrame.setBounds(290,150,1000,800);
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);




        title = new JTextArea("请添加水果：");
        title.setBounds(25,594,90,20);
        jPanel.add(title);

        tishi = new JTextArea("依次为编号，名字，售价，进价，数量");
        tishi.setBounds(300,630,300,20);
        jPanel.add(tishi);

        numid= new JTextField();
        numid.setBounds(125,594,90,20);
        jPanel.add(numid);

        f_name=new JTextField();
        f_name.setBounds(225,594,90,20);
        jPanel.add(f_name);

        f_price1=new JTextField();
        f_price1.setBounds(325,594,90,20);
        jPanel.add(f_price1);

        f_price2=new JTextField();
        f_price2.setBounds(425,594,90,20);
        jPanel.add((f_price2));

        f_amount=new JTextField();
        f_amount.setBounds(525,594,90,20);
        jPanel.add(f_amount);


        /*f_link=new JTextField();
        f_link.setBounds(625,594,90,20);
        jPanel.add(f_link);*/
        update= new JButton("更新");
        update.setBounds(760,594,90,20);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("我们要更新商品");
                //System.out.println("开始更新");
                Connection connection=null;
                int yes=JOptionPane.showConfirmDialog(jFrame,"请再次确认输入的数量正确？","更新提示",JOptionPane.YES_NO_OPTION);
                if(yes==JOptionPane.YES_OPTION) {
                    String sql = "UPDATE FRUITS SET F_NUMBER=? WHERE F_ID=?";
                    String sql1 = "SELECT * FROM FRUITS WHERE F_ID=?";
                    String id = numid.getText();
                    String name = f_name.getText();
                    String sellprice = f_price1.getText();
                    String price = f_price2.getText();
                    String amount = f_amount.getText();
                    String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";
                    PreparedStatement ptmt, ptmt1;
                    int n;
                    ResultSet rs = null;
                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        connection = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
                        ptmt = connection.prepareStatement(sql);
                        ptmt.setString(1, amount);
                        ptmt.setString(2, id);
                        n = ptmt.executeUpdate();
                        if (n > 0) {
                            System.out.println("更新成功");
                            ptmt1 = connection.prepareStatement(sql1);
                            ptmt1.setString(1, id);
                            rs = ptmt1.executeQuery();

                        } else {
                            System.out.println("失败");
                        }

                        List<Fruit> fruitList = new ArrayList<>();

                        while (rs.next()) {
                            //取出的数据，放入一个容器（数据的载体）
                            Fruit fruit = new Fruit();
                            fruit.setF_id(rs.getInt("F_ID"));
                            fruit.setF_name(rs.getString("F_NAME"));
                            fruit.setF_price(rs.getFloat("F_PRICE"));
                            fruit.setF_sellprice(rs.getFloat("F_SELLPRICE"));
                            fruit.setF_number(rs.getFloat("F_NUMBER"));

                            fruitList.add(fruit);//每循环一次把拿到的商品的数据存入集合，好比每摘一个芒果，把芒果放入桶里


                        }


                        rowData = new Object[fruitList.size()][columnNames.length];

                        //把集合的数据转成二维数组

                        for (int i = 0; i < rowData.length; i++) {
                            Fruit fruit = fruitList.get(i);
                            rowData[i] = new Object[]{
                                    fruit.getF_id(),
                                    fruit.getF_name(),
                                    fruit.getF_price(),
                                    fruit.getF_sellprice(),
                                    fruit.getF_number()
                            };

                        }


                        //点击菜单内的代码
                        table = new JTable(rowData, columnNames);
                        jscrollpane.setBounds(0, 0, 1000, 500);
                        jscrollpane.setViewportView(table);
                        table.setRowHeight(35);

                        //表格居中显示
                        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                        r.setHorizontalAlignment(JLabel.CENTER);
                        table.setDefaultRenderer(Object.class, r);
                        jPanel.add(jscrollpane);


                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

      jPanel.add(update);


        add= new JButton("添加");
        add.setBounds(650,594,90,20);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("我们要添加商品啦");
                //String sql=null;
                String id=numid.getText();
                String name=f_name.getText();
                String sellprice=f_price1.getText();
                String price=f_price2.getText();
                String amount=f_amount.getText();
                String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";

                //String link=f_link.getText();
                System.out.println("开始添加");
                Connection conn = null;

                String sql = "INSERT INTO FRUITS(F_ID,F_NAME,F_PRICE,F_SELLPRICE,F_NUMBER) VALUES(?,?,?,?,?)";
                String sql1 = "SELECT * FROM FRUITS WHERE F_ID=?";
                PreparedStatement pstmt,pstmt1;
                int rs1;
                ResultSet rs=null;

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,id);
                    pstmt.setString(2,name);
                    pstmt.setString(3, sellprice);
                    pstmt.setString(4, price);
                    pstmt.setString(5, amount);


                    rs1 = pstmt.executeUpdate();
                    if(rs1>0)
                    {
                        pstmt1 = conn.prepareStatement(sql1);
                        pstmt1.setString(1,id);
                        rs = pstmt1.executeQuery();
                        System.out.println("连接成功");

                    }else
                    {
                        System.out.println("连接失败");
                    }

                    List<Fruit> fruitList = new ArrayList<>();

                    while (rs.next()) {
                        //取出的数据，放入一个容器（数据的载体）
                        Fruit fruit = new Fruit();
                        fruit.setF_id(rs.getInt("F_ID"));
                        fruit.setF_name(rs.getString("F_NAME"));
                        fruit.setF_price(rs.getFloat("F_PRICE"));
                        fruit.setF_sellprice(rs.getFloat("F_SELLPRICE"));
                        fruit.setF_number(rs.getFloat("F_NUMBER"));

                        fruitList.add(fruit);//每循环一次把拿到的商品的数据存入集合，好比每摘一个芒果，把芒果放入桶里


                    }


                    rowData = new Object[fruitList.size()][columnNames.length];

                    //把集合的数据转成二维数组

                    for (int i = 0; i < rowData.length; i++) {
                        Fruit fruit = fruitList.get(i);
                        rowData[i] = new Object[]{
                                fruit.getF_id(),
                                fruit.getF_name(),
                                fruit.getF_price(),
                                fruit.getF_sellprice(),
                                fruit.getF_number()
                        };

                    }


                    //点击菜单内的代码
                    table = new JTable(rowData, columnNames);
                    jscrollpane.setBounds(0, 0, 1000, 500);
                    jscrollpane.setViewportView(table);
                    table.setRowHeight(35);

                    //表格居中显示
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JLabel.CENTER);
                    table.setDefaultRenderer(Object.class, r);
                    jPanel.add(jscrollpane);


                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }


    }
});
       jPanel.add(add);


        leave=new JButton("退出");
        leave.setBounds(300,670,300,30);
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
            }
        });
        jPanel.add(leave);


    }

}