package br.gov.dataprev.rppsapi.enums;

public enum TipoServidorEnum {

    CIVIL('C'),
    MILITAR('M');

    private Character tipo;

    TipoServidorEnum(Character tipo){
        this.tipo = tipo;
    }

    public Character tipo() {
        return tipo;
    }

    public static TipoServidorEnum valorDe(String name) {
        for (TipoServidorEnum e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static TipoServidorEnum valorDe(Character valor) {
        for (TipoServidorEnum e : values()) {
            if (e.tipo().equals(Character.toUpperCase(valor))) {
                return e;
            }
        }
        return null;
    }

}