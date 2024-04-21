/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.flavien.tpbanqueEric.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import java.io.Serializable;
import mg.flavien.tpbanqueflavien.entities.CompteBancaire;
import mg.flavien.tpbanqueflavien.jsf.util.Util;
import mg.flavien.tpbanqueflavien.service.GestionnaireCompte;

/**
 *
 * @author flavi
 */
@Named(value = "ajoutEtRetrait")
@ViewScoped
public class AjoutEtRetrait implements Serializable {

    private Long id;
    private String option;
    private int montant;
    private CompteBancaire compte;

    @Inject
    private GestionnaireCompte bean;

    /**
     * Creates a new instance of AjoutEtRetrait
     */
    public AjoutEtRetrait() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }

    public void loadCompte() {
        this.compte = bean.findById(id);
    }

    public CompteBancaire getCompteBancaire() {
        return this.compte;
    }

    public String valider() {
        boolean erreur = false;
        String link = "";
        try {
            if (montant <= 0) {
                Util.messageErreur("Le montant doit être supérieur à zéro !");
                erreur = true;
            } else {
                if ("retrait".equals(option)) {
                    if (compte.getSolde() < montant) {
                        Util.messageErreur("Solde insuffisant pour effectuer ce retrait !");
                        erreur = true;
                    } else {
                        bean.retirerArgent(compte, montant);
                        Util.addFlashInfoMessage("Retrait de " + montant + " euros effectué avec succès sur le compte " + compte.getNom() + ".");
                    }
                } else if ("ajouter".equals(option)) {
                    bean.deposerArgent(compte, montant);
                    Util.addFlashInfoMessage("Ajout de " + montant + " euros effectué avec succès sur le compte " + compte.getNom() + ".");
                } else {
                    Util.messageErreur("Option invalide !");
                    erreur = true;
                }
            }
            link = "listeComptes?faces-redirect=true";
            if (erreur) {
                return null;
            }
        } catch (OptimisticLockException ex) {
            Util.messageErreur("Le compte de " + compte.getNom()+ " a été modifié ou supprimé par un autre utilisateur !");
            return null; 
        } 
        return link;
    }

}
