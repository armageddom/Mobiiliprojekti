package pine.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class Newpost extends AppCompatActivity {
    EditText title, description;
    Bitmap photo;
    String picturePath;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        title = (EditText)findViewById(R.id.eTitle);
        description = (EditText)findViewById(R.id.eDescription);

    }

    public void AddPicture(View v)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        Log.d("Pic","Startactivity");
        startActivityForResult(photoPickerIntent, 1);

        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // startActivityForResult(intent, 1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            Log.d("IF","First IF succes");
            if (resultCode == Activity.RESULT_OK)
            Log.d("IF","Second IF succes");
            {
                Uri selectedImage = data.getData();
                Log.d("Image","SelectedImage Done!");
                photo = (Bitmap) data.getExtras().get("data");
                Log.d("Image","Getting image url");

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.d("Image","Got image url");

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Log.d("Image","Image rdy");
                ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
                imageView.setImageBitmap(photo);


               /* String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                //image_name_tv.setText(filePath);

                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png"))
                {
                    //FINE
                    Log.d("File Accepted","UPLOAD FILE"+filePath);
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Log.d("Photo inserted","ok");
                    ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
                    imageView.setImageBitmap(photo);


                } else {
                    //NOT IN REQUIRED FORMAT
                }*/
            }
    }
/*
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        //imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }
*/












    public void SendPost(View v)
    {


    }

}
