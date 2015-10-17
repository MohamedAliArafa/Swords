package com.example.nezarsaleh.shareknitest.Arafa.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageUp extends AppCompatActivity {


    ImageView ivImage;
    Button btnSelectPhoto,btnUploadPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_up);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btnSelectPhoto = (Button) findViewById(R.id.btnSelectPhoto);
        btnUploadPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_image_up, menu);
//        return true;
//    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageUp.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), 1337);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                new background().execute(encoded);
                ivImage.setImageBitmap(thumbnail);
            } else if (requestCode == 1337) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                new background().execute(encoded);
                ivImage.setImageBitmap(bm);
            }
        }
    }

    private class background extends AsyncTask<String,Void,String> {
        String url = "http://www.sharekni-web.sdg.ae/_mobfiles/CLS_MobAccount.asmx/UploadImage";
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String param1 = "value1";
        String param2 = "jpg";

        SoapPrimitive response;

        @Override
        protected void onPostExecute(String s) {
//            Log.d("Response :",response.toString());
//            Toast.makeText(ImageUp.this, response.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
//            String SOAP_ACTION="http://MobAccount.org/UploadImage";
//            String METHOD_NAME = "UploadImage";
//            String NAMESPACE = "http://MobAccount.org/";
//            String URL = "http://www.sharekni-web.sdg.ae/_mobfiles/CLS_MobAccount.asmx";
//            try {
//                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//                request.addProperty("ImageContent", params[0]);
//                request.addProperty("ImageExtention", "jpg");
//                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//                envelope.dotNet = true;
//                envelope.setOutputSoapObject(request);
//                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//                androidHttpTransport.call(SOAP_ACTION, envelope);
//                response = (SoapPrimitive) envelope.getResponse();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            callSOAPWebService(params[0]);

            return null;
        }
    }

    private boolean callSOAPWebService(String data) {
        OutputStream out = null;
        int respCode = -1;
        boolean isSuccess = false;
        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL("http://www.sharekni-web.sdg.ae/_mobfiles/CLS_MobAccount.asmx");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            do {
                // httpURLConnection.setHostnameVerifier(DO_NOT_VERIFY);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "keep-alive");
                httpURLConnection.setRequestProperty("Content-Type", "text/xml");
                httpURLConnection.setRequestProperty("SendChunked", "True");
                httpURLConnection.setRequestProperty("UseCookieContainer", "True");
                HttpURLConnection.setFollowRedirects(false);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(true);
                httpURLConnection.setRequestProperty("Content-length", getReqData(data).length + "");
                httpURLConnection.setReadTimeout(10 * 1000);
                // httpURLConnection.setConnectTimeout(10 * 1000);
                httpURLConnection.connect();
                out = httpURLConnection.getOutputStream();
                if (out != null) {
                    out.write(getReqData(data));
                    out.flush();
                }
                if (httpURLConnection != null) {
                    respCode = httpURLConnection.getResponseCode();
                    Log.e("respCode", ":" + respCode);
                }
            } while (respCode == -1);

            // If it works fine
            if (respCode == 200) {
                try {
                    InputStream responce = httpURLConnection.getInputStream();
                    String str = convertStreamToString(responce);
                    System.out.println(".....data....." + new String(str));
                    // String str
                    // =Environment.getExternalStorageDirectory().getAbsolutePath()+"/sunilwebservice.txt";
                    // File f = new File(str);
                    //
                    // try{
                    // f.createNewFile();
                    // FileOutputStream fo = new FileOutputStream(f);
                    // fo.write(b);
                    // fo.close();
                    // }catch(Exception ex){
                    // ex.printStackTrace();
                    // }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                isSuccess = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out = null;
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }
        }
        return isSuccess;
    }

    public static String createSoapHeader() {
        String soapHeader;

        soapHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope "
                + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" + ">";
        return soapHeader;
    }

    public static byte[] getReqData(String data) {
        StringBuilder requestData = new StringBuilder();

        requestData.append(createSoapHeader());
        requestData.append("<soap:Body>" + "<UploadImage" + " xmlns=\"http://MobAccount.org/\">" + "<ImageContent>").append(data).append("</ImageContent>\n").append("<imageExtenstion>jpg</imageExtenstion>").append("</UploadImage> </soap:Body> </soap:Envelope>");
        Log.d("reqData: ",requestData.toString());
        return requestData.toString().trim().getBytes();
    }

    private static String convertStreamToString(InputStream is)
            throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

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
}
