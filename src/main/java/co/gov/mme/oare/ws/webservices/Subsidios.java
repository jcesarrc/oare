package co.gov.mme.oare.ws.webservices;

import co.gov.mme.oare.ws.dao.Query;
import co.gov.mme.oare.ws.entities.RegistroVenta;
import co.gov.mme.oare.ws.entities.Respuesta;

public class Subsidios {

    /**
     * Función para calcular el valor del subsidio que le corresponde al usuario. Almacena auditoria.
     */
    public Respuesta calcularSubsidio(String idEmpresa, String idVendedor, String ip,
                                   long idUsuario, int capCilindro, double valorCilindro)
    {
        Respuesta response = new Respuesta();
        Query query = new Query();
        try
        {
            if (!query.consultarSiExcedeNumeroDeConsultasDiarias(idVendedor))
            {
                if (query.consultarSiEsBeneficiario(idUsuario))
                {
                    String subsidio=query.consultarSubsidio(idUsuario, capCilindro, valorCilindro, ip, idVendedor);
                    if (subsidio!=null)
                        response = new Respuesta(subsidio);
                }
                else response = new Respuesta("OK",""+valorCilindro,
                        "El usuario no es beneficiario del subsidio. El monto a pagar por el usuario es: $" + valorCilindro);
            }
            else response = new Respuesta("ERROR", "", "El vendedor ha excedido el número máximo de consultas diarias");
            return response;

        }
        catch (Exception ex)
        {
            response = new Respuesta("ERROR", "", "Error al consultar la información ingresada, por favor verifique los datos e intente nuevamente");
            return response;
        }

    }

    /**
     * Función para validar el inicio de sesión del usuario
     */
    public Respuesta validarSesion(String idEmpresa, String password)
    {

        Respuesta response = new Respuesta();
        Query query = new Query();

        try
        {
            String sesion = query.validarSesion(idEmpresa.toUpperCase(),password);
            if (sesion!=null)
                response = new Respuesta("OK", "", sesion);
            return response;
        }
        catch (Exception ex)
        {
            response = new Respuesta("ERROR", "",
                    "Error al consultar la información ingresada, por favor verifique la los datos e intente nuevamente");
            return response;
        }

    }

    /*
     * Función para realizar el registro de la venta en la base de datos
     */
    public Respuesta registrarVenta(String ip, String idEmpresa, String idVendedor, long idUsuario, int capCilindro,
                                 double valorCilindro, String nifCilindro, double latitud, double longitud, long factura)
    {

        Respuesta response = new Respuesta();
        Query query = new Query();
        try
        {
            if (query.consultarSiExcedeNumeroDeConsultasDiarias(idVendedor))
            {
                if (query.consultarSiEsBeneficiario(idUsuario))
                {

                    if (query.consultarSiTieneRegistroEnProceso(idUsuario))
                    {
                        RegistroVenta regVenta = query.registrarVenta(ip,idEmpresa,idVendedor,idUsuario,capCilindro,valorCilindro, nifCilindro,
                                latitud,longitud,factura);
                        if (regVenta != null)
                        {
                            double valorTotal = valorCilindro - regVenta.getSubsidio();
                            response = new Respuesta("OK", "", "REGISTRO EXITOSO No. APROBACIÓN " + regVenta.getId() +
                                    "\nEl valor del subsidio es $" + regVenta.getSubsidio() +
                                    "\nEl valor total de la compra es: $" + valorTotal);
                        }
                        else
                            response = new Respuesta("ERROR", "",
                                    "El registro no ha sido almacenado, por favor intente nuevamente");
                    }
                    else
                        response = new Respuesta("ERROR", "", "Existe otra transacción en proceso, por favor intente más tarde");
                }
                else
                    response = new Respuesta("OK", "", "El usuario no es beneficiario del subsidio. El monto a pagar por el usuario es: $" + valorCilindro);
            }
            else
                response = new Respuesta("ERROR", "", "El vendedor ha excedido el número máximo de consultas diarias");

            return response;
        }
        catch (Exception ex)
        {
            try{
                query.borrarTransacciones(idUsuario);

            }catch(Exception exInner){

            }

            response = new Respuesta("ERROR", "", "Error al consultar la información ingresada, por favor verifique los datos e intente nuevamente");
            return response;
        }

    }

    /**
     * Función para verificar los permisos para hacer una operación en el sistema
     */
    public boolean verificarPermisos(String idEmpresa, String idVendedor)
    {
        Query query = new Query();
        try
        {
            return query.verificarPermisos(idEmpresa, idVendedor);
        }
        catch (Exception ex)
        {
            return false;
        }

    }
}
