package com.example.fitnesswatchapp.ui.tasksList

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesswatchapp.R
import com.example.fitnesswatchapp.databinding.ScheduleSettingFragmentBinding
import com.example.fitnesswatchapp.databinding.TasksListFragmentBinding
import com.example.fitnesswatchapp.domain.model.Rutina
import com.example.fitnesswatchapp.ui.adapter.RutinaAdapter
import com.example.fitnesswatchapp.ui.clock.ClockActivity
import com.example.fitnesswatchapp.ui.scheduleSetting.ScheduleSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksListFragment : Fragment() {

    private var _binding: TasksListFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TasksListFragment()
    }

    private lateinit var viewModel: TasksListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //conectamos el bindign
        _binding = TasksListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View Model
        viewModel = ViewModelProvider(this).get(TasksListViewModel::class.java)

        initObserver()

        // tomamos de la BD a todas las rutinas
        viewModel.getRutinas()
    }

    private fun initObserver() {
        viewModel.rutinas.observe(this) {
            initRecyclerView(it)
        }
    }

    private fun initRecyclerView(lista: List<Rutina>) {
        //Log.i("prueba", lista.toString())
        binding.recyclerRutinas.layoutManager = LinearLayoutManager(context)
        binding.recyclerRutinas.adapter =
            RutinaAdapter(lista) { rutina: Rutina, eliminar :Boolean ->
                onItemSelected(rutina, eliminar)
            }
    }

    /**
     * en esta función estamos recuperando el item que selecciono el usuario
     */
    private fun onItemSelected(rutina: Rutina, eliminar: Boolean) {
        if (eliminar) {
            showAlertDialog(rutina)
        } else {
            val intent = Intent(activity, ClockActivity::class.java)
            intent.putExtra("sesiones", rutina.sesiones)
            intent.putExtra("m_trabajo", rutina.minutos_trabajo)
            intent.putExtra("s_trabajo", rutina.segundos_trabajo)
            intent.putExtra("m_descanso", rutina.minutos_descanso)
            intent.putExtra("s_descanso", rutina.segundos_descanso)
            startActivity(intent)
        }
    }

    private fun showAlertDialog(rutina: Rutina) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Eliminar",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.deleteRutina(rutina)
                        Toast.makeText(context, "rutina ${rutina.nombre} eliminada", Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties
           builder.setMessage("¿Está seguro que desea eliminar la rutina " + rutina.nombre + "?")
               .setTitle("Advertencia")
               .setIcon(R.drawable.ic_warning)

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }
}