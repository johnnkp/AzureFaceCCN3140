package hkcc.ccn3140.azureface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

import java.io.IOException;
/* import java.io.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.provider.*;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*; */

public class MainActivity extends AppCompatActivity {
    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;

    // Replace `<API endpoint>` with the Azure region associated with
    // your subscription key. For example,
    // apiEndpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0"
    private final String apiEndpoint = "<API endpoint>";
    // Replace `<Subscription Key>` with your subscription key.
    // For example, subscriptionKey = "0123456789abcdef0123456789ABCDEF"
    private final String subscriptionKey = "<Subscription Key>";
    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(
                        intent, "Select Picture"), PICK_IMAGE);
            }
        });

        detectionProgressDialog = new ProgressDialog(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap);

                // Comment out for tutorial
                // detectAndFrame(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
