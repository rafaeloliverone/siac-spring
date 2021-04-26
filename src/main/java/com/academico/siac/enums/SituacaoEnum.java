package com.academico.siac.enums;

public enum SituacaoEnum {
    AP("Aprovado"),
    RP("Reprovado"),
    FN("Final"),
    MT("Matriculado"),
    RF("Reprovado por falta");

    private String label;

    public String getLabel() {
        return label;
    }

    private SituacaoEnum(String label) {
        this.label = label;
    }

}
