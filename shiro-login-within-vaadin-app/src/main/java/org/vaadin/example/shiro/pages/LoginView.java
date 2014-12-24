package org.vaadin.example.shiro.pages;

import org.vaadin.example.shiro.functionalities.LoginWindow;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View, ClickListener {

    private LoginWindow loginWindow = null;

    public LoginView() {
        setMargin(true);
        setSpacing(true);

        loginWindow = (loginWindow == null ? new LoginWindow(this) : loginWindow);

        if (UI.getCurrent().getWindows().isEmpty()) {
            UI.getCurrent().addWindow(loginWindow);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    @Override
    public void buttonClick(ClickEvent event) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                loginWindow.getUsername(), loginWindow.getPassword());
        try {
            subject.login(token);
            UI.getCurrent().removeWindow(loginWindow);
            UI.getCurrent().getNavigator().navigateTo(SecureView.class.getSimpleName());
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            loginWindow.setUsername("");
            loginWindow.setPassword("");
            loginWindow.setLoginMessageVisible(true);
            loginWindow.setUsernameFocus();
            
            UI.getCurrent().getNavigator().navigateTo(NoRightsView.class.getSimpleName());
        }
    }
}
