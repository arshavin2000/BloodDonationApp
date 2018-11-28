package tn.esprit.blooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter {

    static ArrayList<String> galleryImageList;
    private Context context;

    public ImageAdapter(Context context, int resource, ArrayList<String> images) {
        super(context, resource, images);
        galleryImageList = images;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(115, 115));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(loadThumbnailImage(galleryImageList.get(position)));
        return imageView;
    }

    class ViewHolder {
        private ImageView IMAGE;
    }

    protected Bitmap loadThumbnailImage(String url ) {
        // Get original image ID
        int originalImageId = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));

        // Get (or create upon demand) the micro thumbnail for the original image.
        Uri ur = Uri.parse(url);
        context.grantUriPermission("", ur,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),
                originalImageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
    }
}










