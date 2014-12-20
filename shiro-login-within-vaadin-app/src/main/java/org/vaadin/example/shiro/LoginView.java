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

    private final LoginWindowLayout loginWindowLayout = new LoginWindowLayout();

    private final TextField usernameTxtField = new TextField("Username");
    private final PasswordField passwordTxtField = new PasswordField("Password");
    private final Button loginBtn = new Button("Login", this);
    private final Label loginMessage = new Label("Invalid username or password");

    public LoginView() {
        usernameTxtField.focus();
        loginWindowLayout.addComponents(usernameTxtField, passwordTxtField, loginBtn, loginMessage);
        loginMessage.setVisible(false);
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
            UI.getCurrent().removeWindow(loginWindowLayout.getWindow());

            getUI().getNavigator().addView(SecureView.class.getSimpleName(), SecureView.class);
            getUI().getNavigator().navigateTo(SecureView.class.getSimpleName());
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            usernameTxtField.setValue("");
            passwordTxtField.setValue("");
            loginMessage.setVisible(true);

            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
        }
    }
}
