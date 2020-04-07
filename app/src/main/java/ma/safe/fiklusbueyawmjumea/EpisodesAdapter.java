package ma.safe.fiklusbueyawmjumea;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.MyViewHolder> {
    private Context mContext;
    static String[] mImage;
    private String[] mTitle;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView;
        TextView txtView;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.imgView = (ImageView) itemView.findViewById(R.id.image_item);
            this.txtView = (TextView) itemView.findViewById(R.id.title_item);
        }


    }

    public EpisodesAdapter(Context mContext, String[] image, String[] title) {
        this.mContext = mContext;
        this.mImage = image;
        this.mTitle = title;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {

        holder.txtView.setText(mTitle[i]);
        //    holder.imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        int wallpaperId;

        if (i > mImage.length - 1) {
            wallpaperId = i - mImage.length;
        } else {
            wallpaperId = i;
        }
        Picasso.get().
                // load("http://wallserver.xyz/as-baru/singer/ariana-grande-baru//upload/"+Constant.wallpaper_id[i])
                        load(mContext.getResources().getString(R.string.wallpapercave) + VideoPlayerConfig.pics[wallpaperId] + ".jpg")
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(60, 60)
                .into(holder.imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Picasso", "fetch image success in first time.");
                        //gettoast("fetch image success in first time.");

                    }

                    @Override
                    public void onError(Exception e) {
                        //Try again online if cache failed
                        Log.v("Picasso", "Could not fetch image in first time...");
                        //   gettoast("Could not fetch image in first time..");

                        Picasso.get().
                                load(mContext.getResources().getString(R.string.wallpapercave) + VideoPlayerConfig.pics[wallpaperId] + ".jpg")
                                //       load("http://wallserver.xyz/as-baru/singer/ariana-grande-baru//upload/"+Constant.wallpaper_id[i])
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).error(R.drawable.fiklusbueyawmjumea)
                                .resize(60, 60)
                         .into(holder.imgView, new Callback() {
                        @Override
                        public void onSuccess () {
                            Log.v("Picasso", "fetch image success in try again.");
                            //    gettoast("fetch image success in try again.");

                        }

                        @Override
                        public void onError (Exception e){
                            Log.v("Picasso", "Could not fetch image again...");
                            //  gettoast("Could not fetch image again.");

                        }


                    });
                }


    });


}

    @Override
    public int getItemCount() {
        return mTitle.length;
    }


}
