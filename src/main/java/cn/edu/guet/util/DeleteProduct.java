package cn.edu.guet.util;

import cn.edu.guet.bean.Fruit;
import cn.edu.guet.main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DeletProject
 * @Description TODO
 * @Author 1475
 * @Date 2023/3/31 23:57
 * @Version 1.0
 */

public class DeleteProduct {
    private JPanel jPanel;
    private JFrame jFrame;
    private JTextField numid;
    private JTextArea tishi;
    //private JTextField f_link;
    private JTextArea title;
    private JButton delete;
    private JButton leave;
    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"id", "商品名称", "售价/斤", "进价/斤","数量/斤"};
    private Object[][] rowData = null;


    public DeleteProduct() {

        jFrame = new JFrame("删除商品");
        jFrame.setBounds(290,150,1000,800);
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);




        title = new JTextArea("请删除水果：");
        title.setBounds(85,596,100,20);
        jPanel.add(title);

        numid= new JTextField();
        numid.setBounds(300,594,300,30);
        jPanel.add(numid);

        tishi=new JTextArea("请输入水果的id:");
        tishi.setBounds(190,596,100,20);
        jPanel.add(tishi);



        System.out.println("查找开始");
        Connection conn = null;
        String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";


        String sql = "SELECT * FROM FRUITS ORDER BY F_ID";
        PreparedStatement pstmt;
        ResultSet rs2;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
            pstmt = conn.prepareStatement(sql);

            rs2 = pstmt.executeQuery();
            List<Fruit> fruitList1 = new ArrayList<>();

            while (rs2.next()) {
                //取出的数据，放入一个容器（数据的载体）
                Fruit fruit = new Fruit();
                fruit.setF_id(rs2.getInt("F_ID"));
                fruit.setF_name(rs2.getString("F_NAME"));
                fruit.setF_price(rs2.getFloat("F_PRICE"));
                fruit.setF_sellprice(rs2.getFloat("F_SELLPRICE"));
                fruit.setF_number(rs2.getFloat("F_NUMBER"));

                fruitList1.add(fruit);//每循环一次把拿到的商品的数据存入集合，好比每摘一个芒果，把芒果放入桶里


            }

            rowData = new Object[fruitList1.size()][columnNames.length];

            System.out.println(rowData);
                /*
                把集合的数据转成二维数组
                 */
            for (int i = 0; i < rowData.length; i++) {
                Fruit fruit = fruitList1.get(i);
                rowData[i] = new Object[]{
                        fruit.getF_id(),
                        fruit.getF_name(),
                        fruit.getF_price(),
                        fruit.getF_sellprice(),
                        fruit.getF_number()
                };

            }

            //点击菜单内的代码

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        table = new JTable(rowData, columnNames);
        jscrollpane.setBounds(0, 0, 1000, 450);
        jscrollpane.setViewportView(table);
        table.setRowHeight(35);

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        jPanel.add(jscrollpane);


        /*f_link=new JTextField();
        f_link.setBounds(625,594,90,20);
        jPanel.add(f_link);*/
        delete= new JButton("删除");
        delete.setBounds(650,594,200,30);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("我们要删除商品");
                System.out.println("开始删除");
                Connection connection=null;
                int y=JOptionPane.showConfirmDialog(jFrame,"请你再次确认是否要删除这一条水果？","删除提示",JOptionPane.YES_NO_OPTION);
                if(y==JOptionPane.YES_OPTION) {
                    String sql = "DELETE FROM  FRUITS WHERE F_ID=?";
                    String sql1 = "SELECT * FROM FRUITS ORDER BY F_ID";
                    String id = numid.getText();

                    String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";
                    PreparedStatement ptmt, ptmt1;
                    int n;
                    ResultSet rs = null;
                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        connection = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
                        ptmt = connection.prepareStatement(sql);
                        ptmt.setString(1, id);

                        n = ptmt.executeUpdate();
                        if (n > 0) {
                            System.out.println("删除成功");
                            ptmt1 = connection.prepareStatement(sql1);

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

        jPanel.add(delete);


         leave=new JButton("退出");
         leave.setBounds(300,650,300,40);
         leave.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 jFrame.setVisible(false);
             }
         });
        jPanel.add(leave);


    }
}
