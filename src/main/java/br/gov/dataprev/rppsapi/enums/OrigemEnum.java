package br.gov.dataprev.rppsapi.enums;

public enum OrigemEnum {

    FEDERAL('F'),
    ESTADUAL('E'),
    MUNICIPAL('M');

    private Character origem;

    OrigemEnum(Character origem){
        this.origem = origem;
    }

    public Character origem() {
        return origem;
    }

    public static OrigemEnum valorDe(String name) {
        if (name == null) return null;
        for (OrigemEnum e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static OrigemEnum valorDe(Character valor) {
        if (valor == null) return null;
        for (OrigemEnum e : values()) {
            if (e.origem().equals(Character.toUpperCase(valor))) {
                return e;
            }
        }
        return null;
    }

}