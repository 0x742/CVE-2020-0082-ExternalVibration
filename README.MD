# CVE-2020-0082-PoC
This repo contains a proof-of-concept for 📱🚀👑⚡, a deserialization vuln for local escalation of privilege to system_server in Android 10. This proof-of-concept only activates a privileged intent.

```
In ExternalVibration of ExternalVibration.java, there is a possible activation of an arbitrary intent due to unsafe deserialization. This could lead to local escalation of privilege to system_server with no additional execution privileges needed. User interaction is not needed for exploitation.
Product: Android
Versions: Android-10
Android ID: A-140417434

diff --git a/core/java/android/os/ExternalVibration.java b/core/java/android/os/ExternalVibration.java
index 37ca868..041d21f 100644
--- a/core/java/android/os/ExternalVibration.java
+++ b/core/java/android/os/ExternalVibration.java
@@ -157,7 +157,6 @@
         out.writeInt(mUid);
         out.writeString(mPkg);
         writeAudioAttributes(mAttrs, out, flags);
-        out.writeParcelable(mAttrs, flags);
         out.writeStrongBinder(mController.asBinder());
         out.writeStrongBinder(mToken);
     }
```

This project is made for educational and ethical testing purposes only. Exploiting targets without prior mutual consent is illegal. It is the end user's responsibility to obey all applicable local, state and federal laws. Developers assume no liability and are not responsible for any misuse or damage caused by this program.

## References

[EvilParcel Exploitation](https://www.youtube.com/watch?v=Y09LodhRcJ4)  
[A-140417434](https://android.googlesource.com/platform/frameworks/base/+/3484a2dd5c380c9ef7a2a6e6113a1ef01f2f7aed)  
[CVE-2020-0082](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2020-0082)  
