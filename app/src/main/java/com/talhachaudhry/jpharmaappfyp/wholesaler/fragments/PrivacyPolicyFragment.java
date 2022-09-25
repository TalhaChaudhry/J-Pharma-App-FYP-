package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.talhachaudhry.jpharmaappfyp.databinding.FragmentPrivacyPolicyBinding;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrivacyPolicyFragment extends Fragment {

    FragmentPrivacyPolicyBinding binding;

    public static PrivacyPolicyFragment newInstance() {
        return new PrivacyPolicyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false);
        binding.webView.loadUrl("https://www.pentabitapps.com/privacy-policy");
        binding.webView.setWebViewClient(new WebClient());
        new BackPressHandler(binding.webView).backPressing(requireActivity());
        return binding.getRoot();
    }

    private static class WebClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private static class BackPressHandler {
        WebView webView;

        public BackPressHandler(WebView webView) {
            this.webView = webView;
        }

        public void backPressing(FragmentActivity activity) {
            webView.setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && webView != null) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        activity.onBackPressed();
                    }
                }
                return true;
            });
        }
    }

}