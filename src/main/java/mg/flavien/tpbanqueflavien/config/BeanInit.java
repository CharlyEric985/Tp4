/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.flavien.tpbanqueflavien.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import mg.flavien.tpbanqueflavien.entities.CompteBancaire;
import mg.flavien.tpbanqueflavien.service.GestionnaireCompte;

/**
 *
 * @author flavi
 */
@ApplicationScoped
public class BeanInit {
    
    @Inject    
    private GestionnaireCompte gestionnaireCompte;
    
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        if(gestionnaireCompte.compterCompter() == 0 ){
            CompteBancaire[] compte = new CompteBancaire[4];
            compte[0] = new CompteBancaire("John Lennon", 150000);
            compte[1] = new CompteBancaire("Paul McCartney", 950000);
            compte[2] = new CompteBancaire("Ringo Starr", 20000);
            compte[3] = new CompteBancaire("Georges Harrisson", 100000);
            for (CompteBancaire c : compte) {
                gestionnaireCompte.creerCompte(c);
            }
        }
    }
}
