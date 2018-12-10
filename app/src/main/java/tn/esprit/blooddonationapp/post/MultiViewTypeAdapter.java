package tn.esprit.blooddonationapp.post;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Post;
import tn.esprit.blooddonationapp.model.Request;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList dataSet;
    Context mContext;
    int total_types;
    MediaPlayer mPlayer;
    private boolean fabStateVolume = false;
    private int type;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView txt_position;
        TextView txt_number;
        TextView txt_time;
        ImageView img;
        Button call;





        // CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.txt_position = itemView.findViewById(R.id.txt_postion);
            this.txt_number = itemView.findViewById(R.id.txt_number);
            this.txt_time = itemView.findViewById(R.id.txt_time);
            this.call = itemView.findViewById(R.id.phone);
            this.img = itemView.findViewById(R.id.img);


            //  this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View v) {
            Log.d("ok", "onClick: ");
            Toast.makeText(v.getContext(),"qy bqshlq",Toast.LENGTH_LONG).show();

        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        ImageView postImage;
        ImageView img_profile;
        TextView postText;
        TextView username;
        TextView timePost;
        ImageButton comment ;


        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.postImage = itemView.findViewById(R.id.img_post);
            this.img_profile = itemView.findViewById(R.id.img_profile);
            this.username = itemView.findViewById(R.id.txt_username);
            this.postText = itemView.findViewById(R.id.card_post_text_post);
            this.timePost = itemView.findViewById(R.id.txt_time);

        }
    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        FloatingActionButton fab;

        public AudioTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = itemView.findViewById(R.id.card_post_text_post);
            this.fab = itemView.findViewById(R.id.fab);
        }
    }

    public MultiViewTypeAdapter(ArrayList<Post>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.type=1;
    }
    public MultiViewTypeAdapter(ArrayList<Request>data, Context context, int type) {
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


                    final Request receiver = (Request) dataSet.get(listPosition);

                    ((TextTypeViewHolder) holder).call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("nqqref", "onClick: ");

                        }
                    });

                    ((TextTypeViewHolder) holder).txt_position.setText(receiver.getPlace());
                    ((TextTypeViewHolder) holder).txt_number.setText(receiver.getDonor().getNumber());
                    Log.d("EWEU", "onBindViewHolder:  "+receiver.getBloodgroup() );
                    switch (receiver.getBloodgroup())
                    {
                        case  "A+":
                        ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.a_plus_selected);
                        break;
                        case  "A-":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.a_moins_selected);
                            break;
                        case  "B+":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.b_plus_selected);
                            break;
                        case  "B-":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.b_moins);
                            break;
                        case  "AB+":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.ab_plus_selected);
                            break;
                        case  "AB-":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.ab_moins_selected);
                            break;
                        case  "O+":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.o_plus_selected);
                            break;
                        case  "O-":
                            ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.o_moins_selected);
                            break;
                            default:
                                ((TextTypeViewHolder) holder).img.setBackgroundResource(R.drawable.o_moins_selected);
                                break;


                    }

                    ((TextTypeViewHolder) holder).txt_time.setText("33m");
                    break;
                case Post.IMAGE_TYPE:
                    Post object = (Post) dataSet.get(listPosition);


                   AndroidNetworking.get("http://196.203.252.226:9090/static/images/"+object.getPostImage())
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



                    Glide.with(mContext)
                            .load(object.getUser().getUrl())
                            .apply(RequestOptions.circleCropTransform())
                            .into(((ImageTypeViewHolder) holder).img_profile);

                    ((ImageTypeViewHolder) holder).postText.setText(object.getPostText());
                    ((ImageTypeViewHolder) holder).username.setText(object.getUser().getFirstname()+" "+object.getUser().getLastname());


                    String st = object.getTimePost().replace("T"," ").replace("Z"," ");
                    Timestamp oldTime = Timestamp.valueOf(st);
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    ((ImageTypeViewHolder) holder).timePost.setText(compareTwoTimeStamps(Timestamp.valueOf(currentTime),oldTime));




                    break;
                case Post.AUDIO_TYPE:


            }


        }

    public void addfragment(Fragment newFragment, Context context){

        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.add( newFragment ,"comments");
        transaction.setTransitionStyle(android.R.style.Theme_DeviceDefault_Dialog);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack("back_posts");
        transaction.commit();

    }




    public static String compareTwoTimeStamps(Timestamp currentTime, Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();
        long duration = milliseconds2 - milliseconds1;

        long days = TimeUnit.MILLISECONDS.toDays(duration);
        duration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        StringBuilder durationBuilder = new StringBuilder();


        System.out.println("DAYS"+days+"hours"+hours+"Minutes"+minutes);



        if (days > 0L) {

            return days +" d ";

        }
        if (hours > 10L ) {
            return hours +" h ";


        }

        if (minutes > 0L) {
            return minutes +" m ";


        }



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
