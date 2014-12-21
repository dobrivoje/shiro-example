/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.example.shiro.functionalities;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.vaadin.example.shiro.pages.NoRightsView;

/**
 *
 * @author dobri
 */
public abstract class SecureAccessView extends VerticalLayout implements View {

    protected boolean initialized = false;
    protected Subject subject = SecurityUtils.getSubject();

    public SecureAccessView() {
        setMargin(true);
        setSpacing(true);
    }

    protected abstract void login();

    protected void logout(String page) {
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "LOGOUT !");
        UI.getCurrent().getNavigator().navigateTo(page);

        initialized = false;
    }

    protected void noRights(String page) {
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "NO RIGHTS !");
        UI.getCurrent().getNavigator().navigateTo(page);

        initialized = false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (SecurityUtils.getSubject().isPermitted(SecurityDefs.PERMISSION2)) {
            if (!initialized) {
                login();
                initialized = true;
            }
        } else {
            noRights(NoRightsView.class.getSimpleName());
        }
    }
}
