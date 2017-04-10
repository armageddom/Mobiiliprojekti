package pine.app;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Newpost extends AppCompatActivity implements AsyncResponse {
    EditText title, description;
    Bitmap photo;
    String picturePath;
    CheckBox cFood, cDrink;
    private Uri fileUri;
    String ba1;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        title = (EditText)findViewById(R.id.eTitle);
        description = (EditText)findViewById(R.id.eDescription);
        cFood = (CheckBox)findViewById(R.id.cfood);
        cDrink = (CheckBox)findViewById(R.id.cDrink);
        if (shouldAskPermissions())
        {
            askPermissions();
        }
    }

    public void AddPicture(View v)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
       // photoPickerIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
        Log.d("Pic","Startactivity");
        startActivityForResult(photoPickerIntent, 1);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.d("IF","data is: "+data.getExtras());
            if (resultCode == Activity.RESULT_OK && requestCode == 1)
            {

                Uri selectedImage = data.getData();
                Log.d("Image","selected image done!"+data.getData());
                photo = null;
                try
                {
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    Log.d("Photo","Onnnistui");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


                Log.d("Image","Getting image url");

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                Log.d("PicturePath is:",""+picturePath);
                cursor.close();
                Log.d("Image","Got image url");

                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                Log.d("Image","Image rdy");
                ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
                imageView.setImageBitmap(photo);


            }
    }


    public void SendPost(View v)
    {

        String type = "";
        if(cFood.isChecked() && !cDrink.isChecked())
        {
            type = "NewFood";
        }
        if(cDrink.isChecked()&& !cFood.isChecked())
        {
            type = "NewDrink";
        }
        Log.d("SendPost","Alotus");
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        Log.d("SendPost","DecodeFileLöydetty");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Log.d("SendPost","ByteArrayOutputStrean aloitettu");
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        String encodedImage =Base64.encodeToString(ba,Base64.DEFAULT);

        String ntitle = title.getText().toString();
        String ndescription= description.getText().toString();


            if(type.contains("New"))
            {
                Log.d("IF","Tuli iffiin");
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.delegate = this;
                backgroundWorker.execute(type, ntitle, ndescription, encodedImage);
            }
            else
                {
                Toast.makeText(getBaseContext(), "Select food or drink category!", Toast.LENGTH_SHORT).show();
            }
                title.setText("");
        description.setText("");

    }

    @Override
    public void processFinish(String output){
        Log.d("ProcessFinished",output);

        if(output.contains("ok"))
        {
            Toast.makeText(getBaseContext(), "NewPost onnistui!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ScrollingActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getBaseContext(),"NewPost epäonnistui!",Toast.LENGTH_SHORT).show();
        }
    }
}
