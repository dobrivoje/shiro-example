/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 *
 * @author root
 */
public class NewMain {

    public static void main(String[] args) {
        Sha256Hash s2 = new Sha256Hash("secret");
        Sha256Hash s3 = new Sha256Hash("");
        
        System.err.println(s2.toHex());
        System.err.println(s3.toHex());
    }
    
}
