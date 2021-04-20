package com.me.view;

import com.me.CSFramework.core.Client;
import com.me.CSFramework.core.ClientActionAdapter;
import com.me.encrpt.RSAUtil;
import com.me.model.UserModel;
import com.me.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author quan
 * @create 2021-04-15 17:50
 */
public class RegistryView implements IView {
    protected Client client;

    private JFrame jfrmRegistry;
    protected JTextField jtxtName;
    protected JTextField jtxtSex;
    protected JTextField jtxtAge;
    protected JTextField jtxtTel;

    private JLabel jlblName;
    private JLabel jlblSex;
    private JLabel jlblAge;
    private JLabel jlblTel;

    private JButton jbtnRegistry;
    private LoginView loginView;

    public RegistryView(Client client, LoginView loginView) {
        this.client = client;
        this.client.setClientAction(new RegistryView.RegistryAction());
        this.loginView = loginView;
        initView();
    }

    @Override
    public void init() {
        jfrmRegistry = new JFrame("chatroom - 注册");
        jfrmRegistry.setSize(new Dimension(300, 350));
        jfrmRegistry.setLocationRelativeTo(null);
        jfrmRegistry.setLayout(new BorderLayout());
        jfrmRegistry.setResizable(false);
        jfrmRegistry.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel jlblRegistryTopic = new JLabel("chatroom - 注册", 0);
        jlblRegistryTopic.setForeground(topicColor);
        jlblRegistryTopic.setFont(topicFont);
        jfrmRegistry.add(jlblRegistryTopic, BorderLayout.NORTH);

        JPanel jpnlRegistry = new JPanel(new BorderLayout());
        jfrmRegistry.add(jpnlRegistry, BorderLayout.CENTER);

        JLabel jlblLoginHead = new JLabel(" ");
        jlblLoginHead.setFont(topicFont);
        jpnlRegistry.add(jlblLoginHead, BorderLayout.NORTH);

        JPanel jpnlRegistryContext = new JPanel(new GridLayout(5, 0));
        jpnlRegistry.add(jpnlRegistryContext, BorderLayout.CENTER);

        JPanel jpnlUserName = new JPanel(new FlowLayout());
        jpnlRegistryContext.add(jpnlUserName, 0);

        JPanel jpnlUserSex = new JPanel(new FlowLayout());
        jpnlRegistryContext.add(jpnlUserSex, 1);

        JPanel jpnlUserAge = new JPanel(new FlowLayout());
        jpnlRegistryContext.add(jpnlUserAge, 2);

        JPanel jpnlUserTel = new JPanel(new FlowLayout());
        jpnlRegistryContext.add(jpnlUserTel, 3);

        JLabel jlblBlank1 = new JLabel(" ");
        jpnlRegistryContext.add(jlblBlank1, 4);

        jlblName = new JLabel("昵称 ");
        jlblName.setFont(normalFont);
        jpnlUserName.add(jlblName);

        jtxtName = new JTextField(20);
        jtxtName.setFont(normalFont);
        jpnlUserName.add(jtxtName);

        jlblSex = new JLabel("性别 ");
        jlblSex.setFont(normalFont);
        jpnlUserSex.add(jlblSex);

        jtxtSex = new JTextField(20);
        jtxtSex.setFont(normalFont);
        jpnlUserSex.add(jtxtSex);

        jlblAge = new JLabel("年龄 ");
        jlblAge.setFont(normalFont);
        jpnlUserAge.add(jlblAge);

        jtxtAge = new JTextField(20);
        jtxtAge.setFont(normalFont);
        jpnlUserAge.add(jtxtAge);

        jlblTel = new JLabel("电话 ");
        jlblTel.setFont(normalFont);
        jpnlUserTel.add(jlblTel);

        jtxtTel = new JTextField(20);
        jtxtTel.setFont(normalFont);
        jpnlUserTel.add(jtxtTel);

        JPanel jpnlFooter = new JPanel(new FlowLayout());
        jpnlRegistry.add(jpnlFooter, BorderLayout.SOUTH);

        jbtnRegistry = new JButton(" 注册 ");
        jbtnRegistry.setFont(normalFont);
        jbtnRegistry.setCursor(cursorHand);
        jpnlFooter.add(jbtnRegistry, BorderLayout.SOUTH);
    }

    @Override
    public void reinit() {
    }

    @Override
    public void dealAction() {
        jfrmRegistry.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitView();
            }
        });

        jtxtName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtxtName.selectAll();
            }
        });

        jtxtSex.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtxtSex.setText("");
            }
        });

        jbtnRegistry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              if (!jtxtName.getText().equalsIgnoreCase("")) {
                  String name = jtxtName.getText().trim();
                  String sex = jtxtSex.getText().trim();
                  String age = jtxtAge.getText().trim();
                  String tel = jtxtTel.getText().trim();
                  if (name.equalsIgnoreCase("")
                          || sex.equalsIgnoreCase("")
                          || age.equalsIgnoreCase("")
                          || (!sex.equalsIgnoreCase("男") &&!sex.equalsIgnoreCase("女"))
                          || age.length() > 3
                          || (!tel.equalsIgnoreCase("") && tel.length() != 11)) {

                      ViewTool.showMessage(jfrmRegistry, "姓名、性别、年龄 必须填写且符合规则");
                  } else {
                      UserModel model = client.getUserModel();

                      model.setName(name);
                      model.setSex(sex.equalsIgnoreCase("男") ? 1 : 0);
                      model.setAge(Integer.valueOf(age));
                      model.setTel(tel);

                      System.out.println(model);
                      try {
                          String key = loginView.getEncryptUtil().getSymmetricKey();
                          ViewTool.showMessage(jfrmRegistry, "重要！！！", "请记录key \n\t" + key);
                          model.setAesKey(key);

                          UserModel cloneModel = (UserModel) model.clone();
                          cloneModel.setServerPuk("");

                          ArgumentMaker argumentMaker = new ArgumentMaker();
                          argumentMaker.addArg("com/me/model", cloneModel);
                          String message = argumentMaker.toJson();
                          System.out.println();
                          String puEncrypt = loginView.getEncryptUtil().puEncrypt(message, model.getServerPuk());
                          client.registry(puEncrypt);

                      } catch (Exception e1) {
                          ViewTool.showMessage(jfrmRegistry, "生产密钥失败or加密失败");
                          e1.printStackTrace();
                      }
                  }
              }
            }
        });
    }

    @Override
    public void showView() {
        jfrmRegistry.setVisible(true);
    }

    public void exitView() {
        System.out.println("jfrmRegistry.dispose();");
        jfrmRegistry.dispose();
    }

    @Override
    public void closeView() {
        exitView();
    }

    public class RegistryAction extends ClientActionAdapter {

        public RegistryAction() {
        }

        @Override
        public void serverAbnormalDrop() {
            ViewTool.showMessage(jfrmRegistry, "服务器异常宕机，服务停止！");
            exitView();
        }

        @Override
        public boolean confirmOffline() {
            int choice = ViewTool.choiceYesOrNo(jfrmRegistry, "是否确认下线？");
            return choice == JOptionPane.YES_OPTION;
        }

        @Override
        public void serverForcedown() {
            ViewTool.showMessage(jfrmRegistry, "服务器强制宕机，服务停止！");
            exitView();
        }

        @Override
        public void beGoneByServer() {
            ViewTool.showMessage(jfrmRegistry, "服务器强制本机下线，服务停止！");
            exitView();
        }

        @Override
        public void afterOffline() {
            exitView();
        }

        @Override
        public void RegistryFail() {
            ViewTool.showMessage(jfrmRegistry, "注册失败 请再次尝试");
            exitView();
            loginView.setLoginAction();
        }

        @Override
        public void RegistrySuccess(String mess) {
            ViewTool.showMessage(jfrmRegistry, "注册成功 请记住ID " + mess + " 登陆");
            client.getUserModel().setId(mess);
            exitView();
            loginView.setLoginAction();

        }
    }

    public static void main(String[] args) {
        RegistryView registryView = new RegistryView(new Client(), new LoginView(new Client()));
        registryView.initView();
        registryView.showView();
    }
}