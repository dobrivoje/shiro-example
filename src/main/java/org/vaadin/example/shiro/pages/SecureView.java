package org.vaadin.example.shiro.pages;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import org.apache.shiro.SecurityUtils;
import org.vaadin.example.shiro.functionalities.SecureAccessView;
import org.vaadin.example.shiro.functionalities.SecurityDefs;

public class SecureView extends SecureAccessView {

    private Button logoutButton;

    @Override
    protected void createView() {
        setMargin(true);
        setSpacing(true);

        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "Initializing secure view");
        Label label = new Label("Super secret documentation of your project");
        label.setStyleName(Reindeer.BUTTON_DEFAULT);
        addComponent(label);

        BrowserFrame embedded1 = new BrowserFrame();

        // Obavezno vodeći bek sleš ispred lokacije resursa !!!
        String pdf = "/docs/pdfs/secret.pdf";
        String path = "/docs/imgs/";

        for (int i = 0; i < 8; i++) {
            final String imgPath = path + i + ".jpg";
            Image im = new Image("", new ClassResource(imgPath));
            
            im.setDescription("Dvokliknite da bi ste otvorili sliku.");
            im.setHeight(100, Unit.PIXELS);
            im.setWidth(100, Unit.PIXELS);
            im.addClickListener(new MouseEvents.ClickListener() {

                @Override
                public void click(MouseEvents.ClickEvent event) {
                    if (event.isDoubleClick()) {
                        final Window w = new Window("Slika");
                        w.setStyleName(Reindeer.LAYOUT_BLACK);

                        VerticalSplitPanel vSP = new VerticalSplitPanel();
                        vSP.setSizeFull();

                        VerticalLayout vL = new VerticalLayout();
                        vL.setSizeFull();

                        Button exitButton = new Button("Zatvori", new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                w.close();
                            }
                        });

                        vL.addComponent(exitButton);
                        vL.setComponentAlignment(exitButton, Alignment.BOTTOM_CENTER);

                        Image imgTmp = new Image("sličica", new ClassResource(imgPath));

                        vSP.setSplitPosition(90, Unit.PERCENTAGE);
                        vSP.setFirstComponent(imgTmp);
                        vSP.setSecondComponent(vL);

                        imgTmp.setHeight(100, Unit.PERCENTAGE);
                        imgTmp.setWidth(100, Unit.PERCENTAGE);

                        w.setHeight(66, Unit.PERCENTAGE);
                        w.setWidth(50, Unit.PERCENTAGE);
                        w.center();
                        w.setContent(vSP);

                        getUI().addWindow(w);
                    }
                }
            });

            addComponent(im);
        }

        String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource fileResourcePdf1 = new FileResource(new File(basePath + "/WEB-INF/classes" + pdf));

        embedded1.setSource(fileResourcePdf1);
        embedded1.setHeight(100, Unit.PIXELS);
        embedded1.setWidth(100, Unit.PIXELS);

        addComponent(embedded1);

        logoutButton = new Button("Logout", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout(LogoutView.class.getSimpleName());
            }
        });

        addComponent(logoutButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (isPermitted(SecurityDefs.PERMISSION1)) {
            if (!initialized) {
                createView();
                initialized = true;
            }
        } else {
            noRights(NoRightsView.class.getSimpleName());
        }
    }

    @Override
    protected boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }
}
