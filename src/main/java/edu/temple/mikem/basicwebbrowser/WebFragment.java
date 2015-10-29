package edu.temple.mikem.basicwebbrowser;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class WebFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    Button searchButton;
    EditText addresssBar;
    WebView webview;
    Context c;
    Bundle webViewBundle;


    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);

        this.c = container.getContext();
        searchButton = (Button) v.findViewById(R.id.searchButton);
        addresssBar = (EditText) v.findViewById(R.id.addressBar);
        webview = (WebView) v.findViewById(R.id.webView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        if (checkConnectivity()) {
                            String url = addresssBar.getText().toString();

                            UrlDownloader downloadURL = new UrlDownloader(webview);
                            downloadURL.execute(url);
                        } else {
                            Toast.makeText(c, getString(R.string.no_connection),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                };
                t.start();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onPause()
    {
        super.onPause();

        webViewBundle = new Bundle();
        webview.saveState(webViewBundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (webViewBundle != null)
        {
            webview.restoreState(webViewBundle);
        }
    }

    private boolean checkConnectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
