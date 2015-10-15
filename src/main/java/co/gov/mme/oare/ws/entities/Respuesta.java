package co.gov.mme.oare.ws.entities;

/**
 * Created by jcesarrc on 10/15/15.
 */
public class Respuesta {

    private String code;
    private String content;
    private String message;

    public Respuesta(String code, String content, String message){

        this.code = code;
        this.content = content;
        this.message = message;
    }

    public Respuesta(String message){

        this.code = "OK";
        this.content = "";
        this.message = message;

    }

    public Respuesta(){
        this.code = "ERROR";
        this.content = "";
        this.message = "";
    }


    public String getCode() {
        return code;
    }



    public String getContent() {
        return content;
    }



    public String getMessage() {
        return message;
    }


}
