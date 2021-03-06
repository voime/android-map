package sk.gista.android.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

public class Utils {
	
	private static String TAG = Utils.class.getName();
	
	public static void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP",
				"7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		Log.d(TAG, sb.toString());
	}
	
	public static String httpGet(String urlstring) throws MalformedURLException, IOException {
		URL url = new URL(urlstring);
		URLConnection httpCon = url.openConnection();
		return readInputStream(httpCon.getInputStream());
	}
	
	public static String readFile(String filename) throws IOException {
		return readInputStream(new FileInputStream(filename));
	}
	
	public static String readInputStream(InputStream is) throws IOException {
		String line;
		String response = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(is));
		while ((line = input.readLine()) != null) {
			response += line;
		}
		input.close();
		return response;
	}
	
	public static final float distance(PointF p1, PointF p2) {
		return distance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static final float distance(float x1, float y1, float x2, float y2) {
		float dx = x1-x2;
		float dy = y1-y2;
		return (float) Math.sqrt(dx*dx + dy*dy);
	}
}
