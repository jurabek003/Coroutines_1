package uz.turgunboyevjurabek.coroutines1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.coroutines1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnGo.setOnClickListener {
            binding.btnGo.isEnabled=false
            GlobalScope.launch(Dispatchers.Main) {
                val result = natija(Hissobla(binding.edtBoy.text.toString().toInt(),
                    binding.edtVazn.text.toString().toInt()))
                binding.tvHulosa.text=result
                binding.btnGo.isEnabled=true
            }
        }
    }

    suspend fun Hissobla(boy :Int, massa:Int):Double{
        return GlobalScope.async(Dispatchers.Default) {
            var bmi = (massa.toDouble() * 10000 / (boy*boy.toDouble()))
            bmi
        }.await()
    }
    suspend fun natija(bmi:Double):String{
        return GlobalScope.async(Dispatchers.IO) {
            Thread.sleep(500)
            when(bmi){

               in 0.0 .. 18.5->{
                    "past vazn"
                }
                in 18.6 .. 24.9->{
                    "normal"
                }
                in 25.0 .. 29.0->{
                    "semiz"
                }else->{
                    "no sog'lom"
                }
            }
        }.await()
    }
}