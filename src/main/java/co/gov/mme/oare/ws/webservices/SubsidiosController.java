package co.gov.mme.oare.ws.webservices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.mme.oare.ws.entities.Respuesta;

@RestController
public class SubsidiosController {

    @RequestMapping("/validarsesion")
    public Respuesta validarSesion(@RequestParam(value="idempresa") String idempresa,
                                   @RequestParam(value="password") String password) {
        Subsidios subsi = new Subsidios();
        return subsi.validarSesion(idempresa, password);
    }


    @RequestMapping("/calcularsubsidio")
    public Respuesta calcularSubsidio(@RequestParam(value="idempresa") String empresa,
                                      @RequestParam(value="idvendedor") String vendedor,
                                      @RequestParam(value="ip") String ip,
                                      @RequestParam(value="idusuario") long usuario,
                                      @RequestParam(value="capacidadcilindro") int capacidadCilindro,
                                      @RequestParam(value="valorcilindro") double valorCilindro) {
        Subsidios subsi = new Subsidios();
        return subsi.calcularSubsidio(empresa, vendedor, ip, usuario, capacidadCilindro, valorCilindro);
    }


    @RequestMapping("/registrarventa")
    public Respuesta calcularSubsidio(@RequestParam(value="idempresa") String empresa,
                                      @RequestParam(value="idvendedor") String vendedor,
                                      @RequestParam(value="ip") String ip,
                                      @RequestParam(value="idusuario") long usuario,
                                      @RequestParam(value="capacidadcilindro") int capacidadCilindro,
                                      @RequestParam(value="valorcilindro") double valorCilindro,
                                      @RequestParam(value="nifcilindro") String nifCilindro,
                                      @RequestParam(value="latitud") double latitud,
                                      @RequestParam(value="longitud") double longitud,
                                      @RequestParam(value="factura") long factura) {
        Subsidios subsi = new Subsidios();
        return subsi.registrarVenta(ip,empresa,vendedor,usuario,capacidadCilindro,valorCilindro,nifCilindro,latitud,longitud,factura);
    }

}