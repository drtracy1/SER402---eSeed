package ser402team.weallcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by CrystalGutierrez on 2/3/2016.
 * Modified by DanielTracy
 *
 * @update Kristel Basra
 * username now accessed to this page
 */
public class MainPageActivity extends AppCompatActivity {

    public static final String MY_USERNAME = "ser402team.weallcode.MY_USERNAME";
    public static String myUsername = "";
    public static String absolute_path = "";
    public static final String ABS_PATH_BEGIN = "/data/data/";
    public static final String ABS_PATH_END = "/app_imageDir";

    private static Bitmap bmpFB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ImageView view = (ImageView)findViewById(R.id.playButton);

        //get username from LoginActivity
        // NOTE: **** if pushed back button from another page, then issues may arise ****
        Bundle bund = getIntent().getExtras();
        myUsername = bund.getString(MY_USERNAME);

        TextView welcomeMsg = (TextView) findViewById(R.id.mainPageUsernameView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/CHERI___.TTF");
        welcomeMsg.setTypeface(custom_font);
        String welcomeUser = "Welcome, "+myUsername;
        welcomeMsg.setText(welcomeUser);

        //If bytearray translated bitmap image of facebook profile pic is passed in the intent, then get that bitmap and translate it back into usuable .bmp form
        /*
        if (getIntent().getByteArrayExtra(("imgFB"))!=null){
            byte[] byteArray = getIntent().getByteArrayExtra("imgFB");
            bmpFB = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            view.setImageBitmap(bmpFB);
            android.util.Log.w(getClass().getSimpleName(), "fromBit: onCreate()");
        }
        */


        absolute_path = ABS_PATH_BEGIN + getApplicationContext().getPackageName() + ABS_PATH_END;
        loadImageFromStorage();

        //Changes the current activity to the Question page
        ImageView playButton = (ImageView) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, QuestionActivity.class);
                intent.putExtra(MY_USERNAME, myUsername);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void logOut(View v) {
        Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToAboutUs(View v){
        Intent intent = new Intent(MainPageActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void goToAvatar(View v){
        Intent intent = new Intent(MainPageActivity.this, AvatarMenuActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
    }

    public void goToSettings(View v){
        Intent intent = new Intent(MainPageActivity.this, SettingsActivity.class);
        intent.putExtra(MY_USERNAME, myUsername);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    protected void loadImageFromStorage() {
        try {
            File file = new File(absolute_path, myUsername + ".jpg");
            if(file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                ImageView imageView = (ImageView) findViewById(R.id.avatarImageView);
                imageView.setImageBitmap(bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
