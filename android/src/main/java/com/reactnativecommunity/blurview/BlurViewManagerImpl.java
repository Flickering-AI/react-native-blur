package com.reactnativecommunity.blurview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;

import java.util.Objects;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
class BlurViewManagerImpl {

  public static final String REACT_CLASS = "AndroidBlurView";

  public static final int defaultRadius = 10;
  public static final int defaultSampling = 10;

  public static @Nonnull
  BlurView createViewInstance(@Nonnull ThemedReactContext ctx) {
    BlurView blurView = new BlurView(ctx);
//    View decorView = Objects
//      .requireNonNull(ctx.getCurrentActivity())
//      .getWindow()
//      .getDecorView();
//    ViewGroup rootView = decorView.findViewById(android.R.id.content);
//    Drawable windowBackground = decorView.getBackground();
//    if (Build.VERSION.SDK_INT >= 31) {
//      blurView
//        .setupWith(rootView, new RenderEffectBlur())
//        .setFrameClearDrawable(windowBackground)
//        .setBlurRadius(defaultRadius);
//    } else {
//      blurView
//        .setupWith(rootView, new RenderScriptBlur(ctx))
//        .setFrameClearDrawable(windowBackground)
//        .setBlurRadius(defaultRadius);
//    }
    return blurView;
  }

  public static void setRadius(BlurView view, int radius) {
    view.setBlurRadius(radius);
    view.invalidate();
  }

  public static void setColor(BlurView view, int color) {
    view.setOverlayColor(color);
    view.invalidate();
  }

  public static void setDownsampleFactor(BlurView view, int factor) {
    ThemedReactContext mReactContext = (ThemedReactContext) view.getContext();
//        UIManagerModule uiManagerModule = ((ThemedReactContext)view.getContext()).getNativeModule(UIManagerModule.class);
//        View rootView = uiManagerModule.resolveView(factor);

    UIManagerModule uiManager = mReactContext.getNativeModule(UIManagerModule.class);

    uiManager.addUIBlock(nativeViewHierarchyManager -> {

      try {
        View backgroundView = nativeViewHierarchyManager.resolveView(factor);

        Drawable windowBackground = new ColorDrawable(Color.TRANSPARENT);
        view.setupWith((ViewGroup) backgroundView)
          .setFrameClearDrawable(windowBackground)
          .setBlurAlgorithm(new RenderScriptBlur(view.getContext()))
          .setBlurRadius(defaultRadius)
          .setHasFixedTransformationMatrix(false);
      } catch (Exception error) {
        error.printStackTrace();
      }
    });
  }

  public static void setAutoUpdate(BlurView view, boolean autoUpdate) {
    view.setBlurAutoUpdate(autoUpdate);
    view.invalidate();
  }

  public static void setBlurEnabled(BlurView view, boolean enabled) {
    view.setBlurEnabled(enabled);
  }
}
