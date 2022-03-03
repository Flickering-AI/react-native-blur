package com.cmcewen.blurview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Objects;

import javax.annotation.Nonnull;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;


@SuppressWarnings("unused")
class BlurViewManager extends ViewGroupManager<BlurView> {
    private static final String REACT_CLASS = "BlurView";

    private static final int defaultRadius = 10;
    private static final int defaultSampling = 10;

    @Override
    public @Nonnull String getName() {
        return REACT_CLASS;
    }

    @Override
    public @Nonnull BlurView createViewInstance(@Nonnull ThemedReactContext ctx) {
        BlurView blurView = new BlurView(ctx);
//        View decorView = Objects.requireNonNull(ctx.getCurrentActivity()).getWindow().getDecorView();
//        ViewGroup rootView = decorView.findViewById(android.R.id.content);
//        Drawable windowBackground = decorView.getBackground();
//        blurView.setupWith(rootView)
//            .setFrameClearDrawable(windowBackground)
//            .setBlurAlgorithm(new RenderScriptBlur(ctx))
//            .setBlurRadius(defaultRadius)
//            .setHasFixedTransformationMatrix(false);
        return blurView;
    }

    @ReactProp(name = "blurRadius", defaultInt = defaultRadius)
    public void setRadius(BlurView view, int radius) {
        view.setBlurRadius(radius);
        view.invalidate();
    }

    @ReactProp(name = "overlayColor", customType = "Color")
    public void setColor(BlurView view, int color) {
        view.setOverlayColor(color);
        view.invalidate();
    }

    @ReactProp(name = "downsampleFactor", defaultInt = defaultSampling)
    public void setDownsampleFactor(BlurView view, int factor) {
//        View decorView = Objects.requireNonNull(ctx.getCurrentActivity()).getWindow().getDecorView();
//        ViewGroup rootView = decorView.findViewById(android.R.id.content);
//        Drawable windowBackground = decorView.getBackground();
        ThemedReactContext mReactContext = (ThemedReactContext)view.getContext();
//        UIManagerModule uiManagerModule = ((ThemedReactContext)view.getContext()).getNativeModule(UIManagerModule.class);
//        View rootView = uiManagerModule.resolveView(factor);

        UIManagerModule uiManager = mReactContext.getNativeModule(UIManagerModule.class);

        uiManager.addUIBlock(nativeViewHierarchyManager -> {

            View backgroundView = nativeViewHierarchyManager.resolveView(factor);

            Drawable windowBackground = new ColorDrawable(Color.TRANSPARENT);
            view.setupWith((ViewGroup) backgroundView)
                    .setFrameClearDrawable(windowBackground)
                    .setBlurAlgorithm(new RenderScriptBlur(view.getContext()))
                    .setBlurRadius(defaultRadius)
                    .setHasFixedTransformationMatrix(false);
        });
    }
}
