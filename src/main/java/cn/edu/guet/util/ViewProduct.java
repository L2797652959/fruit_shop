package cn.edu.guet.util;

import cn.edu.guet.bean.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ViewProduct {
    private JPanel jPanel;
    private JFrame jFrame;


    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"id", "商品名称", "单价", "类型"};
    private Object[][] rowData = null;

    public ViewProduct(){
        jFrame = new JFrame("查找商品");
        jFrame.setBounds(290,150,1000,600);
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Connection conn = null;
        String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";
        String sql = "SELECT * FROM PRODUCT";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Product> productList = new ArrayList<>();

            while (rs.next()) {
                //取出的数据，放入一个容器（数据的载体）
                Product product = new Product();
                product.setId(rs.getInt("ID"));
                product.setName(rs.getString("NAME"));
                product.setPrice(rs.getFloat("PRICE"));
                product.setType(rs.getString("TYPE"));

                productList.add(product);//每循环一次把拿到的商品的数据存入集合，好比每摘一个芒果，把芒果放入桶里


            }

            rowData = new Object[productList.size()][4];

                /*
                把集合的数据转成二维数组
                 */
            for (int i = 0; i < rowData.length; i++) {
                Product product = productList.get(i);
                rowData[i] = new Object[]{product.getId(), product.getName(), product.getPrice(), product.getType()};

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


        //点击菜单内的代码
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
    }
}
