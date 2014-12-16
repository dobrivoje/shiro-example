package org.vaadin.example.shiro;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class LoginView extends FormLayout implements View, ClickListener {

    public static final String ID = "LoginView";
    private final LoginLayout loginWindow = new LoginLayout();

    private final TextField usernameTxtField = new TextField("Username");
    private final PasswordField passwordTxtField = new PasswordField("Password");
    private final Button loginBtn = new Button("Login", this);
    private final Label invalidPasswordLabel = new Label("Invalid username or password");

    public LoginView() {
        usernameTxtField.focus();
        loginWindow.addComponents(usernameTxtField, passwordTxtField, loginBtn, invalidPasswordLabel);
        invalidPasswordLabel.setVisible(false);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
    }

    @Override
    public void buttonClick(ClickEvent event) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                usernameTxtField.getValue(), passwordTxtField.getValue());
        try {
            subject.login(token);
            UI.getCurrent().removeWindow(loginWindow.getWindow());
            
            getUI().getNavigator().addView(SecureView.ID, SecureView.class);
            getUI().getNavigator().navigateTo(SecureView.ID);
            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            usernameTxtField.setValue("");
            passwordTxtField.setValue("");
            invalidPasswordLabel.setVisible(true);
        }
    }
}
