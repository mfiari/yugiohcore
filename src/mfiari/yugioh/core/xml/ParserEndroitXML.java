/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.yugioh.core.xml;

import mfiari.lib.game.objet.ObjetEndroitClassique;
import mfiari.lib.game.objet.ObjetEndroitPassage;
import mfiari.lib.game.position.Orientation;
import mfiari.lib.game.position.Position;
import mfiari.lib.game.ville.Batiment;
import mfiari.lib.game.ville.Endroit;
import mfiari.lib.game.ville.Maison;
import mfiari.lib.game.ville.Quartier;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author mike
 */
public class ParserEndroitXML extends DefaultHandler {

    private Attributes mCurrentAttList = null;
    private Endroit endroit;
    private Quartier quartier;
    private ObjetEndroitPassage objet_endroit_passage = null;
    private boolean boucle = false;
    private String idBoucle = "";
    private int debut = 0;
    private int fin = 0;
    

    public Endroit getEndroit () {
        return this.endroit;
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        this.mCurrentAttList = atts;
        System.out.println(qName);
        System.out.println(this.mCurrentAttList);
        if (qName.equalsIgnoreCase("endroit")) {
            String typeEndroit = this.mCurrentAttList.getValue("type");
            if ("quartier".equals(typeEndroit)) {
                this.quartier = new Quartier(this.mCurrentAttList.getValue("nom"), Integer.valueOf(this.mCurrentAttList.getValue("positionx")), 
                    Integer.valueOf(this.mCurrentAttList.getValue("positiony")), Integer.valueOf(this.mCurrentAttList.getValue("longueur")), 
                    Integer.valueOf(this.mCurrentAttList.getValue("largeur")), null);
            } else {
                this.quartier = new Quartier(this.mCurrentAttList.getValue("nom"), Integer.valueOf(this.mCurrentAttList.getValue("positionx")), 
                    Integer.valueOf(this.mCurrentAttList.getValue("positiony")), Integer.valueOf(this.mCurrentAttList.getValue("longueur")), 
                    Integer.valueOf(this.mCurrentAttList.getValue("largeur")), null);
            }
        } else if (qName.equalsIgnoreCase("sol")) {
            /*this.quartier.setSol(this.getSolByTypeName(this.mCurrentAttList.getValue("type")));*/
        } else if (qName.equalsIgnoreCase("boucle")) {
            /*this.boucle = true;
            int startx = Integer.valueOf(this.mCurrentAttList.getValue("startx"));
            int endx = Integer.valueOf(this.mCurrentAttList.getValue("endx"));
            int starty = Integer.valueOf(this.mCurrentAttList.getValue("starty"));
            int endy = Integer.valueOf(this.mCurrentAttList.getValue("endy"));
            for (int i = startx ; i < endx ; i++) {
                for (int j = starty ; j < endy ; j++) {
                    ObjetEndroitClassique objet_endroit_classique = new ObjetEndroitClassique(this.getTypeObjetByTypeName(this.mCurrentAttList.getValue("type")), i, j);
                    this.quartier.ajouterObjetEndroit(objet_endroit_classique);
                }
            }*/
        } else if (qName.equalsIgnoreCase("objet_endroit")) {
            /*int positionx = Integer.valueOf(this.mCurrentAttList.getValue("positionx"));
            int positiony = Integer.valueOf(this.mCurrentAttList.getValue("positiony"));
            ObjetEndroitClassique objet_endroit_classique = new ObjetEndroitClassique(this.getTypeObjetByTypeName(this.mCurrentAttList.getValue("type")), positionx, positiony);
            this.quartier.ajouterObjetEndroit(objet_endroit_classique);*/
        } else if (qName.equalsIgnoreCase("batiment")) {
            String typeBatiment = this.mCurrentAttList.getValue("type");
            int positionx = Integer.valueOf(this.mCurrentAttList.getValue("positionx"));
            int positiony = Integer.valueOf(this.mCurrentAttList.getValue("positiony"));
            if ("maison".equals(typeBatiment)) {
                Maison maison = new Maison("maison", positionx, positiony, 1, 1, 1, Orientation.face, this.quartier);
                this.quartier.ajouterBatiment(maison);
            } else if ("labo".equals(typeBatiment)) {
                Batiment labo = new Batiment("Labo du prof", positionx, positiony, 1, 1, 0, 1,Orientation.face,  this.quartier);
                this.quartier.ajouterBatiment(labo);
            }
        } else if (qName.equalsIgnoreCase("Objet_endroit_passage")) {
            /*this.objet_endroit_passage = new ObjetEndroitPassage(this.getTypeObjetByTypeName(this.mCurrentAttList.getValue("type")), 
                    Integer.valueOf(this.mCurrentAttList.getValue("positionx")), Integer.valueOf(this.mCurrentAttList.getValue("positiony")), 
                    this.mCurrentAttList.getValue("endroit"), Integer.valueOf(this.mCurrentAttList.getValue("positionxdest")), Integer.valueOf(this.mCurrentAttList.getValue("positionydest")), 0, 
                    Orientation.valueOf(this.mCurrentAttList.getValue("orientation")));*/
        } else if (qName.equalsIgnoreCase("Position")) {
            Position p = new Position(Integer.valueOf(this.mCurrentAttList.getValue("positionX")), 
                    Integer.valueOf(this.mCurrentAttList.getValue("positionY")), Integer.valueOf(this.mCurrentAttList.getValue("positionZ")), 
                    null, this.getOrientationByTypeName(this.mCurrentAttList.getValue("orientation")));
            if (this.objet_endroit_passage != null) {
                this.objet_endroit_passage.setPositionArrive(p);
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("endroit")) {
            if (this.quartier != null) {
                this.endroit = quartier;
            }
        } else if (qName.equalsIgnoreCase("Objet_endroit_passage")) {
            this.quartier.ajouterObjetEndroit(this.objet_endroit_passage);
            this.objet_endroit_passage = null;
        } else if (qName.equalsIgnoreCase("boucle")) {
            this.boucle = false;
            this.idBoucle = "";
            this.debut = 0;
            this.fin = 0;
        }
    }

    /*private Terrain getTerrainByTypeName (String type) {
        if (type.equals("normal")) {
            return Terrains.terrainNormal;
        } else {
            return Terrains.terrainNormal;
        }
    }

    private Sol getSolByTypeName (String type) {
        if (type.equals("herbe")) {
            return Sol.herbe;
        } else {
            return Sol.defaut;
        }
    }

    private Type_objet getTypeObjetByTypeName (String type) {
        switch (type) {
            case "chemin":
                return Type_objet.chemin;
            case "arbre":
                return Type_objet.arbre;
            case "sol_left":
                return Type_objet.chemin;
            case "sol_top_left":
                return Type_objet.chemin;
            case "maison":
                return Type_objet.dos_maison;
            default:
                return Type_objet.chemin;
        }
    }*/

    private Orientation getOrientationByTypeName (String type) {
        if (type.equals("dos")) {
            return Orientation.dos;
        } else {
            return Orientation.face;
        }
    }
}