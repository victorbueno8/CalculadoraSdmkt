package br.edu.ifsp.scl.calculadorasdmkt.model

interface ConfiguracaoDAO {
    fun createOrUpdateConfiguracao(configuracao: Configuracao)
    fun readConfiguracao(): Configuracao
}