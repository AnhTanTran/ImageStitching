package tan.examlple.com.javacoban.imageprocess;

import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageProcessThread extends AsyncTask<ImageView,ImageView,ImageView> {

    private ImageProcessingListener imageProcessingListener;
    public interface ImageProcessingListener
    {
         void setUIAfterRun();
         void setUIBeforeRun();
    }
    public ImageProcessThread(ImageProcessingListener imageProcessingListener){
        this.imageProcessingListener = imageProcessingListener;
    }
    @Override
    protected void onPreExecute() {
        imageProcessingListener.setUIBeforeRun();
    }
    @Override
    protected ImageView doInBackground(ImageView... imageViews) {
        //get two image in parameters
        ImageView imv1 = imageViews[0];
        ImageView imv2 = imageViews[1];
        ImageView result = ImageStitcher.getInstance().stitch(imv1,imv2);
        return result;
    }
    @Override
    protected void onPostExecute(ImageView result) {
        imageProcessingListener.setUIAfterRun();
    }
}
