package com.example.hlf;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

public class TouchListener implements View.OnTouchListener {

    public TouchListener(Activity CampoActivity){

        final float xDelta = 0f;
        final float yDelta = 0f;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //Obtenemos coordenadas x e y
        float x = event.getRawX();
        float y = event.getRawY();
        //raiz cuadrada,potencia
        //double aceptacion = Math.sqrt(Math.pow(v.getHeight(), 2.0) +
        //                    Math.pow(v.getHeight(), 2.0)) / 10;


        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(x, y);

            case MotionEvent.ACTION_UP:
                return onReleased(view, relX, relY);

            case MotionEvent.ACTION_CANCEL:
                if(dragging != null) {
                    dragging = null;
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean onTouched(float x, float y) {
    }
}
