package br.edu.ifsp.scl.calculadorasdmkt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.utils.Calculadora
import br.edu.ifsp.scl.calculadorasdmkt.utils.Operador
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.*
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.adicaoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.cincoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.divisaoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.doisBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.lcdTv
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.multiplicacaoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.noveBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.oitoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.pontoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.quatroBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.resultadoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.seisBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.seteBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.subtracaoBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.tresBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.umBt
import kotlinx.android.synthetic.main.fragment_calculadora_avancada.zeroBt

class CalculadoraAvancadaFragment: Fragment(), View.OnClickListener {
    var concatenaLcd: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculadora_avancada, container, false)
    }

    override fun onClick(p: View?) {
        when (p) {
            // Números
            umBt, doisBt, tresBt, quatroBt, cincoBt,
            seisBt, seteBt, oitoBt, noveBt, zeroBt -> {
                // Limpa LCD se último clicado foi um operador
                if (!concatenaLcd) {
                    lcdTv.text = ""
                }
                lcdTv.append((p as Button).text.toString())
                concatenaLcd = true
            }
            // Ponto
            pontoBt -> {
                if (!lcdTv.text.toString().contains(".")){
                    if (!concatenaLcd) {
                        lcdTv.text = "0"
                    }
                    lcdTv.append(".")
                    concatenaLcd = true
                }
            }
            // Operadores
            adicaoBt -> cliqueOperador(Operador.ADICAO)
            subtracaoBt -> cliqueOperador(Operador.SUBTRACAO)
            multiplicacaoBt -> cliqueOperador(Operador.MULTIPLICACAO)
            divisaoBt -> cliqueOperador(Operador.DIVISAO)
            porcentageBt -> cliqueOperador(Operador.PORCENTAGEM)
            raizBt -> cliqueOperador(Operador.RAIZ)
            resultadoBt -> cliqueOperador(Operador.RESULTADO)
            // Clear
            cBt -> {
                lcdTv.text = ""
                concatenaLcd = true
            }
        }
    }

    fun cliqueOperador(operador: Operador) {
        var value = if (lcdTv.text.toString().isEmpty()) 0f else lcdTv.text.toString().toFloat()
        lcdTv.text = Calculadora.calcula(value, operador).toString()
        concatenaLcd = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..(view as ViewGroup).childCount - 1) {
            val v = view.get(i)
            if (v is Button) {
                v.setOnClickListener(::onClick)
            }
        }
    }
}