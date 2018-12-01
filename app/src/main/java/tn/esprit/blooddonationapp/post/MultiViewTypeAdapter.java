package tn.esprit.blooddonationapp.post;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Post;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<Post>dataSet;
    Context mContext;
    int total_types;
    MediaPlayer mPlayer;
    private boolean fabStateVolume = false;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.card_post_text_post);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        ImageView postImage;
        TextView postText;
        TextView username;
        TextView timePost;


        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.postImage = (ImageView) itemView.findViewById(R.id.img_post);
            this.username = (TextView) itemView.findViewById(R.id.txt_username);
            this.postText = (TextView) itemView.findViewById(R.id.card_post_text_post);
            this.timePost = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        FloatingActionButton fab;

        public AudioTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.card_post_text_post);
            this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }
    }

    public MultiViewTypeAdapter(ArrayList<Post>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Post.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);
            case Post.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                return new ImageTypeViewHolder(view);
            case Post.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_type, parent, false);
                return new AudioTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Post.TEXT_TYPE;
            case 1:
                return Post.IMAGE_TYPE;
            case 2:
                return Post.AUDIO_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Post object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case Post.TEXT_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.getPostText());

                    break;
                case Post.IMAGE_TYPE:


                   AndroidNetworking.get("http://192.168.1.31:3000/static/images/1223.png")
                            .setTag("imageRequestTag")
                            .setPriority(Priority.IMMEDIATE)
                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                            .build()
                            .getAsBitmap(new BitmapRequestListener() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    ((ImageTypeViewHolder) holder).postImage.setImageBitmap(bitmap);
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    Log.e("BITMAP",error.getCause().toString());
                                }
                            });

                    ((ImageTypeViewHolder) holder).postText.setText(object.getPostText());
                    ((ImageTypeViewHolder) holder).username.setText(object.getUsername().getFirstname() + " " + object.getUsername().getLastname());


                    String st = object.getTimePost().replace("T"," ").replace("Z"," ");
                    Timestamp oldTime = Timestamp.valueOf(st);
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    ((ImageTypeViewHolder) holder).timePost.setText(compareTwoTimeStamps(Timestamp.valueOf(currentTime),oldTime));


                    break;
                case Post.AUDIO_TYPE:

                    ((AudioTypeViewHolder) holder).txtType.setText(object.getPostText());

                    ((AudioTypeViewHolder) holder).fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (fabStateVolume) {
                                if (mPlayer.isPlaying()) {
                                    mPlayer.stop();

                                }
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.search);
                                fabStateVolume = false;

                            } else {
                              //mPlayer = MediaPlayer.create(mContext, R.raw.sound);
                                mPlayer.setLooping(true);
                                mPlayer.start();
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.search);
                                fabStateVolume = true;
                            }
                        }
                    });
                    break;
            }
        }
    }
    public static String compareTwoTimeStamps(Timestamp currentTime, Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays>0)
            return diffDays+" d";
        if ((diffDays==0)&& (diffHours>0) && diffMinutes>0)
            return diffHours+" H "+diffMinutes+" m";
        if (diffMinutes>0)
            return diffMinutes+" m";

        return diffSeconds+" s";
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
