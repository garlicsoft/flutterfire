// Copyright 2021 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.firebase.dynamiclinks;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import android.os.Bundle;

public class Utils {
  static Map<String, Object> getExceptionDetails(@Nullable Exception exception) {
    Map<String, Object> details = new HashMap<>();
    // There aren't any Dynamic Link Exceptions in the reference:
    // https://firebase.google.com/docs/reference/android/com/google/firebase/dynamiclinks/package-summary
    details.put("code", "unknown");
    if (exception != null) {
      details.put("message", exception.getMessage());
    } else {
      details.put("message", "An unknown error has occurred.");
    }
    return details;
  }

  static Map<String, Object> getMapFromPendingDynamicLinkData(
      PendingDynamicLinkData pendingDynamicLinkData) {
    if (pendingDynamicLinkData == null) {
      return null;
    }

    Map<String, Object> dynamicLink = new HashMap<>();

    Uri link = pendingDynamicLinkData.getLink();
    dynamicLink.put("link", link != null ? link.toString() : null);

    Map<String, String> utmParameters = new HashMap<>();
    Set<String> keys = pendingDynamicLinkData.getUtmParameters().keySet();
    Iterator<String> iterator = keys.iterator();
    while (iterator.hasNext()) {
        String key = iterator.next();
        utmParameters.put(key, pendingDynamicLinkData.getUtmParameters().getString(key));
    }
    dynamicLink.put("utmParameters", utmParameters);

    Map<String, Object> androidData = new HashMap<>();
    androidData.put("clickTimestamp", pendingDynamicLinkData.getClickTimestamp());
    androidData.put("minimumVersion", pendingDynamicLinkData.getMinimumAppVersion());

    dynamicLink.put("android", androidData);
    return dynamicLink;
  }
}
