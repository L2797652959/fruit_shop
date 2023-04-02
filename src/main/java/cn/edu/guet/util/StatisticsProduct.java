package cn.edu.guet.util;

import cn.edu.guet.bean.Fruit;
import cn.edu.guet.bean.SALE;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsProduct {

    private JPanel jPanel;
    private JFrame jFrame;
    float price = 0;
    float num = 0;

    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"商品id", "商品名称","销售价格/元","销售数量/斤","销售时间"};
    private Object[][] rowData = null;


    public StatisticsProduct(){
        jFrame = new JFrame("销售商品表");
        jFrame.setBounds(290,150,1000,600);
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);


        List<SALE> saleList = new ArrayList<>();
        List<Fruit> fruitList = new ArrayList<>();

        //System.out.println("查找开始");
        Connection conn = null;
        String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";

        String sql = "SELECT * FROM SALE";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();


            while (rs.next()) {
                //取出的数据，放入一个容器（数据的载体）
                SALE sale= new SALE();
                sale.setS_id(rs.getInt("S_ID"));
                sale.setS_name(rs.getString("S_NAME"));
                sale.setS_price(rs.getFloat("S_PRICE"));
                sale.setS_number(rs.getFloat("S_NUMBER"));
                sale.setS_date(rs.getString("S_DATE"));

                saleList.add(sale);//每循环一次把拿到的商品的数据存入集合，好比每摘一个芒果，把芒果放入桶里


            }
            rowData = new Object[saleList.size()+1][columnNames.length];

            for (int i = 0; i < rowData.length-1; i++) {
                SALE sale = saleList.get(i);
                //Fruit fruit = fruitList.get(i);
                rowData[i] = new Object[]{
                        sale.getS_id(),
                        sale.getS_name(),
                        sale.getS_price(),
                        sale.getS_number(),
                        sale.getS_date()
                };

            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


        System.out.println("执行统计");
        sql = "SELECT * FROM FRUITS";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();


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


            for(int i=0,j=0;i<rowData.length-1;i++){
                for(j=0;j<fruitList.size();j++){
                    Fruit fruit = fruitList.get(j);
                    if (fruit.getF_id()==Float.parseFloat(String.valueOf(rowData[i][0]))) {
                        break;
                    }
                }
                Fruit fruit = fruitList.get(j);
                price += (Float.parseFloat(String.valueOf(rowData[i][2]))-
                        Float.parseFloat(String.valueOf(fruit.getF_price())))*
                    Float.parseFloat(String.valueOf(rowData[i][3]));
                num += Float.parseFloat(String.valueOf(rowData[i][3]));
            }
            rowData[rowData.length - 1][0] = "总计";
            rowData[rowData.length - 1][1] = "";
            rowData[rowData.length - 1][2] = price;
            rowData[rowData.length - 1][3] = num;
            rowData[rowData.length - 1][4] = rowData[0][4] + "~" + rowData[rowData.length - 2][4];

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
                /*
                把集合的数据转成二维数组
                 */





            //点击菜单内的代码


        table = new JTable(rowData, columnNames);
        jscrollpane.setBounds(0, 0, 1000, 450);
        jscrollpane.setViewportView(table);
        table.setRowHeight(35);


        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        jPanel.add(jscrollpane);



    }
}
