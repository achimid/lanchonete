package br.com.achimid.lanchonete.root.base;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {
    private static Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }

    public String exceptionToString(Exception e){
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
