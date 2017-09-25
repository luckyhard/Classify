package haonan.classify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haonan.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_login, new LoginFragment())
                    .commit();
        }
    }
}
