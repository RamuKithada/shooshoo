package com.android.shooshoo.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FeedCommentsAdapter;
import com.android.shooshoo.models.Comment;
import com.android.shooshoo.presenters.FeedCommentsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.views.FeedCommentsView;

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
    ImageView iv_back, iv_send_cmnt;
    EditText edt_comment;
    TextView total_comments, reply_for;
//    List<Comment> comments;
    FeedCommentsPresenter presenter;
    ConnectionDetector connectionDetector;
    LinearLayoutManager linearLayoutManager;
    boolean isComment =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_comments);
        commentList=findViewById(R.id.comment_list);
        iv_back=findViewById(R.id.iv_back);
        iv_send_cmnt =findViewById(R.id.iv_send_msg);
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
        if (currentPage <= TOTAL_PAGES) {
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
            case R.id.iv_send_msg:

                if(edt_comment.getText().toString().length()>0) {
                    if(connectionDetector.isConnectingToInternet()) {
                        if(isComment)
                                presenter.postComment(getIntent().getStringExtra("feedId"),userSession.getUserId(),edt_comment.getText().toString());
                         else {
                             if(comment!=null){
                                 presenter.postReply(comment.getPostId(),userSession.getUserId(),comment.getCommentId(),edt_comment.getText().toString());
                             }
                        }
                    }else {
                        edt_comment.setError("comment here");
                        edt_comment.clearFocus();
                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;

        }

    }

    @Override
    public void onCommentPosted(int status, String msg) {
     if(status==1){
         edt_comment.setText(null);
         edt_comment.clearFocus();
         TOTAL_PAGES++;
         loadNextPage();
     }
    }

    @Override
    public void onReplyPosted(int status, String msg) {
        if(status==1){
            edt_comment.setHint("Type your comment here");
            isComment =true;
            reply_for.setText("");
            reply_for.setVisibility(View.GONE);
            edt_comment.setText(null);
        }
    }

    @Override
    public void onAllComments(List<Comment> comments,int totalPages) {
        TOTAL_PAGES=totalPages;
        feedCommentsAdapter.addAll(comments);
        currentPage=feedCommentsAdapter.getItemCount();
        total_comments.setText("All Comments("+totalPages+")");
    }

    Comment comment;
    @Override
    public void onReply(Comment comment) {
        this.comment=comment;
        isComment =false;
        reply_for.setVisibility(View.VISIBLE);
        reply_for.setText("Reply @ "+comment.getComment());
        edt_comment.setHint("Type your reply here");

    }

    @Override
    public void onReport(Comment comment) {

    }

    @Override
    public void onBackPressed() {
        if(isComment)
        super.onBackPressed();
        else {
            edt_comment.setHint("Type your comment here");
            edt_comment.setText(null);
            isComment =true;
            reply_for.setText("");
            reply_for.setVisibility(View.GONE);
        }


    }
}
