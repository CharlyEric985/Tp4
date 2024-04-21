/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.flavien.tpbanqueflavien.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import mg.flavien.tpbanqueflavien.entities.CompteBancaire;
import mg.flavien.tpbanqueflavien.entities.OperationBancaire;
import mg.flavien.tpbanqueflavien.service.GestionnaireCompte;

/**
 *
 * @author flavi
 */
@Named(value = "operations")
@ViewScoped
public class Operations implements Serializable{
    private Long idcompte;
    private CompteBancaire compte;
    
    @Inject
    private GestionnaireCompte bean;
    
    public void loadCompteById(){
        this.compte = bean.findByIdWithOperations(this.idcompte);
    }
    
    public List<OperationBancaire> AllOperations(){
        return this.compte.getOperations();
    }

    public Long getIdcompte() {
        return idcompte;
    }

    public void setIdcompte(Long idcompte) {
        this.idcompte = idcompte;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }
    
    
    /**
     * Creates a new instance of Operations
     */
    public Operations() {
    }
    
}
