package analizadorLexico;


public enum TipoDeToken{
    pr_class,
    pr_extends,
    pr_static,
    pr_dynamic,
    pr_void,
    pr_boolean,
    pr_char,
    pr_int,
    pr_String,
    pr_public,
    pr_private,
    pr_if,
    pr_else,
    pr_for,
    pr_return,
    pr_this,
    pr_new,
    pr_null,
    pr_true,
    pr_false,

    id_clase,
    id_metVar,

    lit_entero,
    lit_caracter,
    lit_string,

    punt_parentIzq,
    punt_parentDer,
    punt_llaveIzq,
    punt_llaveDer,
    punt_puntoYComa,
    punt_coma,
    punt_punto,
    
    op_mayor,
    op_menor,
    op_not,
    op_igualdad,
    op_mayorOIgual,
    op_menorOIgual,
    op_notIgual,
    op_suma,
    op_resta,
    op_multiplicacion,
    op_division,
    op_and,
    op_or,
    op_modulo,

    op_asignacion,
    op_incremento,
    op_decremento,
    
    EOF
}
