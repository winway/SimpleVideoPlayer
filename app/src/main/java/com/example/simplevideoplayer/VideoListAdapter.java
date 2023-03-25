package com.example.simplevideoplayer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @PackageName: com.example.simplevideoplayer
 * @ClassName: VideoListAdapter
 * @Author: winwa
 * @Date: 2023/3/24 11:52
 * @Description:
 **/
public class VideoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<VideoBean.ItemListBean> mData;

    public VideoListAdapter(Context context, List<VideoBean.ItemListBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.lvitem_main_video, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        VideoBean.ItemListBean.DataBean dataBean = mData.get(i).getData();
        VideoBean.ItemListBean.DataBean.AuthorBean author = dataBean.getAuthor();
        holder.mAuthorTV.setText(author.getName());
        holder.mSignatureTV.setText(author.getDescription());
        String iconUrl = author.getIcon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(mContext).load(iconUrl).into(holder.mAvatarIV);
        } else {
            Picasso.with(mContext).load(R.mipmap.ic_launcher_round).into(holder.mAvatarIV);
        }

        VideoBean.ItemListBean.DataBean.ConsumptionBean consumption = dataBean.getConsumption();
        holder.mLikeTV.setText(consumption.getRealCollectionCount() + "");
        holder.mCommentTV.setText(consumption.getReplyCount() + "");

        holder.mJzvdStd.setUp(dataBean.getPlayUrl(), dataBean.getTitle(), Jzvd.SCREEN_NORMAL);

        String thumbUrl = dataBean.getCover().getFeed();
        Picasso.with(mContext).load(thumbUrl).into(holder.mJzvdStd.thumbImageView);

        holder.mJzvdStd.positionInList = i;

        return view;
    }

    class ViewHolder {
        JzvdStd mJzvdStd;
        ImageView mAvatarIV;
        TextView mAuthorTV;
        TextView mSignatureTV;
        TextView mLikeTV;
        TextView mCommentTV;

        public ViewHolder(View view) {
            mJzvdStd = view.findViewById(R.id.lvitem_main_video_jzvd);
            mAvatarIV = view.findViewById(R.id.lvitem_main_video_avatar_iv);
            mAuthorTV = view.findViewById(R.id.lvitem_main_video_author_tv);
            mSignatureTV = view.findViewById(R.id.lvitem_main_video_signature_tv);
            mLikeTV = view.findViewById(R.id.lvitem_main_video_like_tv);
            mCommentTV = view.findViewById(R.id.lvitem_main_video_comment_tv);
        }
    }
}
