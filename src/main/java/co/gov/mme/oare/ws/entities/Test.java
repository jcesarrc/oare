package co.gov.mme.oare.ws.entities;

import co.gov.mme.oare.ws.dao.Query;
import co.gov.mme.oare.ws.webservices.Subsidios;

/**
 * Created by jcesarrc on 10/15/15.
 */
public class Test {

    public static void mainmethod(String args[]){

        String empresa = "1";
        String password = "131313";

        Query q = new Query();

        try {
            Subsidios subsi = new Subsidios();
            Respuesta rta = subsi.calcularSubsidio("MME","1","127.0.0.1",123456789,20,256789);
            System.out.println(rta.getCode());
            System.out.println(rta.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
