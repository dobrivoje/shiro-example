/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.example.shiro;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author root
 */
public class LoginWindowLayout extends VerticalLayout {

    private final Window window;

    private final float WINDOW_WIDTH_PERCENT = 33;
    private final float WINDOW_HEIGHT_PERCENT = 40;

    public LoginWindowLayout() {
        setMargin(true);
        setSpacing(true);
        
        this.window = new Window("Login Window");

        this.window.center();
        this.window.setWidth(WINDOW_WIDTH_PERCENT, Unit.PERCENTAGE);
        this.window.setHeight(WINDOW_HEIGHT_PERCENT, Unit.PERCENTAGE);
        this.window.setContent(this);

        UI.getCurrent().addWindow(this.window);
    }

    public Window getWindow() {
        return window;
    }
}
