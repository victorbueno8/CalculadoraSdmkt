package br.edu.ifsp.scl.calculadorasdmkt.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class ConfiguracaoSqlite(context: Context): ConfiguracaoDAO {
    // Constants
    companion object Constantes {
        val NOME_BD = "configuracoes"
        val MODO_BD = Context.MODE_PRIVATE
        val NOME_TABELA = "configuracao"
        val ATRIBUTO_ID = "id"
        val ATRIBUTO_LEIAUTE_AVANCADO = "leiauteAvancado"
        val ATRIBUTO_SEPARADOR = "separador"
        val ID_PADRAO = 0
        // Bool <-> Int Sqlite não tem Booleano
        val TRUE = 1
        val FALSE = 0

        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ${NOME_TABELA} (" +
                "${ATRIBUTO_ID} INTEGER, " +
                "${ATRIBUTO_LEIAUTE_AVANCADO} INTEGER, " +
                "${ATRIBUTO_SEPARADOR} TEXT);"

        val QUERY_TUPLE = "SELECT * FROM ${NOME_TABELA} WHERE id = ${ID_PADRAO}"
    }

    // Referencia para o Sqlite BD
    val sqliteBd: SQLiteDatabase
    init{
        // Cria ou abre conexao com BD
        sqliteBd = context.openOrCreateDatabase(NOME_BD, MODO_BD, null)

        // Cria tabela se a mesma nao existe
        try {
            sqliteBd.execSQL(CREATE_TABLE)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun createOrUpdateConfiguracao(configuracao: Configuracao) {
        val leiauteAvancadoInt = if (configuracao.leiauteAvancado) TRUE else FALSE
        val resultadoCursor = sqliteBd.rawQuery(QUERY_TUPLE, arrayOf())

        // String guarda query de update ou insert
        var query: String

        if ( resultadoCursor.moveToFirst() ) {
            // Update!
            query =  "UPDATE ${NOME_TABELA} SET " +
                    "${ATRIBUTO_LEIAUTE_AVANCADO} = ${leiauteAvancadoInt}, " +
                    "${ATRIBUTO_SEPARADOR} = '${configuracao.separador}' " +
                    "WHERE ${ATRIBUTO_ID} = ${ID_PADRAO};"
        } else {
            // Insert!
            query = "INSERT INTO ${NOME_TABELA} VALUES (" +
                    "${ID_PADRAO}, ${leiauteAvancadoInt}, " +
                    "'${configuracao.separador}');"
        }

        // Executa query
        try {
            sqliteBd.execSQL(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun readConfiguracao(): Configuracao {
        val resultadoCursor = sqliteBd.rawQuery(QUERY_TUPLE, arrayOf())

        return if ( resultadoCursor.moveToFirst() ) {
            val indiceLeiauteAvancado = resultadoCursor.getColumnIndex(ATRIBUTO_LEIAUTE_AVANCADO)
            val indiceSeparador = resultadoCursor.getColumnIndex(ATRIBUTO_SEPARADOR)

            val leiauteAvancadoCursor = resultadoCursor.getInt(indiceLeiauteAvancado)
            val separadorCursor = resultadoCursor.getString(indiceSeparador)

            val leiauteAvancado = leiauteAvancadoCursor == TRUE
            val separador = if (separadorCursor.equals(Separador.PONTO.name))
                                Separador.PONTO
                            else
                                Separador.VIRGULA

            // Retorna um objeto Configuracao do Banco de Dados
            Configuracao(leiauteAvancado, separador)
        } else {
            // Retorna um objeto de Configuracao com valores padroes
            Configuracao()
        }
    }

}