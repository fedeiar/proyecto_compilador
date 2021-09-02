package analizadorLexico;

public class Token {
    
    private TipoDeToken token;
    private String lexema;
    private int nroLinea;

    public Token(TipoDeToken token, String lexema, int nroLinea){
        this.token = token;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
    }

    public TipoDeToken getTipoDeToken(){
        return token;
    }

    public String getLexema(){
        return lexema;
    }

    public int getNroLinea(){
        return nroLinea;
    }
}
