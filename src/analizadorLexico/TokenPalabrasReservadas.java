package analizadorLexico;
import java.util.HashMap;
import java.util.Map;

public class TokenPalabrasReservadas {
    
    private Map<String, TipoDeToken> map = new HashMap<String, TipoDeToken>();
    
    public TokenPalabrasReservadas(){
        map.put("class", TipoDeToken.pr_class);
        map.put("extends", TipoDeToken.pr_extends);
        map.put("static", TipoDeToken.pr_static);
        map.put("dynamic", TipoDeToken.pr_dynamic);
        map.put("void", TipoDeToken.pr_void);
        map.put("boolean", TipoDeToken.pr_boolean);
        map.put("char", TipoDeToken.pr_char);
        map.put("int", TipoDeToken.pr_int);
        map.put("String", TipoDeToken.pr_String);
        map.put("public", TipoDeToken.pr_public);
        map.put("private", TipoDeToken.pr_private);
        map.put("if", TipoDeToken.pr_if);
        map.put("else", TipoDeToken.pr_else);
        map.put("for", TipoDeToken.pr_for);
        map.put("return", TipoDeToken.pr_return);
        map.put("this", TipoDeToken.pr_this);
        map.put("new", TipoDeToken.pr_new);
        map.put("null", TipoDeToken.pr_null);
        map.put("true", TipoDeToken.pr_true);
        map.put("false", TipoDeToken.pr_false);

    }

    public TipoDeToken getTipoDeToken(String token){
        return map.get(token);
    }
}
