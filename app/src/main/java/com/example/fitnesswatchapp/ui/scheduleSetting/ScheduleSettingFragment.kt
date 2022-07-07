package com.example.fitnesswatchapp.ui.scheduleSetting

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitnesswatchapp.databinding.ScheduleSettingFragmentBinding
import com.example.fitnesswatchapp.ui.clock.ClockActivity

class ScheduleSettingFragment : Fragment() {

    // Properties

    var sesiones = 1
    var m_trabajo = 0
    var s_trabajo = 0
    var m_descanso = 0
    var s_descanso = 0

    private var _binding: ScheduleSettingFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ScheduleSettingViewModel

    companion object {
        //fun newInstance() = ScheduleSettingFragment()
    }

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //conectamos el bindign
        _binding = ScheduleSettingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //usamos este porque onActivityCreate ya está depresiado
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View Model
        viewModel = ViewModelProvider(this).get(ScheduleSettingViewModel::class.java)

        initListener()
        initObserver()
    }

    private fun initObserver() {
        viewModel.rutinaModel.observe(this) { rutina ->
            binding.etSesiones.text = rutina.sesiones.toString()
            sesiones = rutina.sesiones.toString().toInt()
            binding.etMinTrabajo.text = rutina.minutos_trabajo.toString()
            m_trabajo = rutina.minutos_trabajo.toString().toInt()
            binding.etSegTrabajo.text = rutina.segundos_trabajo.toString()
            s_trabajo = rutina.segundos_trabajo.toString().toInt()
            binding.etMinDescanso.text = rutina.minutos_descanso.toString()
            m_descanso = rutina.minutos_descanso.toString().toInt()
            binding.etSegDescanso.text = rutina.segundos_descanso.toString()
            s_descanso = rutina.segundos_descanso.toString().toInt()
        }
    }

    private fun initListener() {
        //click listener for our buttons
        with(binding) {
            btnIniciar.setOnClickListener { onIniciar() }
            btnMenosSesiones.setOnClickListener { viewModel.onMenosSesiones() }
            btnMasSesiones.setOnClickListener { viewModel.onMasSesiones() }
            btnMenosTrabajoMinutos.setOnClickListener { viewModel.onMenosTrabajoMinutos() }
            btnMasTrabajoMinutos.setOnClickListener { viewModel.onMasTrabajoMinutos() }
            btnMenosTrabajoSegundos.setOnClickListener { viewModel.onMenosTrabajoSegundos() }
            btnMasTrabajoSegundos.setOnClickListener { viewModel.onMasTrabajoSegundos() }
            btnMenosDescansoMinutos.setOnClickListener { viewModel.onMenosDescansoMinutos() }
            btnMasDescansoMinutos.setOnClickListener { viewModel.onMasDescansoMinutos() }
            btnMenosDescansoSegundos.setOnClickListener { viewModel.onMenosDescansoSegundos() }
            btnMasDescansoSegundos.setOnClickListener { viewModel.onMasDescansoSegundos() }
        }

    }

    /** Methods for buttons presses **/

    private fun onIniciar() {
        if (!viewModel.validar()) {
            val intent = Intent(activity, ClockActivity::class.java)
            intent.putExtra("sesiones", sesiones)
            intent.putExtra("m_trabajo", m_trabajo)
            intent.putExtra("s_trabajo", s_trabajo)
            intent.putExtra("m_descanso", m_descanso)
            intent.putExtra("s_descanso", s_descanso)
            startActivity(intent)
        } else {
            Toast.makeText(context, "Asegurese de establecer tiempo para el trabajo y descanso", Toast.LENGTH_SHORT).show()
        }
    }
}