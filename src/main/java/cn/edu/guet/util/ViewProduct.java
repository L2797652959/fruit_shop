package cn.edu.guet.util;

import cn.edu.guet.bean.Fruit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ViewProduct {



    private JPanel jPanel;
    private JFrame jFrame;
    private JTextField account;
    private JButton view;
    private JTextArea text;


    JTable table;
    JScrollPane jscrollpane = new JScrollPane();
    private String columnNames[] = {"id", "商品名称", "进价/斤", "售价/斤","数量/斤"};
    private Object[][] rowData = null;
    private Object[][] Data=null;

    public ViewProduct(){
        jFrame = new JFrame("查找商品");
        jFrame.setBounds(290,150,1000,600);
        jPanel= (JPanel) jFrame.getContentPane();
        jFrame.setVisible(true);//界面可视化
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);


        System.out.println("查找开始");
        Connection conn = null;
        String url = "jdbc:oracle:thin:@101.42.46.29:1521:orcl";

                /*if(name==" ") {
                    sql = "SELECT * FROM FRUITS";
                }
                else{
                    sql = "SELECT * FROM FRUITS WHERE F_NAME=%"+name+"%";
                }*/
        String sql = "SELECT * FROM FRUITS ORDER BY F_ID";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, "lgl", "Grcl1234LgL");
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
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

            System.out.println(rowData);
                /*
                把集合的数据转成二维数组
                 */
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

        account = new JTextField("红富士苹果");
        account.setBounds(220,490,400,30);
        jPanel.add(account);

        text = new JTextArea("请输入关键词查询：");
        text.setBounds(75,494,110,20);
        jPanel.add(text);

        view = new JButton("查找");
        view.setBounds(700,490,200,30);
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("我们要查看商品啦");
                Data=new Object[rowData.length][rowData.length];
                String name=account.getText();

                System.out.println(name);
                int num=0;
                for(int i=0;i<rowData.length;i++){
                    String str= (String) rowData[i][1];
                    if(str.indexOf(name)!=-1){
                        for(int j=0;j<rowData[0].length;j++){
                            Data[num][j]=rowData[i][j];
                        }
                        num++;
                    };
                }


                table = new JTable(Data, columnNames);
                jscrollpane.setBounds(0, 0, 1000, 450);
                jscrollpane.setViewportView(table);
                table.setRowHeight(35);

            }
        });
        jPanel.add(view);



        /**
         * 字居中显示设置
         */

    }
}
