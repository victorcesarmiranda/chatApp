package com.victor.chatapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.victor.chatapp.R;
import com.victor.chatapp.coneccaoservidor.Application;
import com.victor.chatapp.coneccaoservidor.ListaUsuarios;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        viewPager = findViewById(R.id.pager);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

//    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 0) {
//            super.onBackPressed();
//        } else {
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        Login fragLogin     = new Login();
        Contatos contatos   = new Contatos();
        Mensagens mensagens = new Mensagens();

        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
            //
            ListaUsuarios.getListaDeUsuarios().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(Application.getInstance().getCurrentFragmentPosition());
                        }
                    };
                    runOnUiThread(r);

                }
            });
        }

        @Override
        public Fragment createFragment(int position) {
            if(position == 0) {
                return fragLogin;
            }
            if((position == 1) && (viewPager.getCurrentItem() != 2)){
                return contatos;
            }
            if(position == 2) {
                return mensagens;
            }

            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}