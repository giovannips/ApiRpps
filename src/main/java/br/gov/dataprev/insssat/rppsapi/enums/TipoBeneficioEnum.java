package br.gov.dataprev.insssat.rppsapi.enums;

public enum TipoBeneficioEnum {

    APOSENTADORIA('A'),
    PENSAO('P');

    private Character tipo;

    TipoBeneficioEnum(Character tipo){
        this.tipo = tipo;
    }

    public Character tipo() {
        return tipo;
    }

    public static TipoBeneficioEnum valorDe(String name) {
        if (name == null) return null;
        for (TipoBeneficioEnum e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static TipoBeneficioEnum valorDe(Character valor) {
        if (valor == null) return null;
        for (TipoBeneficioEnum e : values()) {
            if (e.tipo().equals(Character.toUpperCase(valor))) {
                return e;
            }
        }
        return null;
    }

}