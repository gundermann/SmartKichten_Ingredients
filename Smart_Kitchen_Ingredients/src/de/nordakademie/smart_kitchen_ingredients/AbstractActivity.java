package de.nordakademie.smart_kitchen_ingredients;

/**
 * @ Kathrin Kurtz
 * 
 * Genutzte Plugins
 * 
 * Lint: http://tools.android.com/tips/lint 
 * Checkstyle: http://eclipse-cs.sf.net/update/
 * Classycle: http://classycleplugin.graf-tec.ch/update
 * CodePro: http://dl.google.com/eclipse/inst/codepro/latest/3.7
 * FindBugs: http://findbugs.cs.umd.edu/eclipse  
 * 
 * Genutzte Codequellen(siehe Android Private Libraries)
 * 
 * Apache:  commons-logging-1.1.3.jar http://commons.apache.org/proper/commons-logging/download_logging.cgi
 * Apache:  httpcore-nio-4.3.jar http://hc.apache.org/httpcomponents-asyncclient-dev/httpasyncclient/dependencies.html
 * Apache:  httpcore-4.3.jar http://hc.apache.org/downloads.cgi
 * Google:  mockito-all-1.9.5.jar https://code.google.com/p/mockito/downloads/detail?name=mockito-all-1.9.5.jar&can=2&q=
 * Apache:  httpcore-ab-4.3.jar http://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore-ab/4.3/
 * Apache:  commons-codec-1.6.jar http://commons.apache.org/proper/commons-codec/
 * Apache:  commons-cli-1.2.jar http://commons.apache.org/proper/commons-cli/download_cli.cgi
 * Google:  gson-2.2.4.jar https://code.google.com/p/google-gson/downloads/list
 * Apache:  httpmime-4.3.1.jar http://hc.apache.org/downloads.cgi
 * Apache:  httpclient-4.2.1.jar http://hc.apache.org/downloads.cgi
 * ZXing:	com.google.zxing.client.android https://code.google.com/p/zxing/downloads/list
 * 
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public abstract class AbstractActivity extends FragmentActivity {

	protected IngredientsApplication app;
	protected static String TAG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IngredientsApplication) getApplication();
		TAG = this.getClass().getSimpleName();
		Log.d("SmartKitchenActivity", this.getClass().getSimpleName());
	}

	protected void startNextActivity(Class<?> activityClass) {
		startActivity(new Intent(getApplicationContext(), activityClass));
	}

	protected void makeLongToast(int textId) {
		Toast.makeText(getApplicationContext(), getText(textId),
				Toast.LENGTH_SHORT).show();
	}

}