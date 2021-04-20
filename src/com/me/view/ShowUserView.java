package com.me.view;

import com.me.CSFramework.core.Client;
import com.me.model.UserModel;

/**
 * @author quan
 * @create 2021-04-18 8:21
 */
public class ShowUserView extends RegistryView {

    public ShowUserView(Client client) {
        super(client, null);
    }

    @Override
    public void reinit() {
        super.reinit();

        jtxtName.setEditable(false);
        jtxtSex.setEditable(false);
        jtxtAge.setEditable(false);
        jtxtTel.setEditable(false);

        UserModel model = client.getUserModel();
        jtxtName.setText(model.getName());
        jtxtSex.setText(model.getSex()==1?"男":"女");
        jtxtAge.setText(String.valueOf(model.getAge()));
        jtxtTel.setText(model.getTel());

    }
}
