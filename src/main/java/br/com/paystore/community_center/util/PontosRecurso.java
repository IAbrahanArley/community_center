package br.com.paystore.community_center.util;

import br.com.paystore.community_center.model.Recursos;

public class PontosRecurso {

    public static final int MEDICO = 4;
    public static final int VOLUNTARIO = 3;
    public static final int KIT_MEDICO = 7;
    public static final int VEICULO = 5;
    public static final int CESTA_BASICA = 2;

    public static int calcularTotalPontos(Recursos recursos) {
        return recursos.getMedicos() * MEDICO
                + recursos.getVoluntarios() * VOLUNTARIO
                + recursos.getKitsMedicos() * KIT_MEDICO
                + recursos.getVeiculosTransporte() * VEICULO
                + recursos.getCestasBasicas() * CESTA_BASICA;
    }
}
