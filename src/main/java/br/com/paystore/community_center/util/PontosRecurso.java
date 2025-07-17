package br.com.paystore.community_center.util;

import br.com.paystore.community_center.model.Recursos;

public class PontosRecurso {

    public static final int MEDICO = 4;
    public static final int VOLUNTARIO = 3;
    public static final int KIT_MEDICO = 7;
    public static final int VEICULO = 5;
    public static final int CESTA_BASICA = 2;

    public static int calcularTotalPontos(Recursos recursos) {
        if (recursos == null) return 0;

        return nullSafe(recursos.getMedicos()) * MEDICO
                + nullSafe(recursos.getVoluntarios()) * VOLUNTARIO
                + nullSafe(recursos.getKitsMedicos()) * KIT_MEDICO
                + nullSafe(recursos.getVeiculosTransporte()) * VEICULO
                + nullSafe(recursos.getCestasBasicas()) * CESTA_BASICA;
    }
    private static int nullSafe(Integer valor) {
        return valor != null ? valor : 0;
    }
}
