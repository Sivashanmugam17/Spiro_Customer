//package com.cabilyhandyforalldinedoo.chd.xmpp;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager;
//
//import org.jivesoftware.smack.ConnectionListener;
//import org.jivesoftware.smack.ReconnectionManager;
//import org.jivesoftware.smack.SmackException;
//import org.jivesoftware.smack.XMPPConnection;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smack.chat2.Chat;
//import org.jivesoftware.smack.chat2.ChatManager;
//import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
//import org.jivesoftware.smack.packet.Message;
//import org.jivesoftware.smack.tcp.XMPPTCPConnection;
//import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
//import org.jivesoftware.smackx.ping.PingManager;
//import org.jxmpp.jid.DomainBareJid;
//import org.jxmpp.jid.EntityBareJid;
//import org.jxmpp.jid.impl.JidCreate;
//import org.jxmpp.stringprep.XmppStringprepException;
//
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//public class RoosterConnection implements ConnectionListener
//{
//    private static final String TAG = "xmppconnection";
//    private  final Context mApplicationContext;
//    String mUsername="";
//    String mPassword="";
//    private XMPPTCPConnection mConnection;
//    SessionManager mSessionManager = null;
//    String hostname = "";
//    public static enum ConnectionState
//    {
//        CONNECTED ,AUTHENTICATED, CONNECTING ,DISCONNECTING ,DISCONNECTED;
//    }
//    public static enum LoggedInState
//    {
//        LOGGED_IN , LOGGED_OUT;
//    }
//    public RoosterConnection( Context context)
//    {
//        mApplicationContext = context.getApplicationContext();
//        mSessionManager = new SessionManager(context);
//        hostname = mSessionManager.getxmpp_host_name();
//        mPassword = md5( mSessionManager.getDriverId());
//        mUsername   = mSessionManager.getDriverId();
//        Log.d("xmppusername",mUsername+"---"+mPassword+"------"+hostname);
//
//
//    }
//    public void connect() throws IOException,XMPPException,SmackException
//    {
//        if(isNetworkAvailable())
//        {
//            DomainBareJid serviceNames = null;
//            try
//            {
//                serviceNames = JidCreate.domainBareFrom(hostname);
//            }
//            catch (XmppStringprepException e)
//            {
//                e.printStackTrace();
//            }
//            XMPPTCPConnectionConfiguration conf = XMPPTCPConnectionConfiguration.builder()
//                    .setXmppDomain(serviceNames)
//                    .setHost(mSessionManager.getxmpp_host_name())
//                    .setResource("XmppConnection")
//                    .setDebuggerEnabled(true)
//                    .setKeystoreType(null) //This line seems to get rid of the problem
//                    //  .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
//                    .setSendPresence(true)
//                    .setConnectTimeout(50000)
//                    .setCompressionEnabled(true).build();
//
//            mConnection = new XMPPTCPConnection(conf);
//
//
//            PingManager pingManager = PingManager.getInstanceFor(mConnection);
//            pingManager.setPingInterval(300);
//            XMPPTCPConnection.setUseStreamManagementDefault(true);
//
//
//
//            mConnection.addConnectionListener(this);
//            try {
//                mConnection.connect();
//                try {
//                    mConnection.login(mUsername,mPassword);
//                } catch (XMPPException e) {
//                    e.printStackTrace();
//                } catch (SmackException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            ChatManager.getInstanceFor(mConnection).addIncomingListener(new IncomingChatMessageListener() {
//                @Override
//                public void newIncomingMessage(EntityBareJid messageFrom, Message message, Chat chat) {
//                    System.out.println("xmpp Message driver end---"+message.toString());
//                    if (message.getType() == Message.Type.chat && message.getBody() != null) {
//                        try
//                        {
//                            String data = message.getBody();
//                            Log.d("dghshdfbhjd",data);
//                            System.out.println("xmpp Message---"+message.toString());
//                            Xmpprequestsplitation xmpp_messagehandler = new Xmpprequestsplitation(mApplicationContext);
//                            xmpp_messagehandler.onHandleChatMessage(message);
//                        }
//                        catch (Exception e)
//                        {
//                        }
//                    }
//                }
//            });
//            ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(mConnection);
//            reconnectionManager.setEnabledPerDefault(true);
//            reconnectionManager.enableAutomaticReconnection();
//        }
//
//    }
//
//
//
//    public void disconnect()
//    {
//        Log.d(TAG,"Xmpp-->Connected disconnect");
//        if (mConnection != null)  mConnection.disconnect();
//        mConnection = null;
//
//    }
//
//
//    public void trackingemiteerusertrack( String body ,String toJid)
//    {
//        if(mConnection != null && mConnection.isAuthenticated())
//        {
//            EntityBareJid jid = null;
//            ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
//            try {
//                jid = JidCreate.entityBareFrom(toJid);
//            } catch (XmppStringprepException e) {
//                e.printStackTrace();
//            }
//            Chat chat = chatManager.chatWith(jid);
//            try {
//                Message message = new Message(jid, Message.Type.chat);
//                message.setBody(body);
//                chat.send(message);
//                Log.d(TAG,"Xmpp-->Location update emitter--->"+body);
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//    public void trackingemiteer( String body ,String toJid)
//    {
//        if(mConnection != null && mConnection.isAuthenticated())
//        {
//            EntityBareJid jid = null;
//            ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
//            try {
//                jid = JidCreate.entityBareFrom(toJid);
//            } catch (XmppStringprepException e) {
//                e.printStackTrace();
//            }
//            Chat chat = chatManager.chatWith(jid);
//            try {
//                Message message = new Message(jid, Message.Type.chat);
//                message.setBody(body);
//                chat.send(message);
//                Log.d(TAG,"Xmpp-->Location update emitter--->"+body);
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//
//    public void tripupdatemitter( String body ,String toJid)
//    {
//        if(mConnection != null && mConnection.isAuthenticated())
//        {
//            EntityBareJid jid = null;
//            ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
//            try {
//                jid = JidCreate.entityBareFrom(toJid);
//            } catch (XmppStringprepException e) {
//                e.printStackTrace();
//            }
//            Chat chat = chatManager.chatWith(jid);
//            try {
//                Message message = new Message(jid, Message.Type.chat);
//                message.setBody(body);
//                chat.send(message);
//                Log.d(TAG,"Xmpp-->trip update emitterdfff--->"+body);
//
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    @Override
//    public void connected(XMPPConnection connection) {
//        Log.d(TAG,"Xmpp-->Connected Successfully");
//    }
//    @Override
//    public void authenticated(XMPPConnection connection, boolean resumed) {
//        Log.d(TAG,"Xmpp-->Authenticated Successfully");
//    }
//    @Override
//    public void connectionClosed() {
//        Log.d(TAG,"Xmpp--->Connectionclosed()");
//
//    }
//    @Override
//    public void connectionClosedOnError(Exception e) {
//        Log.d(TAG,"Xmpp-->ConnectionClosedOnError, error "+ e.toString());
//
//    }
//    @Override
//    public void reconnectingIn(int seconds) {
//        Log.d(TAG,"Xmpp-->ReconnectingIn() ");
//    }
//    @Override
//    public void reconnectionSuccessful() {
//        Log.d(TAG,"Xmpp-->ReconnectionSuccessful()");
//    }
//    @Override
//    public void reconnectionFailed(Exception e) {
//        Log.d(TAG,"Xmpp-->reconnectionFailed()");
//
//
//    }
//    public static final String md5(final String s)
//    {
//        final String MD5 = "MD5";
//        try
//        {
//            MessageDigest digest = MessageDigest
//                    .getInstance(MD5);
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//            StringBuilder hexString = new StringBuilder();
//            for (byte aMessageDigest : messageDigest) {
//                String h = Integer.toHexString(0xFF & aMessageDigest);
//                while (h.length() < 2)
//                    h = "0" + h;
//                hexString.append(h);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//}
