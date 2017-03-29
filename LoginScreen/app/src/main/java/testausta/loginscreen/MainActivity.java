package testausta.loginscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.eUsername);
        password = (EditText)findViewById(R.id.ePassword);

    }


/*    BackgroundWorker asyncTask = new BackgroundWorker(new AsyncResponse(){

        @Override
        void processFinish(String output){
            String name = output;
            //Here you will receive the result fired from async class
            //of onPostExecute(result) method.
        }
    });*/

    void Login(View v)
    {
        String uName, uPassword;
        uName = username.getText().toString();
        uPassword = password.getText().toString();
        String type = "login";
        username.setText("");
        password.setText("");
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, uName, uPassword);



    }

    void Register(View v)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }



}
