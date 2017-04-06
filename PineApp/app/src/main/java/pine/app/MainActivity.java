package pine.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity  extends AppCompatActivity implements AsyncResponse {
EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.eUsername);
        password = (EditText)findViewById(R.id.ePassword);




    }

    public void Login(View v)
    {

        String uName, uPassword;
        uName = username.getText().toString();
        uPassword = password.getText().toString();
        String type = "login";
        username.setText("");
        password.setText("");
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.delegate = this;
        backgroundWorker.execute(type, uName, uPassword);

    }

    void Register(View v)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    void Testaus(View v)
    {
        Intent intent = new Intent(this,Newpost.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(String output){
    Log.d("ProcessFinished",output);

        if(output.contains("ok"))
        {
            Toast.makeText(getBaseContext(),"Kirjautuminen onnistui",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ScrollingActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getBaseContext(),"Login epäonnistui!",Toast.LENGTH_SHORT).show();
        }
    }


}
