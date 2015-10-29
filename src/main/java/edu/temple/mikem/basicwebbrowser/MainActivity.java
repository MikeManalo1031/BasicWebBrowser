package edu.temple.mikem.basicwebbrowser;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity{

    FrameLayout webFragment;
    ArrayList<Fragment> fragmentArrayList;
    int fragCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment wv = WebFragment.newInstance();
        fragCount = 0;
        webFragment = (FrameLayout) findViewById(R.id.webViewFragment);
        fragmentArrayList = new ArrayList<>();
        loadFragment(R.id.webViewFragment, wv, false);
        fragmentArrayList.add(wv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case R.id.action_new:
                Fragment wv = WebFragment.newInstance();
                fragmentArrayList.add(wv);
                loadFragment(R.id.webViewFragment, wv, false);
                fragCount++;
                return true;

            case R.id.action_next:
                if(fragCount == (fragmentArrayList.size()-1)) {
                    Toast.makeText(this, getString(R.string.last),
                            Toast.LENGTH_SHORT)
                            .show();
                }else{
                    loadFragment(R.id.webViewFragment,fragmentArrayList.get(fragCount+1),false);
                    fragCount++;
                }
                return true;

            case R.id.action_previous:
                if(fragCount == 0) {
                    Toast.makeText(this, getString(R.string.first),
                            Toast.LENGTH_SHORT)
                            .show();
                }else{
                    loadFragment(R.id.webViewFragment,fragmentArrayList.get(fragCount-1),false);
                    fragCount--;

                }

        }

        return super.onOptionsItemSelected(item);
    }


    public void loadFragment(int paneId, Fragment fragment , boolean backStackPosition){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(paneId,fragment);

        if(backStackPosition){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

}
