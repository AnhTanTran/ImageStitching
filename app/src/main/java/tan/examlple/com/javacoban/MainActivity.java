package tan.examlple.com.javacoban;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.features2d.FeatureDetector;

import tan.examlple.com.javacoban.dialog.DialogWaiting;
import tan.examlple.com.javacoban.imageprocess.ImageProcessThread;
import tan.examlple.com.javacoban.imageprocess.ImageProcessThread.ImageProcessingListener;

public class MainActivity extends AppCompatActivity  implements ImageProcessingListener {

    private static int PICK_IMAGE_1 = 1;
    private static int PICK_IMAGE_2 = 2;
    DialogWaiting dialogWaiting;
    Button btnStich;
    ImageView imv1;
    ImageView imv2;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    //TODO: openCV code goes there
                    new ImageProcessThread(MainActivity.this).execute(imv1,imv2);
                    Log.i("OpenCV", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapViews();
        initDailogWaiting();
        setOnClick();

    }
    private void mapViews(){
        imv1 = findViewById(R.id.imvFirstImage);
        imv2 = findViewById(R.id.imvSecondImage);
        btnStich = findViewById(R.id.btnStitch);
    }
    private void setOnClick(){
        btnStich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!OpenCVLoader.initDebug()) {
                    Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
                    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, MainActivity.this, mLoaderCallback);
                } else {
                    Log.d("OpenCV", "OpenCV library found inside package. Using it!");
                    mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
                }
            }
        });
        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery(PICK_IMAGE_1);
            }
        });
        imv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery(PICK_IMAGE_2);
            }
        });
    }

    @Override
    public void setUIAfterRun() {
        //TODO: open dialog
        dialogWaiting.show();
    }

    @Override
    public void setUIBeforeRun() {
        //TODO: turn of dialog
        dialogWaiting.dismiss();
    }
    private void initDailogWaiting(){
        dialogWaiting = new DialogWaiting(this);
    }
    private void chooseImageFromGallery(int possition){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int targetImageCode = PICK_IMAGE_1;
        if(possition==2) targetImageCode = PICK_IMAGE_2;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), targetImageCode);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode==RESULT_OK){
            Bitmap bitmap = data.getExtras().getParcelable("data");
            if (requestCode == PICK_IMAGE_1) {
                imv1.setImageBitmap(bitmap);
            }
            if (requestCode == PICK_IMAGE_2) {
                imv2.setImageBitmap(bitmap);
            }
        }
        else {
            Toast.makeText(this,"Choose image fail!!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } /*else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            //mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }*/
    }
}
