package info.devexchanges.lockscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id._switch)
    Switch _switch;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        _switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent=new Intent(MainActivity.this,LockActivity.class);
                    SharedPreferences sharedPreference=getSharedPreferences("SaveData",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreference.edit();
                    editor.putString("key","key");
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}