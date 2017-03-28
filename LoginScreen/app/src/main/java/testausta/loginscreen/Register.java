package testausta.loginscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {
EditText Firstname, Lastname, Username, Email, Password;
    AlertDialog alertDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firstname = (EditText)findViewById(R.id.eFirstname);
        Lastname = (EditText)findViewById(R.id.eSurname);
        Username = (EditText)findViewById(R.id.eUsername);
        Email = (EditText)findViewById(R.id.eMail);
        Password = (EditText)findViewById(R.id.ePassword);
    }


    void Cancel(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    void Register(View v)
    {
        int approved = 4;
        String notice = "";
            String fname = Firstname.getText().toString();
                if(fname.length()<1)
                {
                    notice = "You need to input a name\n";
                    approved--;
                }
            String sname = Lastname.getText().toString();
                if(sname.length()<1)
                {
                notice = "You need to input a Surnamme\n";
                approved--;
                }
            String uname = Username.getText().toString();
                if(uname.length()<1)
                {
                    notice += "You need to input a Username\n";
                    approved--;
                }
            String uemail = Email.getText().toString();
                if(uemail.contains("@") && uemail.contains("."))
                {
                    approved++;
                }
                else
                {
                    notice += "You need to input valid Email\n";
                    approved--;
                }
            String upassword = Password.getText().toString();
                if(upassword.length()<6)
                {
                    notice += "Password must contain at least 6 characters\n";
                    approved--;
                }


                if (approved == 5)
                {
                    String type = "register";
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute(type, fname, sname, uname, uemail, upassword);
                }

                else if (approved <5)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(notice)
                            .create()
                            .show();
                }

    }



}
