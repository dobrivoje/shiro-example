/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.example.shiro.functionalities;

/**
 *
 * @author dobri
 */
public enum SecurityDefs_New {

    ROLE1("stampac_print_pdf"),
    ROLE2("stampac_print_all"),
    ROLE_ADMIN("admin"),
    PERMISSION1("xerox5225:print:pdf"),
    PERMISSION2("xerox5225:print:*");

    private final String name;

    private SecurityDefs_New(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getRole() {
        return toString();
    }

}
