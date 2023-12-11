package com.example.sportsworlddemo2.homepage;

import android.animation.ValueAnimator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;


public class MarkerAnimation {

    private void animateMarkerAlongRoute(final Marker marker, final List<LatLng> points) {
        final long duration = 5000; // 动画持续时间，单// 位为毫秒
        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                int index = (int) (fraction * (points.size() - 1));
                LatLng newPosition = latLngInterpolator.interpolate(fraction, points.get(index), points.get(index + 1));
                marker.setPosition(newPosition);
            }
        });

        valueAnimator.start();
    }

    public interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }
}


