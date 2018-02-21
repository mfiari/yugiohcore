/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.yugioh.core.ville;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mfiari.lib.game.ville.Endroit;
import mfiari.yugioh.core.xml.ParserEndroitXML;

/**
 *
 * @author mike
 */
public class EndroitFactory {
    
    private String directory;
    private Endroit endroit;
    
    public EndroitFactory () {
        this.directory = "media/endroits/";
    }
    
    public Endroit createEndroit (String code) {
        this.loadFromXML(code);
        return endroit;
    }
    
    private void loadFromXML (String code) {
        String filename = this.directory + code + ".xml";
        System.out.println(filename);
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            ParserEndroitXML myHandler = new ParserEndroitXML();
            System.out.println("debut parse");
            sp.parse(inputStream, myHandler);
            System.out.println("fin parse");
            this.endroit = myHandler.getEndroit();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EndroitFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
}