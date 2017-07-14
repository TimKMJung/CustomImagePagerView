package android.ssoim.com.customimagepagerview.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.ssoim.com.customimagepagerview.R;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by renewinspirit on 9/5/16.
 */
public class CustomViewPagerAdapter extends PagerAdapter {

//private FirebaseAnalytics mFirebaseAnalytics;

    private Context mContext;
    private int[] mResources;

    private boolean oneFlag, twoFlag, thrFlag, frFlag, fvFlag, sxFlag;

    public CustomViewPagerAdapter(Context mContext, int[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

        ImageView imgView = (ImageView) itemView.findViewById(R.id.bg_org_img);


        Bitmap bmp = null;

        switch(position) {
            case 0:
                // ROUNDED BMP
                bmp = BitmapFactory.decodeResource(mContext.getResources(), mResources[0]);
                Bitmap roundedBmp = getRoundedBitmap(bmp, 20);

                Drawable roundedBmPD = new BitmapDrawable(roundedBmp);
                imgView.setBackground(roundedBmPD);
                

                container.addView(itemView);

                break;


            case 1 :
                // ROUNDED BMP
                bmp = BitmapFactory.decodeResource(mContext.getResources(), mResources[1]);
                Bitmap roundedBmp1 = getRoundedBitmap(bmp, 20);

                Drawable roundedBmPD1 = new BitmapDrawable(roundedBmp1);
                imgView.setBackground(roundedBmPD1);

                container.addView(itemView);

                break;

            case 2 :
                // ROUNDED BMP
                bmp = BitmapFactory.decodeResource(mContext.getResources(), mResources[2]);
                Bitmap roundedBmp2 = getRoundedBitmap(bmp, 20);

                Drawable roundedBmPD2 = new BitmapDrawable(roundedBmp2);
                imgView.setBackground(roundedBmPD2);

                container.addView(itemView);


                break;

            case 3 :
                // ROUNDED BMP
                bmp = BitmapFactory.decodeResource(mContext.getResources(), mResources[3]);
                Bitmap roundedBmp3 = getRoundedBitmap(bmp, 20);

                Drawable roundedBmPD3 = new BitmapDrawable(roundedBmp3);
                imgView.setBackground(roundedBmPD3);

                container.addView(itemView);


                break;

            case 4 :
                // ROUNDED BMP
                bmp = BitmapFactory.decodeResource(mContext.getResources(), mResources[4]);
                Bitmap roundedBmp4 = getRoundedBitmap(bmp, 20);

                Drawable roundedBmPD4 = new BitmapDrawable(roundedBmp4);
                imgView.setBackground(roundedBmPD4);

                container.addView(itemView);

                break;


            case 5:
                // ROUNDED BMP
                bmp = BitmapFactory.decodeResource(mContext.getResources(), mResources[5]);
                Bitmap roundedBmp5 = getRoundedBitmap(bmp, 20);

                Drawable roundedBmPD5 = new BitmapDrawable(roundedBmp5);
                imgView.setBackground(roundedBmPD5);

                container.addView(itemView);

                break;

        }



        return itemView;
    }


    /*
     *  MAKE ROUNDED BITMAP
     *  @params bitmap, radius
     *
     */
    private Bitmap getRoundedBitmap(Bitmap srcBitmap, int cornerRadius) {
        Bitmap dstBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth(), // Width
                srcBitmap.getHeight(), // Height

                Bitmap.Config.ARGB_8888 // Config
        );

        Canvas canvas = new Canvas(dstBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Rect rect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        srcBitmap.recycle();

        return dstBitmap;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}


