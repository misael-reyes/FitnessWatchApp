package com.example.fitnesswatchapp.ui.clock

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesswatchapp.MainActivity
import com.example.fitnesswatchapp.R
import com.example.fitnesswatchapp.databinding.ActivityClockBinding
import com.example.fitnesswatchapp.models.RutinaModel

@Suppress("DEPRECATION")
class ClockActivity : AppCompatActivity() {

    //properties

    private lateinit var soundPool: SoundPool
    private var sound_play = 0

    private var vibration: Vibrator? = null


    private lateinit var rutina: RutinaModel

    private lateinit var viewModel: ClockViewModel
    private lateinit var viewModelFactory: ClockViewModelFactory

    //preparamos el view binding
    private lateinit var binding: ActivityClockBinding

    private var pausado = false

    // properties for our notificaton channel

    private val channelId = "channelId"
    private val channelName = "channelName"
    private val notificationId  = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // matenemos activa la pantalla
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val bundle = intent.extras
        if (bundle != null)
            recuperarDatos(bundle)

        // esta es solo una forma de inicializar el viewModel cuando tenemos factory

        viewModelFactory = ClockViewModelFactory(rutina)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ClockViewModel::class.java)

        createNotificationChannel()
        createSound()
        createVibrator()
        initView()
        initObserver()
        initListener()
    }

    /**
     * función para crear el sonido de la alarma
     */
    private fun createSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_ALARM, 1)
        }
        sound_play = soundPool.load(this, R.raw.alarma, 1)
    }

    /**
     * función para crear nuestro canal de notificación
     */
    private fun createNotificationChannel() {
        /**
         * a partir de android o (8) se implementaron los canales para las
         * notificaciones
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // importancia de la notificación
            val importance: Int = NotificationManager.IMPORTANCE_HIGH

            // creamos el canal
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                lightColor = Color.RED
                enableLights(true)
                enableVibration(true)
            }

            // necesitamos un notification manager para construir el canal
            // getSystemService regresa un any, por eso tenemos que haer el casteo a notification manager
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    /**
     * función para configruar el vibrador del telefono
     */
    private fun createVibrator() {
        vibration = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibration!!.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else
            vibration!!.vibrate(1000)
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
                    viewContainer.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
            } else {
                with(binding) {
                    estadoSesion.text = "Descansando"
                    viewContainer.setBackgroundColor(Color.parseColor("#E1DF2C"))
                }
                alarma()
                vibrate()
            }
        }

        viewModel.rutinaTerminada.observe(this) { rutinaTerminada ->
            if (rutinaTerminada) {
                notificar("haz terminado tu rutina :)")
                startActivity(Intent(this, MainActivity::class.java))
            }
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

    private fun alarma() {
        // sonido de alarma
        soundPool.play(sound_play, 1F,1F,1,0, 1F)
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

    private fun notificar(texto: String) {
        // configuración de la notificación
        var notification = NotificationCompat.Builder(this, channelId).also {
            it.setContentTitle("FitnessWatchApp")
            it.setContentText(texto)
            it.setSmallIcon(R.drawable.alarma)
            it.setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()

        //
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, notification)
    }

    /**
     * función para finalizar la actividad
     */
    private fun closeActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}

