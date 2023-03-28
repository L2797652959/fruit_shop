package cn.edu.guet.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {

    private JPanel jPanel;
    private JTextField account;
    private JTextField password;
    private JButton login;

    public Login(String title){
        super(title);

        jPanel = (JPanel) this.getContentPane();
        jPanel.setLayout(null);//布局为空（自己控制）

        account = new JTextField("zhangsan");
        account.setBounds(125,130,200,30);
        jPanel.add(account);

        password = new JTextField("123456");
        password.setBounds(125,170,200,30);
        jPanel.add(password);

        login = new JButton("登录");
        login.setBounds(125,210,200,30);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user=account.getText();
                String pass=password.getText();
                System.out.println(user);
                System.out.println(pass);
                System.out.println("准备登录");

                Connection connection = null;
                String url="jdbc:oracle:thin:@101.42.46.29:1521:orcl";
                String sql="SELECT * FROM users WHERE username=?";
                PreparedStatement pstmt;
                ResultSet rs;

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    connection= DriverManager.getConnection(url,"lgl","Grcl1234LgL");

                    if(connection!=null){
                        System.out.println("连接成功");
                        pstmt= connection.prepareStatement(sql);
                        pstmt.setString(1,user);
                        //pstmt.setString(2,MD5);
                        rs=pstmt.executeQuery();//执行查询
                        if(rs.next()){
                            System.out.println("用户名合法");
                            String encPass = rs.getString("PASSWORD");
                            //System.out.println("取出的密码："+encPass);
                            PasswordEncoder passwordEncoder=new PasswordEncoder("这是我的盐");
                            //PasswordEncoder encoderMd5 = new PasswordEncoder("6e9e4676d01c434da03d1aaf45c7413e", "MD5");

                            Boolean result = passwordEncoder.matches(encPass,pass);



                            if(result==true){
                                setVisible(false);

                                Main main = new Main();
                            }
                        }
                        else{
                            System.out.println("登陆失败");
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }finally{
                    try{
                    connection.close();//关闭数据库连接
                } catch(SQLException ex){
                        ex.printStackTrace();
                    }

                }

            }
        });
        jPanel.add(login);

        setBounds(510,190,450,350);
        setVisible(true);//界面可视化
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new Login("登录窗口");
    }
}
