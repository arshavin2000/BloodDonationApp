package tn.esprit.blooddonationapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import tn.esprit.blooddonationapp.model.Post;

public class PostAdapter extends ArrayAdapter<Post> implements View.OnClickListener{

    private ArrayList<Post> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView postImage;
        TextView postText;
        TextView username;
        TextView timePost;
        TextView numberLikes;
        TextView NumberComments;
    }

    public PostAdapter(ArrayList<Post> data, Context context) {
        super(context, R.layout.card_post, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Post dataModel=(Post)object;

       /** switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }**/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Post dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_post, parent, false);
            viewHolder.postImage = (ImageView) convertView.findViewById(R.id.card_post_img_profile);
            viewHolder.username = (TextView) convertView.findViewById(R.id.card_post_user_name);
            viewHolder.postText = (TextView) convertView.findViewById(R.id.card_post_text_post);
            viewHolder.timePost = (TextView) convertView.findViewById(R.id.card_post_time);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

       // Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

//        viewHolder.postImage.setBackground(Resources.getSystem().getDrawable(R.drawable.img_post));
        viewHolder.postText.setText(dataModel.getPostText());
        viewHolder.timePost.setText(dataModel.getTimePost());
        //viewHolder.timePost.setOnClickListener(this);
        //viewHolder.numberLikes.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}