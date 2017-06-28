package com.ashutoshbarthwal.couchtime.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashutoshbarthwal.couchtime.R;
import com.ashutoshbarthwal.couchtime.models.videos.Video;
import com.ashutoshbarthwal.couchtime.utils.ImageLoadingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Ashutosh on 04-03-2017.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    public interface Callbacks {
        public void onVideoClick(Video movieVideos);
    }

    private Callbacks mCallbacks;
    private Context context;
    private List<Video> mFeedList;

    public VideosAdapter(List<Video> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = View.inflate(parent.getContext(), R.layout.item_video, null);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, int position) {
            final Video movieVideos = mFeedList.get(position);

            ImageLoadingUtils.load(holder.mVideoContainer, "http://img.youtube.com/vi/" + movieVideos.getKey() + "/0.jpg");
            holder.mVideoTitle.setText(movieVideos.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mCallbacks!=null) {
                        mCallbacks.onVideoClick(movieVideos);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mVideoContainer;
        private TextView mVideoTitle;

        public VideosViewHolder(View itemView) {
            super(itemView);
            mVideoContainer = (SimpleDraweeView) itemView.findViewById(R.id.videoThumb);
            mVideoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
        }
    }

}
