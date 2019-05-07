package tan.examlple.com.javacoban.imageprocess;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.features2d.FeatureDetector;

public class ImageStitcher {
    private static ImageStitcher imageStitcher;

    public static ImageStitcher getInstance() {
        if (imageStitcher == null) imageStitcher = new ImageStitcher();
        return imageStitcher;
    }

    private ImageStitcher() {
    }

    public ImageView stitch(ImageView imv1, ImageView imv2) {
        //TODO: use opencv for image stitching right there
        //TODO: return new image that is result of stitching

        Log.d("TEST OPENCV", "stitch: SIFT called");
        return null;
    }
}
