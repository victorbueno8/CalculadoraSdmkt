package br.edu.ifsp.scl.calculadorasdmkt.model

import android.content.Context

class ConfiguracaoService(context: Context) {
    var configuracaoDao: ConfiguracaoDAO
    init{
        // Inicia conforme a fonte de dados utilizada
        // configuracaoDao = ConfiguracaoSharedPreferences(context)
        configuracaoDao = ConfiguracaoSqlite(context)
    }

    fun setConfiguracao(configuracao: Configuracao) {
        // Tratar dados aqui
        // Delega ao modelo
        configuracaoDao.createOrUpdateConfiguracao(configuracao)
    }

    fun getConfiguracao(): Configuracao {
        // Tratar dados aqui
        // Delega ao modelo
        return configuracaoDao.readConfiguracao()
    }
}