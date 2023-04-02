package cn.edu.guet.util;

import cn.edu.guet.bean.Fruit;
import cn.edu.guet.pay.WXPay;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellingGoods {

    private JPanel jPanel;
    private JFrame jFrame;

    JButton pay;
    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"商品ID", "商品名称", "售价"};

    int length=0;

    private Object[][] rowData ={
            {"","",""},
            {"","",""},
            {"","",""},
            {"","",""},
            {"","",""},
            {"","",""},
            {"","",""},
            {"","",""},
            {"","",""}
    };

    public SellingGoods(){
        jFrame = new JFrame("销售");
        jFrame.setBounds(290,150,1000,600);
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);

        pay = new JButton("结账");
        pay.setBounds(300, 500, 100, 30);
        pay.addActionListener(e -> {
            /*
            开始结账
             */
            float price = 0;
            String name="";
            //System.out.println(length);
            for (int i = 0; i < length; i++) {
                name+=rowData[i][1]+";";
                //System.out.println(rowData[i][2]);
                //System.out.println(rowData[i][2].getClass());
                price += Float.parseFloat(String.valueOf(rowData[i][2]));


            }
            //int a= (int) (price*100);
            //System.out.println(a);
            WXPay.unifiedOrder(name,(int) (price*100));

            /*
            显示二维码
             */
            JFrame jFrame=new JFrame("结账页面");
            jFrame.setSize(300,330);

            JLabel pay=new JLabel();
            ImageIcon icon=new ImageIcon("pay.jpg");    icon.setImage(icon.getImage().getScaledInstance(300,300, Image.SCALE_DEFAULT));

            pay.setVerticalTextPosition(JLabel.TOP);
            pay.setBounds(0,0,300,300);

            String strMsg1 = name;
            String strMsg = "<html><body bgcolor='green' color='red'>" +strMsg1+ "<br>" +price+ "<body></html>";
            pay.setText(strMsg);
            pay.setIcon(icon);

            JPanel jPanel= (JPanel) jFrame.getContentPane();
            jPanel.setLayout(null);
            jPanel.add(pay);
            jFrame.setVisible(true);

        });

        table = new JTable(rowData, columnNames);
        //设置表头高度
        table.getTableHeader().setPreferredSize(new Dimension(table.getColumn("商品ID").getPreferredWidth(), 30));
        //表头居中
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        table.setRowHeight(30);
        /**
         * 表数据居中显示
         * */
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(r.CENTER);
        table.setDefaultRenderer(Object.class, r);
        jscrollpane.setBounds(0, 80, 700, 35 * 7 + 120);
        jscrollpane.setViewportView(table);
        jPanel.add(jscrollpane);
        jPanel.add(pay);
        jPanel.repaint();

        //去数据库查询商品，并以JLabel的方式显示
        Connection conn = null;
        String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";
        String sql = "SELECT * FROM FRUITS ";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
            pstmt = conn.prepareStatement(sql);
            //pstmt.setString(1,name);
            rs = pstmt.executeQuery();
            List<Fruit> fruitList = new ArrayList<>();

            int x=830,y=40;
            while(rs.next()) {
                //Fruit fruit = new Fruit();
                JLabel product01 = new JLabel();
                //headPhoto.setVerticalTextPosition(JLabel.CENTER);
                product01.setHorizontalTextPosition(JLabel.RIGHT);
                product01.setBounds(x, y, 120, 150);
                x += 105;
                if (x > 1400) {
                    x = 830;
                    y += 140;
                }
                String strMsg1 = rs.getString("F_NAME");
                String strMsg2 = String.valueOf(rs.getFloat("F_SELLPRICE"));
                String strMsg3 = rs.getString("F_link");
                int strMsg4 = rs.getInt("F_ID");
                String strMsg = "<html><body bgcolor='#fae070' color='black'>" +
                        "<img width='100' height='100' align='left' " +
                        "src=" + strMsg3 + "/>"
                        + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ¥" + strMsg1 + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + strMsg2 + "<body></html>";
                product01.setText(strMsg);
                jPanel.add(product01);


                //JLabel product01 = new JLabel();
                product01.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        //                 把所选的菜品添加到左侧
                        Fruit fruit = new Fruit();
                        fruit.setF_sellprice(Float.valueOf(strMsg2));
                        fruit.setF_name(strMsg1);
                        fruit.setF_id(strMsg4);
                        fruitList.add(fruit);

                        length=fruitList.size();
                        /* */
                        //Data = new Object[fruitList.size()][2];
                        for (int i = 0; i < fruitList.size(); i++) {
                            Fruit fruit1 = fruitList.get(i);
                            rowData[i][0] = fruit1.getF_id();
                            rowData[i][1] = fruit1.getF_name();
                            rowData[i][2] = fruit1.getF_sellprice();

                            /*Data[i][0] = fruit1.getF_id();
                            Data[i][1] = fruit1.getF_name();
                            Data[i][2] = fruit1.getF_price();*/
                        }
                        jscrollpane.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });


            }


            jPanel.repaint();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        };
    }
}
