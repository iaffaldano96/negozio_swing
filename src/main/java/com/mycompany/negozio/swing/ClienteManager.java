/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.negozio.swing;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author tss
 */
public class ClienteManager {

    private static EntityManager getEm() {
        return Principale.getEm();
    }

    public static Cliente save(Cliente cli) {
        
        getEm().getTransaction().begin();
        Cliente salvato = getEm().merge(cli);
        getEm().getTransaction().commit();

        return salvato;
    }

    public static List<Cliente> findAll() {
        return getEm().createNamedQuery("Cliente.all").getResultList();
    }

    public static Cliente find(Long cliente_id) {
        return getEm().find(Cliente.class, cliente_id);
    }

    static void remove(Long id) {
        Cliente finded = find(id);
        getEm().getTransaction().begin();
        getEm().remove(finded);
        getEm().getTransaction().commit();
    }
}
