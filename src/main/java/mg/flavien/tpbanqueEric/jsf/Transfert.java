/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.flavien.tpbanqueEric.jsf;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import mg.flavien.tpbanqueEric.entities.CompteBancaire;
import mg.flavien.tpbanqueEric.jsf.util.Util;
import mg.flavien.tpbanqueEric.service.GestionnaireCompte;

/**
 *
 * @author flavi
 */
@Named(value = "transfert")
@RequestScoped
public class Transfert {

    @Inject
    private GestionnaireCompte bean;
    private Long idSource;
    private Long idDestination;
    private int montant;

    public Long getIdSource() {
        return idSource;
    }

    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }

    public Long getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(Long idDestination) {
        this.idDestination = idDestination;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
    
    
    
    public Transfert() {
    }
    
    public String valider() {
        boolean erreur = false;
        CompteBancaire source=bean.findById(idSource);
        CompteBancaire destinataire=bean.findById(idDestination);
        if(source==null){
            Util.messageErreur("Aucun compte avec cet id !\", \"Aucun compte avec cet id !\", \"form:source");
            erreur=true;
        }else if(source.getSolde()<montant){
            Util.messageErreur("(Source) n'a pas assez de fond pour la transaction!");
            erreur=true;
        }else if(destinataire==null){
            Util.messageErreur("Aucun compte avec cet id !\", \"Aucun compte avec cet id !\", \"form:destinataire");
            erreur=true;
        }else if(montant<0){
            Util.messageErreur("Vous devez mettre un montant correct!");
            erreur=true;
        }
        String lien="listeComptes?faces-redirect=true";
        if(erreur){
            return null;
        }
        bean.transferer(source, destinataire, montant);
        Util.addFlashInfoMessage("Le transfert de "+montant+"Euro de "+source.getNom()+" vers "+destinataire.getNom()+" est bien effectué!");
        return lien;
    }
    
    
}
