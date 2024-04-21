/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.flavien.tpbanqueflavien.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import mg.flavien.tpbanqueflavien.entities.CompteBancaire;
import mg.flavien.tpbanqueflavien.entities.OperationBancaire;

/**
 *
 * @author flavi
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "root", // nom et
        password = "root", // mot de passe que vous avez donnés lors de la création de la base de données
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true",
            "driverClass=com.mysql.cj.jdbc.Driver"
        }
)
@ApplicationScoped
public class GestionnaireCompte {

    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    @Transactional
    public void creerCompte(CompteBancaire c) {
        c.getOperations().add(new OperationBancaire("Création du compte", c.getSolde()));
        em.persist(c);
    }

    public List<CompteBancaire> getAllComptes() {
        Query query = em.createQuery("select c from CompteBancaire as c");
        return query.getResultList();
    }

    public CompteBancaire findByIdWithOperations(Long id) {
        return em.createQuery("SELECT cb FROM CompteBancaire cb JOIN FETCH cb.operations WHERE cb.id = :id", CompteBancaire.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public int compterCompter() {
        return this.getAllComptes().size();
    }

    public CompteBancaire findById(Long idCompte) {
        return em.find(CompteBancaire.class, idCompte);
    }

    @Transactional
    public void transferer(CompteBancaire source, CompteBancaire destination,
            int montant) {
        retirerArgent(source, montant);
        deposerArgent(destination, montant);
    }

    @Transactional
    public CompteBancaire update(CompteBancaire compteBancaire) {
        return em.merge(compteBancaire);
    }

    @Transactional
    public void retirerArgent(CompteBancaire source, int montant) {
        source.getOperations().add(new OperationBancaire("Débit", (-1 * montant)));
        source.retirer(montant);
        update(source);
    }

    @Transactional
    public void deposerArgent(CompteBancaire source, int montant) {
        source.getOperations().add(new OperationBancaire("Crédit", montant));
        source.deposer(montant);
        update(source);
    }

    @Transactional
    public void supprimerCompte(CompteBancaire compte) {
        em.remove(em.merge(compte));
    }

    @Transactional
    public void modifierNom(CompteBancaire compte, String nom) {
        compte.getOperations().add(new OperationBancaire("Modification compte", compte.getSolde()));
        compte.setNom(nom);
        update(compte);
    }
}
