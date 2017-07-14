package android.ssoim.com.customimagepagerview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.ssoim.com.customimagepagerview.adapters.CustomViewPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomImagePagerView extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    protected View view;
    private ViewPager introPager;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private CustomViewPagerAdapter mAdapter;

    private ImageView bgImg;

    private DisplayMetrics metrics;
    private int[] mImageResources = {
            R.drawable.img_barcelona_org,
            R.drawable.img_cordova_org,
            R.drawable.img_dubruvnik_org,
            R.drawable.img_road_org,
            R.drawable.img_sunset_org
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_image_pager_view);

        init();
    }


    private void init() {

        bgImg = (ImageView) findViewById(R.id.org_bg_img);
        bgImg.setBackgroundResource(R.drawable.img_barcelona_org);
        BitmapDrawable drawable = (BitmapDrawable) bgImg.getBackground();
        BitmapDrawable dblurred = blurRenderer(drawable);
        bgImg.setBackground(dblurred);

        introPager = (ViewPager) findViewById(R.id.custom_purpose_pager);

        // SET INDICATOR
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int pageMargin = ((metrics.widthPixels / 8));
        introPager.setPageMargin(-pageMargin);

        // SET VIEWPAGER
        pager_indicator = (LinearLayout) findViewById(R.id.aim_pager_dots);
        mAdapter = new CustomViewPagerAdapter(this, mImageResources);
        introPager.setAdapter(mAdapter);
        introPager.setCurrentItem(0);
        introPager.setOnPageChangeListener(this);

        setUiPageViewController();
    }

    private void setUiPageViewController() {
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.unselected_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(15, 0, 15, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BitmapDrawable dblurred = null;
        BitmapDrawable drawable;

        switch (position) {
            case 0:

                bgImg.setBackgroundResource(R.drawable.img_barcelona_org);
                drawable = (BitmapDrawable) bgImg.getBackground();
                dblurred = blurRenderer(drawable);

                break;

            case 1 :

                bgImg.setBackgroundResource(R.drawable.img_cordova_org);
                drawable = (BitmapDrawable) bgImg.getBackground();
                dblurred = blurRenderer(drawable);

                break;

            case 2:
                bgImg.setBackgroundResource(R.drawable.img_dubruvnik_org);
                drawable = (BitmapDrawable) bgImg.getBackground();
                dblurred = blurRenderer(drawable);

                break;

            case 3 :
                bgImg.setBackgroundResource(R.drawable.img_road_org);
                drawable = (BitmapDrawable) bgImg.getBackground();
                dblurred = blurRenderer(drawable);

                break;

            case 4:
                bgImg.setBackgroundResource(R.drawable.img_sunset_org);
                drawable = (BitmapDrawable) bgImg.getBackground();
                dblurred = blurRenderer(drawable);

                break;

        }

        // SET BG IMG
        bgImg.setBackground(dblurred);

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.unselected_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*
     * BLUR RENDERER
     * @params bitmapdrawable
     */

    private BitmapDrawable blurRenderer(BitmapDrawable bmpD) {
        Bitmap bitmap = bmpD.getBitmap();


        Bitmap blurred = blurRenderScript(bitmap, 25);
        BitmapDrawable drawableBlurred = new BitmapDrawable(blurred);

        return drawableBlurred;
    }



    @SuppressLint("NewApi")
    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(this);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }


    // RGB TO ARGB WITH BITMAP
    /* @params bitmap
     *
     *
     */
    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //GET JPEG PIXELS,
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        // CREATE A BITMAP OF THE PROPER FORMAT
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        // SET RGB PIXELS
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());

        return result;
    }

}
