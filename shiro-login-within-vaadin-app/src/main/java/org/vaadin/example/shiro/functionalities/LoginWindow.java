/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.example.shiro.functionalities;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 *
 * @author dobri
 */
public class LoginWindow extends Window implements ClickListener {

    // calling View that interacts with this Login Window
    private ClickListener callingView;

    private final FormLayout formLayout = new FormLayout();

    private final float WINDOW_WIDTH_PERCENT = 33;
    private final float WINDOW_HEIGHT_PERCENT = 40;

    private final TextField usernameTxtField = new TextField("Username");
    private final PasswordField passwordTxtField = new PasswordField("Password");
    private final Button loginBtn = new Button("Login", this);
    private final Label loginMessage = new Label("Invalid username or password");

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public LoginWindow() {
        setCaption("Login Window");

        formLayout.setMargin(true);
        formLayout.setSpacing(true);

        formLayout.addComponents(usernameTxtField, passwordTxtField, loginBtn, loginMessage);
        usernameTxtField.focus();
        loginMessage.setVisible(false);

        center();
        setWidth(WINDOW_WIDTH_PERCENT, Unit.PERCENTAGE);
        setHeight(WINDOW_HEIGHT_PERCENT, Unit.PERCENTAGE);
        setContent(formLayout);
    }

    public LoginWindow(ClickListener callingView) {
        this();
        this.callingView = callingView;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public String getUsername() {
        return usernameTxtField.getValue();
    }

    public String getPassword() {
        return passwordTxtField.getValue();
    }

    public void setUsername(String username) {
        usernameTxtField.setValue(username);
    }

    public void setPassword(String password) {
        passwordTxtField.setValue(password);
    }

    public void setLoginMessageVisible(boolean visible) {
        loginMessage.setVisible(visible);
    }
    //</editor-fold>

    @Override
    public void buttonClick(Button.ClickEvent event) {
        callingView.buttonClick(event);
    }

}
