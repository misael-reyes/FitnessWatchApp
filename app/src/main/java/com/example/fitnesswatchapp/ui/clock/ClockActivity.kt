package com.example.fitnesswatchapp.ui.clock

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesswatchapp.MainActivity
import com.example.fitnesswatchapp.R
import com.example.fitnesswatchapp.databinding.ActivityClockBinding
import com.example.fitnesswatchapp.models.RutinaModel

class ClockActivity : AppCompatActivity() {

    //properties

    private lateinit var rutina: RutinaModel

    private lateinit var viewModel: ClockViewModel
    private lateinit var viewModelFactory: ClockViewModelFactory

    //preparamos el view binding
    private lateinit var binding: ActivityClockBinding

    private var pausado = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null)
            recuperarDatos(bundle)

        // esta es solo una forma de inicializar el viewModel cuando tenemos factory

        viewModelFactory = ClockViewModelFactory(rutina)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ClockViewModel::class.java)

        initView()
        initObserver()
        initListener()
    }

    /**
     * recupereamos los datos que nos pasaron del activity anterior y la convertimos
     * en un objeto rutina
     */
    private fun recuperarDatos(bundle: Bundle) {
        rutina = RutinaModel("",
            bundle.getInt("sesiones"),
            bundle.getInt("m_trabajo"),
            bundle.getInt("s_trabajo"),
            bundle.getInt("m_descanso"),
            bundle.getInt("s_descanso"))
    }

    /** iniciar los componentes **/

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.titleSets.text = "1"+"/"+rutina.sesiones
        binding.tvMinutos.text = rutina.minutos_trabajo.toString()
        binding.tvSegundos.text = rutina.segundos_trabajo.toString()
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun initObserver() {

        viewModel.minutos.observe(this) {
            if (it.toString().length == 1)
                binding.tvMinutos.text = "0$it"
            else
                binding.tvMinutos.text = it.toString()
        }

        viewModel.segundos.observe(this) {
            if (it.toString().length == 1)
                binding.tvSegundos.text = "0$it"
            else
                binding.tvSegundos.text = it.toString()
        }

        viewModel.sesionActual.observe(this) {
            binding.titleSets.text = "$it/${rutina.sesiones}"
        }

        viewModel.trabajando.observe(this) { trabajando ->
            if (trabajando) {
                with(binding) {
                    estadoSesion.text = "Trabajando"
                    tvMinutos.setTextColor(Color.parseColor("#FF000000"))
                    tvSegundos.setTextColor(Color.parseColor("#FF000000"))
                }
            } else {
                binding.estadoSesion.text = "Descansando"
                with(binding) {
                    tvMinutos.setTextColor(Color.parseColor("#E90000"))
                    tvSegundos.setTextColor(Color.parseColor("#E90000"))
                }
            }
        }

        viewModel.rutinaTerminada.observe(this) { rutinaTerminada ->
            if (rutinaTerminada)
                startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.cronometroPausado.observe(this) {
            if (it) {
                binding.btnStart.setImageResource(R.drawable.icono_play)
                pausado = true
            } else {
                binding.btnStart.setImageResource(R.drawable.icono_pausa)
                pausado = false
            }
        }
    }

    private fun initListener() {
        binding.btnClose.setOnClickListener { closeActivity() }
        binding.btnStart.setOnClickListener {
            if (!pausado)
                viewModel.pauseTimer()
            else
                viewModel.resumeTimer()
        }
    }

    /**
     * función para finalizar la actividad
     */
    private fun closeActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}

