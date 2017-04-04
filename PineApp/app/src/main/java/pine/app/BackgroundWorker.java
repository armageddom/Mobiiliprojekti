package pine.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
    public AsyncResponse delegate = null;



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
        String login_url = "http://ec2-35-167-155-40.us-west-2.compute.amazonaws.com/login";
        String register_url = "http://ec2-35-167-155-40.us-west-2.compute.amazonaws.com/users";


        if (type.equals("login")){
            try {
                String user_name = params[1];
                String password = params[2];
                // Tehdään JsonOnjecti
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username",user_name);
                jsonObject.put("Password",password);
                String jsonstring = jsonObject.toString();
                Log.d("Login", jsonstring);

                // Otetaan osoite minne halutaan yhdistää ja annetaan RequestPropertyt mitä ollaan lähettämässä.
                // Sen jälkeen lähetetään Jsonstringi eteenpäin.
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(jsonstring);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Otetaan tuleva data talteen

                InputStream is = httpURLConnection.getInputStream();
                parsedString = convertinputStreamToString(is);
                httpURLConnection.disconnect();




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return parsedString;

        }

        else if (type.equals("response"))
        {
            try {

                String user_name = params[1];
                String password = params[2];
                Log.d("Response",user_name+password);
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
                jsonObject1.put("Firstname", fname);
                jsonObject1.put("Lastname", sname);
                jsonObject1.put("Username", uname);
                jsonObject1.put("Email", uemail);
                jsonObject1.put("Password", upassword);
                String jsonToString = jsonObject1.toString();
                String kalle = jsonToString;
                Log.d("Register",kalle);

                // Otetaan osoite minne halutaan yhdistää ja annetaan RequestPropertyt mitä ollaan lähettämässä.
                // Sen jälkeen lähetetään Jsonstringi eteenpäin.
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(jsonToString);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // Haetaan tuleva data
                InputStream is = httpURLConnection.getInputStream();
                // Muunnetaan tuleva data String muotoiseksi
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

            //alertDialog.setTitle("Register");
            return parsedString;

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        // alertDialog = new AlertDialog.Builder(context).create();
        // alertDialog.setTitle("Login");

    }

    @Override
    protected void onPostExecute(String parsedString) {
        // alertDialog.setMessage(parsedString);
       // alertDialog.show();
        delegate.processFinish(parsedString);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    ///// Kasataan tuleva inputstreami String muotoon
    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            Log.d("InputStreamToString", "tuli");
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