package br.edu.ifsp.scl.calculadorasdmkt.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        // Fragment
        supportFragmentManager.beginTransaction().replace(R.id.calculadoraFl, CalculadoraBasicaFragment()).commit()
    }

    // Cria o menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    object Constantes{
        // Mesma tela pode chamar varias outras
        val CONFIGURACOES_REQUEST_CODE = 0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno = false
        when (item.itemId) {
            R.id.sairMenuItem -> {
                retorno = true
                finish() // onPause onStop onDestroy
            }
            R.id.configuracoesMenuItem -> {
                retorno = true
                val configuracaoIntent = Intent(this, ConfiguracaoActivity::class.java)

                startActivityForResult(configuracaoIntent, Constantes.CONFIGURACOES_REQUEST_CODE)
            }
        }

        return retorno
    }

    // Quando setResult na ConfiguracaoActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constantes.CONFIGURACOES_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // Pegar configuração retornada
            val configuracao = data?.getParcelableExtra<Configuracao>(ConfiguracaoActivity.Constantes.CONFIGURACAO)

            if (configuracao!!.leiauteAvancado) {
                // Fragment Avançada
                supportFragmentManager.beginTransaction().replace(R.id.calculadoraFl, CalculadoraAvancadaFragment()).commit()
            }
            else {
                // Fragment Básica
                supportFragmentManager.beginTransaction().replace(R.id.calculadoraFl, CalculadoraBasicaFragment()).commit()
            }
        }

    }

}
