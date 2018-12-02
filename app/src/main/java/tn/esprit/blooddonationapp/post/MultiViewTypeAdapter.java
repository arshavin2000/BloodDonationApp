package tn.esprit.blooddonationapp.post;
import android.content.Context;
import android.graphics.Bitmap;
import android.icu.util.DateInterval;
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
import tn.esprit.blooddonationapp.model.Receiver;
import tn.esprit.blooddonationapp.util.Util;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.jaiselrahman.filepicker.utils.TimeUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList dataSet;
    Context mContext;
    int total_types;
    MediaPlayer mPlayer;
    private boolean fabStateVolume = false;
    private int type;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txt_position;
        TextView txt_number;
        TextView txt_time;


       // CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.txt_position = (TextView) itemView.findViewById(R.id.txt_postion);
            this.txt_number = (TextView) itemView.findViewById(R.id.txt_number);
            this.txt_time = (TextView) itemView.findViewById(R.id.txt_time);
          //  this.cardView = (CardView) itemView.findViewById(R.id.card_view);
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
        this.type=1;
    }
    public MultiViewTypeAdapter(ArrayList<Receiver>data, Context context,int type) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.type=type;
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

        switch (type) {
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



            switch (type) {
                case Post.TEXT_TYPE:
                    Receiver receiver = (Receiver) dataSet.get(listPosition);

                    ((TextTypeViewHolder) holder).txt_position.setText(receiver.getPosition());
                    ((TextTypeViewHolder) holder).txt_number.setText(receiver.getNumber());
                    ((TextTypeViewHolder) holder).txt_time.setText(receiver.getTime());
                    break;
                case Post.IMAGE_TYPE:
                    Post object = (Post) dataSet.get(listPosition);


                   AndroidNetworking.get("http://192.168.1.11:3000/static/images/"+object.getPostImage())
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
                                    Log.e("BITMAP",error.getErrorBody());
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


            }
        }

    public static String compareTwoTimeStamps(Timestamp currentTime, Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();
        long duration = milliseconds2 - milliseconds1;


        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        StringBuilder durationBuilder = new StringBuilder();

        if (hours > 0L) {
            durationBuilder.append(hours).append(" h ");
        }

        if (minutes < 10L) {
            durationBuilder.append('0');
        }

        durationBuilder.append(minutes).append(" m ");
        if (seconds < 10L) {
            durationBuilder.append('0');
        }

        durationBuilder.append(seconds);
        return durationBuilder.toString();
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


/*
   / ((AudioTypeViewHolder) holder).txtType.setText(object.getPostText());

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
* */


}