/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.negozio.swing;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author tss
 */
public class App {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu"); //collego al db
        EntityManager em = emf.createEntityManager();   
        
        creaCliente("Carrefour",em);
        creaCliente("Bennet",em);
                     
        creaProdotti(em);
       
        creaOrdineCarrefour(em);
        
        List<Cliente> clienti= em
                    //.createQuery("select c from Cliente c order by c.ragioneSociale DESC",Cliente.class)
                    .createNamedQuery("Cliente.findByName", Cliente.class)
                    .setParameter("ragsoc", "Lidl")
                    .getResultList(); //i ris li mette in una lista
        
        if (clienti.isEmpty()) {
           System.out.println("Nessun cliente trovato");             
        } 
        for (Cliente cliente : clienti) {             
             System.out.println(cliente); 
        }
    }

    private static void creaCliente(String ragsoc, EntityManager em) {
        Cliente cli = new Cliente(ragsoc);
        cli.setIndirizzo("via castellamonte, 2 Torino");
        
        em.getTransaction().begin();
        em.persist(cli); //sarebbe come ==> insert into negozio
        em.getTransaction().commit();
    }

    private static void creaOrdine(EntityManager em) {
        Cliente cli = new Cliente("Lidl");
        Ordine o=new Ordine();
        o.setNumero("A001");
        o.setDescrizione("Ordine di prova");
        o.setCliente(cli);
        
        em.getTransaction().begin();
        em.persist(o); //sarebbe come ==> insert into negozio
        em.getTransaction().commit();
    }
    
    private static void creaOrdineCarrefour(EntityManager em) {
       
        Cliente cliCarr = em.find(Cliente.class, 1l); //cerco il cliente nel db
                                                // 1l sta per long
        Ordine o=new Ordine();
        o.setNumero("A001");
        o.setDescrizione("Ordine di prova");
        o.setCliente(cliCarr);
        
        Prodotto pCoca = em.find(Prodotto.class, 1l);
        Prodotto pFanta = em.find(Prodotto.class, 2l); //l'ultimo creato ha l'id più grande
        
        o.addDettaglio(new DettaglioOrdine(o, pCoca, 10));
        o.addDettaglio(new DettaglioOrdine(o, pFanta, 1000));
        
        em.getTransaction().begin();
        em.persist(o); //sarebbe come ==> insert into negozio
        em.getTransaction().commit();
    }

    private static void creaProdotti(EntityManager em) {
        Prodotto p1 = new Prodotto();
        p1.setNomeProdotto("CocaCola");
        p1.setPrezzoUni(10);
        
        Prodotto p2 = new Prodotto();
        p2.setNomeProdotto("Fanta");
        p2.setPrezzoUni(15); 
        
        em.getTransaction().begin();
        em.persist(p1); //sarebbe come ==> insert into negozio
        em.persist(p2);
        em.getTransaction().commit();
    }
}
