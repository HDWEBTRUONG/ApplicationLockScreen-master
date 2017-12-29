package info.devexchanges.lockscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockActivity extends AppCompatActivity {

    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;

    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4})
    List<ImageView> dots;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    private static final String TRUE_CODE = "2869";
    private static final int MAX_LENGHT = 4;
    private String codeString = "";
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreference;
    SharedPreferences sharedpreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
        sharedPreferences=getSharedPreferences("SavePass",MODE_PRIVATE);
        sharedPreference=getSharedPreferences("Save",MODE_PRIVATE);
        sharedpreference=getSharedPreferences("SaveData",MODE_PRIVATE);
        String key=sharedpreference.getString("key","");
        if(key!=null&&!key.isEmpty()){
            btnDelete.setVisibility(View.VISIBLE);
            Intent intent=new Intent(LockActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_clear)
    public void onClear() {
        if (codeString.length() > 0) {
            codeString = removeLastChar(codeString);
            setDotImagesState();
        }
    }
    @OnClick(R.id.btnDelete)
    public void delete(){
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putBoolean("key",true);
        editor.commit();
        Intent intent=new Intent(LockActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }


    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9})
    public void onClick(Button button) {
        getStringCode(button.getId());
        if (codeString.length() == MAX_LENGHT) {
            String pass=sharedPreferences.getString("pass","");
            if(pass.equals("")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pass", codeString);
                editor.commit();
                Intent intent=new Intent(LockActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                if(pass.equals(codeString)){
                    Intent intent=new Intent(LockActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    shakeAnimation();
                }
            }
        } else if (codeString.length() > MAX_LENGHT){
            codeString = "";
            getStringCode(button.getId());
        }
        setDotImagesState();
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        findViewById(R.id.dot_layout).startAnimation(shake);
        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
    }

    private void getStringCode(int buttonId) {
        switch (buttonId) {
            case R.id.btn0:
                codeString += "0";
                break;
            case R.id.btn1:
                codeString += "1";
                break;
            case R.id.btn2:
                codeString += "2";
                break;
            case R.id.btn3:
                codeString += "3";
                break;
            case R.id.btn4:
                codeString += "4";
                break;
            case R.id.btn5:
                codeString += "5";
                break;
            case R.id.btn6:
                codeString += "6";
                break;
            case R.id.btn7:
                codeString += "7";
                break;
            case R.id.btn8:
                codeString += "8";
                break;
            case R.id.btn9:
                codeString += "9";
                break;
            default:
                break;
        }
    }

    private void setDotImagesState() {
        for (int i = 0; i < codeString.length(); i++) {
            dots.get(i).setImageResource(R.drawable.dot_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setImageResource(R.drawable.dot_disable);
            }
        }
    }

    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

}
