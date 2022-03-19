package com.example.ladm_u2_practica1_blancaramirez

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

class Lienzo(act:MainActivity) : View(act) {

    val act = act

    var intensidad = 1

    val nieves = Array<Nieve>(1000,{Nieve(this)})

    val scope = CoroutineScope(Job()+Dispatchers.Main)
    val corrutina = scope.launch (EmptyCoroutineContext, CoroutineStart.LAZY){
        while (true){
            (0..nieves.size-1).forEach{
                nieves[it].mover()
            }
            intensidad++
            act.runOnUiThread{
                invalidate()
            }
            delay(80L)
        }
    }


    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        var p = Paint();

        c.drawColor(Color.rgb(23,21,21))

        //MONTAÑA IZQUIERDA
        p.color = Color.WHITE
        c.drawOval(-100f,600f,1600f,1300f,p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 4.0f
        p.color = Color.BLACK
        c.drawOval(-100f,600f,1600f,1300f,p)

        //MONTAÑA IZQUIERDA
        p.color = Color.WHITE
        p.style = Paint.Style.FILL
        c.drawOval(900f,500f,2500f,1300f,p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 4.0f
        p.color = Color.BLACK
        c.drawOval(900f,500f,2500f,1300f,p)

        //ARBOL
        p.color = Color.rgb(180,114,20)
        p.style = Paint.Style.FILL
        c.drawRect(500f,700f,550f,800f,p)
        p.color = Color.rgb(10,62,12)
        c.drawOval(450f,470f,600f,565f,p)
        c.drawOval(450f,630f,600f,720f,p)
        c.drawOval(450f,560f,600f,650f,p)

        //CASITA
        p.color = Color.rgb(1,87,12) // color madera
        c.drawRect(1250f, 400f, 1650f, 600f,p)
        p.color = Color.rgb(51, 14,2) //PUERTA
        c.drawRect(1300f,470f,1380f, 600f, p)
        p.color = Color.rgb(3,29,174) // color calido
        c.drawRect(1460f,470f,1560f,540f,p)
        p.color = Color.rgb(166,128,88) // Color rojillo

        var path = Path()
        path.moveTo(1210f, 430f)
        path.lineTo(1680f, 430f)
        path.lineTo(1645f, 330f)
        path.lineTo(1245f, 330f)
        path.lineTo(1210f, 430f)
        c.drawPath(path, p)

        //CHIMENEA
        p.color = Color.rgb(162,61,1)
        c.drawRect(1600f,275f,1520f, 360f, p)

        //NUBES
        p.color = Color.LTGRAY
        c.drawOval(1600f,235f,1550f, 265f, p)
        c.drawOval(1550f,170f,1450f, 230f, p)
        c.drawOval(1290f,150f,1430f, 220f, p)

        //LUNA
        p.color = Color.WHITE
        c.drawCircle(500f,200f,80f,p)
        p.color = Color.rgb(23,21,21)
        c.drawCircle(530f,180f,50f,p)

        corrutina.start()

        nevada(c)

    }

    fun nevada(c:Canvas) {
        (0..30).forEach {
            nieves[it].mover()
            nieves[it].pintar(c)
        }
        if (intensidad > 300 && intensidad < 600) {
            (30..200).forEach {
                nieves[it].mover()
                nieves[it].pintar(c)
            }
        }
        if (intensidad > 600 && intensidad < 1100) {
            (30..nieves.size-1).forEach {
                nieves[it].mover()
                nieves[it].pintar(c)
            }
        }
        if (intensidad == 1100){
            intensidad = 1
        }
    }

}