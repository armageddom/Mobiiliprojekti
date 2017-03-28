package testausta.loginscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Fouler on 21.3.2017.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker(Context ctx) {
        context = ctx;
    }



    @Override
    protected String doInBackground(String... params) {

        String type     = params[0];


        /////Kokeilua
        String parsedString = "";

        //URL laitetaan minne lähetetään
        String login_url = "http://ec2-35-167-155-40.us-west-2.compute.amazonaws.com/users";
        if (type.equals("login")){
            try {
                String user_name = params[1];
                String password = params[2];
                // Tehdään JsonOnjecti
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username",user_name);
                jsonObject.put("password",password);
                String jsonstring = jsonObject.toString();

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
               //  String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"
               //          +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(jsonstring);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Read income responnses

                InputStream is = httpURLConnection.getInputStream();
                parsedString = convertinputStreamToString(is);


              /*  InputStream inputStream = httpURLConnection.getInputStream();
               // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!= null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();*/
                httpURLConnection.disconnect();
                return parsedString;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        else if (type.equals("response"))
        {
            try {

                String user_name = params[1];
                String password = params[2];

                URL url = new URL(login_url);
                URLConnection conn = url.openConnection();

                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                InputStream is = httpConn.getInputStream();
                parsedString = convertinputStreamToString(is);
                httpConn.disconnect();
                return parsedString;
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("register"))
        {

            String fname = params[1];
            String sname = params[2];
            String uname = params[3];
            String uemail = params[4];
            String upassword = params[5];


            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("FirstName", fname);
                jsonObject1.put("Surname", sname);
                jsonObject1.put("username", uname);
                jsonObject1.put("Email", uemail);
                jsonObject1.put("Password", upassword);
                String jsonToString = jsonObject1.toString();
                String kalle = jsonToString;
                Log.d("homokalle",kalle);
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(jsonToString);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream is = httpURLConnection.getInputStream();
                parsedString = convertinputStreamToString(is);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return parsedString;

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login");

    }

    @Override
    protected void onPostExecute(String parsedString) {
        alertDialog.setMessage(parsedString);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    ///// Kokeilua
    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }




}