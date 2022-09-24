package com.example.reconocimiento_paises

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.DateFormat.DEFAULT
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.annotation.RequiresApi
//import org.apache.commons.codec.binary.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineStart
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.text.DateFormat.DEFAULT
import java.util.HashMap

class MainActivity : AppCompatActivity() {
     private val selecionaract= 50
     val token="AIzaSyB5MkIB5lNnQH1kC1tZ3ATeEsv7z66moKs"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*    Glide
            .with(this@MainActivity)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSkWLDWUQi6ZaGwyrltBJw4t2EWjxs-Z-cPQ&usqp=CAU")
            .centerCrop()
            .placeholder(R.drawable.subir)
            .into(img1);
    }*/
        var img1:ImageView=findViewById(R.id.img1)
        img1.setOnClickListener{
            ImageController.seleccionarfoto(this,selecionaract)
        }

      /*  var btnsave:Button=findViewById(R.id.btnsave)
        btnsave.setOnClickListener{
            imageUri?.let
            ImageController.guardarimg(this@MainActivity,10,it)
        }*/
         enviarimg()
    }
   /* fun encode64(image:Bitmap): String?
    {
        val baos=ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG)
        val b= baos.toByteArray()
        return  Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun decode64(): Bitmap?
    {
        val decotebyte:ByteArray= Base64.decode("aa",0)
        return  BitmapFactory.decodeByteArray(decotebyte,0,decotebyte.size)
    }

   @RequiresApi(Build.VERSION_CODES.P)*/


   @RequiresApi(Build.VERSION_CODES.O)
   fun enviarimg() {

       val bitmap:Bitmap = BitmapFactory.decodeResource(resources, R.drawable.subir)
       val bos:ByteArrayOutputStream = ByteArrayOutputStream()
       bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
       val image:ByteArray = bos.toByteArray()
       val base64Encoded = java.util.Base64.getEncoder().encodeToString(image)


       val rootObject = JSONObject()
       rootObject.put("file", base64Encoded)

      // var str="{\"requests\":[{\"image\":{\"content\":\"imagen\"},\"features\":[{\"maxResults\":10,\"type\":\"WEB_DETECTION\"},]}]}"


       var str="{\"requests\":[{\"image\":{\"content\":"+base64Encoded+"\"},\"features\":[{\"maxResults\":10,\"type\":\"WEB_DETECTION\"},]}]}"

       var jsonobj= JSONObject(str)
        var item_regions=ArrayList<String>();
        val queue = Volley.newRequestQueue(this)
      /*  var JsonArrayRq:()-> JsonObjectRequest =
            {   var json = object: JsonObjectRequest(
                POST,"https://cloud.google.com/vision/docs/detecting-web",jsonobj,
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Content-Type", "application/json")
                    headers.put("Authorization", "Bearer "+token)
                    return headers
                }
            }
                json
            }*/
        var Jsonarr= object :JsonObjectRequest(POST,"https://cloud.google.com/vision/docs/detecting-web",jsonobj,
            { response->
                val a= response

            },{}) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "Bearer "+token)
                return headers
            }
        }
       // var json=JsonArrayRq()
        queue.add(Jsonarr)
    }
}