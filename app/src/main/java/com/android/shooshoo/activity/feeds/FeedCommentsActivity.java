package com.android.shooshoo.activity.feeds;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.adapter.FeedCommentsAdapter;
import com.android.shooshoo.models.Comment;
import com.android.shooshoo.models.CommentReply;
import com.android.shooshoo.presenters.FeedCommentsPresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.views.FeedCommentsView;
import com.squareup.picasso.Picasso;

import java.util.List;
public class FeedCommentsActivity extends BaseActivity implements View.OnClickListener, FeedCommentsView, FeedCommentsAdapter.FeedCommentListener {
    /**{@link FeedCommentsActivity} is used show list of comment for a feed and provide to comment on the feed
     *
     */

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 3;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;
    RecyclerView commentList;
    FeedCommentsAdapter feedCommentsAdapter;
    ImageView iv_back,iv_profile_pic;
    EditText edt_comment;
    TextView total_comments, reply_for,iv_send_cmnt;
//    List<Comment> comments;
    FeedCommentsPresenter presenter;
    ConnectionDetector connectionDetector;
    LinearLayoutManager linearLayoutManager;
    /***
     *  This isComment is used to hold the comment or a reply click decision on ui.isComment true means it is a comment .isComment is false it is a reply for comment
     */
        boolean isComment =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_comments);
        setFinishOnTouchOutside(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// Full view fpr  the screen
        commentList=findViewById(R.id.comment_list);
        iv_back=findViewById(R.id.iv_back);
        iv_send_cmnt =findViewById(R.id.iv_send_msg);
        iv_profile_pic =findViewById(R.id.iv_profile_pic);
        reply_for =findViewById(R.id.reply_for);
        edt_comment =findViewById(R.id.edt_message);
        total_comments =findViewById(R.id.total_comments);
        iv_send_cmnt.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        feedCommentsAdapter=new FeedCommentsAdapter(this,this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        commentList.setLayoutManager(linearLayoutManager);
        commentList.setItemAnimator(new DefaultItemAnimator());
        commentList.setAdapter(feedCommentsAdapter);
        presenter=new FeedCommentsPresenter();
        presenter.attachView(this);
        if(userSession.getUserInfo()!=null)
        Picasso.with(this).load(ApiUrls.PROFILE_IMAGE_URL+userSession.getUserInfo().getImage()).noPlaceholder().into(iv_profile_pic);
        commentList.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() { return TOTAL_PAGES; }

            @Override
            public boolean isLastPage() { return isLastPage; }

            @Override
            public boolean isLoading() { return isLoading; }
        });
        connectionDetector=new ConnectionDetector(this);
        if(connectionDetector.isConnectingToInternet()){
            presenter.getComments(getIntent().getStringExtra("feedId"),"8","0");
        }
    }

    private void loadNextPage() {
        if (currentPage < TOTAL_PAGES) {
            if (connectionDetector.isConnectingToInternet())
                presenter.getComments(getIntent().getStringExtra("feedId"), "8", "" + currentPage);
                feedCommentsAdapter.addLoadingFooter();
        }
        else
            isLastPage = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /**
             *  send icon action performed
             */
            case R.id.iv_send_msg:

                if(edt_comment.getText().toString().length()>0) {
                    if(connectionDetector.isConnectingToInternet()) {
                        if(isComment)
                                presenter.postComment(getIntent().getStringExtra("feedId"),userSession.getUserId(),edt_comment.getText().toString(),getIntent().getStringExtra("type"));
                         else {
                             if(comment!=null){
                                 presenter.postReply(comment.getPostId(),userSession.getUserId(),comment.getCommentId(),edt_comment.getText().toString(),getIntent().getStringExtra("type"));
                                 CommentReply  commentReply=new CommentReply();
                                 commentReply.setComment(edt_comment.getText().toString());
                                 commentReply.setCreatedOn(ApiUrls.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                                 commentReply.setUserId(userSession.getUserInfo().getUserId());
                                 commentReply.setImage(userSession.getUserInfo().getImage());
                                 commentReply.setPostId(comment.getPostId());
                                 comment.getCommentReply().add(0,commentReply);
                             }
                        }
                    }else {
                        edt_comment.setError("comment here");
                        edt_comment.clearFocus();
                    }
                }
                break;
            case R.id.iv_back:
                /**
                 *  back icon action performed
                 */
                finish();
                break;

        }

    }

    /***
     * After comment is posted  successfully we update the input box and load the comment to the list
     * @param status service response status
     * @param msg status message
     */

    @Override
    public void onCommentPosted(int status, String msg) {
     if(status==1){
         edt_comment.setText(null);
         edt_comment.clearFocus();
         TOTAL_PAGES++;
         loadNextPage();
     }
    }


    /***
     * Reply  service executed successfully we update the input box
     * @param status service response status
     * @param msg status message
     */
    @Override
    public void onReplyPosted(int status, String msg) {
        if(status==1){
            edt_comment.setHint("Type your comment here");
            isComment =true;
            reply_for.setText("");
            reply_for.setVisibility(View.GONE);
            edt_comment.setText(null);
            feedCommentsAdapter.notifyDataSetChanged();
            comment=null;
        }
    }

    /**
     * Here we get the all comments from server
     * @param comments next list of comments
     * @param totalPages count of the comments
     */
    @Override
    public void onAllComments(List<Comment> comments,int totalPages) {
        TOTAL_PAGES=totalPages;
        feedCommentsAdapter.addAll(comments);
        currentPage=feedCommentsAdapter.getItemCount();
//        total_comments.setText("All Comments("+totalPages+")");
        isLoading=false;
        feedCommentsAdapter.removeLoadingFooter();
//        feedCommentsAdapter.removeLoadingFooter();
    }

    Comment comment;/**
     used to store comment for reply
     @param comment is a comment to witch user want to reply .
     */
    @Override
    public void onReply(Comment comment) {
        this.comment=comment;
        isComment =false;
        reply_for.setVisibility(View.VISIBLE);
        reply_for.setText("Reply @ "+comment.getComment());
        edt_comment.setHint("Type your reply here");
    }

    /** To click action for the like symbol of a comment
     *
     * @param comment liked comment
     */
    @Override
    public void onLike(Comment comment) {

    }

    @Override
    public void onBackPressed() {
        /**
         *  if user is on comments action then we close the screen
         */
        if(isComment)
         super.onBackPressed();
        else {
            /**
             *  if user on reply for comments first we move him to comment action
             */
            edt_comment.setHint("Type your comment here");
            edt_comment.setText(null);
            isComment =true;
            reply_for.setText("");
            reply_for.setVisibility(View.GONE);
        }


    }
}
