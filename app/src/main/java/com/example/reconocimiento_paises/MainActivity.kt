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
     val token="ya29.a0Aa4xrXOW38oFRNWavbzUoJUieGwc0bjRTdgUhyxgzjbRA2RMHLNsf-Tl10Q_sn9aGzKbmS66_GeZUuWPRKdxhTJfSZC61xgN0BZdbSO-nbSLG9WLxN-WSmlsu85ua7yf_L9uro9YA8YPNyYX_CUwhGrgcJiDHdjjD-ZNPBARoh-sFKFQae0RxnPYe1uYYT70ZPKRfY7Vwday9eKgXd0YNiyqdk2WHVSVVGrGdyT93Y9XC-WWBe3BQqizN76EnNYNqF0tyQaCgYKATASARMSFQEjDvL9osgfo39vBZMXMg4lJeBrjA0269"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var img1:ImageView=findViewById(R.id.img1)
        img1.setOnClickListener{
            ImageController.seleccionarfoto(this,selecionaract)
        }
         enviarimg()
    }


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


       var str="{\"requests\":[{\"image\":{\"source\":\"$base64Encoded\"},\"features\":[{\"maxResults\":10,\"type\":\"LABEL_DETECTION\"},]}]}"

       var jsonobj= JSONObject(str)
        var item_regions=ArrayList<String>();
        val queue = Volley.newRequestQueue(this)

        var Jsonarr= object :JsonObjectRequest(POST,"https://vision.googleapis.com/v1/images:annotate",jsonobj,
            { response->
                val a= response

            },{ error ->
                val e=error;

            }) {
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