/*
** Copyright (C) 2011 The Liquid Settings Project
**
** Licensed under the Apache License, Version 2.0 (the "License"); 
** you may not use this file except in compliance with the License. 
** You may obtain a copy of the License at 
**
**     http://www.apache.org/licenses/LICENSE-2.0 
**
** Unless required by applicable law or agreed to in writing, software 
** distributed under the License is distributed on an "AS IS" BASIS, 
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
** See the License for the specific language governing permissions and 
** limitations under the License.
*/

package com.liquid.settings.activities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Config;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

import com.liquid.settings.R;
import com.liquid.settings.utilities.RootHelper;

public class PropViewerActivity extends AlertActivity {

    private static final String TAG = "LGB*DEBUG*";
    private static final String SHOWBUILD_PATH = "/system/tmp/showbuild";

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Reader reader = null;
        StringBuilder data = null;
        RootHelper.remountRW();
        RootHelper.updateShowBuild();

        try {
            data = new StringBuilder(2048);
            char tmp[] = new char[2048];
            int numRead;
            reader = new BufferedReader(new FileReader(SHOWBUILD_PATH));
            while ((numRead = reader.read(tmp)) >= 0) {
                data.append(tmp, 0, numRead);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException while reading file:", e);
            showErrorAndFinish();
            return;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException while closing reader:", e);
            }
        }

        if (TextUtils.isEmpty(data)) {
            showErrorAndFinish();
            return;
        }

        WebView webView = new WebView(this);
        webView.loadDataWithBaseURL(null, data.toString(), "text/plain", "utf-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mAlert.setTitle(getString(R.string.propviewer_dialog));
            }
        });

        final AlertController.AlertParams p = mAlertParams;
        p.mTitle = getString(R.string.propviewer_loading);
        p.mView = webView;
        p.mForceInverseBackground = true;
        setupAlert();
    }

    private void showErrorAndFinish() {
        Toast.makeText(this, R.string.propviewer_error, Toast.LENGTH_LONG).show();
        finish();
    }
}
