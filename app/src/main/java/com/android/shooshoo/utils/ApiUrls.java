package com.android.shooshoo.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Api call related constants i.e, end points of the urls
 *
 *
 */
public class ApiUrls {
    public static final String BASE_URL="http://165.22.94.168/api/service/";//http://testingmadesimple.org/shooshoo/api/service/";
    public static  final String DEVICE_TYPE="android";
    public static  final String DEVICE_TOKEN="abcdefghijklmn";
    public static final String IMAGE_URL = "http://165.22.94.168/uploads/";
    public static final String SPONSOR_BANNER_IMAGE_URL="http://165.22.94.168/uploads/sponsors/banners/";


    /**
     * Validating the String object whether is empty or not
     * @param s
     * @return true if s has value
     */
   public static boolean validateString(String s){
         if(s==null)
               return false;
         if(s.trim().length()==0)
               return false;

         return true;
   }
    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {


            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    public  synchronized static String getDurationTimeStamp(String newsdatetime) {
        String timeDifference = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");

        Date newsdate = null;
        try {
            newsdate = sdf.parse(newsdatetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(newsdate);
        int ndd=cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(currentDate);
        int cdd=cal.get(Calendar.DAY_OF_MONTH);

        if(ndd!=cdd)
            timeDifference=""+ndd+" "+monthFormat.format(newsdate);
        else {
            long duration = currentDate.getTime()-newsdate.getTime();
            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
            if(diffInHours>=1)
                timeDifference = timeformat.format(newsdate);
            else if(diffInMinutes>=1)
                timeDifference=""+diffInMinutes+"M "+"ago";
            else
                timeDifference=""+diffInSeconds+"S "+"ago";
        }
        return timeDifference;
    }

}
