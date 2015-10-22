package com.fefu.dns.renju;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RANJU_PORT = 6000;
    ServerSocket serverSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnAsClient).setOnClickListener(this);
        findViewById(R.id.btnAsServer).setOnClickListener(this);
        findViewById(R.id.btnReset).setOnClickListener(this);
    }

    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.btnAsClient:
                workAsClient();
                break;
            case R.id.btnAsServer:
                workAsServer();
                findViewById(R.id.btnAsServer).setEnabled(false);
                break;
            case R.id.btnReset:
                resetGame();
                break;
            default:
                Log.e("Renju", "Not releaseed");
        }
    }

    private void resetGame() {

    }

    private void workAsServer() {
     /*   Log.d("Renju", "Work As Server");
       // Handler h = new Handler(new Handler.Callback());
        Runnable worker = new ServerWorker(hndl);
        Thread t  = new Thread(worker);
        t.start();

*/
    }

    private void workAsClient() {

    }


    private class ServerWorker implements Runnable {
         private Handler hndl;

        private ServerWorker(Handler hndl) {
            this.hndl = hndl;
        }


        @Override
        public void run() {
            try
            {
                serverSocket = new ServerSocket(RANJU_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Socket socket = serverSocket.accept();
                    while (!Thread.currentThread().isInterrupted()){
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                        String line = input.readLine();
                        if (line == null)
                            break;
                        Log.d("ranju client:", "Client says: " + line + "'");
                        String[] parts = line.split(" ");
                        if (parts.length ==2) {
                            int column = Integer.parseInt(parts[0]);
                            int row = Integer.parseInt(parts[1]);
                            if (0 <row && row <15 && 0 < column  && column < 15)
                            {
                                Message m = Message.obtain();
                                m.arg1 = column;
                                m.arg2 = row;

                            }

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}


