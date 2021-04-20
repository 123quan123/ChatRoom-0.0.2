package com.me.view;

import com.me.CSFramework.core.Client;
import com.me.CSFramework.core.ClientActionAdapter;
import com.me.model.UserModel;
import com.me.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginView implements IView {
	private Client client;
	
	private JFrame jfrmLogin;
	private JTextField jtxtUserId;
	private JPasswordField jpswPassword;
	
	private JLabel jlblUserId;
	private JLabel jlblPassword;
	
	private JButton jbtnLogin;
	private JButton jbtnRegistry;

	private LoginAction loginAction;

	public LoginView(Client client) {
		this.client = client;
		loginAction = new LoginAction();
		this.client.setClientAction(loginAction);
		initView();
	}

	@Override
	public void init() {
		jfrmLogin = new JFrame("chatroom - 登录");
		jfrmLogin.setSize(new Dimension(360, 250));
		jfrmLogin.setLocationRelativeTo(null);
		jfrmLogin.setLayout(new BorderLayout());
		jfrmLogin.setResizable(false);
		jfrmLogin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JLabel jlblLoginTopic = new JLabel("chatroom - 登录", 0);
		jlblLoginTopic.setForeground(topicColor);
		jlblLoginTopic.setFont(topicFont);
		jfrmLogin.add(jlblLoginTopic, BorderLayout.NORTH);
		
		JPanel jpnlLogin = new JPanel(new BorderLayout());
		jfrmLogin.add(jpnlLogin, BorderLayout.CENTER);
		
		JLabel jlblLoginHead = new JLabel(" ");
		jlblLoginHead.setFont(topicFont);
		jpnlLogin.add(jlblLoginHead, BorderLayout.NORTH);
		
		JPanel jpnlLoginContext = new JPanel(new GridLayout(3, 0));
		jpnlLogin.add(jpnlLoginContext, BorderLayout.CENTER);
		
		JPanel jpnlStuId = new JPanel(new FlowLayout());
		jpnlLoginContext.add(jpnlStuId, 0);
		
		JPanel jpnlPassword = new JPanel(new FlowLayout());
		jpnlLoginContext.add(jpnlPassword, 1);
		
		JLabel jlblBlank1 = new JLabel(" ");
		jpnlLoginContext.add(jlblBlank1, 2);
		
		jlblUserId = new JLabel("账号 ");
		jlblUserId.setFont(normalFont);
		jpnlStuId.add(jlblUserId);

		jtxtUserId = new JTextField(20);
		jtxtUserId.setFont(normalFont);
		jpnlStuId.add(jtxtUserId);
		
		jlblPassword = new JLabel("密码 ");
		jlblPassword.setFont(normalFont);
		jpnlPassword.add(jlblPassword);

		jpswPassword = new JPasswordField(20);
		jpswPassword.setFont(normalFont);
		jpnlPassword.add(jpswPassword);
		
		JPanel jpnlFooter = new JPanel(new FlowLayout());
		jpnlLogin.add(jpnlFooter, BorderLayout.SOUTH);
		
		jbtnRegistry = new JButton(" 注册 ");
		jbtnRegistry.setFont(normalFont);
		jbtnRegistry.setForeground(Color.GRAY);
		jbtnRegistry.setCursor(cursorHand);
		jpnlFooter.add(jbtnRegistry, BorderLayout.SOUTH);
		
		jbtnLogin = new JButton(" 登录 ");
		jbtnLogin.setFont(btnFont);
		jpnlFooter.add(jbtnLogin);
	}

	@Override
	public void reinit() {
	}

	@Override
	public void dealAction() {
		jfrmLogin.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeView();
			}
		});
		
		jtxtUserId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				jtxtUserId.selectAll();
			}
		});
		
		jpswPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				jpswPassword.setText("");
			}
		});
		
		jbtnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!jlblUserId.getText().trim().equalsIgnoreCase("账号")) {
					jlblUserId.setText("账号 ");
					jlblPassword.setText(" key ");
					jpswPassword.setEditable(true);
				} else if (!jtxtUserId.getText().trim().equalsIgnoreCase("")) {
					jpswPassword.setEditable(true);
					String id = jtxtUserId.getText().trim();
					char[] chPassword = jpswPassword.getPassword();
					String key = String.valueOf(chPassword);
					key = String.valueOf(key);
				
					UserModel userModel = client.getUserModel();
					userModel.setId(id);
					userModel.setAesKey(key);
					ArgumentMaker argumentMaker = new ArgumentMaker();
					UserModel userModel2 = new UserModel();
					userModel2.setId(id);
					userModel2.setAesKey(key);
					if (userModel2.getId().equals("") && userModel2.getAesKey().equals("")) {
						return;
					}
					argumentMaker.addArg("com/me/model", userModel2);
					String encrypt;
					try {
						encrypt = RSAUtil.puEncrypt(argumentMaker.toJson(), client.getUserModel().getServerPuk());
						client.loginIn(encrypt);
					} catch (Exception e1) {
						ViewTool.showMessage(jfrmLogin, "aes加密失败");
						e1.printStackTrace();
					}
				}
			}
		});
		
		jbtnRegistry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistryView registryView = new RegistryView(client, LoginView.this);
				registryView.showView();
			}
		});
	}

	@Override
	public void showView() {
		jfrmLogin.setVisible(true);
	}

	public void exitView() {
		System.out.println("jfrmLogin.dispose();");
		jfrmLogin.dispose();
	}
	
	@Override
	public void closeView() {
		client.offline();
	}
	
	public class LoginAction extends ClientActionAdapter {
		
		public LoginAction() {
		}

		@Override
		public void serverAbnormalDrop() {
			ViewTool.showMessage(jfrmLogin, "服务器异常宕机，服务停止！");
			exitView();
		}

		@Override
		public boolean confirmOffline() {
			int choice = ViewTool.choiceYesOrNo(jfrmLogin, "是否确认下线？");
			return choice == JOptionPane.YES_OPTION;
		}

		@Override
		public void serverForcedown() {
			ViewTool.showMessage(jfrmLogin, "服务器强制宕机，服务停止！");
			exitView();
		}

		@Override
		public void beGoneByServer() {
			ViewTool.showMessage(jfrmLogin, "服务器强制本机下线，服务停止！");
			exitView();
		}

		@Override
		public void afterOffline() {
			exitView();
		}

		@Override
		public void loginInFail() {
			ViewTool.showMessage(jfrmLogin, "账号密码错误或者进行注册");
			jtxtUserId.setText("");
			jpswPassword.setText("");
		}

		@Override
		public void loginInSuccess(String mess) {
			try {
				String decrypt = AESUtil.decrypt(mess, client.getUserModel().getAesKey());
				ArgumentMaker argumentMaker = new ArgumentMaker(decrypt);
				UserModel value = argumentMaker.getValue("com/me/model", UserModel.class);
				client.getUserModel().setName(value.getName());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			ViewTool.showMessage(jfrmLogin, "登录成功");
			exitView();
			new ChatView(client).showView();
		
		}

		@Override
		public void RegistryFail() {
			ViewTool.showMessage(jfrmLogin, "注册失败 请再次尝试");
			jtxtUserId.setText("");
			jpswPassword.setText("");
		}

		@Override
		public void RegistrySuccess(String mess) {
			ViewTool.showMessage(jfrmLogin, "注册成功 请记住ID 登陆");
			try {
				String id = mess;
				
				jtxtUserId.setText(id);
				jpswPassword.setText("");
			} catch (Exception e) {
				ViewTool.showMessage(jfrmLogin, "RegistrySuccess  注册成功解密失败");
				e.printStackTrace();
			}
			
		}
	}

	public void setLoginAction() {
		client.setClientAction(loginAction);
	}

	public static void main(String[] args) {
		new LoginView(new Client()).showView();
	}
	
}
