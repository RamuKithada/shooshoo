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
import android.text.format.DateUtils;
import android.util.Log;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.WEEK_IN_MILLIS;
import static android.text.format.DateUtils.YEAR_IN_MILLIS;

/**
 * Api call related constants i.e, end points of the urls
 *
 *
 */
public class ApiUrls {
    public static final String SPONSERS="sponsors";
    public static final String JACKPOTS="jackpots";
    public static final String PRIVATE="privates";
    public static final String ENTERED="entered";
    public static final String SAVED="saved";
    public static final String FINAL="finals";
    public static final String CREATED="created";
    public static final String BASE_URL="http://165.22.94.168/api/service/";//http://testingmadesimple.org/shooshoo/api/service/";
    public static  final String DEVICE_TYPE="android";
    public static  final String DEVICE_TOKEN="abcdefghijklmn";
    public static final String IMAGE_URL = "http://165.22.94.168/uploads/";
    public static final String SPONSOR_BANNER_IMAGE_URL="http://165.22.94.168/uploads/sponsors/banners/";
    public static final String JACKPOT_BANNER_IMAGE_URL="http://165.22.94.168/uploads/jackpots/banners/";
    public static final String SPONSOR_VIDEO_URL="http://165.22.94.168/uploads/sponsors/videos/";
    public static final String VIDEO_URL="http://165.22.94.168/uploads/";
    public static final String JACKPOT_VIDEO_URL="http://165.22.94.168/uploads/jackpots/videos/";
    public static final String SPONSOR_FEEDS_VIDEO_URL="http://165.22.94.168/uploads/feeds/";
    public static final String PROFILE_IMAGE_URL="http://165.22.94.168/uploads/profiles/";


    /**
     * Validating the String object whether is empty or not
     * @param string
     * @return true if s has value
     */
   public static boolean validateString(String string){
         if(string==null)
               return false;
         if(string.trim().length()==0)
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
       if(newsdatetime==null)
           return null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=simpleDateFormat.parse(newsdatetime);
            CharSequence relativeDate =
                    DateUtils.getRelativeTimeSpanString(date.getTime(),System.currentTimeMillis(),
                            0L,DateUtils.FORMAT_ABBREV_RELATIVE);
            String actcualDate=relativeDate.toString();
            String output=null;
            output=actcualDate;
            if(actcualDate.toLowerCase().contains("in")){
                relativeDate=actcualDate.replaceAll("In","")+" left";
            }

            return relativeDate.toString();
        }catch (ParseException e){
            e.printStackTrace();
            return newsdatetime;
        }

    }

    public synchronized static String getCurrentTime(String format){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        String output=simpleDateFormat.format(new Date());
       return output;
    }

    public  synchronized static String getDurationTimeStamp2(String newsdatetime) {
        if(newsdatetime==null)
            return null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=simpleDateFormat.parse(newsdatetime);
            CharSequence relativeDate =
                    DateUtils.getRelativeTimeSpanString(date.getTime(),System.currentTimeMillis(),
                            0L,DateUtils.FORMAT_ABBREV_RELATIVE);
            return relativeDate.toString();
        }catch (ParseException e){
            e.printStackTrace();
            return newsdatetime;
        }

    }
    public synchronized static String getRemaingTime(){
        String dateStart = "01/14/2012 09:29:58";
        String dateStop = "01/15/2012 10:31:48";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);




        } catch (Exception e) {
            e.printStackTrace();
        }
           return null;
    }


    public static String removeDecimals(String s) {
        return s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static String ChangeDateFormat(String date) {
        Log.i("date before",date);
        SimpleDateFormat inFormat=new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            Date date1=inFormat.parse(date);
            SimpleDateFormat outFormat= new SimpleDateFormat("yyyy-MM-dd");
            date=outFormat.format(date1);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Log.i("date after",date);

    return date;

    }
}
