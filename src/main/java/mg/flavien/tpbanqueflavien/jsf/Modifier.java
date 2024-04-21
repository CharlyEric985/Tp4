/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.flavien.tpbanqueflavien.jsf;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import mg.flavien.tpbanqueflavien.entities.CompteBancaire;
import mg.flavien.tpbanqueflavien.jsf.util.Util;
import mg.flavien.tpbanqueflavien.service.GestionnaireCompte;

/**
 *
 * @author flavi
 */
@Named(value = "modifier")
@ViewScoped
public class Modifier implements Serializable {

    /**
     * Creates a new instance of Modifier
     */
    public Modifier() {
    }
    
    private Long id;
    private String nom;
    private CompteBancaire compte;

    @Inject
    private GestionnaireCompte bean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }

    public void loadCompte() {
        this.compte = bean.findById(id);
        this.nom = this.compte.getNom();
    }

    public CompteBancaire getCompteBancaire() {
        return this.compte;
    }
    
    public String valider(){
        String ancienNom = compte.getNom();
        bean.modifierNom(compte,nom);
        Util.addFlashInfoMessage("Nom (" + ancienNom + ") changé en ("+nom+")");
        return "listeComptes?faces-redirect=true";
    }
    
}
