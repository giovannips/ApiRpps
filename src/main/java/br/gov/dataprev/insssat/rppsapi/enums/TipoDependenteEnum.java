package br.gov.dataprev.insssat.rppsapi.enums;

public enum TipoDependenteEnum {

    CONJUGE('C'),
    FILHO('F'),
    PAIS('P'),
    OUTROS('O');

    private Character tipo;

    TipoDependenteEnum(Character tipo){
        this.tipo = tipo;
    }

    public Character tipo() {
        return tipo;
    }

    public static TipoDependenteEnum valorDe(String name) {
        if (name == null) return null;
        for (TipoDependenteEnum e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static TipoDependenteEnum valorDe(Character valor) {
        if (valor == null) return null;
        for (TipoDependenteEnum e : values()) {
            if (e.tipo().equals(Character.toUpperCase(valor))) {
                return e;
            }
        }
        return null;
    }

}