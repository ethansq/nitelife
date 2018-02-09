package me.ethansq.nitelife.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

public class Utils {
    private static final String TAG = "Utils";

    public static void crossfade(final View in, final View out) {
        if (in != null) {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            in.setAlpha(0f);
            in.setVisibility(View.VISIBLE);

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            in.animate()
                    .alpha(1f)
                    .setDuration(Constants.CROSSFADE_DURATION)
                    .setListener(null);
        }

        if (out != null) {
            // Animate the loading view to 0% opacity. After the animation ends,
            // set its visibility to GONE as an optimization step (it won't
            // participate in layout passes, etc.)
            out.animate()
                    .alpha(0f)
                    .setDuration(Constants.CROSSFADE_DURATION)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            out.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
